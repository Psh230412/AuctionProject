package secondweek;

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
	private JFrame frame;
	private JPanel contentPane;
//   Connection conn = DBUtil.getConnection();

	public AuctionFrame(DataBase data) {
		timer = new Timer();
		frame = new JFrame();
		frame.setSize(1200, 800);
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
		pnl1.setBounds(50, 200, 200, 250);
		JButton viewProductBtn1 = new JButton("경매보기");
		JPanel pnl2 = new JPanel();
		pnl2.setBounds(280, 200, 200, 250);
		JButton viewProductBtn2 = new JButton("경매보기");
		JPanel pnl3 = new JPanel();
		pnl3.setBounds(500, 200, 200, 250);
		JButton viewProductBtn3 = new JButton("경매보기");
		JPanel pnl4 = new JPanel();
		pnl4.setBounds(720, 200, 200, 250);
		JButton viewProductBtn4 = new JButton("경매보기");
		JPanel pnl5 = new JPanel();
		pnl5.setBounds(940, 200, 200, 250);
		JButton viewProductBtn5 = new JButton("경매보기");
		JPanel pnl6 = new JPanel();
		pnl6.setBounds(50, 470, 200, 250);
		JButton viewProductBtn6 = new JButton("경매보기");
		JPanel pnl7 = new JPanel();
		pnl7.setBounds(280, 470, 200, 250);
		JButton viewProductBtn7 = new JButton("경매보기");
		JPanel pnl8 = new JPanel();
		pnl8.setBounds(500, 470, 200, 250);
		JButton viewProductBtn8 = new JButton("경매보기");
		JPanel pnl9 = new JPanel();
		pnl9.setBounds(720, 470, 200, 250);
		JButton viewProductBtn9 = new JButton("경매보기");
		JPanel pnl10 = new JPanel();
		pnl10.setBounds(940, 470, 200, 250);
		JButton viewProductBtn10 = new JButton("경매보기");

		JButton[] btn = { viewProductBtn1, viewProductBtn2, viewProductBtn3, viewProductBtn4, viewProductBtn5,
				viewProductBtn6, viewProductBtn7, viewProductBtn8, viewProductBtn9, viewProductBtn10 };

		for (int i = 0; i < btn.length; i++) {
			int index = i;
			btn[i].addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					List<Product> products = timer.selectProduct();
					if (products.size() >= index) {
						Product product = products.get(index);
						data.setProduct(product);
						new DetailFrame(data);
						frame.setVisible(false);
					}
				}
			});
		}
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

//      int x = 25;
//      int y = 150;
//      for (int i = 0; i < pnls.length; i++) {
//
//         pnls[i].setBounds(x, y, 130, 225);
//         JButton viewProductBtn = new JButton("경매보기");
//
//         pnls[i].add(viewProductBtn);
//
//         contentPane.add(pnls[i]);
//
//         if ((i + 1) / 5 == 1) {
//            y = 400;
//         }
//         if ((i + 1) == 5) {
//            x = 25;
//         } else {
//            x += 155;
//         }
//      }

		lblTime1 = new JLabel("00 : 00 : 01");
		pnl1.add(lblTime1);

		lblTime2 = new JLabel("00 : 00 : 02");
		pnl2.add(lblTime2);

		lblTime3 = new JLabel("00 : 00 : 03");
		pnl3.add(lblTime3);

		lblTime4 = new JLabel("00 : 00 : 04");
		pnl4.add(lblTime4);

		lblTime5 = new JLabel("00 : 00 : 05");
		pnl5.add(lblTime5);

		lblTime6 = new JLabel("00 : 00 : 06");
		pnl6.add(lblTime6);

		lblTime7 = new JLabel("00 : 00 : 07");
		pnl7.add(lblTime7);

		lblTime8 = new JLabel("00 : 00 : 08");
		pnl8.add(lblTime8);

		lblTime9 = new JLabel("00 : 00 : 09");
		pnl9.add(lblTime9);

		lblTime10 = new JLabel("00 : 00 : 10");
		pnl10.add(lblTime10);

		lblPrice1 = new JLabel("현재가 8001원");

		pnl1.add(lblPrice1);

		lblPrice2 = new JLabel("현재가 8002원");
		pnl2.add(lblPrice2);

		lblPrice3 = new JLabel("현재가 8003원");
		pnl3.add(lblPrice3);

		lblPrice4 = new JLabel("현재가 8004원");
		pnl4.add(lblPrice4);

		lblPrice5 = new JLabel("현재가 8005원");
		pnl5.add(lblPrice5);

		lblPrice6 = new JLabel("현재가 8006원");
		pnl6.add(lblPrice6);

		lblPrice7 = new JLabel("현재가 8007원");
		pnl7.add(lblPrice7);

		lblPrice8 = new JLabel("현재가 8008원");
		pnl8.add(lblPrice8);

		lblPrice9 = new JLabel("현재가 8009원");
		pnl9.add(lblPrice9);

		lblPrice10 = new JLabel("현재가 8010원");
		pnl10.add(lblPrice10);

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
		Image changeSize = img.getScaledInstance(140, 140, Image.SCALE_SMOOTH);
		return new ImageIcon(changeSize);
	}

	public static void updatLabel(LocalDateTime now) {
		Connection conn = null;

		try {
			conn = DBUtil.getConnection();
			ImageRetriever.retrieveImage(conn);

			for (int i = 0; i < timer.selectProduct().size(); i++) {
				switch (i) {
				case 0: {
					lblPrice1.setText(Integer.toString(timer.selectProduct().get(0).getProductPriceNow()));

//               ImageIcon imageIcon = ImageRetriever.retrieveImage(conn, 1);
//
//               if (imageIcon != null) {
//
//                  lblImage1.setIcon(iconSize(imageIcon));
//               }
					ImageIcon imageIcon = ImageRetriever.list.get(0);
					if (imageIcon != null) {
						lblImage1.setIcon(iconSize(imageIcon));
					}

					String result1 = duration(timer.selectProduct().get(0).getEndTime(), now);
					lblTime1.setText(result1);
					break;
				}
				case 1: {
					lblPrice2.setText(Integer.toString(timer.selectProduct().get(1).getProductPriceNow()));

//               ImageIcon imageIcon = ImageRetriever.retrieveImage(conn, 2);
//
//               if (imageIcon != null) {
//
//                  lblImage2.setIcon(iconSize(imageIcon));
//               }

					ImageIcon imageIcon = ImageRetriever.list.get(1);
					if (imageIcon != null) {
						lblImage2.setIcon(iconSize(imageIcon));
					}
					String result2 = duration(timer.selectProduct().get(1).getEndTime(), now);
					lblTime2.setText(result2);
					break;
				}
				case 2: {
					lblPrice3.setText(Integer.toString(timer.selectProduct().get(2).getProductPriceNow()));

//               ImageIcon imageIcon = ImageRetriever.retrieveImage(conn, 3);
//
//               if (imageIcon != null) {
//
//                  lblImage3.setIcon(iconSize(imageIcon));
//               }

					ImageIcon imageIcon = ImageRetriever.list.get(2);
					if (imageIcon != null) {
						lblImage3.setIcon(iconSize(imageIcon));
					}

					String result3 = duration(timer.selectProduct().get(2).getEndTime(), now);
					lblTime3.setText(result3);
					break;
				}
				case 3: {
					lblPrice4.setText(Integer.toString(timer.selectProduct().get(3).getProductPriceNow()));
//               ImageIcon imageIcon = ImageRetriever.retrieveImage(conn, 4);
//
//               if (imageIcon != null) {
//
//                  lblImage4.setIcon(iconSize(imageIcon));
//               }

					ImageIcon imageIcon = ImageRetriever.list.get(3);
					if (imageIcon != null) {
						lblImage4.setIcon(iconSize(imageIcon));
					}

					String result4 = duration(timer.selectProduct().get(3).getEndTime(), now);
					lblTime4.setText(result4);
					break;
				}
				case 4: {
					lblPrice5.setText(Integer.toString(timer.selectProduct().get(4).getProductPriceNow()));

//               ImageIcon imageIcon = ImageRetriever.retrieveImage(conn, 5);
//
//               if (imageIcon != null) {
//
//                  lblImage5.setIcon(iconSize(imageIcon));
//               }

					ImageIcon imageIcon = ImageRetriever.list.get(4);
					if (imageIcon != null) {
						lblImage5.setIcon(iconSize(imageIcon));
					}

					String result5 = duration(timer.selectProduct().get(4).getEndTime(), now);
					lblTime5.setText(result5);
					break;
				}
				case 5: {
					lblPrice6.setText(Integer.toString(timer.selectProduct().get(5).getProductPriceNow()));

//               ImageIcon imageIcon = ImageRetriever.retrieveImage(conn, 6);
//
//               if (imageIcon != null) {
//
//                  lblImage6.setIcon(iconSize(imageIcon));
//               }

					ImageIcon imageIcon = ImageRetriever.list.get(5);
					if (imageIcon != null) {
						lblImage6.setIcon(iconSize(imageIcon));
					}

					String result6 = duration(timer.selectProduct().get(5).getEndTime(), now);
					lblTime6.setText(result6);
					break;
				}
				case 6: {
					lblPrice7.setText(Integer.toString(timer.selectProduct().get(6).getProductPriceNow()));

//               ImageIcon imageIcon = ImageRetriever.retrieveImage(conn, 7);
//
//               if (imageIcon != null) {
//
//                  lblImage7.setIcon(iconSize(imageIcon));
//               }

					ImageIcon imageIcon = ImageRetriever.list.get(6);
					if (imageIcon != null) {
						lblImage7.setIcon(iconSize(imageIcon));
					}
					String result7 = duration(timer.selectProduct().get(6).getEndTime(), now);
					lblTime7.setText(result7);
					break;
				}
				case 7: {
					lblPrice8.setText(Integer.toString(timer.selectProduct().get(7).getProductPriceNow()));

//               ImageIcon imageIcon = ImageRetriever.retrieveImage(conn, 8);
//
//               if (imageIcon != null) {
//
//                  lblImage8.setIcon(iconSize(imageIcon));
//               }

					ImageIcon imageIcon = ImageRetriever.list.get(7);
					if (imageIcon != null) {
						lblImage8.setIcon(iconSize(imageIcon));
					}
					String result8 = duration(timer.selectProduct().get(7).getEndTime(), now);
					lblTime8.setText(result8);
					break;
				}
				case 8: {
					lblPrice9.setText(Integer.toString(timer.selectProduct().get(8).getProductPriceNow()));

//               ImageIcon imageIcon = ImageRetriever.retrieveImage(conn, 9);
//
//               if (imageIcon != null) {
//
//                  lblImage9.setIcon(iconSize(imageIcon));
//               }
					ImageIcon imageIcon = ImageRetriever.list.get(8);
					if (imageIcon != null) {
						lblImage9.setIcon(iconSize(imageIcon));
					}
					String result9 = duration(timer.selectProduct().get(8).getEndTime(), now);
					lblTime9.setText(result9);
					break;
				}
				case 9: {
					lblPrice10.setText(Integer.toString(timer.selectProduct().get(9).getProductPriceNow()));

//               ImageIcon imageIcon = ImageRetriever.retrieveImage(conn, 10);
//
//               if (imageIcon != null) {
//
//                  lblImage10.setIcon(iconSize(imageIcon));
//               }
					ImageIcon imageIcon = ImageRetriever.list.get(9);
					if (imageIcon != null) {
						lblImage10.setIcon(iconSize(imageIcon));
					}
					String result10 = duration(timer.selectProduct().get(9).getEndTime(), now);
					lblTime10.setText(result10);
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