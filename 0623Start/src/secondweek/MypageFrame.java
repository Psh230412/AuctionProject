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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

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

	private static JLabel[] images = new JLabel[10];
	private static JLabel[] names = new JLabel[10];
	private static JLabel[] times = new JLabel[10];
	private static JLabel[] prices = new JLabel[10];

	private static JButton previousEnroll;
	private static JButton nextEnroll;
	private static JLabel lblNum1;

	private static JButton previousParticipate;
	private static JButton nextParticipate;
	private static JLabel lblNum2;

//	private static JButton previousBidinfo;

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
	}

	public MypageFrame(DataBase data) {
		this.data = data;
		timer = new Timer();

		timer.inputSuccessbidinfo();
		timer.setIsBid();

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

				Image image = toolkit.getImage("img/myPage_1.jpg");
				g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
			}
		};

		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel userNameLb = new JLabel();
		userNameLb.setBounds(180, 115, 188, 67);
		userNameLb.setText(data.getCurrentUser().getName());
		userNameLb.setFont(new Font("돋움", Font.BOLD, 20));
		userNameLb.setForeground(Color.BLACK);
		contentPane.add(userNameLb);

		JButton resetBtn = new JButton();
		resetBtn.setBounds(400, 123, 200, 90);
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
		resetBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new ChangeInformationFrame(data);
				frame.dispose();
				data.setIndex(0);
				data.setIndexPar(0);
			}
		});
		contentPane.add(resetBtn);

		JButton resgistBtn = new JButton();
		resgistBtn.setBounds(650, 123, 200, 90);
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
				frame.dispose();
				data.setIndex(0);
				data.setIndexPar(0);
			}
		});

		contentPane.add(resgistBtn);

		JButton mainBtn = new JButton();
		mainBtn.setBounds(50, 30, 110, 65);
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
				frame.dispose();
				data.setIndex(0);
				data.setIndexPar(0);
			}
		});

		contentPane.add(mainBtn);

		JPanel pnl1 = new JPanel(new GridLayout(6, 0));
		JPanel pnl2 = new JPanel(new GridLayout(6, 0));
		JPanel pnl3 = new JPanel(new GridLayout(5, 0));

		pnl1.setBounds(100, 300, 450, 420);
		pnl1.setOpaque(false);
		pnl2.setBounds(650, 300, 450, 420);
		pnl2.setOpaque(false);
		pnl3.setBounds(780, 350, 300, 300);
		pnl3.setOpaque(false);

		previousEnroll = new JButton();
		ImageIcon imgpreviousEnroll = new ImageIcon("img/previous_1.png");
		previousEnroll.setContentAreaFilled(false);
		previousEnroll.setBorderPainted(false);
		previousEnroll.setIcon(imgpreviousEnroll);

		previousEnroll.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseExited(MouseEvent e) {
				ImageIcon imgpreviousEnroll = new ImageIcon("img/previous_1.png");
				previousEnroll.setIcon(imgpreviousEnroll);

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				ImageIcon imgpreviousEnroll = new ImageIcon("img/previous.png");
				previousEnroll.setIcon(imgpreviousEnroll);

			}

		});
		previousEnroll.setBounds(190, 710, 110, 50);
		frame.add(previousEnroll);

		lblNum1 = new JLabel(" - 1 - ");
		lblNum1.setBounds(300, 710, 80, 40);
		frame.add(lblNum1);

		nextEnroll = new JButton();
		nextEnroll.setBounds(400, 710, 110, 50);
		nextEnroll.setContentAreaFilled(false);
		nextEnroll.setBorderPainted(false);
		ImageIcon imgnextEnroll = new ImageIcon("img/next_1.png");
		nextEnroll.setIcon(imgnextEnroll);

		nextEnroll.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseExited(MouseEvent e) {
				ImageIcon imgnextEnroll = new ImageIcon("img/next_1.png");
				nextEnroll.setIcon(imgnextEnroll);

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				ImageIcon imgnextEnroll = new ImageIcon("img/next.png");
				nextEnroll.setIcon(imgnextEnroll);

			}

		});
		frame.add(nextEnroll);

		previousParticipate = new JButton();
		ImageIcon imgpreviousEnroll1 = new ImageIcon("img/previous_1.png");
		previousParticipate.setContentAreaFilled(false);
		previousParticipate.setBorderPainted(false);
		previousParticipate.setIcon(imgpreviousEnroll1);
		previousParticipate.setIcon(imgpreviousEnroll1);
		previousParticipate.setBounds(720, 710, 110, 50);
		previousParticipate.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseExited(MouseEvent e) {
				ImageIcon imgpreviousEnroll1 = new ImageIcon("img/previous_1.png");
				previousParticipate.setIcon(imgpreviousEnroll1);

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				ImageIcon imgpreviousEnroll1 = new ImageIcon("img/previous.png");
				previousParticipate.setIcon(imgpreviousEnroll1);

			}

		});

		frame.add(previousParticipate);

		lblNum2 = new JLabel(" - 1 - ");
		lblNum2.setBounds(830, 710, 80, 40);
		frame.add(lblNum2);

		nextParticipate = new JButton();
		nextParticipate.setBounds(930, 710, 110, 50);
		nextParticipate.setContentAreaFilled(false);
		nextParticipate.setBorderPainted(false);
		ImageIcon imgnextEnroll1 = new ImageIcon("img/next_1.png");
		nextParticipate.setIcon(imgnextEnroll1);

		nextParticipate.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseExited(MouseEvent e) {
				ImageIcon imgnextEnroll1 = new ImageIcon("img/next_1.png");
				nextParticipate.setIcon(imgnextEnroll1);

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				ImageIcon imgnextEnroll1 = new ImageIcon("img/next.png");
				nextParticipate.setIcon(imgnextEnroll1);

			}

		});
		frame.add(nextParticipate);

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

		previousEnroll.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (data.getIndex() >= 5) {
					data.setIndex(data.getIndex() - 5);
				}
			}
		});

		nextEnroll.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ListRepository repo = new ListRepository();
//				List<EnrollParticipate> enrollList = repo.getEnrollment(data.getCurrentUser().getNo());
				List<EnrollParticipate> enrollList = new ArrayList<EnrollParticipate>();

				for (Map.Entry<Integer, EnrollParticipate> entry : Cache.enrollCacheMap.entrySet()) {
					EnrollParticipate value = entry.getValue();
					enrollList.add(value);
				}
				if (Math.floor((enrollList.size() - 1) / 5) > (data.getIndex() / 5)) {
					data.setIndex(data.getIndex() + 5);
				}
			}
		});

		previousParticipate.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (data.getIndexPar() >= 5) {
					data.setIndexPar(data.getIndexPar() - 5);
				}
			}
		});

		nextParticipate.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ListRepository repo = new ListRepository();
//				List<EnrollParticipate> participateList = repo.getParticipateList(data.getCurrentUser().getNo());

				List<EnrollParticipate> participateList = new ArrayList<EnrollParticipate>();

				for (Map.Entry<Integer, EnrollParticipate> entry : Cache.participateCacheMap.entrySet()) {
					EnrollParticipate value = entry.getValue();
					participateList.add(value);
				}

				if (Math.floor((participateList.size() - 1) / 5) > (data.getIndexPar() / 5)) {
					data.setIndexPar(data.getIndexPar() + 5);
				}
			}
		});

		inputImages();
		inputNames();
		inputTimes();
		inputPrices();

		for (int i = 0; i < names.length; i++) {
			names[i].setForeground(Color.darkGray);
		}

		for (int i = 0; i < times.length; i++) {
			times[i].setForeground(Color.RED);
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

		frame.getContentPane().add(contentPane);
		frame.setVisible(true);

		LocalDateTime now = LocalDateTime.now();
		updatLabel(now);

		try {
			scheduler = StdSchedulerFactory.getDefaultScheduler();

			JobDataMap jobDataMap = new JobDataMap();

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
			names[i].setHorizontalAlignment(SwingConstants.LEFT);
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

		for (int i = 0; i < prices.length; i++) {
			prices[i].setText("");
			prices[i].setHorizontalAlignment(SwingConstants.RIGHT);
			prices[i].setVerticalAlignment(SwingConstants.CENTER);
			Font font = new Font("맑은 고딕", Font.BOLD, 18);
			prices[i].setFont(font);
		}

		for (int i = 0; i < images.length; i++) {
			images[i].setIcon(null);
		}
		lblNum1.setText(" - " + String.valueOf((data.getIndex() / 5) + 1) + " - ");
		lblNum2.setText(" - " + String.valueOf((data.getIndexPar() / 5) + 1) + " - ");
		lblNum1.setHorizontalAlignment(SwingConstants.CENTER);
		Font font = new Font("맑은 고딕", Font.BOLD, 25);
		lblNum1.setFont(font);

		lblNum2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNum2.setFont(font);
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

	public static void updatLabel(LocalDateTime now) {
		ListRepository repo = new ListRepository();
		Connection conn = null;
		lblNum1.setText(" - " + String.valueOf((data.getIndex() / 5) + 1) + " - ");
		lblNum2.setText(" - " + String.valueOf((data.getIndexPar() / 5) + 1) + " - ");
		initialLabel();

		try {
			conn = DBUtil.getConnection();
			// updatLabel 호출 될때마다 cache와 원본테이블 교차검증
			Cache.isProductnoparticipateCacheMap(data.getCurrentUser().getNo(), conn);
			Cache.isProductnoEnrollCacheMap(data.getCurrentUser().getNo(), conn);

//			List<EnrollParticipate> enrollList = repo.getEnrollment(data.getCurrentUser().getNo());

			List<EnrollParticipate> enrollList = new ArrayList<>();

			for (Map.Entry<Integer, EnrollParticipate> entry : Cache.enrollCacheMap.entrySet()) {
			    EnrollParticipate value = entry.getValue();
			    enrollList.add(value);
			}

			Collections.sort(enrollList, new Comparator<EnrollParticipate>() {
			    @Override
			    public int compare(EnrollParticipate o1, EnrollParticipate o2) {
			        return o1.getEndTime().compareTo(o2.getEndTime());
			    }
			});

			List<EnrollParticipate> negativeTimeList = new ArrayList<>();
			List<EnrollParticipate> positiveTimeList = new ArrayList<>();

			for (EnrollParticipate enrollParticipate : enrollList) {
			    long diff = enrollParticipate.getEndTime().compareTo(now);
			    if (diff < 0) {
			        negativeTimeList.add(enrollParticipate);
			    } else {
			        positiveTimeList.add(enrollParticipate);
			    }
			}

			Collections.reverse(negativeTimeList);

			enrollList.clear();
			enrollList.addAll(positiveTimeList);
			enrollList.addAll(negativeTimeList);


			EnrollParticipate enroll = null;

			if (enrollList != null) {
				int count = 0;
				for (int i = data.getIndex(); i < enrollList.size(); i++) {
					enroll = enrollList.get(i);
					count++;
					if (count == 6) {
						break;
					}
					if (enroll == null) {
						continue;
					} else {
						switch (i % 5) {
						case 0: {
							lblPrice11.setText(formatInt(enroll.getProductPriceNow()));
							lblPrice11.setPreferredSize(new Dimension(140, lblPrice11.getPreferredSize().height));

							lblImage11.setIcon(iconSize(enroll.getImage()));

							String result11 = durationFailed(enroll.getEndTime(), now, enroll.getAuctionno(), repo);
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

							String result12 = durationFailed(enroll.getEndTime(), now, enroll.getAuctionno(), repo);
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

							String result13 = durationFailed(enroll.getEndTime(), now, enroll.getAuctionno(), repo);
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

							String result14 = durationFailed(enroll.getEndTime(), now, enroll.getAuctionno(), repo);
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

							String result15 = durationFailed(enroll.getEndTime(), now, enroll.getAuctionno(), repo);
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

//			List<EnrollParticipate> participateList = repo.getParticipateList(data.getCurrentUser().getNo());
			List<EnrollParticipate> participateList = new ArrayList<>();

			for (Map.Entry<Integer, EnrollParticipate> entry : Cache.participateCacheMap.entrySet()) {
			    EnrollParticipate value = entry.getValue();
			    participateList.add(value);
			}

			Collections.sort(participateList, new Comparator<EnrollParticipate>() {
			    @Override
			    public int compare(EnrollParticipate o1, EnrollParticipate o2) {
			        return o1.getEndTime().compareTo(o2.getEndTime());
			    }
			});

			List<EnrollParticipate> negativeTimeList2 = new ArrayList<>();
			List<EnrollParticipate> positiveTimeList2 = new ArrayList<>();

			for (EnrollParticipate participate : participateList) {
			    long diff = participate.getEndTime().compareTo(now);
			    if (diff < 0) {
			        negativeTimeList2.add(participate);
			    } else {
			        positiveTimeList2.add(participate);
			    }
			}
			
			Collections.reverse(negativeTimeList2);

			participateList.clear();
			participateList.addAll(positiveTimeList2);
			participateList.addAll(negativeTimeList2);

			

			EnrollParticipate participate = null;
			if (participateList != null) {
				int count2 = 0;
				for (int i = data.getIndexPar(); i < participateList.size(); i++) {
					participate = participateList.get(i);
					count2++;
					if (count2 == 6) {
						break;
					}
					if (enroll == null) {
						continue;
					} else {
						switch (i % 5) {
						case 0: {
							lblPrice21.setText(formatInt(participate.getProductPriceNow()));
							lblPrice21.setPreferredSize(new Dimension(140, lblPrice21.getPreferredSize().height));

							lblImage21.setIcon(iconSize(participate.getImage()));

							String result21 = durationBid(participate.getEndTime(), now);
							lblTime21.setText(result21);
							lblTime21.setPreferredSize(new Dimension(120, lblTime21.getPreferredSize().height));

							lblName21.setText(participate.getProductname());
							lblName21.setPreferredSize(new Dimension(100, lblName21.getPreferredSize().height));

							break;
						}
						case 1: {
							lblPrice22.setText(formatInt(participate.getProductPriceNow()));
							lblPrice22.setPreferredSize(new Dimension(140, lblPrice22.getPreferredSize().height));

							lblImage22.setIcon(iconSize(participate.getImage()));

							String result22 = durationBid(participate.getEndTime(), now);
							lblTime22.setText(result22);
							lblTime22.setPreferredSize(new Dimension(120, lblTime22.getPreferredSize().height));

							lblName22.setText(participate.getProductname());
							lblName22.setPreferredSize(new Dimension(100, lblName22.getPreferredSize().height));
							break;
						}
						case 2: {
							lblPrice23.setText(formatInt(participate.getProductPriceNow()));
							lblPrice23.setPreferredSize(new Dimension(140, lblPrice23.getPreferredSize().height));

							lblImage23.setIcon(iconSize(participate.getImage()));

							String result23 = durationBid(participate.getEndTime(), now);
							lblTime23.setText(result23);
							lblTime23.setPreferredSize(new Dimension(120, lblTime23.getPreferredSize().height));

							lblName23.setText(participate.getProductname());
							lblName23.setPreferredSize(new Dimension(100, lblName23.getPreferredSize().height));
							break;
						}
						case 3: {
							lblPrice24.setText(formatInt(participate.getProductPriceNow()));
							lblPrice24.setPreferredSize(new Dimension(140, lblPrice24.getPreferredSize().height));

							lblImage24.setIcon(iconSize(participate.getImage()));

							String result24 = durationBid(participate.getEndTime(), now);
							lblTime24.setText(result24);
							lblTime24.setPreferredSize(new Dimension(120, lblTime24.getPreferredSize().height));

							lblName24.setText(participate.getProductname());
							lblName24.setPreferredSize(new Dimension(100, lblName24.getPreferredSize().height));
							break;
						}
						case 4: {
							lblPrice25.setText(formatInt(participate.getProductPriceNow()));
							lblPrice25.setPreferredSize(new Dimension(140, lblPrice25.getPreferredSize().height));

							lblImage25.setIcon(iconSize(participate.getImage()));

							String result25 = durationBid(participate.getEndTime(), now);
							lblTime25.setText(result25);
							lblTime25.setPreferredSize(new Dimension(120, lblTime25.getPreferredSize().height));

							lblName25.setText(participate.getProductname());
							lblName25.setPreferredSize(new Dimension(100, lblName25.getPreferredSize().height));

							break;
						}
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

	public static String durationFailed(LocalDateTime targetDateTime, LocalDateTime now, int auctionno,
			ListRepository repo) {
		Duration duration = Duration.between(now, targetDateTime);
		List<Integer> list = repo.getIsBid();

		long days = duration.toDays();
		long hours = duration.toHours() % 24;
		long minutes = duration.toMinutes() % 60;
		long seconds = duration.getSeconds() % 60;

		if (duration.isNegative()) {
			if (list.size() != 0) {
				for (int i = 0; i < list.size(); i++) {
					if (list.get(i) == auctionno) {
						return "낙찰";
					}
				}
			}
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