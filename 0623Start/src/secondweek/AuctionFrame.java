package secondweek;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
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

public class AuctionFrame extends JFrame {

	private Scheduler scheduler;
	private static Timer timer;
	private static DataBase data;
	private static Auction auction;

	private static JTextField searchTab;

	private static JLabel lblImage1;
	private static JLabel lblImage2;
	private static JLabel lblImage3;
	private static JLabel lblImage4;
	private static JLabel lblImage5;
	private static JLabel lblImage6;
	private static JLabel lblImage7;
	private static JLabel lblImage8;
	private static JLabel lblImage9;
	private static JLabel lblImage10;

	JLabel[] lblImageArr = new JLabel[10];

	private static JLabel lblTime1;
	private static JLabel lblTime2;
	private static JLabel lblTime3;
	private static JLabel lblTime4;
	private static JLabel lblTime5;
	private static JLabel lblTime6;
	private static JLabel lblTime7;
	private static JLabel lblTime8;
	private static JLabel lblTime9;
	private static JLabel lblTime10;

	JLabel[] lblTimeArr = new JLabel[10];

	private static JLabel lblPrice1;
	private static JLabel lblPrice2;
	private static JLabel lblPrice3;
	private static JLabel lblPrice4;
	private static JLabel lblPrice5;
	private static JLabel lblPrice6;
	private static JLabel lblPrice7;
	private static JLabel lblPrice8;
	private static JLabel lblPrice9;
	private static JLabel lblPrice10;

	JLabel[] lblPriceArr = new JLabel[10];

	private static JLabel lblName1;
	private static JLabel lblName2;
	private static JLabel lblName3;
	private static JLabel lblName4;
	private static JLabel lblName5;
	private static JLabel lblName6;
	private static JLabel lblName7;
	private static JLabel lblName8;
	private static JLabel lblName9;
	private static JLabel lblName10;

	JLabel[] lblNameArr = new JLabel[10];

	private static JButton previousEnroll;
	private static JButton nextEnroll;
	private static JLabel lblNum1;

	private JFrame frame;
	private JPanel contentPane;
	private static JButton[] btns;

	private static List<Auction> list;

	private static boolean checkBtn = false;
	private static String searchText;

	public AuctionFrame(DataBase data) {
		for (int i = 0; i < lblImageArr.length; i++) {
			lblImageArr[i] = new JLabel();
		}
		for (int i = 0; i < lblNameArr.length; i++) {
			lblNameArr[i] = new JLabel();
		}
		for (int i = 0; i < lblPriceArr.length; i++) {
			lblPriceArr[i] = new JLabel();
		}
		for (int i = 0; i < lblTimeArr.length; i++) {
			lblTimeArr[i] = new JLabel();
		}

		this.data = data;
		timer = new Timer();
		frame = new JFrame();
		frame.setSize(1200, 1000);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		contentPane = new JPanel() {

			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);

				Toolkit toolkit = Toolkit.getDefaultToolkit();

				Image image = toolkit.getImage("img/mainPage.png");
				g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
			}
		};

		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel userLbl = new JLabel("여기에 로그인 중인 아이디 표시");
		userLbl.setText(data.getCurrentUser().getName());
		userLbl.setBounds(105, 50, 100, 20);

		JButton mypageBtn = new JButton("마이페이지");
		mypageBtn.setBounds(600, 150, 100, 20);
		mypageBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new MypageFrame(data);
				frame.setVisible(false);

//				try {
//					scheduler.shutdown();
//				} catch (SchedulerException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}

			}
		});

		JButton logoutBtn = new JButton("로그아웃");
		logoutBtn.setBounds(750, 150, 100, 20);
		logoutBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int choice = JOptionPane.showConfirmDialog(null, "로그아웃하시겠습니까?", "로그아웃", JOptionPane.YES_NO_OPTION);
				if (choice == JOptionPane.YES_OPTION) {
					JOptionPane.showMessageDialog(null, "로그아웃되었습니다.");
					DataBase newdata = new DataBase();
					new LoginFrame(newdata);
					frame.setVisible(false);
				}
			}
		});

		JButton exitBtn = new JButton("종료");
		exitBtn.setBounds(900, 150, 100, 20);
		exitBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int choice = JOptionPane.showConfirmDialog(null, "종료하시겠습니까?", "종료", JOptionPane.YES_NO_OPTION);
				if (choice == JOptionPane.YES_OPTION) {
					frame.setVisible(false);
					System.exit(0);
				}
			}
		});
		JButton searchAll = new JButton("전체보기");
		searchAll.setBounds(53, 100, 52, 40);
		searchAll.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				checkBtn = false;
			}
		});

		JButton searchBtn = new JButton("S");
		searchBtn.setBounds(53, 150, 52, 40);
		searchBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				checkBtn = true;
				searchText = searchTab.getText();
//				if (searchTab.getText().length() > 0) {
//					ImageRetriever.listForSearch.clear();
//
//					resetLabel();
//					String searchObject = searchTab.getText();
//					LocalDateTime now = LocalDateTime.now();
//
//					Connection conn = null;
//					PreparedStatement stmt = null;
//					ResultSet rs = null;
//
//					List<Auction> listSearch = ImageRetriever.listForSearch;
//
//					try {
//
//						scheduler.shutdown();
//						conn = DBUtil.getConnection();
//
////					이미지 이름 가격 남은 시간
//
//						while (rs.next()) {
//							Blob imageBlob = rs.getBlob("image");
//							byte[] imageBytes = imageBlob.getBytes(1, (int) imageBlob.length());
//
//							String productname = rs.getString("productname");
//							int finalprice = rs.getInt("finalprice");
//							Timestamp deadline = rs.getTimestamp("deadline");
//
//							ImageRetriever.setImageForSearch(imageBytes, productname, finalprice, deadline);
//
//						}
//
//						for (int i = 0; i < listSearch.size() && i < lblPriceArr.length; i++) {
//							lblPriceArr[i].setText(formatInt(listSearch.get(i).getFinalprice()));
//							lblPriceArr[i]
//									.setPreferredSize(new Dimension(200, lblPriceArr[i].getPreferredSize().height));
//							lblPriceArr[i].setHorizontalAlignment(SwingConstants.CENTER);
//
//							lblNameArr[i].setText(listSearch.get(i).getProductname());
//							lblNameArr[i].setPreferredSize(new Dimension(200, lblNameArr[i].getPreferredSize().height));
//							lblNameArr[i].setHorizontalAlignment(SwingConstants.CENTER);
//
//							lblImageArr[i].setIcon(iconSize(listSearch.get(i).getImageIcon()));
//
//							String result = duration(listSearch.get(i).getDeadline().toLocalDateTime(), now);
//							lblTimeArr[i].setText(result);
//							lblTimeArr[i].setPreferredSize(new Dimension(200, lblTimeArr[i].getPreferredSize().height));
//							lblTimeArr[i].setHorizontalAlignment(SwingConstants.CENTER);
//
//							if (durationTen(listSearch.get(i).getDeadline().toLocalDateTime(), now)) {
//								lblTimeArr[i].setForeground(Color.RED);
//								lblTimeArr[i].setFont(lblTimeArr[i].getFont().deriveFont(15f));
//							}
//
//							btns[i].setVisible(true);
//							list = new ArrayList();
//							list.add(new Auction(i, timer.selectProduct().get(i).getAuctionNo()));
//						}
//					} catch (SQLException e1) {
//						e1.printStackTrace();
//					} catch (SchedulerException e1) {
//						e1.printStackTrace();
//					} finally {
//						DBUtil.close(rs);
//						DBUtil.close(stmt);
//						DBUtil.close(conn);
//					}
//
//				}
			}
		});

		searchTab = new JTextField();
		searchTab.setBounds(105, 150, 300, 40);

		contentPane.add(userLbl);
		contentPane.add(mypageBtn);
		contentPane.add(logoutBtn);
		contentPane.add(exitBtn);
		contentPane.add(searchTab);
		contentPane.add(searchBtn);
		contentPane.add(searchAll);

		JPanel pnl1 = new JPanel();
		pnl1.setLayout(new FlowLayout());
		pnl1.setBounds(50, 200, 200, 300);
		JPanel pnl2 = new JPanel();
		pnl2.setBounds(280, 200, 200, 300);
		JPanel pnl3 = new JPanel();
		pnl3.setBounds(500, 200, 200, 300);
		JPanel pnl4 = new JPanel();
		pnl4.setBounds(720, 200, 200, 300);
		JPanel pnl5 = new JPanel();
		pnl5.setBounds(940, 200, 200, 300);
		JPanel pnl6 = new JPanel();
		pnl6.setBounds(50, 520, 200, 300);
		JPanel pnl7 = new JPanel();
		pnl7.setBounds(280, 520, 200, 300);
		JPanel pnl8 = new JPanel();
		pnl8.setBounds(500, 520, 200, 300);
		JPanel pnl9 = new JPanel();
		pnl9.setBounds(720, 520, 200, 300);
		JPanel pnl10 = new JPanel();
		pnl10.setBounds(940, 520, 200, 300);

		lblImage1 = lblImageArr[0];
		pnl1.add(lblImage1);

		lblImage2 = lblImageArr[1];
		pnl2.add(lblImage2);

		lblImage3 = lblImageArr[2];
		pnl3.add(lblImage3);

		lblImage4 = lblImageArr[3];
		pnl4.add(lblImage4);

		lblImage5 = lblImageArr[4];
		pnl5.add(lblImage5);

		lblImage6 = lblImageArr[5];
		pnl6.add(lblImage6);

		lblImage7 = lblImageArr[6];
		pnl7.add(lblImage7);

		lblImage8 = lblImageArr[7];
		pnl8.add(lblImage8);

		lblImage9 = lblImageArr[8];
		pnl9.add(lblImage9);

		lblImage10 = lblImageArr[9];
		pnl10.add(lblImage10);

		lblName1 = lblNameArr[0];
		pnl1.add(lblName1);

		lblName2 = lblNameArr[1];
		pnl2.add(lblName2);

		lblName3 = lblNameArr[2];
		pnl3.add(lblName3);

		lblName4 = lblNameArr[3];
		pnl4.add(lblName4);

		lblName5 = lblNameArr[4];
		pnl5.add(lblName5);

		lblName6 = lblNameArr[5];
		pnl6.add(lblName6);

		lblName7 = lblNameArr[6];
		pnl7.add(lblName7);

		lblName8 = lblNameArr[7];
		pnl8.add(lblName8);

		lblName9 = lblNameArr[8];
		pnl9.add(lblName9);

		lblName10 = lblNameArr[9];
		pnl10.add(lblName10);

		lblPrice1 = lblPriceArr[0];
		pnl1.add(lblPrice1);

		lblPrice2 = lblPriceArr[1];
		pnl2.add(lblPrice2);

		lblPrice3 = lblPriceArr[2];
		pnl3.add(lblPrice3);

		lblPrice4 = lblPriceArr[3];
		pnl4.add(lblPrice4);

		lblPrice5 = lblPriceArr[4];
		pnl5.add(lblPrice5);

		lblPrice6 = lblPriceArr[5];
		pnl6.add(lblPrice6);

		lblPrice7 = lblPriceArr[6];
		pnl7.add(lblPrice7);

		lblPrice8 = lblPriceArr[7];
		pnl8.add(lblPrice8);

		lblPrice9 = lblPriceArr[8];
		pnl9.add(lblPrice9);

		lblPrice10 = lblPriceArr[9];
		pnl10.add(lblPrice10);

		lblTime1 = lblTimeArr[0];
		pnl1.add(lblTime1);

		lblTime2 = lblTimeArr[1];
		pnl2.add(lblTime2);

		lblTime3 = lblTimeArr[2];
		pnl3.add(lblTime3);

		lblTime4 = lblTimeArr[3];
		pnl4.add(lblTime4);

		lblTime5 = lblTimeArr[4];
		pnl5.add(lblTime5);

		lblTime6 = lblTimeArr[5];
		pnl6.add(lblTime6);

		lblTime7 = lblTimeArr[6];
		pnl7.add(lblTime7);

		lblTime8 = lblTimeArr[7];
		pnl8.add(lblTime8);

		lblTime9 = lblTimeArr[8];
		pnl9.add(lblTime9);

		lblTime10 = lblTimeArr[9];
		pnl10.add(lblTime10);

		JButton viewProductBtn1 = new JButton("경매보기");
		JButton viewProductBtn2 = new JButton("경매보기");
		JButton viewProductBtn3 = new JButton("경매보기");
		JButton viewProductBtn4 = new JButton("경매보기");
		JButton viewProductBtn5 = new JButton("경매보기");
		JButton viewProductBtn6 = new JButton("경매보기");
		JButton viewProductBtn7 = new JButton("경매보기");
		JButton viewProductBtn8 = new JButton("경매보기");
		JButton viewProductBtn9 = new JButton("경매보기");
		JButton viewProductBtn10 = new JButton("경매보기");

		btns = new JButton[] { viewProductBtn1, viewProductBtn2, viewProductBtn3, viewProductBtn4, viewProductBtn5,
				viewProductBtn6, viewProductBtn7, viewProductBtn8, viewProductBtn9, viewProductBtn10 };

		for (int i = 0; i < btns.length; i++) {
			int index = i;
			Font font = new Font("맑은 고딕", Font.BOLD, 14);
			btns[i].setFont(font);
			btns[i].addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (checkBtn) {
						List<Product> products = timer.selectSearchProduct(searchTab.getText());
						if (products.size() >= index) {
							Product product = products.get(list.get(index).getIndex());
							data.setProduct(product);
							new DetailFrame(data, list.get(index).getAuctionNo());
							frame.setVisible(false);
						}

					} else {
						List<Product> products = timer.selectProduct();
						if (products.size() >= index) {
							Product product = products.get(list.get(index).getIndex());
							data.setProduct(product);
							new DetailFrame(data, list.get(index).getAuctionNo());
							frame.setVisible(false);
						}
					}
				}
			});
		}

		previousEnroll = new JButton("이전");
		previousEnroll.setBounds(460, 860, 80, 40);
		contentPane.add(previousEnroll);

		lblNum1 = new JLabel(" - 1 - ");
		lblNum1.setBounds(560, 860, 80, 40);
		contentPane.add(lblNum1);

		nextEnroll = new JButton("다음");
		nextEnroll.setBounds(660, 860, 80, 40);
		contentPane.add(nextEnroll);

		previousEnroll.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (data.getIndexMain() >= 10) {
					data.setIndexMain(data.getIndexMain() - 10);
				}
			}
		});

		nextEnroll.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				if (((timer.selectProduct().size() - 1) / 10) > (data.getIndexMain() / 10)) {
					data.setIndexMain(data.getIndexMain() + 10);
				}
			}
		});

		pnl1.add(viewProductBtn1);
		pnl2.add(viewProductBtn2);
		pnl3.add(viewProductBtn3);
		pnl4.add(viewProductBtn4);
		pnl5.add(viewProductBtn5);
		pnl6.add(viewProductBtn6);
		pnl7.add(viewProductBtn7);
		pnl8.add(viewProductBtn8);
		pnl9.add(viewProductBtn9);
		pnl10.add(viewProductBtn10);

		contentPane.add(pnl1);
		contentPane.add(pnl2);
		contentPane.add(pnl3);
		contentPane.add(pnl4);
		contentPane.add(pnl5);
		contentPane.add(pnl6);
		contentPane.add(pnl7);
		contentPane.add(pnl8);
		contentPane.add(pnl9);
		contentPane.add(pnl10);

		frame.getContentPane().add(contentPane);
		frame.setVisible(true);

		LocalDateTime now = LocalDateTime.now();
		updatLabel(now);

		try {
			scheduler = StdSchedulerFactory.getDefaultScheduler();

			JobDataMap jobDataMap = new JobDataMap();

			JobDetail job = JobBuilder.newJob(AutionUpdateJob.class).withIdentity("labelUpdateJob", "group1")
					.usingJobData(jobDataMap).build();

			Trigger trigger = TriggerBuilder.newTrigger().withIdentity("labelUpdateTrigger", "group1").startNow()
					.withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(1).repeatForever())
					.build();

			// 스케줄러가 job의 key를 가지고 있으면 다시 scheduleJob을 생성하지 않도록
			if (!scheduler.checkExists(job.getKey())) {
				scheduler.scheduleJob(job, trigger);
			}

			scheduler.start();

		} catch (SchedulerException e) {
			e.printStackTrace();
		}
		
		

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

	public static void resetLabel() {
		lblImage1.setIcon(new ImageIcon("img/임시이미지.png"));
		lblImage2.setIcon(new ImageIcon("img/임시이미지.png"));
		lblImage3.setIcon(new ImageIcon("img/임시이미지.png"));
		lblImage4.setIcon(new ImageIcon("img/임시이미지.png"));
		lblImage5.setIcon(new ImageIcon("img/임시이미지.png"));
		lblImage6.setIcon(new ImageIcon("img/임시이미지.png"));
		lblImage7.setIcon(new ImageIcon("img/임시이미지.png"));
		lblImage8.setIcon(new ImageIcon("img/임시이미지.png"));
		lblImage9.setIcon(new ImageIcon("img/임시이미지.png"));
		lblImage10.setIcon(new ImageIcon("img/임시이미지.png"));

		Font font = new Font("맑은 고딕", Font.BOLD, 12);

		lblName1.setText("");
		lblName2.setText("");
		lblName3.setText("");
		lblName4.setText("");
		lblName5.setText("");
		lblName6.setText("");
		lblName7.setText("");
		lblName8.setText("");
		lblName9.setText("");
		lblName10.setText("");
		lblName1.setFont(font);
		lblName2.setFont(font);
		lblName3.setFont(font);
		lblName4.setFont(font);
		lblName5.setFont(font);
		lblName6.setFont(font);
		lblName7.setFont(font);
		lblName8.setFont(font);
		lblName9.setFont(font);
		lblName10.setFont(font);

		lblTime1.setText("");
		lblTime2.setText("");
		lblTime3.setText("");
		lblTime4.setText("");
		lblTime5.setText("");
		lblTime6.setText("");
		lblTime7.setText("");
		lblTime8.setText("");
		lblTime9.setText("");
		lblTime10.setText("");

		lblPrice1.setText("");
		lblPrice2.setText("");
		lblPrice3.setText("");
		lblPrice4.setText("");
		lblPrice5.setText("");
		lblPrice6.setText("");
		lblPrice7.setText("");
		lblPrice8.setText("");
		lblPrice9.setText("");
		lblPrice10.setText("");
		lblPrice1.setFont(font);
		lblPrice2.setFont(font);
		lblPrice3.setFont(font);
		lblPrice4.setFont(font);
		lblPrice5.setFont(font);
		lblPrice6.setFont(font);
		lblPrice7.setFont(font);
		lblPrice8.setFont(font);
		lblPrice9.setFont(font);
		lblPrice10.setFont(font);

		lblTime1.setForeground(Color.BLACK);
		lblTime1.setFont(font);
		lblTime2.setForeground(Color.BLACK);
		lblTime2.setFont(font);
		lblTime3.setForeground(Color.BLACK);
		lblTime3.setFont(font);
		lblTime4.setForeground(Color.BLACK);
		lblTime4.setFont(font);
		lblTime5.setForeground(Color.BLACK);
		lblTime5.setFont(font);
		lblTime6.setForeground(Color.BLACK);
		lblTime6.setFont(font);
		lblTime7.setForeground(Color.BLACK);
		lblTime7.setFont(font);
		lblTime8.setForeground(Color.BLACK);
		lblTime8.setFont(font);
		lblTime9.setForeground(Color.BLACK);
		lblTime9.setFont(font);
		lblTime10.setForeground(Color.BLACK);
		lblTime10.setFont(font);

		for (int i = 0; i < btns.length; i++) {
			btns[i].setVisible(false);
		}

		lblNum1.setText(" - " + String.valueOf((data.getIndexMain() / 10) + 1) + " - ");
		lblNum1.setHorizontalAlignment(SwingConstants.CENTER);
		Font font2 = new Font("맑은 고딕", Font.BOLD, 25);
		lblNum1.setFont(font2);
	}

	public static void updatLabel(LocalDateTime now) {
		Connection conn = null;
		list = new ArrayList();

		try {
			conn = DBUtil.getConnection();
			ImageRetriever.retrieveImage(conn);
			
			resetLabel();
			
			lblNum1.setText(" - " + String.valueOf((data.getIndexMain() / 10) + 1) + " - ");
			
			int count = 0;
			for (int i = data.getIndexMain(); i < timer.selectProduct().size(); i++) {
				count ++;
				if (count == 11) {
					break;
				}
				switch (i % 10) {
				case 0: {
					lblPrice1.setText(formatInt(timer.selectProduct().get(i).getProductPriceNow()));
					lblPrice1.setPreferredSize(new Dimension(200, lblPrice1.getPreferredSize().height));
					lblPrice1.setHorizontalAlignment(SwingConstants.CENTER);

					lblName1.setText(timer.selectProduct().get(i).getProductName());
					lblName1.setPreferredSize(new Dimension(200, lblName1.getPreferredSize().height));
					lblName1.setHorizontalAlignment(SwingConstants.CENTER);
					
//					ImageIcon imageIcon = ImageRetriever.list.get(i);
					ImageIcon imageIcon = ImageRetriever.llist.get(i).getImageicon();
					if (imageIcon != null) {
						lblImage1.setIcon(iconSize(imageIcon));
					}
					String result1 = duration(timer.selectProduct().get(i).getEndTime(), now);
					lblTime1.setText(result1);
					lblTime1.setPreferredSize(new Dimension(200, lblTime1.getPreferredSize().height));
					lblTime1.setHorizontalAlignment(SwingConstants.CENTER);
					
					if (durationTen(timer.selectProduct().get(i).getEndTime(), now)) {
						lblTime1.setForeground(Color.RED);
						lblTime1.setFont(lblTime1.getFont().deriveFont(15f));
					}

					btns[0].setVisible(true);
					list.add(new Auction(i, timer.selectProduct().get(i).getAuctionNo()));
					break;
				}
				case 1: {
					lblPrice2.setText(formatInt(timer.selectProduct().get(i).getProductPriceNow()));
					lblPrice2.setPreferredSize(new Dimension(200, lblPrice2.getPreferredSize().height));
					lblPrice2.setHorizontalAlignment(SwingConstants.CENTER);
					
					lblName2.setText(timer.selectProduct().get(i).getProductName());
					lblName2.setPreferredSize(new Dimension(200, lblName2.getPreferredSize().height));
					lblName2.setHorizontalAlignment(SwingConstants.CENTER);
					
//					ImageIcon imageIcon = ImageRetriever.list.get(i);
					ImageIcon imageIcon = ImageRetriever.llist.get(i).getImageicon();
					if (imageIcon != null) {
						lblImage2.setIcon(iconSize(imageIcon));
					}
					String result2 = duration(timer.selectProduct().get(i).getEndTime(), now);
					lblTime2.setText(result2);
					lblTime2.setPreferredSize(new Dimension(200, lblTime2.getPreferredSize().height));
					lblTime2.setHorizontalAlignment(SwingConstants.CENTER);
					
					if (durationTen(timer.selectProduct().get(i).getEndTime(), now)) {
						lblTime1.setForeground(Color.RED);
						lblTime1.setFont(lblTime1.getFont().deriveFont(15f));
					}
					
					btns[i].setVisible(true);
					break;
				}
				case 2: {
					lblPrice3.setText(formatInt(timer.selectProduct().get(i).getProductPriceNow()));
					lblPrice3.setPreferredSize(new Dimension(200, lblPrice3.getPreferredSize().height));
					lblPrice3.setHorizontalAlignment(SwingConstants.CENTER);
					
					lblName3.setText(timer.selectProduct().get(i).getProductName());
					lblName3.setPreferredSize(new Dimension(200, lblName3.getPreferredSize().height));
					lblName3.setHorizontalAlignment(SwingConstants.CENTER);
					
//					ImageIcon imageIcon = ImageRetriever.list.get(i);
					ImageIcon imageIcon = ImageRetriever.llist.get(i).getImageicon();
					if (imageIcon != null) {
						lblImage3.setIcon(iconSize(imageIcon));
					}

					String result3 = duration(timer.selectProduct().get(i).getEndTime(), now);
					lblTime3.setText(result3);
					lblTime3.setPreferredSize(new Dimension(200, lblTime3.getPreferredSize().height));
					lblTime3.setHorizontalAlignment(SwingConstants.CENTER);
					
					if (durationTen(timer.selectProduct().get(i).getEndTime(), now)) {
						lblTime1.setForeground(Color.RED);
						lblTime1.setFont(lblTime1.getFont().deriveFont(15f));
					}

					btns[2].setVisible(true);
					list.add(new Auction(i, timer.selectProduct().get(i).getAuctionNo()));
					break;
				}
				case 3: {
					lblPrice4.setText(formatInt(timer.selectProduct().get(i).getProductPriceNow()));
					lblPrice4.setPreferredSize(new Dimension(200, lblPrice4.getPreferredSize().height));
					lblPrice4.setHorizontalAlignment(SwingConstants.CENTER);
					
					lblName4.setText(timer.selectProduct().get(i).getProductName());
					lblName4.setPreferredSize(new Dimension(200, lblName4.getPreferredSize().height));
					lblName4.setHorizontalAlignment(SwingConstants.CENTER);
					
//					ImageIcon imageIcon = ImageRetriever.list.get(i);
					ImageIcon imageIcon = ImageRetriever.llist.get(i).getImageicon();
					if (imageIcon != null) {
						lblImage4.setIcon(iconSize(imageIcon));
					}

					String result4 = duration(timer.selectProduct().get(i).getEndTime(), now);
					lblTime4.setText(result4);
					lblTime4.setPreferredSize(new Dimension(200, lblTime4.getPreferredSize().height));
					lblTime4.setHorizontalAlignment(SwingConstants.CENTER);
					
					if (durationTen(timer.selectProduct().get(i).getEndTime(), now)) {
						lblTime1.setForeground(Color.RED);
						lblTime1.setFont(lblTime1.getFont().deriveFont(15f));
					}

					btns[3].setVisible(true);
					list.add(new Auction(i, timer.selectProduct().get(i).getAuctionNo()));
					break;
				}
				case 4: {
					lblPrice5.setText(formatInt(timer.selectProduct().get(i).getProductPriceNow()));
					lblPrice5.setPreferredSize(new Dimension(200, lblPrice5.getPreferredSize().height));
					lblPrice5.setHorizontalAlignment(SwingConstants.CENTER);
					
					lblName5.setText(timer.selectProduct().get(i).getProductName());
					lblName5.setPreferredSize(new Dimension(200, lblName5.getPreferredSize().height));
					lblName5.setHorizontalAlignment(SwingConstants.CENTER);
					
//					ImageIcon imageIcon = ImageRetriever.list.get(i);
					ImageIcon imageIcon = ImageRetriever.llist.get(i).getImageicon();
					if (imageIcon != null) {
						lblImage5.setIcon(iconSize(imageIcon));
					}

					String result5 = duration(timer.selectProduct().get(i).getEndTime(), now);
					lblTime5.setText(result5);
					lblTime5.setPreferredSize(new Dimension(200, lblTime5.getPreferredSize().height));
					lblTime5.setHorizontalAlignment(SwingConstants.CENTER);
					
					if (durationTen(timer.selectProduct().get(i).getEndTime(), now)) {
						lblTime1.setForeground(Color.RED);
						lblTime1.setFont(lblTime1.getFont().deriveFont(15f));
					}

					btns[4].setVisible(true);
					list.add(new Auction(i, timer.selectProduct().get(i).getAuctionNo()));
					break;
				}
				case 5: {
					lblPrice6.setText(formatInt(timer.selectProduct().get(i).getProductPriceNow()));
					lblPrice6.setPreferredSize(new Dimension(200, lblPrice6.getPreferredSize().height));
					lblPrice6.setHorizontalAlignment(SwingConstants.CENTER);
					
					lblName6.setText(timer.selectProduct().get(i).getProductName());
					lblName6.setPreferredSize(new Dimension(200, lblName6.getPreferredSize().height));
					lblName6.setHorizontalAlignment(SwingConstants.CENTER);
					
//					ImageIcon imageIcon = ImageRetriever.list.get(i);
					ImageIcon imageIcon = ImageRetriever.llist.get(i).getImageicon();
					if (imageIcon != null) {
						lblImage6.setIcon(iconSize(imageIcon));
					}

					String result6 = duration(timer.selectProduct().get(i).getEndTime(), now);
					lblTime6.setText(result6);
					lblTime6.setPreferredSize(new Dimension(200, lblTime6.getPreferredSize().height));
					lblTime6.setHorizontalAlignment(SwingConstants.CENTER);
					
					if (durationTen(timer.selectProduct().get(i).getEndTime(), now)) {
						lblTime1.setForeground(Color.RED);
						lblTime1.setFont(lblTime1.getFont().deriveFont(15f));
					}

					btns[5].setVisible(true);
					list.add(new Auction(i, timer.selectProduct().get(i).getAuctionNo()));
					break;
				}
				case 6: {
					lblPrice7.setText(formatInt(timer.selectProduct().get(i).getProductPriceNow()));
					lblPrice7.setPreferredSize(new Dimension(200, lblPrice7.getPreferredSize().height));
					lblPrice7.setHorizontalAlignment(SwingConstants.CENTER);
					
					lblName7.setText(timer.selectProduct().get(i).getProductName());
					lblName7.setPreferredSize(new Dimension(200, lblName7.getPreferredSize().height));
					lblName7.setHorizontalAlignment(SwingConstants.CENTER);
					
//					ImageIcon imageIcon = ImageRetriever.list.get(i);
					ImageIcon imageIcon = ImageRetriever.llist.get(i).getImageicon();
					if (imageIcon != null) {
						lblImage7.setIcon(iconSize(imageIcon));
					}
					String result7 = duration(timer.selectProduct().get(i).getEndTime(), now);
					lblTime7.setText(result7);
					lblTime7.setPreferredSize(new Dimension(200, lblTime7.getPreferredSize().height));
					lblTime7.setHorizontalAlignment(SwingConstants.CENTER);
					
					if (durationTen(timer.selectProduct().get(i).getEndTime(), now)) {
						lblTime1.setForeground(Color.RED);
						lblTime1.setFont(lblTime1.getFont().deriveFont(15f));
					}

					btns[6].setVisible(true);
					list.add(new Auction(i, timer.selectProduct().get(i).getAuctionNo()));
					break;
				}
				case 7: {
					lblPrice8.setText(formatInt(timer.selectProduct().get(i).getProductPriceNow()));
					lblPrice8.setPreferredSize(new Dimension(200, lblPrice8.getPreferredSize().height));
					lblPrice8.setHorizontalAlignment(SwingConstants.CENTER);
					
					lblName8.setText(timer.selectProduct().get(i).getProductName());
					lblName8.setPreferredSize(new Dimension(200, lblName8.getPreferredSize().height));
					lblName8.setHorizontalAlignment(SwingConstants.CENTER);
					
//					ImageIcon imageIcon = ImageRetriever.list.get(i);
					ImageIcon imageIcon = ImageRetriever.llist.get(i).getImageicon();
					if (imageIcon != null) {
						lblImage8.setIcon(iconSize(imageIcon));
					}
					String result8 = duration(timer.selectProduct().get(i).getEndTime(), now);
					lblTime8.setText(result8);
					lblTime8.setPreferredSize(new Dimension(200, lblTime8.getPreferredSize().height));
					lblTime8.setHorizontalAlignment(SwingConstants.CENTER);
					
					if (durationTen(timer.selectProduct().get(i).getEndTime(), now)) {
						lblTime1.setForeground(Color.RED);
						lblTime1.setFont(lblTime1.getFont().deriveFont(15f));
					}

					btns[7].setVisible(true);
					list.add(new Auction(i, timer.selectProduct().get(i).getAuctionNo()));
					break;
				}
				case 8: {
					lblPrice9.setText(formatInt(timer.selectProduct().get(i).getProductPriceNow()));
					lblPrice9.setPreferredSize(new Dimension(200, lblPrice9.getPreferredSize().height));
					lblPrice9.setHorizontalAlignment(SwingConstants.CENTER);
					
					lblName9.setText(timer.selectProduct().get(i).getProductName());
					lblName9.setPreferredSize(new Dimension(200, lblName9.getPreferredSize().height));
					lblName9.setHorizontalAlignment(SwingConstants.CENTER);
					
//					ImageIcon imageIcon = ImageRetriever.list.get(i);
					ImageIcon imageIcon = ImageRetriever.llist.get(i).getImageicon();
					if (imageIcon != null) {
						lblImage9.setIcon(iconSize(imageIcon));
					}
					String result9 = duration(timer.selectProduct().get(i).getEndTime(), now);
					lblTime9.setText(result9);
					lblTime9.setPreferredSize(new Dimension(200, lblTime9.getPreferredSize().height));
					lblTime9.setHorizontalAlignment(SwingConstants.CENTER);
					
					if (durationTen(timer.selectProduct().get(i).getEndTime(), now)) {
						lblTime1.setForeground(Color.RED);
						lblTime1.setFont(lblTime1.getFont().deriveFont(15f));
					}

					btns[8].setVisible(true);
					list.add(new Auction(i, timer.selectProduct().get(i).getAuctionNo()));
					break;
				}
				case 9: {
					lblPrice10.setText(formatInt(timer.selectProduct().get(i).getProductPriceNow()));
					lblPrice10.setPreferredSize(new Dimension(200, lblPrice10.getPreferredSize().height));
					lblPrice10.setHorizontalAlignment(SwingConstants.CENTER);
					
					lblName10.setText(timer.selectProduct().get(i).getProductName());
					lblName10.setPreferredSize(new Dimension(200, lblName10.getPreferredSize().height));
					lblName10.setHorizontalAlignment(SwingConstants.CENTER);
					
//					ImageIcon imageIcon = ImageRetriever.list.get(i);
					ImageIcon imageIcon = ImageRetriever.llist.get(i).getImageicon();
					if (imageIcon != null) {
						lblImage10.setIcon(iconSize(imageIcon));
					}
					String result10 = duration(timer.selectProduct().get(i).getEndTime(), now);
					lblTime10.setText(result10);
					lblTime10.setPreferredSize(new Dimension(200, lblTime10.getPreferredSize().height));
					lblTime10.setHorizontalAlignment(SwingConstants.CENTER);
					
					if (durationTen(timer.selectProduct().get(i).getEndTime(), now)) {
						lblTime1.setForeground(Color.RED);
						lblTime1.setFont(lblTime1.getFont().deriveFont(15f));
					}

					btns[9].setVisible(true);
					list.add(new Auction(i, timer.selectProduct().get(i).getAuctionNo()));
					break;
				}
				}
			}
		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			DBUtil.close(conn);
		}
	}

	public static void updateSearchLabel(LocalDateTime now, String text) {
		Connection conn = null;
		list = new ArrayList();
		resetLabel();

		try {
			conn = DBUtil.getConnection();
			ImageRetriever.retrieveImage(conn);

			lblNum1.setText(" - " + String.valueOf((data.getIndexMain() / 10) + 1) + " - ");

			int count = 0;
			for (int i = data.getIndexMain(); i < timer.selectSearchProduct(text).size(); i++) {
				count++;
				if (count == 11) {
					break;
				}
				switch (i % 10) {

				case 0: {
					lblPrice1.setText(formatInt(timer.selectSearchProduct(text).get(i).getProductPriceNow()));
					lblPrice1.setPreferredSize(new Dimension(200, lblPrice1.getPreferredSize().height));
					lblPrice1.setHorizontalAlignment(SwingConstants.CENTER);

					lblName1.setText(timer.selectSearchProduct(text).get(i).getProductName());
					lblName1.setPreferredSize(new Dimension(200, lblName1.getPreferredSize().height));
					lblName1.setHorizontalAlignment(SwingConstants.CENTER);

					ImageIcon imageIcon = ImageRetriever.llist.get(i).getImageicon();
					if (imageIcon != null) {
						lblImage1.setIcon(iconSize(imageIcon));
					}
					String result1 = duration(timer.selectSearchProduct(text).get(i).getEndTime(), now);
					lblTime1.setText(result1);
					lblTime1.setPreferredSize(new Dimension(200, lblTime1.getPreferredSize().height));
					lblTime1.setHorizontalAlignment(SwingConstants.CENTER);

					if (durationTen(timer.selectSearchProduct(text).get(i).getEndTime(), now)) {
						lblTime1.setForeground(Color.RED);
						lblTime1.setFont(lblTime1.getFont().deriveFont(15f));
					}

					btns[0].setVisible(true);
					list.add(new Auction(i, timer.selectSearchProduct(text).get(i).getAuctionNo()));
					break;
				}
				case 1: {
					lblPrice2.setText(formatInt(timer.selectSearchProduct(text).get(i).getProductPriceNow()));
					lblPrice2.setPreferredSize(new Dimension(200, lblPrice2.getPreferredSize().height));
					lblPrice2.setHorizontalAlignment(SwingConstants.CENTER);

					lblName2.setText(timer.selectSearchProduct(text).get(i).getProductName());
					lblName2.setPreferredSize(new Dimension(200, lblName2.getPreferredSize().height));
					lblName2.setHorizontalAlignment(SwingConstants.CENTER);

//					ImageIcon imageIcon = ImageRetriever.list.get(i);
					ImageIcon imageIcon = ImageRetriever.llist.get(i).getImageicon();
					if (imageIcon != null) {
						lblImage2.setIcon(iconSize(imageIcon));
					}
					String result2 = duration(timer.selectSearchProduct(text).get(i).getEndTime(), now);
					lblTime2.setText(result2);
					lblTime2.setPreferredSize(new Dimension(200, lblTime2.getPreferredSize().height));
					lblTime2.setHorizontalAlignment(SwingConstants.CENTER);

					if (durationTen(timer.selectSearchProduct(text).get(i).getEndTime(), now)) {
						lblTime1.setForeground(Color.RED);
						lblTime1.setFont(lblTime1.getFont().deriveFont(15f));
					}

					btns[1].setVisible(true);
					list.add(new Auction(i, timer.selectSearchProduct(text).get(i).getAuctionNo()));
					break;
				}
				case 2: {
					lblPrice3.setText(formatInt(timer.selectSearchProduct(text).get(i).getProductPriceNow()));
					lblPrice3.setPreferredSize(new Dimension(200, lblPrice3.getPreferredSize().height));
					lblPrice3.setHorizontalAlignment(SwingConstants.CENTER);

					lblName3.setText(timer.selectSearchProduct(text).get(i).getProductName());
					lblName3.setPreferredSize(new Dimension(200, lblName3.getPreferredSize().height));
					lblName3.setHorizontalAlignment(SwingConstants.CENTER);

//					ImageIcon imageIcon = ImageRetriever.list.get(i);
					ImageIcon imageIcon = ImageRetriever.llist.get(i).getImageicon();
					if (imageIcon != null) {
						lblImage3.setIcon(iconSize(imageIcon));
					}

					String result3 = duration(timer.selectSearchProduct(text).get(i).getEndTime(), now);
					lblTime3.setText(result3);
					lblTime3.setPreferredSize(new Dimension(200, lblTime3.getPreferredSize().height));
					lblTime3.setHorizontalAlignment(SwingConstants.CENTER);

					if (durationTen(timer.selectSearchProduct(text).get(i).getEndTime(), now)) {
						lblTime1.setForeground(Color.RED);
						lblTime1.setFont(lblTime1.getFont().deriveFont(15f));
					}

					btns[2].setVisible(true);
					list.add(new Auction(i, timer.selectSearchProduct(text).get(i).getAuctionNo()));
					break;
				}
				case 3: {
					lblPrice4.setText(formatInt(timer.selectSearchProduct(text).get(i).getProductPriceNow()));
					lblPrice4.setPreferredSize(new Dimension(200, lblPrice4.getPreferredSize().height));
					lblPrice4.setHorizontalAlignment(SwingConstants.CENTER);

					lblName4.setText(timer.selectSearchProduct(text).get(i).getProductName());
					lblName4.setPreferredSize(new Dimension(200, lblName4.getPreferredSize().height));
					lblName4.setHorizontalAlignment(SwingConstants.CENTER);

//					ImageIcon imageIcon = ImageRetriever.list.get(i);
					ImageIcon imageIcon = ImageRetriever.llist.get(i).getImageicon();
					if (imageIcon != null) {
						lblImage4.setIcon(iconSize(imageIcon));
					}

					String result4 = duration(timer.selectSearchProduct(text).get(i).getEndTime(), now);
					lblTime4.setText(result4);
					lblTime4.setPreferredSize(new Dimension(200, lblTime4.getPreferredSize().height));
					lblTime4.setHorizontalAlignment(SwingConstants.CENTER);

					if (durationTen(timer.selectSearchProduct(text).get(i).getEndTime(), now)) {
						lblTime1.setForeground(Color.RED);
						lblTime1.setFont(lblTime1.getFont().deriveFont(15f));
					}

					btns[3].setVisible(true);
					list.add(new Auction(i, timer.selectSearchProduct(text).get(i).getAuctionNo()));
					break;
				}
				case 4: {
					lblPrice5.setText(formatInt(timer.selectSearchProduct(text).get(i).getProductPriceNow()));
					lblPrice5.setPreferredSize(new Dimension(200, lblPrice5.getPreferredSize().height));
					lblPrice5.setHorizontalAlignment(SwingConstants.CENTER);

					lblName5.setText(timer.selectSearchProduct(text).get(i).getProductName());
					lblName5.setPreferredSize(new Dimension(200, lblName5.getPreferredSize().height));
					lblName5.setHorizontalAlignment(SwingConstants.CENTER);

//					ImageIcon imageIcon = ImageRetriever.list.get(i);
					ImageIcon imageIcon = ImageRetriever.llist.get(i).getImageicon();
					if (imageIcon != null) {
						lblImage5.setIcon(iconSize(imageIcon));
					}

					String result5 = duration(timer.selectSearchProduct(text).get(i).getEndTime(), now);
					lblTime5.setText(result5);
					lblTime5.setPreferredSize(new Dimension(200, lblTime5.getPreferredSize().height));
					lblTime5.setHorizontalAlignment(SwingConstants.CENTER);

					if (durationTen(timer.selectSearchProduct(text).get(i).getEndTime(), now)) {
						lblTime1.setForeground(Color.RED);
						lblTime1.setFont(lblTime1.getFont().deriveFont(15f));
					}

					btns[4].setVisible(true);
					list.add(new Auction(i, timer.selectSearchProduct(text).get(i).getAuctionNo()));
					break;
				}
				case 5: {
					lblPrice6.setText(formatInt(timer.selectSearchProduct(text).get(i).getProductPriceNow()));
					lblPrice6.setPreferredSize(new Dimension(200, lblPrice6.getPreferredSize().height));
					lblPrice6.setHorizontalAlignment(SwingConstants.CENTER);

					lblName6.setText(timer.selectSearchProduct(text).get(i).getProductName());
					lblName6.setPreferredSize(new Dimension(200, lblName6.getPreferredSize().height));
					lblName6.setHorizontalAlignment(SwingConstants.CENTER);

//					ImageIcon imageIcon = ImageRetriever.list.get(i);
					ImageIcon imageIcon = ImageRetriever.llist.get(i).getImageicon();
					if (imageIcon != null) {
						lblImage6.setIcon(iconSize(imageIcon));
					}

					String result6 = duration(timer.selectSearchProduct(text).get(i).getEndTime(), now);
					lblTime6.setText(result6);
					lblTime6.setPreferredSize(new Dimension(200, lblTime6.getPreferredSize().height));
					lblTime6.setHorizontalAlignment(SwingConstants.CENTER);

					if (durationTen(timer.selectSearchProduct(text).get(i).getEndTime(), now)) {
						lblTime1.setForeground(Color.RED);
						lblTime1.setFont(lblTime1.getFont().deriveFont(15f));
					}

					btns[5].setVisible(true);
					list.add(new Auction(i, timer.selectSearchProduct(text).get(i).getAuctionNo()));
					break;
				}
				case 6: {
					lblPrice7.setText(formatInt(timer.selectSearchProduct(text).get(i).getProductPriceNow()));
					lblPrice7.setPreferredSize(new Dimension(200, lblPrice7.getPreferredSize().height));
					lblPrice7.setHorizontalAlignment(SwingConstants.CENTER);

					lblName7.setText(timer.selectSearchProduct(text).get(i).getProductName());
					lblName7.setPreferredSize(new Dimension(200, lblName7.getPreferredSize().height));
					lblName7.setHorizontalAlignment(SwingConstants.CENTER);

//					ImageIcon imageIcon = ImageRetriever.list.get(i);
					ImageIcon imageIcon = ImageRetriever.llist.get(i).getImageicon();
					if (imageIcon != null) {
						lblImage7.setIcon(iconSize(imageIcon));
					}
					String result7 = duration(timer.selectSearchProduct(text).get(i).getEndTime(), now);
					lblTime7.setText(result7);
					lblTime7.setPreferredSize(new Dimension(200, lblTime7.getPreferredSize().height));
					lblTime7.setHorizontalAlignment(SwingConstants.CENTER);

					if (durationTen(timer.selectSearchProduct(text).get(i).getEndTime(), now)) {
						lblTime1.setForeground(Color.RED);
						lblTime1.setFont(lblTime1.getFont().deriveFont(15f));
					}

					btns[6].setVisible(true);
					list.add(new Auction(i, timer.selectSearchProduct(text).get(i).getAuctionNo()));
					break;
				}
				case 7: {
					lblPrice8.setText(formatInt(timer.selectSearchProduct(text).get(i).getProductPriceNow()));
					lblPrice8.setPreferredSize(new Dimension(200, lblPrice8.getPreferredSize().height));
					lblPrice8.setHorizontalAlignment(SwingConstants.CENTER);

					lblName8.setText(timer.selectSearchProduct(text).get(i).getProductName());
					lblName8.setPreferredSize(new Dimension(200, lblName8.getPreferredSize().height));
					lblName8.setHorizontalAlignment(SwingConstants.CENTER);

//					ImageIcon imageIcon = ImageRetriever.list.get(i);
					ImageIcon imageIcon = ImageRetriever.llist.get(i).getImageicon();
					if (imageIcon != null) {
						lblImage8.setIcon(iconSize(imageIcon));
					}
					String result8 = duration(timer.selectSearchProduct(text).get(i).getEndTime(), now);
					lblTime8.setText(result8);
					lblTime8.setPreferredSize(new Dimension(200, lblTime8.getPreferredSize().height));
					lblTime8.setHorizontalAlignment(SwingConstants.CENTER);

					if (durationTen(timer.selectSearchProduct(text).get(i).getEndTime(), now)) {
						lblTime1.setForeground(Color.RED);
						lblTime1.setFont(lblTime1.getFont().deriveFont(15f));
					}

					btns[7].setVisible(true);
					list.add(new Auction(i, timer.selectSearchProduct(text).get(i).getAuctionNo()));
					break;
				}
				case 8: {
					lblPrice9.setText(formatInt(timer.selectSearchProduct(text).get(i).getProductPriceNow()));
					lblPrice9.setPreferredSize(new Dimension(200, lblPrice9.getPreferredSize().height));
					lblPrice9.setHorizontalAlignment(SwingConstants.CENTER);

					lblName9.setText(timer.selectSearchProduct(text).get(i).getProductName());
					lblName9.setPreferredSize(new Dimension(200, lblName9.getPreferredSize().height));
					lblName9.setHorizontalAlignment(SwingConstants.CENTER);

//					ImageIcon imageIcon = ImageRetriever.list.get(i);
					ImageIcon imageIcon = ImageRetriever.llist.get(i).getImageicon();
					if (imageIcon != null) {
						lblImage9.setIcon(iconSize(imageIcon));
					}
					String result9 = duration(timer.selectSearchProduct(text).get(i).getEndTime(), now);
					lblTime9.setText(result9);
					lblTime9.setPreferredSize(new Dimension(200, lblTime9.getPreferredSize().height));
					lblTime9.setHorizontalAlignment(SwingConstants.CENTER);

					if (durationTen(timer.selectSearchProduct(text).get(i).getEndTime(), now)) {
						lblTime1.setForeground(Color.RED);
						lblTime1.setFont(lblTime1.getFont().deriveFont(15f));
					}

					btns[8].setVisible(true);
					list.add(new Auction(i, timer.selectSearchProduct(text).get(i).getAuctionNo()));
					break;
				}
				case 9: {
					lblPrice10.setText(formatInt(timer.selectSearchProduct(text).get(i).getProductPriceNow()));
					lblPrice10.setPreferredSize(new Dimension(200, lblPrice10.getPreferredSize().height));
					lblPrice10.setHorizontalAlignment(SwingConstants.CENTER);

					lblName10.setText(timer.selectSearchProduct(text).get(i).getProductName());
					lblName10.setPreferredSize(new Dimension(200, lblName10.getPreferredSize().height));
					lblName10.setHorizontalAlignment(SwingConstants.CENTER);

//					ImageIcon imageIcon = ImageRetriever.list.get(i);
					ImageIcon imageIcon = ImageRetriever.llist.get(i).getImageicon();
					if (imageIcon != null) {
						lblImage10.setIcon(iconSize(imageIcon));
					}
					String result10 = duration(timer.selectSearchProduct(text).get(i).getEndTime(), now);
					lblTime10.setText(result10);
					lblTime10.setPreferredSize(new Dimension(200, lblTime10.getPreferredSize().height));
					lblTime10.setHorizontalAlignment(SwingConstants.CENTER);

					if (durationTen(timer.selectSearchProduct(text).get(i).getEndTime(), now)) {
						lblTime1.setForeground(Color.RED);
						lblTime1.setFont(lblTime1.getFont().deriveFont(15f));
					}

					btns[9].setVisible(true);
					list.add(new Auction(i, timer.selectSearchProduct(text).get(i).getAuctionNo()));
					break;
				}
				}
			}
		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			DBUtil.close(conn);
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

	public static boolean durationTen(LocalDateTime targetDateTime, LocalDateTime now) {
		Duration duration = Duration.between(now, targetDateTime);
		long seconds = duration.getSeconds();

		if (seconds <= 60) {
			return true;
		}
		return false;
	}

	public static String formatInt(int price) {
		return String.format("%,d원", price);
	}

	public static class AutionUpdateJob implements Job {
		public void execute(JobExecutionContext context) throws JobExecutionException {
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {

					LocalDateTime now = LocalDateTime.now();
					if (checkBtn) {
						updateSearchLabel(now, searchText);
					} else {
						updatLabel(now);
					}
				}
			});
		}
	}
}