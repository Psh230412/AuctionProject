package secondweek;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import dbutil.DBUtil;

public class DetailFrame extends JFrame {
	private Scheduler scheduler;
	private static Timer timer;
	private static JLabel lblImage;

	private static JLabel lblName;

	private static JLabel lblDetail;

	private static JLabel lblTime;

	private static JLabel lblPrice;
	private static JLabel lblPriceMin;

	private static DataBase data;

	private static JLabel lblisOwn;
	// 이미지, 제품이름, 상세설명, 남은시간, 가격

	public DetailFrame(DataBase data, int auctionNo) {

		this.data = data;
		timer = new Timer();

		JPanel pnl = new JPanel();

		lblImage = new JLabel("이미지");
//		lblImage.setBounds(168, 230, 94, 15);
		lblImage.setBounds(10, 10, 170, 170);

		for (int i = 0; i < ImageRetriever.llistForDetail.size(); i++) {
			if (ImageRetriever.llistForDetail.get(i).getProductno() == data.getProduct().getProductNo()) {

				ImageIcon imageIcon = ImageRetriever.llistForDetail.get(i).getImageicon();
				if (imageIcon != null) {
					lblImage.setIcon(iconSize(imageIcon));
				}
			}

		}

		lblName = new JLabel("제품명");
		lblName.setBounds(514, 150, 94, 15);
		lblDetail = new JLabel("상세설명");
		lblDetail.setBounds(162, 400, 226, 116);
		lblTime = new JLabel("00:00:00");
		lblTime.setBounds(514, 346, 94, 15);
		lblPrice = new JLabel("가격");
		lblPrice.setBounds(514, 278, 94, 15);
		lblPriceMin = new JLabel("최소입찰가 : 0원");
		lblPriceMin.setBounds(324, 478, 194, 35);
		JLabel lblMessage = new JLabel("입찰가격은 최소입찰가를 상회해야 합니다.");
		lblMessage.setBounds(380, 562, 318, 35);
		lblisOwn = new JLabel();
		lblisOwn.setBounds(200, 562 + 35 + 49, 400, 35);

		JTextField priceTF = new JTextField(10);
		priceTF.setBounds(514, 478, 94, 35);

		JButton participateBtn = new JButton("입찰하기");
		participateBtn.setBounds(503, 416, 105, 23);

		if (!timer.isOwn(data.getCurrentUser().getNo(), data.getProduct().getProductNo())) {
			lblisOwn.setText(data.getProduct().getProductName() + "은(는) 본인이 등록한 상품입니다.");
			participateBtn.setBackground(Color.black);

		} else {
			participateBtn.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {

					Product product = data.getProduct();
					String bid = priceTF.getText();

					if (PriceMin(product.getProductPriceNow()) >= Integer.parseInt(bid)) {
						lblMessage.setForeground(Color.RED);
						lblMessage.setText("입찰가격은 현재가격의 105% 이상이어야 합니다.");
					} else if (!bidMin(bid)) {
						lblMessage.setForeground(Color.RED);
						lblMessage.setText("입찰단위는 100원입니다.");
					} else {
						int choice = JOptionPane.showConfirmDialog(null, "입찰을 하면 취소할 수 없습니다.\n정말 입찰하시겠습니까?", "입찰",
								JOptionPane.YES_NO_OPTION);
						if (choice == JOptionPane.YES_OPTION) {
							// 입찰 쿼리문
							timer.insertParticipate(data.getCurrentUser().getNo(), auctionNo);

							int auctionno = timer.getAuctionNo(data.getProduct().getProductNo());
							timer.plusOneMinute(auctionno);

							int setNo = product.getSetNo();
							timer.updatePrice(setNo, bid);

							new AuctionFrame(data);
							setVisible(false);
						}
					}
				}
			});
		}

		JButton backButton = new JButton("뒤로가기");
		backButton.setBounds(380, 416, 105, 23);
		backButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new AuctionFrame(data);
				setVisible(false);
			}
		});

		pnl.setLayout(null);

		pnl.add(lblImage);
		pnl.add(lblName);
		pnl.add(lblDetail);
		pnl.add(lblTime);
		pnl.add(lblPrice);
		pnl.add(lblPriceMin);
		pnl.add(lblMessage);
		pnl.add(lblisOwn);
		pnl.add(participateBtn);
		pnl.add(priceTF);
		pnl.add(backButton);
		getContentPane().add(pnl);

		try {
			scheduler = StdSchedulerFactory.getDefaultScheduler();

			JobDataMap jobDataMap = new JobDataMap();

			JobDetail job3 = JobBuilder.newJob(AutionUpdateJob.class).withIdentity("labelUpdateJob3", "group3")
					.usingJobData(jobDataMap).build();

			Trigger trigger3 = TriggerBuilder.newTrigger().withIdentity("labelUpdateTrigger3", "group3").startNow()
					.withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(1).repeatForever())
					.build();

			// 스케줄러가 job의 key를 가지고 있으면 다시 scheduleJob을 생성하지 않도록
			if (!scheduler.checkExists(job3.getKey())) {
				scheduler.scheduleJob(job3, trigger3);
			}

			scheduler.start();
		} catch (SchedulerException e) {
			e.printStackTrace();
		}

		setSize(800, 700);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		setLocationRelativeTo(null);
		setResizable(false);
	}

	public boolean bidMin(String bid) {
		int result = Integer.parseInt(bid);
		return (result % 100 == 0);
	}

	public static int PriceMin(int price) {
		double result = price * 1.05;
		int roundedResult = (int) Math.ceil(result / 100) * 100;
		return roundedResult;
	}

	public static String TimeFormatString(LocalDateTime startTime) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd일 HH:mm:ss");
		return startTime.format(formatter);
	}

	public static ImageIcon iconSize(ImageIcon icon) {
		Image img = icon.getImage();
		Image changeSize = img.getScaledInstance(170, 170, Image.SCALE_SMOOTH);
		return new ImageIcon(changeSize);
	}

	public static void updatLabel(LocalDateTime now) {
		Connection conn = null;

		try {
			conn = DBUtil.getConnection();
			Product product = data.getProduct();

//			lblImage
			lblName.setText(product.getProductName());
			lblDetail.setText(product.getProductContent());
			String result1 = duration(product.getEndTime(), now);
			lblTime.setText(result1);
			lblPrice.setText(formatInt(product.getProductPriceNow()));
			lblPriceMin.setText("최소입찰가 : " + formatInt(PriceMin(product.getProductPriceNow())));

		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			DBUtil.close(conn);
		}
	}

	public static String formatInt(int price) {
		return String.format("%,d원", price);
	}

	public static String duration(LocalDateTime targetDateTime, LocalDateTime now) {
		Duration duration = Duration.between(now, targetDateTime);
		long days = duration.toDays();
		long hours = duration.toHours() % 24;
		long minutes = duration.toMinutes() % 60;
		long seconds = duration.getSeconds() % 60;

		return String.format("%02d일 %02d:%02d:%02d", days, hours, minutes, seconds);
	}

	public static class AutionUpdateJob implements Job {
		public void execute(JobExecutionContext context) throws JobExecutionException {
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {

					LocalDateTime now = LocalDateTime.now();
					updatLabel(now);
				}
			});
		}
	}
}
