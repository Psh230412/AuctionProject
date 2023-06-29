package secondweek;



import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
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
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

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

public class MypageFrame extends JFrame {
	private static DataBase data;
	
	private Scheduler scheduler;
	private JPanel contentPane;
	private JFrame frame;
	private Timer timer;
	
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

	private static JButton previousEnroll;

	private static JButton nextEnroll;

	private static JButton previousParticipate;

	private static JButton nextParticipate;

	private static JButton previousBidinfo;

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

	public MypageFrame(DataBase data) {
		this.data = data;
		timer = new Timer();

		timer.inputSuccessbidinfo();
		timer.setIsBid();
		 
		 frame = new JFrame();
				frame.setSize(1200,800);
			        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
			        contentPane = new JPanel(){

				@Override
				protected void paintComponent(Graphics g) {
					super.paintComponent(g);

					Toolkit toolkit = Toolkit.getDefaultToolkit();

					Image image = toolkit.getImage("img/myPage.png");
					g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
				}
			};
			
			
			contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
			setContentPane(contentPane);
			contentPane.setLayout(null);
		
	

		JLabel userNameLb = new JLabel("user_name");
		userNameLb.setBounds(190, 160, 188, 67);
		userNameLb.setText(data.getCurrentUser().getName());
		contentPane.add(userNameLb);

		JButton resetBtn = new JButton("개인정보변경");
		resetBtn.setBounds(400, 123, 149, 67);
		resetBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new ChangeInformationFrame(data);
				setVisible(false);
			}
		});
		contentPane.add(resetBtn);
		resetBtn.setBounds(330, 123, 210, 120);
		ImageIcon imgreset = new ImageIcon("img/changeinfo_1.png");
		resetBtn.setContentAreaFilled(false);
		resetBtn.setBorderPainted(false);
		resetBtn.setIcon(imgreset);
		resetBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseExited(MouseEvent e) {
				ImageIcon imgreset = new ImageIcon("img/changeinfo_1.png");
				resetBtn.setIcon(imgreset);

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				ImageIcon imgreset = new ImageIcon("img/changeinfo.png");
				resetBtn.setIcon(imgreset);

			}

		});

		contentPane.add(resetBtn);

		JButton resgistBtn = new JButton("물품등록하기");
		resgistBtn.setBounds(600, 123, 210, 120);
		ImageIcon imgresgist = new ImageIcon("img/myregist_1.png");
		resgistBtn.setContentAreaFilled(false);
		resgistBtn.setBorderPainted(false);
		resgistBtn.setIcon(imgresgist);
		resgistBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseExited(MouseEvent e) {
				ImageIcon imgresgist = new ImageIcon("img/myregist_1.png");
				resgistBtn.setIcon(imgresgist);

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				ImageIcon imgreset = new ImageIcon("img/myregist.png");
				resgistBtn.setIcon(imgreset);

			}

		});

		resgistBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new RegistFrame(data);
				frame.setVisible(false);
			}
		});

		contentPane.add(resgistBtn);

		JButton mainBtn = new JButton("메인화면");
		mainBtn.setBounds(75, 60, 150, 80);
		ImageIcon imgmain = new ImageIcon("img/gomain_1.png");
		mainBtn.setContentAreaFilled(false);
		mainBtn.setBorderPainted(false);
		mainBtn.setIcon(imgmain);
		mainBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseExited(MouseEvent e) {
				ImageIcon imgmain = new ImageIcon("img/gomain_1.png");
				mainBtn.setIcon(imgmain);

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				ImageIcon imgmain = new ImageIcon("img/gomain.png");
				mainBtn.setIcon(imgmain);

			}

		});

		mainBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new AuctionFrame(data);
				frame.setVisible(false);
			}
		});

		contentPane.add(mainBtn);

		JPanel pnl1 = new JPanel(new GridLayout(5, 0));
		JPanel pnl2 = new JPanel(new GridLayout(5, 0));
		JPanel pnl3 = new JPanel(new GridLayout(5, 0));

		pnl1.setBounds(100, 300, 450, 350);
		pnl1.setOpaque(false);
		pnl2.setBounds(650, 300, 450, 350);
		pnl2.setOpaque(false);
		pnl3.setBounds(780, 350, 300, 300);
		pnl3.setOpaque(false);

		previousEnroll = new JButton("이전");
		previousEnroll.setBounds(60, 650, 150, 63);
		frame.add(previousEnroll);

		nextEnroll = new JButton("다음");
		nextEnroll.setBounds(210, 650, 150, 63);
		frame.add(nextEnroll);

		previousParticipate = new JButton("이전");
		previousParticipate.setBounds(450, 650, 150, 63);
		frame.add(previousParticipate);

		nextParticipate = new JButton("다음");
		nextParticipate.setBounds(600, 650, 150, 63);
		frame.add(nextParticipate);

		previousBidinfo = new JButton("이전");
		previousBidinfo.setBounds(820, 650, 150, 63);
		frame.add(previousBidinfo);

		nextBidinfo = new JButton("다음");
		nextBidinfo.setBounds(970, 650, 150, 63);
		frame.add(nextBidinfo);

		JPanel pnl11 = new JPanel();
		pnl11.setOpaque(false);
		JPanel pnl12 = new JPanel();
		pnl12.setOpaque(false);
		JPanel pnl13 = new JPanel();
		pnl13.setOpaque(false);
		JPanel pnl14 = new JPanel();
		pnl14.setOpaque(false);
		JPanel pnl15 = new JPanel();
		pnl15.setOpaque(false);
		JPanel pnl21 = new JPanel();
		pnl21.setOpaque(false);
		JPanel pnl22 = new JPanel();
		pnl22.setOpaque(false);
		JPanel pnl23 = new JPanel();
		pnl23.setOpaque(false);
		JPanel pnl24 = new JPanel();
		pnl24.setOpaque(false);
		JPanel pnl25 = new JPanel();
		pnl25.setOpaque(false);
		JPanel pnl31 = new JPanel();
		pnl31.setOpaque(false);
		JPanel pnl32 = new JPanel();
		pnl32.setOpaque(false);
		JPanel pnl33 = new JPanel();
		pnl33.setOpaque(false);
		JPanel pnl34 = new JPanel();
		pnl34.setOpaque(false);
		JPanel pnl35 = new JPanel();
		pnl35.setOpaque(false);

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

		contentPane.add(pnl1);
		contentPane.add(pnl2);
//		contentPane.add(pnl3);

//
//		JLabel lblNewLabel = new JLabel("등록물품");
//		lblNewLabel.setBounds(138, 258, 125, 15);
//		contentPane.add(lblNewLabel);
//
//		JLabel lblNewLabel_1 = new JLabel("입찰물품");
//		lblNewLabel_1.setBounds(492, 258, 57, 15);
//		contentPane.add(lblNewLabel_1);
//
//		JLabel lblNewLabel_2 = new JLabel("낙찰/유찰 물품");
//		lblNewLabel_2.setBounds(842, 258, 125, 15);
//		contentPane.add(lblNewLabel_2);
		frame.getContentPane().add(contentPane);
		frame.setVisible(true);

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

			// 스케줄러가 job의 key를 가지고 있으면 다시 scheduleJob을 생성하지 않도록
			if (!scheduler.checkExists(job2.getKey())) {
				scheduler.scheduleJob(job2, trigger2);
			}
			scheduler.start();
		} catch (SchedulerException e) {
			e.printStackTrace();
		}

	}

	private static void initialLabel() {
		for (int i = 0; i < names.length; i++) {
			names[i].setText("");
			names[i].setHorizontalAlignment(SwingConstants.RIGHT);
			names[i].setVerticalAlignment(SwingConstants.CENTER);
			Font font = new Font("맑은 고딕", Font.BOLD, 18);
			names[i].setFont(font);
		}

		for (int i = 0; i < times.length; i++) {
			times[i].setText("");
			times[i].setHorizontalAlignment(SwingConstants.CENTER);
			times[i].setVerticalAlignment(SwingConstants.CENTER);
			Font font = new Font("맑은 고딕", Font.BOLD, 18);
			times[i].setFont(font);
		}

		for (int i = 0; i < bids.length; i++) {
			bids[i].setText("");
		}

		for (int i = 0; i < prices.length; i++) {
			prices[i].setText("");
			prices[i].setHorizontalAlignment(SwingConstants.RIGHT);
			prices[i].setVerticalAlignment(SwingConstants.CENTER);
			prices[i].setFont(prices[i].getFont().deriveFont(18f));
			Font font = new Font("맑은 고딕", Font.BOLD, 18);
			prices[i].setFont(font);
		}

		for (int i = 0; i < images.length; i++) {
			images[i].setIcon(null);
		}
	}

	public static String TimeFormatString(LocalDateTime startTime) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd일 HH:mm:ss");
		return startTime.format(formatter);
	}

	public static ImageIcon iconSize(ImageIcon icon) {
		Image img = icon.getImage();
		Image changeSize = img.getScaledInstance(70, 70, Image.SCALE_SMOOTH);
		return new ImageIcon(changeSize);
	}

	static int cntEnroll = 0;

	private static JButton nextBidinfo;

	public static void updatLabel(LocalDateTime now) {
		ListRepository repo = new ListRepository();
		Connection conn = null;

		try {
			conn = DBUtil.getConnection();
			List<EnrollParticipate> enrollList = repo.getEnrollment(data.getCurrentUser().getNo());

			EnrollParticipate enroll = null;
			
			initialLabel();

			if (enrollList != null) {

				if (cntEnroll >= 4) {
					previousEnroll.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							cntEnroll -= 4;
	
						}
					});

				}

				nextEnroll.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						cntEnroll += 4;
	
					}
				});
				
				
				
				for (int i=cntEnroll; i<cntEnroll+4 && i<enrollList.size(); i++) {
					
					enroll = enrollList.get(i);
					
					if (enroll == null) {
						continue;
					} else {
						
						switch (i%4) {
						
						case 0: {
							lblPrice11.setText(formatInt(enroll.getProductPriceNow()));
							lblPrice11.setPreferredSize(new Dimension(140, lblPrice11.getPreferredSize().height));
							
							lblImage11.setIcon(iconSize(enroll.getImage()));
							
							String result11 = durationFailed(enroll.getEndTime(), now);
							lblTime11.setText(result11);
							lblTime11.setPreferredSize(new Dimension(120, lblTime11.getPreferredSize().height));
							
							
							lblName11.setText(enroll.getProductname());
							
							lblName11.setPreferredSize(new Dimension(100, lblName11.getPreferredSize().height));
							break;
						}
						case 1: {
							lblPrice12.setText(formatInt(enroll.getProductPriceNow()));
							lblPrice12.setPreferredSize(new Dimension(140, lblPrice12.getPreferredSize().height));
							
							lblImage12.setIcon(iconSize(enroll.getImage()));
							
							String result12 = durationFailed(enroll.getEndTime(), now);
							lblTime12.setText(result12);
							lblTime12.setPreferredSize(new Dimension(120, lblTime12.getPreferredSize().height));
							
							lblName12.setText(enroll.getProductname());
							lblName12.setPreferredSize(new Dimension(100, lblName12.getPreferredSize().height));
							break;
						}
						case 2: {
							lblPrice13.setText(formatInt(enroll.getProductPriceNow()));
							lblPrice13.setPreferredSize(new Dimension(140, lblPrice13.getPreferredSize().height));
							
							lblImage13.setIcon(iconSize(enroll.getImage()));
							
							String result13 = durationFailed(enroll.getEndTime(), now);
							lblTime13.setText(result13);
							lblTime13.setPreferredSize(new Dimension(120, lblTime13.getPreferredSize().height));
							
							lblName13.setText(enroll.getProductname());
							lblName13.setPreferredSize(new Dimension(100, lblName13.getPreferredSize().height));
							break;
						}
						case 3: {
							lblPrice14.setText(formatInt(enroll.getProductPriceNow()));
							lblPrice14.setPreferredSize(new Dimension(140, lblPrice14.getPreferredSize().height));
							
							lblImage14.setIcon(iconSize(enroll.getImage()));
							
							String result14 = durationFailed(enroll.getEndTime(), now);
							lblTime14.setText(result14);
							lblTime14.setPreferredSize(new Dimension(120, lblTime14.getPreferredSize().height));
							
							lblName14.setText(enroll.getProductname());
							lblName14.setPreferredSize(new Dimension(100, lblName14.getPreferredSize().height));
							break;
						}
						case 4: {
							lblPrice15.setText(formatInt(enroll.getProductPriceNow()));
							lblPrice15.setPreferredSize(new Dimension(140, lblPrice15.getPreferredSize().height));
							
							lblImage15.setIcon(iconSize(enroll.getImage()));
							
							String result15 = durationFailed(enroll.getEndTime(), now);
							lblTime15.setText(result15);
							lblTime15.setPreferredSize(new Dimension(120, lblTime15.getPreferredSize().height));
							
							lblName15.setText(enroll.getProductname());
							lblName15.setPreferredSize(new Dimension(100, lblName15.getPreferredSize().height));
							break;
						}
						}
					}
				}
			}

			List<EnrollParticipate> participateList = repo.getParticipateList(data.getCurrentUser().getNo());
			
				for (int i = 0; i < participateList.size(); i++) {
						switch (i) {
						case 0: {
							lblPrice21.setText(formatInt(participateList.get(i).getProductPriceNow()));
							lblPrice21.setPreferredSize(new Dimension(140, lblPrice21.getPreferredSize().height));
							
							lblImage21.setIcon(iconSize(participateList.get(i).getImage()));
							
							String result21 = durationBid(participateList.get(i).getEndTime(), now);
							lblTime21.setText(result21);
							lblTime21.setPreferredSize(new Dimension(120, lblTime21.getPreferredSize().height));
							
							lblName21.setText(participateList.get(i).getProductname());
							lblName21.setPreferredSize(new Dimension(100, lblName21.getPreferredSize().height));
							
							break;
						}
						case 1: {
							lblPrice22.setText(formatInt(participateList.get(i).getProductPriceNow()));
							lblPrice22.setPreferredSize(new Dimension(140, lblPrice22.getPreferredSize().height));

							lblImage22.setIcon(iconSize(participate.getImage()));

							String result22 = duration(participate.getEndTime(), now);
							lblTime22.setText(result22);
							lblTime22.setPreferredSize(new Dimension(120, lblTime22.getPreferredSize().height));
							
							lblName22.setText(participateList.get(i).getProductname());
							lblName22.setPreferredSize(new Dimension(100, lblName22.getPreferredSize().height));
							break;
						}
						case 2: {
							lblPrice23.setText(formatInt(participateList.get(i).getProductPriceNow()));
							lblPrice23.setPreferredSize(new Dimension(140, lblPrice23.getPreferredSize().height));
							
							lblImage23.setIcon(iconSize(participateList.get(i).getImage()));
							
							String result23 = durationBid(participateList.get(i).getEndTime(), now);
							lblTime23.setText(result23);
							lblTime23.setPreferredSize(new Dimension(120, lblTime23.getPreferredSize().height));
							
							lblName23.setText(participateList.get(i).getProductname());
							lblName23.setPreferredSize(new Dimension(100, lblName23.getPreferredSize().height));
							break;
						}
						case 3: {
							lblPrice24.setText(formatInt(participateList.get(i).getProductPriceNow()));
							lblPrice24.setPreferredSize(new Dimension(140, lblPrice24.getPreferredSize().height));
							
							lblImage24.setIcon(iconSize(participateList.get(i).getImage()));
							
							String result24 = durationBid(participateList.get(i).getEndTime(), now);
							lblTime24.setText(result24);
							lblTime24.setPreferredSize(new Dimension(120, lblTime24.getPreferredSize().height));
							
							lblName24.setText(participateList.get(i).getProductname());
							lblName24.setPreferredSize(new Dimension(100, lblName24.getPreferredSize().height));
							break;
						}
						case 4: {
							lblPrice25.setText(formatInt(participateList.get(i).getProductPriceNow()));
							lblPrice25.setPreferredSize(new Dimension(140, lblPrice25.getPreferredSize().height));
							
							lblImage25.setIcon(iconSize(participateList.get(i).getImage()));
							
							String result25 = durationBid(participateList.get(i).getEndTime(), now);
							lblTime25.setText(result25);
							lblTime25.setPreferredSize(new Dimension(120, lblTime25.getPreferredSize().height));
							
							lblName25.setText(participateList.get(i).getProductname());
							lblName25.setPreferredSize(new Dimension(100, lblName25.getPreferredSize().height));
							
							break;
						}
						}
					}
//				}
//			}

//			List<Bidinfo> bidinfoList = repo.getBidinfoList(data.getCurrentUser().getNo());
//			Bidinfo bid = null;
//			if (bidinfoList != null) {
//				for (int i = 0; i < bidinfoList.size(); i++) {
//					bid = bidinfoList.get(i);
//					if (bid == null) {
//						continue;
//					} else {
//						switch (i) {
//						case 0: {
//							lblPrice31.setText(formatInt(bid.getProductPriceFinal()));
//							lblPrice31.setPreferredSize(new Dimension(140, lblPrice31.getPreferredSize().height));
//							lblPrice31.setHorizontalAlignment(SwingConstants.RIGHT);
//							
//							lblImage31.setIcon(iconSize(bid.getImage()));
//							
//							lblBid1.setText(bidOrNot(bid.isBid()));
//							lblBid1.setPreferredSize(new Dimension(30, lblBid1.getPreferredSize().height));
//							lblBid1.setHorizontalAlignment(SwingConstants.RIGHT);
//							
//							lblName31.setText(bid.getProductname());
//							lblName31.setPreferredSize(new Dimension(100, lblName31.getPreferredSize().height));
//							lblName31.setHorizontalAlignment(SwingConstants.RIGHT);
//							break;
//						}
//						case 1: {
//							lblPrice32.setText(formatInt(bid.getProductPriceFinal()));
//							lblPrice32.setPreferredSize(new Dimension(140, lblPrice32.getPreferredSize().height));
//							lblPrice32.setHorizontalAlignment(SwingConstants.RIGHT);
//							
//							lblImage32.setIcon(iconSize(bid.getImage()));
//							
//							lblBid2.setText(bidOrNot(bid.isBid()));
//							lblBid2.setPreferredSize(new Dimension(30, lblBid2.getPreferredSize().height));
//							lblBid2.setHorizontalAlignment(SwingConstants.RIGHT);
//							
//							lblName32.setText(bid.getProductname());
//							lblName32.setPreferredSize(new Dimension(100, lblName32.getPreferredSize().height));
//							lblName32.setHorizontalAlignment(SwingConstants.RIGHT);
//							
//							break;
//						}
//						case 2: {
//							lblPrice33.setText(formatInt(bid.getProductPriceFinal()));
//							lblPrice33.setPreferredSize(new Dimension(140, lblPrice33.getPreferredSize().height));
//							lblPrice33.setHorizontalAlignment(SwingConstants.RIGHT);
//							
//							lblImage33.setIcon(iconSize(bid.getImage()));
//							
//							lblBid3.setText(bidOrNot(bid.isBid()));
//							lblBid3.setPreferredSize(new Dimension(30, lblBid3.getPreferredSize().height));
//							lblBid3.setHorizontalAlignment(SwingConstants.RIGHT);
//							
//							lblName33.setText(bid.getProductname());
//							lblName33.setPreferredSize(new Dimension(100, lblName33.getPreferredSize().height));
//							lblName33.setHorizontalAlignment(SwingConstants.RIGHT);
//							
//							break;
//						}
//						case 3: {
//							lblPrice34.setText(formatInt(bid.getProductPriceFinal()));
//							lblPrice34.setPreferredSize(new Dimension(140, lblPrice34.getPreferredSize().height));
//							lblPrice34.setHorizontalAlignment(SwingConstants.RIGHT);
//							
//							lblImage34.setIcon(iconSize(bid.getImage()));
//							
//							lblBid4.setText(bidOrNot(bid.isBid()));
//							lblBid4.setPreferredSize(new Dimension(30, lblBid4.getPreferredSize().height));
//							lblBid4.setHorizontalAlignment(SwingConstants.RIGHT);
//							
//							lblName34.setText(bid.getProductname());
//							lblName34.setPreferredSize(new Dimension(100, lblName34.getPreferredSize().height));
//							lblName34.setHorizontalAlignment(SwingConstants.RIGHT);
//							
//							break;
//						}
//						case 4: {
//							lblPrice35.setText(formatInt(bid.getProductPriceFinal()));
//							lblPrice35.setPreferredSize(new Dimension(140, lblPrice35.getPreferredSize().height));
//							lblPrice35.setHorizontalAlignment(SwingConstants.RIGHT);
//							
//							lblImage35.setIcon(iconSize(bid.getImage()));
//							
//							lblBid5.setText(bidOrNot(bid.isBid()));
//							lblBid5.setPreferredSize(new Dimension(30, lblBid5.getPreferredSize().height));
//							lblBid5.setHorizontalAlignment(SwingConstants.RIGHT);
//							
//							lblName35.setText(bid.getProductname());
//							lblName35.setPreferredSize(new Dimension(100, lblName35.getPreferredSize().height));
//							lblName35.setHorizontalAlignment(SwingConstants.RIGHT);
//							
//							break;
//						}
//						}
//					}
//				}
//			}
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

	public static String durationFailed(LocalDateTime targetDateTime, LocalDateTime now) {
	    Duration duration = Duration.between(now, targetDateTime);
	    long days = duration.toDays();
	    long hours = duration.toHours() % 24;
	    long minutes = duration.toMinutes() % 60;
	    long seconds = duration.getSeconds() % 60;

	    if (duration.isNegative()) {
	        return "유찰";
	    } else {
	        return String.format("%02d일 %02d:%02d:%02d", days, hours, minutes, seconds);
	    }
	}
	
	public static String durationBid(LocalDateTime targetDateTime, LocalDateTime now) {
	    Duration duration = Duration.between(now, targetDateTime);
	    long days = duration.toDays();
	    long hours = duration.toHours() % 24;
	    long minutes = duration.toMinutes() % 60;
	    long seconds = duration.getSeconds() % 60;

	    if (duration.isNegative()) {
	        return "낙찰";
	    } else {
	        return String.format("%02d일 %02d:%02d:%02d", days, hours, minutes, seconds);
	    }
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