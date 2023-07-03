package secondweek;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.text.PlainDocument;
import javax.swing.text.DocumentFilter.FilterBypass;

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
import secondweek.AuctionFrame.NumberOnlyFilter;

public class DetailFrame extends JFrame {
	private Scheduler scheduler;
	private static Timer timer;
	private JPanel contentPane;
	private JFrame frame;

	private static JLabel lblImage;
	private static JLabel lblName;
	private static JLabel lblDetail;
	private static JLabel lblTime;

	private static JLabel lblPrice;
	private static JLabel lblPriceMin;

	private static DataBase data;

	private static JLabel lblisOwn;

	private static JLabel prePriceLbl1;
	private static JLabel prePriceLbl2;
	private static JLabel prePriceLbl3;
	private static JLabel prePriceLbl4;

	private JLabel lblisContinue;

	// 이미지, 제품이름, 상세설명, 남은시간, 가격
	
	class NumberOnlyFilter extends DocumentFilter {
		private int maxLength; // 최대 입력 길이

		public NumberOnlyFilter(int maxLength) {
			this.maxLength = maxLength;
		}

		@Override
		public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
				throws BadLocationException {
			// 입력 문자열이 숫자인 경우에만 삽입을 허용합니다.
			if (string.matches("\\d+") && (fb.getDocument().getLength() + string.length()) <= maxLength) {
				super.insertString(fb, offset, string, attr);
			}
		}

		@Override
		public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
				throws BadLocationException {
			// 입력 문자열이 숫자인 경우에만 대체를 허용합니다.
			if (text.matches("\\d+") && (fb.getDocument().getLength() - length + text.length()) <= maxLength) {
				super.replace(fb, offset, length, text, attrs);
			}
		}
	}

	public DetailFrame(DataBase data, Product product) {

		this.data = data;
		timer = new Timer();

		frame = new JFrame();
		frame.setSize(1200, 800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		 frame.setResizable(false);
		contentPane = new JPanel() {

			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);

				Toolkit toolkit = Toolkit.getDefaultToolkit();

				Image image = toolkit.getImage("img/detailPage_1.png");
				g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
			}
		};

		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		lblImage = new JLabel();
		lblImage.setBounds(168, 240, 400, 400);
		// 리스트를 불러오지 않고 DataBase에 저장된 Product에 있는 Blob 이미지를 사용하도록 수정
		byte[] imageBites = null;
		try {
			imageBites = data.getProduct().getImage().getBytes(1, (int) data.getProduct().getImage().length());
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		ImageIcon imageIcon = new ImageIcon(imageBites);
		lblImage.setIcon(iconSize(imageIcon));
		
//		

		lblName = new JLabel("제품명");
		lblName.setFont(lblName.getFont().deriveFont(16f));
		lblName.setBounds(850, 148, 150, 25);
		lblDetail = new JLabel("상세설명");
		lblDetail.setForeground(Color.GRAY);
		lblDetail.setBounds(740, 420, 226, 116);
		lblTime = new JLabel("00:00:00");
		lblTime.setFont(new Font("돋움", Font.BOLD, 20));
		lblTime.setForeground(Color.RED);
		lblTime.setFont(lblTime.getFont().deriveFont(16f));
		lblTime.setBounds(850, 200, 150, 20);
		JLabel nowprice = new JLabel("현재 가격 : ");
		nowprice.setBounds(750, 230, 100, 20);
		lblPrice = new JLabel("가격");
		lblPrice.setBounds(850, 230, 150, 15);
		lblPrice.setFont(lblPrice.getFont().deriveFont(14f));
		lblPriceMin = new JLabel("최소입찰가 : 0원");
		lblPriceMin.setFont(lblPrice.getFont().deriveFont(12f));
		lblPriceMin.setBounds(850, 408, 150, 15);
		lblPriceMin.setForeground(Color.GRAY);

		JLabel lblMessage = new JLabel("입찰가격은 최소입찰가를 상회해야 합니다.");
		lblMessage.setForeground(Color.darkGray);
		lblMessage.setBounds(760, 425, 268, 35);

		lblisOwn = new JLabel();
		lblisOwn.setBounds(200, 646, 400, 35);
		lblisContinue = new JLabel();
		lblisContinue.setBounds(200, 680, 400, 35);

		JTextField priceTF = new JTextField(10);
		PlainDocument docPrice = (PlainDocument) priceTF.getDocument();
		docPrice.setDocumentFilter(new NumberOnlyFilter(15));
		priceTF.setBounds(843, 380, 160, 25);

		JButton participateBtn = new JButton();
		participateBtn.setBounds(730, 620, 350, 100);
		participateBtn.setContentAreaFilled(false);
		participateBtn.setBorderPainted(false);
		ImageIcon imgbid = new ImageIcon("img/bid_1.png");
		participateBtn.setIcon(imgbid);
		contentPane.add(participateBtn);
		participateBtn.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseExited(MouseEvent e) {
				ImageIcon imgbid = new ImageIcon("img/bid_1.png");
				participateBtn.setIcon(imgbid);

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				ImageIcon imgbid = new ImageIcon("img/bid.png");
				participateBtn.setIcon(imgbid);

			}

		});

		if (!timer.isContinue(product.getAuctionNo(), data.getCurrentUser().getNo())) {
			lblisContinue.setText("연속 입찰은 불가능 합니다.");
			participateBtn.setEnabled(false);
		}

		if (!timer.isOwn(data.getCurrentUser().getNo(), data.getProduct().getProductNo())) {
			lblisOwn.setText(data.getProduct().getProductName() + "은(는) 본인이 등록한 상품입니다.");
			participateBtn.setBackground(Color.black);

		} else if (timer.isContinue(product.getAuctionNo(), data.getCurrentUser().getNo())) {
			participateBtn.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {

					Product product = data.getProduct();
					String bid = priceTF.getText();

					if (PriceMin(product.getProductPriceNow()) > Integer.parseInt(bid)) {
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
							timer.insertParticipate(data.getCurrentUser().getNo(), data.getProduct().getAuctionNo(),
									Integer.parseInt(bid));

							int auctionno = timer.getAuctionNo(data.getProduct().getProductNo());
							timer.plusOneMinute(auctionno);

							int setNo = product.getSetNo();
							timer.updatePrice(setNo, bid);

							new AuctionFrame(data);
							frame.dispose();
						}
					}
				}
			});
		}

		JButton backButton = new JButton();
		backButton.setBounds(50, 25, 130, 50);
		ImageIcon imgreturnBtn = new ImageIcon("img/Goback_1.png");
		backButton.setContentAreaFilled(false);
		backButton.setBorderPainted(false);
		backButton.setIcon(imgreturnBtn);

		backButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseExited(MouseEvent e) {
				ImageIcon imgreturnBtn = new ImageIcon("img/Goback_1.png");
				backButton.setIcon(imgreturnBtn);

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				ImageIcon imgreturnBtn = new ImageIcon("img/Goback.png");
				backButton.setIcon(imgreturnBtn);

			}
		});
		backButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new AuctionFrame(data);
				frame.dispose();
			}
		});

		// 이전가격 라벨 추가
		JLabel prePriceInfoLbl = new JLabel("최근 입찰가 : ");
		prePriceInfoLbl.setBounds(750, 250, 200, 200);
		prePriceLbl1 = new JLabel("(가장오래된) 입찰가1");
		prePriceLbl1.setBounds(850, 195, 150, 20);
		prePriceLbl1.setForeground(Color.GRAY);
		prePriceLbl2 = new JLabel("입찰가2");
		prePriceLbl2.setBounds(850, 215, 150, 20);
		prePriceLbl2.setForeground(Color.GRAY);
		prePriceLbl3 = new JLabel("입찰가3");
		prePriceLbl3.setBounds(850, 235, 150, 20);
		prePriceLbl3.setForeground(Color.GRAY);
		prePriceLbl4 = new JLabel("(가장최근) 입찰가4");
		prePriceLbl4.setBounds(850, 255, 150, 20);
		prePriceLbl4.setForeground(Color.GRAY);

		contentPane.add(prePriceInfoLbl);
		prePriceInfoLbl.add(prePriceLbl1);
		prePriceInfoLbl.add(prePriceLbl2);
		prePriceInfoLbl.add(prePriceLbl3);
		prePriceInfoLbl.add(prePriceLbl4);

		contentPane.add(lblImage);
		contentPane.add(lblName);
		contentPane.add(lblDetail);
		contentPane.add(lblTime);
		contentPane.add(lblPrice);
		contentPane.add(lblPriceMin);
		contentPane.add(lblMessage);
		contentPane.add(lblisOwn);
		contentPane.add(participateBtn);
		contentPane.add(priceTF);
		contentPane.add(backButton);
		
		contentPane.add(lblisContinue);

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

		frame.getContentPane().add(contentPane);
		frame.setVisible(true);
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
		Image changeSize = img.getScaledInstance(350, 350, Image.SCALE_SMOOTH);
		return new ImageIcon(changeSize);
	}

	public static void updatLabel(LocalDateTime now) {
		Connection conn = null;

		try {
			conn = DBUtil.getConnection();
			Product product = data.getProduct();

			lblName.setText(product.getProductName());
			lblDetail.setText(product.getProductContent());
			String result1 = duration(product.getEndTime(), now);
			lblTime.setText(result1);
			lblPrice.setText(formatInt(product.getProductPriceNow()));
			lblPriceMin.setText("최소입찰가 : " + formatInt(PriceMin(product.getProductPriceNow())));

			prePriceLbl1.setText("");
			prePriceLbl2.setText("");
			prePriceLbl3.setText("");
			prePriceLbl4.setText("");

			List<Integer> priceList = timer.participateList(product.getAuctionNo(), conn);
			
			if (priceList != null) {
				for (int i = 0; i < priceList.size(); i++) {
					if (priceList.get(i) != null) {
						int price = priceList.get(i);
						if (price == 0) {
							continue;
						}
						switch (i + 1) {
						case 1:
							prePriceLbl4.setText(formatInt(price));
							break;
						case 2:
							prePriceLbl3.setText(formatInt(price));
							break;
						case 3:
							prePriceLbl2.setText(formatInt(price));
							break;
						case 4:
							prePriceLbl1.setText(formatInt(price));
							break;
						}
					}
				}
			}

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
