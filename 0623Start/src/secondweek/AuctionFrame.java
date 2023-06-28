package secondweek;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
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
import javax.swing.JOptionPane;
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

public class AuctionFrame extends JFrame {

	private Scheduler scheduler;
	private static Timer timer;
	
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
	
	private JFrame frame;
	private JPanel contentPane;
	private static JButton[] btns;

	public AuctionFrame(DataBase data) {
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

		contentPane.add(userLbl);
		contentPane.add(mypageBtn);
		contentPane.add(logoutBtn);
		contentPane.add(exitBtn);

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
		
		lblImage1 = new JLabel("");
		pnl1.add(lblImage1);

		lblImage2 = new JLabel("");
		pnl2.add(lblImage2);

		lblImage3 = new JLabel("");
		pnl3.add(lblImage3);

		lblImage4 = new JLabel("");
		pnl4.add(lblImage4);

		lblImage5 = new JLabel("");
		pnl5.add(lblImage5);

		lblImage6 = new JLabel("");
		pnl6.add(lblImage6);

		lblImage7 = new JLabel("");
		pnl7.add(lblImage7);

		lblImage8 = new JLabel("");
		pnl8.add(lblImage8);

		lblImage9 = new JLabel("");
		pnl9.add(lblImage9);

		lblImage10 = new JLabel("");
		pnl10.add(lblImage10);
		
		
		lblName1 = new JLabel("");
		pnl1.add(lblName1);
		
		lblName2 = new JLabel("");
		pnl2.add(lblName2);

		lblName3 = new JLabel("");
		pnl3.add(lblName3);
		
		lblName4 = new JLabel("");
		pnl4.add(lblName4);
		
		lblName5 = new JLabel("");
		pnl5.add(lblName5);
		
		lblName6 = new JLabel("");
		pnl6.add(lblName6);
		
		lblName7 = new JLabel("");
		pnl7.add(lblName7);
		
		lblName8 = new JLabel("");
		pnl8.add(lblName8);
		
		lblName9 = new JLabel("");
		pnl9.add(lblName9);
		
		lblName10 = new JLabel("");
		pnl10.add(lblName10);

		
		lblPrice1 = new JLabel("");
		pnl1.add(lblPrice1);

		lblPrice2 = new JLabel("");
		pnl2.add(lblPrice2);

		lblPrice3 = new JLabel("");
		pnl3.add(lblPrice3);

		lblPrice4 = new JLabel("");
		pnl4.add(lblPrice4);

		lblPrice5 = new JLabel("");
		pnl5.add(lblPrice5);

		lblPrice6 = new JLabel("");
		pnl6.add(lblPrice6);

		lblPrice7 = new JLabel("");
		pnl7.add(lblPrice7);

		lblPrice8 = new JLabel("");
		pnl8.add(lblPrice8);

		lblPrice9 = new JLabel("");
		pnl9.add(lblPrice9);

		lblPrice10 = new JLabel("");
		pnl10.add(lblPrice10);
		
		
		lblTime1 = new JLabel("");
		pnl1.add(lblTime1);

		lblTime2 = new JLabel("");
		pnl2.add(lblTime2);

		lblTime3 = new JLabel("");
		pnl3.add(lblTime3);

		lblTime4 = new JLabel("");
		pnl4.add(lblTime4);

		lblTime5 = new JLabel("");
		pnl5.add(lblTime5);

		lblTime6 = new JLabel("");
		pnl6.add(lblTime6);

		lblTime7 = new JLabel("");
		pnl7.add(lblTime7);

		lblTime8 = new JLabel("");
		pnl8.add(lblTime8);

		lblTime9 = new JLabel("");
		pnl9.add(lblTime9);

		lblTime10 = new JLabel("");
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
			btns[i].addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					List<Product> products = timer.selectProduct();
					if (products.size() >= index) {
						Product product = products.get(index);
						data.setProduct(product);
						new DetailFrame(data, timer.selectProduct().get(index).getAuctionNo());
						frame.setVisible(false);
					}
				}
			});
		}
		
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
		
		for (int i = 0; i < btns.length; i++) {
			btns[i].setVisible(false);
		}
	}

	public static void updatLabel(LocalDateTime now) {
		Connection conn = null;

		try {
			conn = DBUtil.getConnection();
			ImageRetriever.retrieveImage(conn);
			
			resetLabel();
			
			for (int i = 0; i < timer.selectProduct().size(); i++) {
				
				
				switch (i) {
				case 0: {
					lblPrice1.setText(formatInt(timer.selectProduct().get(0).getProductPriceNow()));
					lblPrice1.setPreferredSize(new Dimension(200, lblPrice1.getPreferredSize().height));
					lblPrice1.setHorizontalAlignment(SwingConstants.CENTER);

					lblName1.setText(timer.selectProduct().get(0).getProductName());
					lblName1.setPreferredSize(new Dimension(200, lblName1.getPreferredSize().height));
					lblName1.setHorizontalAlignment(SwingConstants.CENTER);
					
//					ImageIcon imageIcon = ImageRetriever.list.get(0);
					ImageIcon imageIcon = ImageRetriever.llist.get(0).getImageicon();
					if (imageIcon != null) {
						lblImage1.setIcon(iconSize(imageIcon));
					}
					String result1 = duration(timer.selectProduct().get(0).getEndTime(), now);
					lblTime1.setText(result1);
					lblTime1.setPreferredSize(new Dimension(200, lblTime1.getPreferredSize().height));
					lblTime1.setHorizontalAlignment(SwingConstants.CENTER);
					
					btns[i].setVisible(true);
					break;
				}
				case 1: {
					lblPrice2.setText(formatInt(timer.selectProduct().get(1).getProductPriceNow()));
					lblPrice2.setPreferredSize(new Dimension(200, lblPrice2.getPreferredSize().height));
					lblPrice2.setHorizontalAlignment(SwingConstants.CENTER);
					
					lblName2.setText(timer.selectProduct().get(1).getProductName());
					lblName2.setPreferredSize(new Dimension(200, lblName2.getPreferredSize().height));
					lblName2.setHorizontalAlignment(SwingConstants.CENTER);
					
//					ImageIcon imageIcon = ImageRetriever.list.get(1);
					ImageIcon imageIcon = ImageRetriever.llist.get(1).getImageicon();
					if (imageIcon != null) {
						lblImage2.setIcon(iconSize(imageIcon));
					}
					String result2 = duration(timer.selectProduct().get(1).getEndTime(), now);
					lblTime2.setText(result2);
					lblTime2.setPreferredSize(new Dimension(200, lblTime2.getPreferredSize().height));
					lblTime2.setHorizontalAlignment(SwingConstants.CENTER);
					
					btns[i].setVisible(true);
					break;
				}
				case 2: {
					lblPrice3.setText(formatInt(timer.selectProduct().get(2).getProductPriceNow()));
					lblPrice3.setPreferredSize(new Dimension(200, lblPrice3.getPreferredSize().height));
					lblPrice3.setHorizontalAlignment(SwingConstants.CENTER);
					
					lblName3.setText(timer.selectProduct().get(2).getProductName());
					lblName3.setPreferredSize(new Dimension(200, lblName3.getPreferredSize().height));
					lblName3.setHorizontalAlignment(SwingConstants.CENTER);
					
//					ImageIcon imageIcon = ImageRetriever.list.get(2);
					ImageIcon imageIcon = ImageRetriever.llist.get(2).getImageicon();
					if (imageIcon != null) {
						lblImage3.setIcon(iconSize(imageIcon));
					}

					String result3 = duration(timer.selectProduct().get(2).getEndTime(), now);
					lblTime3.setText(result3);
					lblTime3.setPreferredSize(new Dimension(200, lblTime3.getPreferredSize().height));
					lblTime3.setHorizontalAlignment(SwingConstants.CENTER);
					
					btns[i].setVisible(true);
					break;
				}
				case 3: {
					lblPrice4.setText(formatInt(timer.selectProduct().get(3).getProductPriceNow()));
					lblPrice4.setPreferredSize(new Dimension(200, lblPrice4.getPreferredSize().height));
					lblPrice4.setHorizontalAlignment(SwingConstants.CENTER);
					
					lblName4.setText(timer.selectProduct().get(3).getProductName());
					lblName4.setPreferredSize(new Dimension(200, lblName4.getPreferredSize().height));
					lblName4.setHorizontalAlignment(SwingConstants.CENTER);
					
//					ImageIcon imageIcon = ImageRetriever.list.get(3);
					ImageIcon imageIcon = ImageRetriever.llist.get(3).getImageicon();
					if (imageIcon != null) {
						lblImage4.setIcon(iconSize(imageIcon));
					}

					String result4 = duration(timer.selectProduct().get(3).getEndTime(), now);
					lblTime4.setText(result4);
					lblTime4.setPreferredSize(new Dimension(200, lblTime4.getPreferredSize().height));
					lblTime4.setHorizontalAlignment(SwingConstants.CENTER);
					
					btns[i].setVisible(true);
					break;
				}
				case 4: {
					lblPrice5.setText(formatInt(timer.selectProduct().get(4).getProductPriceNow()));
					lblPrice5.setPreferredSize(new Dimension(200, lblPrice5.getPreferredSize().height));
					lblPrice5.setHorizontalAlignment(SwingConstants.CENTER);
					
					lblName5.setText(timer.selectProduct().get(4).getProductName());
					lblName5.setPreferredSize(new Dimension(200, lblName5.getPreferredSize().height));
					lblName5.setHorizontalAlignment(SwingConstants.CENTER);
					
//					ImageIcon imageIcon = ImageRetriever.list.get(4);
					ImageIcon imageIcon = ImageRetriever.llist.get(4).getImageicon();
					if (imageIcon != null) {
						lblImage5.setIcon(iconSize(imageIcon));
					}

					String result5 = duration(timer.selectProduct().get(4).getEndTime(), now);
					lblTime5.setText(result5);
					lblTime5.setPreferredSize(new Dimension(200, lblTime5.getPreferredSize().height));
					lblTime5.setHorizontalAlignment(SwingConstants.CENTER);
					
					btns[i].setVisible(true);
					break;
				}
				case 5: {
					lblPrice6.setText(formatInt(timer.selectProduct().get(5).getProductPriceNow()));
					lblPrice6.setPreferredSize(new Dimension(200, lblPrice6.getPreferredSize().height));
					lblPrice6.setHorizontalAlignment(SwingConstants.CENTER);
					
					lblName6.setText(timer.selectProduct().get(5).getProductName());
					lblName6.setPreferredSize(new Dimension(200, lblName6.getPreferredSize().height));
					lblName6.setHorizontalAlignment(SwingConstants.CENTER);
					
//					ImageIcon imageIcon = ImageRetriever.list.get(5);
					ImageIcon imageIcon = ImageRetriever.llist.get(5).getImageicon();
					if (imageIcon != null) {
						lblImage6.setIcon(iconSize(imageIcon));
					}

					String result6 = duration(timer.selectProduct().get(5).getEndTime(), now);
					lblTime6.setText(result6);
					lblTime6.setPreferredSize(new Dimension(200, lblTime6.getPreferredSize().height));
					lblTime6.setHorizontalAlignment(SwingConstants.CENTER);
					
					btns[i].setVisible(true);
					break;
				}
				case 6: {
					lblPrice7.setText(formatInt(timer.selectProduct().get(6).getProductPriceNow()));
					lblPrice7.setPreferredSize(new Dimension(200, lblPrice7.getPreferredSize().height));
					lblPrice7.setHorizontalAlignment(SwingConstants.CENTER);
					
					lblName7.setText(timer.selectProduct().get(6).getProductName());
					lblName7.setPreferredSize(new Dimension(200, lblName7.getPreferredSize().height));
					lblName7.setHorizontalAlignment(SwingConstants.CENTER);
					
//					ImageIcon imageIcon = ImageRetriever.list.get(6);
					ImageIcon imageIcon = ImageRetriever.llist.get(6).getImageicon();
					if (imageIcon != null) {
						lblImage7.setIcon(iconSize(imageIcon));
					}
					String result7 = duration(timer.selectProduct().get(6).getEndTime(), now);
					lblTime7.setText(result7);
					lblTime7.setPreferredSize(new Dimension(200, lblTime7.getPreferredSize().height));
					lblTime7.setHorizontalAlignment(SwingConstants.CENTER);
					
					btns[i].setVisible(true);
					break;
				}
				case 7: {
					lblPrice8.setText(formatInt(timer.selectProduct().get(7).getProductPriceNow()));
					lblPrice8.setPreferredSize(new Dimension(200, lblPrice8.getPreferredSize().height));
					lblPrice8.setHorizontalAlignment(SwingConstants.CENTER);
					
					lblName8.setText(timer.selectProduct().get(7).getProductName());
					lblName8.setPreferredSize(new Dimension(200, lblName8.getPreferredSize().height));
					lblName8.setHorizontalAlignment(SwingConstants.CENTER);
					
//					ImageIcon imageIcon = ImageRetriever.list.get(7);
					ImageIcon imageIcon = ImageRetriever.llist.get(7).getImageicon();
					if (imageIcon != null) {
						lblImage8.setIcon(iconSize(imageIcon));
					}
					String result8 = duration(timer.selectProduct().get(7).getEndTime(), now);
					lblTime8.setText(result8);
					lblTime8.setPreferredSize(new Dimension(200, lblTime8.getPreferredSize().height));
					lblTime8.setHorizontalAlignment(SwingConstants.CENTER);
					
					btns[i].setVisible(true);
					break;
				}
				case 8: {
					lblPrice9.setText(formatInt(timer.selectProduct().get(8).getProductPriceNow()));
					lblPrice9.setPreferredSize(new Dimension(200, lblPrice9.getPreferredSize().height));
					lblPrice9.setHorizontalAlignment(SwingConstants.CENTER);
					
					lblName9.setText(timer.selectProduct().get(8).getProductName());
					lblName9.setPreferredSize(new Dimension(200, lblName9.getPreferredSize().height));
					lblName9.setHorizontalAlignment(SwingConstants.CENTER);
					
//					ImageIcon imageIcon = ImageRetriever.list.get(8);
					ImageIcon imageIcon = ImageRetriever.llist.get(8).getImageicon();
					if (imageIcon != null) {
						lblImage9.setIcon(iconSize(imageIcon));
					}
					String result9 = duration(timer.selectProduct().get(8).getEndTime(), now);
					lblTime9.setText(result9);
					lblTime9.setPreferredSize(new Dimension(200, lblTime9.getPreferredSize().height));
					lblTime9.setHorizontalAlignment(SwingConstants.CENTER);
					
					btns[i].setVisible(true);
					break;
				}
				case 9: {
					lblPrice10.setText(formatInt(timer.selectProduct().get(9).getProductPriceNow()));
					lblPrice10.setPreferredSize(new Dimension(200, lblPrice10.getPreferredSize().height));
					lblPrice10.setHorizontalAlignment(SwingConstants.CENTER);
					
					lblName10.setText(timer.selectProduct().get(9).getProductName());
					lblName10.setPreferredSize(new Dimension(200, lblName10.getPreferredSize().height));
					lblName10.setHorizontalAlignment(SwingConstants.CENTER);
					
//					ImageIcon imageIcon = ImageRetriever.list.get(9);
					ImageIcon imageIcon = ImageRetriever.llist.get(9).getImageicon();
					if (imageIcon != null) {
						lblImage10.setIcon(iconSize(imageIcon));
					}
					String result10 = duration(timer.selectProduct().get(9).getEndTime(), now);
					lblTime10.setText(result10);
					lblTime10.setPreferredSize(new Dimension(200, lblTime10.getPreferredSize().height));
					lblTime10.setHorizontalAlignment(SwingConstants.CENTER);
					
					btns[i].setVisible(true);
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
	
	public static String formatInt(int price) {
		return String.format("%,d원", price);
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