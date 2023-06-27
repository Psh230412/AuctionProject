

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.JPanel;
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
import secondweek.AuctionFrame;
import secondweek.DataBase;
import secondweek.RegistFrame;

public class MypageFrame2 extends JFrame {
	private static DataBase data;
	
	private Scheduler scheduler;
	
	private static JLabel lblImage11;
	private static JLabel lblImage12;
	private static JLabel lblImage13;
	private static JLabel lblImage14;
	private static JLabel lblImage15;
	private static JLabel lblImage21;
	private static JLabel lblImage22;
	private static JLabel lblImage23;
	private static JLabel lblImage24;
	private static JLabel lblImage25;
	private static JLabel lblImage31;
	private static JLabel lblImage32;
	private static JLabel lblImage33;
	private static JLabel lblImage34;
	private static JLabel lblImage35;

	private static JLabel lblName11;
	private static JLabel lblName12;
	private static JLabel lblName13;
	private static JLabel lblName14;
	private static JLabel lblName15;
	private static JLabel lblName21;
	private static JLabel lblName22;
	private static JLabel lblName23;
	private static JLabel lblName24;
	private static JLabel lblName25;
	private static JLabel lblName31;
	private static JLabel lblName32;
	private static JLabel lblName33;
	private static JLabel lblName34;
	private static JLabel lblName35;
	
	private static JLabel lblTime11;
	private static JLabel lblTime12;
	private static JLabel lblTime13;
	private static JLabel lblTime14;
	private static JLabel lblTime15;
	private static JLabel lblTime21;
	private static JLabel lblTime22;
	private static JLabel lblTime23;
	private static JLabel lblTime24;
	private static JLabel lblTime25;

	private static JLabel lblBid1;
	private static JLabel lblBid2;
	private static JLabel lblBid3;
	private static JLabel lblBid4;
	private static JLabel lblBid5;

	private static JLabel lblPrice11;
	private static JLabel lblPrice12;
	private static JLabel lblPrice13;
	private static JLabel lblPrice14;
	private static JLabel lblPrice15;
	private static JLabel lblPrice21;
	private static JLabel lblPrice22;
	private static JLabel lblPrice23;
	private static JLabel lblPrice24;
	private static JLabel lblPrice25;
	private static JLabel lblPrice31;
	private static JLabel lblPrice32;
	private static JLabel lblPrice33;
	private static JLabel lblPrice34;
	private static JLabel lblPrice35;


	private static JLabel[] images = new JLabel[15];
	private static JLabel[] names = new JLabel[15];
	private static JLabel[] times = new JLabel[10];
	private static JLabel[] bids = new JLabel[5];
	private static JLabel[] prices = new JLabel[15];

	public static void inputImages() {
		// 등록패널 제품이미지라벨
		images[0] = lblImage11;
		images[1] = lblImage12;
		images[2] = lblImage13;
		images[3] = lblImage14;
		images[4] = lblImage15;

		// 입찰패널 제품이미지라벨
		images[5] = lblImage21;
		images[6] = lblImage22;
		images[7] = lblImage23;
		images[8] = lblImage24;
		images[9] = lblImage25;

		// 낙찰/유찰패널 제품이미지라벨
		images[10] = lblImage31;
		images[11] = lblImage32;
		images[12] = lblImage33;
		images[13] = lblImage34;
		images[14] = lblImage35;
	}

	public static void inputNames() {
		// 등록패널 제품이름라벨
		names[0] = lblName11;
		names[1] = lblName12;
		names[2] = lblName13;
		names[3] = lblName14;
		names[4] = lblName15;

		// 입찰패널 제품이름라벨
		names[5] = lblName21;
		names[6] = lblName22;
		names[7] = lblName23;
		names[8] = lblName24;
		names[9] = lblName25;

		// 낙찰/유찰패널 제품이름라벨
		names[10] = lblName31;
		names[11] = lblName32;
		names[12] = lblName33;
		names[13] = lblName34;
		names[14] = lblName35;
	}

	public static void inputTimes() {
		// 등록패널 시간라벨
		times[0] = lblTime11;
		times[1] = lblTime12;
		times[2] = lblTime13;
		times[3] = lblTime14;
		times[4] = lblTime15;

		// 입찰패널 시간라벨
		times[5] = lblTime21;
		times[6] = lblTime22;
		times[7] = lblTime23;
		times[8] = lblTime24;
		times[9] = lblTime25;
	}

	public static void inputBids() {
		// 낙찰/유찰패널 낙찰여부라벨
		bids[0] = lblBid1;
		bids[1] = lblBid2;
		bids[2] = lblBid3;
		bids[3] = lblBid4;
		bids[4] = lblBid5;
	}

	public static void inputPrices() {
		// 등록패널 가격라벨
		prices[0] = lblPrice11;
		prices[1] = lblPrice12;
		prices[2] = lblPrice13;
		prices[3] = lblPrice14;
		prices[4] = lblPrice15;

		// 입찰패널 가격라벨
		prices[5] = lblPrice21;
		prices[6] = lblPrice22;
		prices[7] = lblPrice23;
		prices[8] = lblPrice24;
		prices[9] = lblPrice25;

		// 낙찰/유찰패널 가격라벨
		prices[10] = lblPrice31;
		prices[11] = lblPrice32;
		prices[12] = lblPrice33;
		prices[13] = lblPrice34;
		prices[14] = lblPrice35;
	}

	public MypageFrame2(DataBase data) {
		this.data = data;

		JPanel pnl = new JPanel();
		pnl.setLayout(null);
		pnl.setBackground(Color.BLUE);

		JLabel userNameLb = new JLabel("user_name");
		userNameLb.setBounds(75, 123, 188, 67);
		userNameLb.setText(data.getCurrentUser().getName());
		pnl.add(userNameLb);

		JButton resetBtn = new JButton("개인정보변경");
		resetBtn.setBounds(400, 123, 149, 67);
		pnl.add(resetBtn);

		JButton resgistBtn = new JButton("물품등록하기");
		resgistBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new RegistFrame(data);
				setVisible(false);
			}
		});
		resgistBtn.setBounds(750, 123, 149, 67);
		pnl.add(resgistBtn);

		JButton mainBtn = new JButton("메인화면");
		mainBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new AuctionFrame(data);
				setVisible(false);
			}
		});
		mainBtn.setBounds(934, 60, 116, 30);
		pnl.add(mainBtn);

		JPanel pnl1 = new JPanel(new GridLayout(5, 0));
		JPanel pnl2 = new JPanel(new GridLayout(5, 0));
		JPanel pnl3 = new JPanel(new GridLayout(5, 0));

		pnl1.setBounds(50, 300, 300, 300);
		pnl1.setBackground(Color.BLUE);
		pnl2.setBounds(400, 300, 300, 300);
		pnl2.setBackground(Color.BLUE);
		pnl3.setBounds(750, 300, 300, 300);
		pnl3.setBackground(Color.BLUE);

		JPanel pnl11 = new JPanel();
		JPanel pnl12 = new JPanel();
		JPanel pnl13 = new JPanel();
		JPanel pnl14 = new JPanel();
		JPanel pnl15 = new JPanel();

		JPanel pnl21 = new JPanel();
		JPanel pnl22 = new JPanel();
		JPanel pnl23 = new JPanel();
		JPanel pnl24 = new JPanel();
		JPanel pnl25 = new JPanel();

		JPanel pnl31 = new JPanel();
		JPanel pnl32 = new JPanel();
		JPanel pnl33 = new JPanel();
		JPanel pnl34 = new JPanel();
		JPanel pnl35 = new JPanel();

		lblImage11 = new JLabel("");
		pnl11.add(lblImage11);
		lblImage12 = new JLabel("");
		pnl12.add(lblImage12);
		lblImage13 = new JLabel("");
		pnl13.add(lblImage13);
		lblImage14 = new JLabel("");
		pnl14.add(lblImage14);
		lblImage15 = new JLabel("");
		pnl15.add(lblImage15);
		
		lblImage21 = new JLabel("");
		pnl21.add(lblImage21);
		lblImage22 = new JLabel("");
		pnl22.add(lblImage22);
		lblImage23 = new JLabel("");
		pnl23.add(lblImage23);
		lblImage24 = new JLabel("");
		pnl24.add(lblImage24);
		lblImage25 = new JLabel("");
		pnl25.add(lblImage25);
		
		lblImage31 = new JLabel("");
		pnl31.add(lblImage31);
		lblImage32 = new JLabel("");
		pnl32.add(lblImage32);
		lblImage33 = new JLabel("");
		pnl33.add(lblImage33);
		lblImage34 = new JLabel("");
		pnl34.add(lblImage34);
		lblImage35 = new JLabel("");
		pnl35.add(lblImage35);

		lblName11 = new JLabel("");
		pnl11.add(lblName11);
		lblName12 = new JLabel("");
		pnl12.add(lblName12);
		lblName13 = new JLabel("");
		pnl13.add(lblName13);
		lblName14 = new JLabel("");
		pnl14.add(lblName14);
		lblName15 = new JLabel("");
		pnl15.add(lblName15);
		
		lblName21 = new JLabel("");
		pnl21.add(lblName21);
		lblName22 = new JLabel("");
		pnl22.add(lblName22);
		lblName23 = new JLabel("");
		pnl23.add(lblName23);
		lblName24 = new JLabel("");
		pnl24.add(lblName24);
		lblName25 = new JLabel("");
		pnl25.add(lblName25);
		
		lblName31 = new JLabel("");
		pnl31.add(lblName31);
		lblName32 = new JLabel("");
		pnl32.add(lblName32);
		lblName33 = new JLabel("");
		pnl33.add(lblName33);
		lblName34 = new JLabel("");
		pnl34.add(lblName34);
		lblName35 = new JLabel("");
		pnl35.add(lblName35);

		lblTime11 = new JLabel("00 : 00 : 00");
		pnl11.add(lblTime11);
		lblTime12 = new JLabel("00 : 00 : 00");
		pnl12.add(lblTime12);
		lblTime13 = new JLabel("00 : 00 : 00");
		pnl13.add(lblTime13);
		lblTime14 = new JLabel("00 : 00 : 00");
		pnl14.add(lblTime14);
		lblTime15 = new JLabel("00 : 00 : 00");
		pnl15.add(lblTime15);
		
		lblTime21 = new JLabel("00 : 00 : 00");
		pnl21.add(lblTime21);
		lblTime22 = new JLabel("00 : 00 : 00");
		pnl22.add(lblTime22);
		lblTime23 = new JLabel("00 : 00 : 00");
		pnl23.add(lblTime23);
		lblTime24 = new JLabel("00 : 00 : 00");
		pnl24.add(lblTime24);
		lblTime25 = new JLabel("00 : 00 : 00");
		pnl25.add(lblTime25);

		lblBid1 = new JLabel(" / ");
		pnl31.add(lblBid1);
		lblBid2 = new JLabel(" / ");
		pnl32.add(lblBid2);
		lblBid3 = new JLabel(" / ");
		pnl33.add(lblBid3);
		lblBid4 = new JLabel(" / ");
		pnl34.add(lblBid4);
		lblBid5 = new JLabel(" / ");
		pnl35.add(lblBid5);

		lblPrice11 = new JLabel(" - ");
		pnl11.add(lblPrice11);
		lblPrice12 = new JLabel(" - ");
		pnl12.add(lblPrice12);
		lblPrice13 = new JLabel(" - ");
		pnl13.add(lblPrice13);
		lblPrice14 = new JLabel(" - ");
		pnl14.add(lblPrice14);
		lblPrice15 = new JLabel(" - ");
		pnl15.add(lblPrice15);
		lblPrice21 = new JLabel(" - ");
		pnl21.add(lblPrice21);
		lblPrice22 = new JLabel(" - ");
		pnl22.add(lblPrice22);
		lblPrice23 = new JLabel(" - ");
		pnl23.add(lblPrice23);
		lblPrice24 = new JLabel(" - ");
		pnl24.add(lblPrice24);
		lblPrice25 = new JLabel(" - ");
		pnl25.add(lblPrice25);
		lblPrice31 = new JLabel(" - ");
		pnl31.add(lblPrice31);
		lblPrice32 = new JLabel(" - ");
		pnl32.add(lblPrice32);
		lblPrice33 = new JLabel(" - ");
		pnl33.add(lblPrice33);
		lblPrice34 = new JLabel(" - ");
		pnl34.add(lblPrice34);
		lblPrice35 = new JLabel(" - ");
		pnl35.add(lblPrice35);

		inputImages();
		inputNames();
		inputTimes();
		inputBids();
		inputPrices();

		for (int i = 0; i < names.length; i++) {
			names[i].setForeground(Color.GREEN);
		}
		
		for (int i = 0; i < times.length; i++) {
			times[i].setForeground(Color.RED);
		}
		
		for (int i = 0; i < bids.length; i++) {
			bids[i].setForeground(Color.BLACK);
		}
		
		for (int i = 0; i < prices.length; i++) {
			prices[i].setForeground(Color.BLUE);
		}

		pnl1.add(pnl11);
		pnl1.add(pnl12);
		pnl1.add(pnl13);
		pnl1.add(pnl14);
		pnl1.add(pnl15);
		pnl2.add(pnl21);
		pnl2.add(pnl22);
		pnl2.add(pnl23);
		pnl2.add(pnl24);
		pnl2.add(pnl25);
		pnl3.add(pnl31);
		pnl3.add(pnl32);
		pnl3.add(pnl33);
		pnl3.add(pnl34);
		pnl3.add(pnl35);

		pnl.add(pnl1);
		pnl.add(pnl2);
		pnl.add(pnl3);

		getContentPane().add(pnl);

		JLabel lblNewLabel = new JLabel("등록물품");
		lblNewLabel.setBounds(138, 258, 125, 15);
		pnl.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("입찰물품");
		lblNewLabel_1.setBounds(492, 258, 57, 15);
		pnl.add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("낙찰/유찰 물품");
		lblNewLabel_2.setBounds(842, 258, 125, 15);
		pnl.add(lblNewLabel_2);

		LocalDateTime now = LocalDateTime.now();
		updatLabel(now);

		try {
			scheduler = StdSchedulerFactory.getDefaultScheduler();

			JobDataMap jobDataMap = new JobDataMap();
//         jobDataMap.put("label", lblImage1);

			JobDetail job2 = JobBuilder.newJob(AutionUpdateJob.class).withIdentity("labelUpdateJobMain", "group2")
					.usingJobData(jobDataMap).build();

			Trigger trigger2 = TriggerBuilder.newTrigger().withIdentity("labelUpdateTriggerMain", "group2").startNow()
					.withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(1).repeatForever())
					.build();

			scheduler.scheduleJob(job2, trigger2);

			scheduler.start();
		} catch (SchedulerException e) {
			e.printStackTrace();
		}

		setSize(1100, 700);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		setLocationRelativeTo(null);
		setResizable(false);
	}

	public static String TimeFormatString(LocalDateTime startTime) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd일 HH:mm:ss");
		return startTime.format(formatter);
	}

	public static ImageIcon iconSize(ImageIcon icon) {
		Image img = icon.getImage();
		Image changeSize = img.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
		return new ImageIcon(changeSize);
	}

	public static void updatLabel(LocalDateTime now) {
		ListRepository repo = new ListRepository();
		Connection conn = null;

		try {
			conn = DBUtil.getConnection();
			List<EnrollParticipate> enrollList = repo.getEnrollment(data.getCurrentUser().getNo());
			EnrollParticipate enroll = null;
			if (enrollList != null) {
				for (int i = 0; i < enrollList.size(); i++) {
					enroll = enrollList.get(i);
					if (enroll == null) {
						continue;
					} else {
						switch (i) {
						case 0: {
							lblPrice11.setText(formatInt(enroll.getProductPriceNow()));
							lblImage11.setIcon(iconSize(enroll.getImage()));
							String result11 = duration(enroll.getEndTime(), now);
							lblTime11.setText(result11);
							lblName11.setText(enroll.getProductname());
							break;
						}
						case 1: {
							lblPrice12.setText(formatInt(enroll.getProductPriceNow()));
							lblImage12.setIcon(iconSize(enroll.getImage()));
							String result12 = duration(enroll.getEndTime(), now);
							lblTime12.setText(result12);
							lblName12.setText(enroll.getProductname());
							break;
						}
						case 2: {
							lblPrice13.setText(formatInt(enroll.getProductPriceNow()));
							lblImage13.setIcon(iconSize(enroll.getImage()));
							String result13 = duration(enroll.getEndTime(), now);
							lblTime13.setText(result13);
							lblName13.setText(enroll.getProductname());
							break;
						}
						case 3: {
							lblPrice14.setText(formatInt(enroll.getProductPriceNow()));
							lblImage14.setIcon(iconSize(enroll.getImage()));
							String result14 = duration(enroll.getEndTime(), now);
							lblTime14.setText(result14);
							lblName14.setText(enroll.getProductname());
							break;
						}
						case 4: {
							lblPrice15.setText(formatInt(enroll.getProductPriceNow()));
							lblImage15.setIcon(iconSize(enroll.getImage()));
							String result15 = duration(enroll.getEndTime(), now);
							lblTime15.setText(result15);
							lblName15.setText(enroll.getProductname());
							break;
						}
						}
					}
				}
			}

			List<EnrollParticipate> participateList = repo.getParticipateList(data.getCurrentUser().getNo());
			EnrollParticipate participate = null;
			if (participateList != null) {
				for (int i = 0; i < participateList.size(); i++) {
					participate = participateList.get(i);
					if (participate == null) {
						continue;
					} else {
						switch (i) {
						case 0: {
							lblPrice21.setText(formatInt(participate.getProductPriceNow()));
							lblImage21.setIcon(iconSize(participate.getImage()));
							String result21 = duration(participate.getEndTime(), now);
							lblTime21.setText(result21);
							lblName21.setText(participate.getProductname());
							break;
						}
						case 1: {
							lblPrice22.setText(formatInt(participate.getProductPriceNow()));
							lblImage22.setIcon(iconSize(participate.getImage()));
							String result22 = duration(participate.getEndTime(), now);
							lblTime22.setText(result22);
							lblName22.setText(participate.getProductname());
							break;
						}
						case 2: {
							lblPrice23.setText(formatInt(participate.getProductPriceNow()));
							lblImage23.setIcon(iconSize(participate.getImage()));
							String result23 = duration(participate.getEndTime(), now);
							lblTime23.setText(result23);
							lblName23.setText(participate.getProductname());
							break;
						}
						case 3: {
							lblPrice24.setText(formatInt(participate.getProductPriceNow()));
							lblImage24.setIcon(iconSize(participate.getImage()));
							String result24 = duration(participate.getEndTime(), now);
							lblTime24.setText(result24);
							lblName24.setText(participate.getProductname());
							break;
						}
						case 4: {
							lblPrice25.setText(formatInt(participate.getProductPriceNow()));
							lblImage25.setIcon(iconSize(participate.getImage()));
							String result25 = duration(participate.getEndTime(), now);
							lblTime25.setText(result25);
							lblName25.setText(participate.getProductname());
							break;
						}
						}
					}
				}
			}

			List<Bidinfo> bidinfoList = repo.getBidinfoList(data.getCurrentUser().getNo());
			Bidinfo bid = null;
			if (bidinfoList != null) {
				for (int i = 0; i < bidinfoList.size(); i++) {
					bid = bidinfoList.get(i);
					if (bid == null) {
						continue;
					} else {
						switch (i) {
						case 0: {
							lblPrice31.setText(formatInt(bid.getProductPriceFinal()));
							lblImage31.setIcon(iconSize(bid.getImage()));
							lblBid1.setText(bidOrNot(bid.isBid()));
							lblName31.setText(bid.getProductname());
							break;
						}
						case 1: {
							lblPrice32.setText(formatInt(bid.getProductPriceFinal()));
							lblImage32.setIcon(iconSize(bid.getImage()));
							lblBid2.setText(bidOrNot(bid.isBid()));
							lblName32.setText(bid.getProductname());
							break;
						}
						case 2: {
							lblPrice33.setText(formatInt(bid.getProductPriceFinal()));
							lblImage33.setIcon(iconSize(bid.getImage()));
							lblBid3.setText(bidOrNot(bid.isBid()));
							lblName33.setText(bid.getProductname());
							break;
						}
						case 3: {
							lblPrice34.setText(formatInt(bid.getProductPriceFinal()));
							lblImage34.setIcon(iconSize(bid.getImage()));
							lblBid4.setText(bidOrNot(bid.isBid()));
							lblName34.setText(bid.getProductname());
							break;
						}
						case 4: {
							lblPrice35.setText(formatInt(bid.getProductPriceFinal()));
							lblImage35.setIcon(iconSize(bid.getImage()));
							lblBid5.setText(bidOrNot(bid.isBid()));
							lblName35.setText(bid.getProductname());
							break;
						}
						}
					}
				}
			}
		} catch (

		SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(conn);
		}
	}

	public static String formatInt(int price) {
		return String.format("%,d원", price);
	}

	public static String bidOrNot(boolean b) {
		if (b) {
			return "낙찰";
		} else {
			return "유찰";
		}
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