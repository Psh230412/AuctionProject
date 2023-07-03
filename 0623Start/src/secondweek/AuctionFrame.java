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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
		frame.setSize(1200, 900);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
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

		JLabel userLbl = new JLabel();
		userLbl.setText(data.getCurrentUser().getName());
		userLbl.setFont(new Font("돋움", Font.BOLD, 20));
		userLbl.setForeground(Color.BLACK);
		userLbl.setBounds(100, 110, 100, 25);
		JLabel tailLbl1 = new JLabel();
		tailLbl1.setText("님 환영합니다.");
		tailLbl1.setFont(new Font("돋움", Font.PLAIN, 16));
		tailLbl1.setForeground(Color.gray);
		tailLbl1.setBounds(190, 112, 110, 25);
		contentPane.add(tailLbl1);

		JButton mypageBtn = new JButton("마이페이지");
		mypageBtn.setBounds(433, 117, 118, 49);
		ImageIcon imgmypageBtn = new ImageIcon("img/main_mypage_1.png");
		mypageBtn.setContentAreaFilled(false);
		mypageBtn.setBorderPainted(false);
		mypageBtn.setIcon(imgmypageBtn);

		mypageBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseExited(MouseEvent e) {
				ImageIcon imgmypageBtn = new ImageIcon("img/main_mypage_1.png");
				mypageBtn.setIcon(imgmypageBtn);

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				ImageIcon imgmypageBtn = new ImageIcon("img/main_mypage.png");
				mypageBtn.setIcon(imgmypageBtn);

			}
		});
		
		mypageBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				data.setCheckBtn(false);
				data.setSearchText("");
				new MypageFrame(data);
				frame.setVisible(false);
			}
		});

		JButton logoutBtn = new JButton("로그아웃");
		logoutBtn.setBounds(580,  117, 118, 49);
		ImageIcon imglogoutBtn = new ImageIcon("img/main_logout_1.png");
		logoutBtn.setContentAreaFilled(false);
		logoutBtn.setBorderPainted(false);
		logoutBtn.setIcon(imglogoutBtn);

		logoutBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseExited(MouseEvent e) {
				ImageIcon imglogoutBtn = new ImageIcon("img/main_logout_1.png");
				logoutBtn.setIcon(imglogoutBtn);

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				ImageIcon imglogoutBtn = new ImageIcon("img/main_logout.png");
				logoutBtn.setIcon(imglogoutBtn);

			}
		});
		logoutBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int choice = JOptionPane.showConfirmDialog(null, "로그아웃하시겠습니까?", "로그아웃", JOptionPane.YES_NO_OPTION);
				if (choice == JOptionPane.YES_OPTION) {
					data.setCheckBtn(false);
					data.setSearchText("");
					JOptionPane.showMessageDialog(null, "로그아웃되었습니다.");
					DataBase newdata = new DataBase();
					new LoginFrame(newdata);
					frame.setVisible(false);
				}
			}
		});

		JButton exitBtn = new JButton("종료");
		exitBtn.setBounds(720,  117, 118, 49);
		ImageIcon imgexitBtn = new ImageIcon("img/main_exit_1.png");
		exitBtn.setContentAreaFilled(false);
		exitBtn.setBorderPainted(false);
		exitBtn.setIcon(imgexitBtn);

		exitBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseExited(MouseEvent e) {
				ImageIcon imgexitBtn = new ImageIcon("img/main_exit_1.png");
				exitBtn.setIcon(imgexitBtn);

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				ImageIcon imgexitBtn = new ImageIcon("img/main_exit.png");
				exitBtn.setIcon(imgexitBtn);

			}
		});
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
				data.setCheckBtn(false);
				data.setSearchText("");
				data.setIndexMainSearch(0);
				searchTab.setText("");
			}
		});

		JButton searchBtn = new JButton("S");
		ImageIcon imgsearchBtn = new ImageIcon("img/searchbtn.png");
		searchBtn.setContentAreaFilled(false);
		searchBtn.setBorderPainted(false);
		searchBtn.setIcon(imgsearchBtn);

		searchBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseExited(MouseEvent e) {
				ImageIcon imgsearchBtn = new ImageIcon("img/searchbtn.png");
				searchBtn.setIcon(imgsearchBtn);

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				ImageIcon imgsearchBtn = new ImageIcon("img/searchbtn_1.png");
				searchBtn.setIcon(imgsearchBtn);

			}
		});
		
		searchBtn.setBounds(452, 43, 108, 61);
		searchBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				data.setCheckBtn(true);
				data.setSearchText(searchTab.getText());
				data.setIndexMain(0);

			}
		});

		searchTab = new JTextField();
		searchTab.setBounds(560, 52, 160, 25);

		contentPane.add(userLbl);
		contentPane.add(mypageBtn);
		contentPane.add(logoutBtn);
		contentPane.add(exitBtn);
		contentPane.add(searchTab);
		contentPane.add(searchBtn);
		contentPane.add(searchAll);

		JPanel pnl1 = new JPanel();
		pnl1.setLayout(new FlowLayout());
		pnl1.setOpaque(false);
		pnl1.setBounds(35, 180, 200, 300);
		JPanel pnl2 = new JPanel();
		pnl2.setOpaque(false);
		pnl2.setBounds(260, 180, 200, 300);
		JPanel pnl3 = new JPanel();
		pnl3.setOpaque(false);
		pnl3.setBounds(492, 180, 200, 300);
		JPanel pnl4 = new JPanel();
		pnl4.setOpaque(false);
		pnl4.setBounds(724, 180, 200, 300);
		JPanel pnl5 = new JPanel();
		pnl5.setOpaque(false);
		pnl5.setBounds(960, 180, 200, 300);
		JPanel pnl6 = new JPanel();
		pnl6.setOpaque(false);
		pnl6.setBounds(35, 485, 200, 300);
		JPanel pnl7 = new JPanel();
		pnl7.setOpaque(false);
		pnl7.setBounds(260, 485, 200, 300);
		JPanel pnl8 = new JPanel();
		pnl8.setOpaque(false);
		pnl8.setBounds(492, 485, 200, 300);
		JPanel pnl9 = new JPanel();
		pnl9.setOpaque(false);
		pnl9.setBounds(724, 485, 200, 300);
		JPanel pnl10 = new JPanel();
		pnl10.setOpaque(false);
		pnl10.setBounds(960, 485, 200, 300);

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

		JButton viewProductBtn1 = new JButton();
		contentPane.add(viewProductBtn1);
	
		JButton viewProductBtn2 = new JButton();
		contentPane.add(viewProductBtn2);
	
		JButton viewProductBtn3 = new JButton();
		contentPane.add(viewProductBtn3);
	
		JButton viewProductBtn4 = new JButton();
		contentPane.add(viewProductBtn4);
	
		JButton viewProductBtn5 = new JButton();
	
		contentPane.add(viewProductBtn5);

		JButton viewProductBtn6 = new JButton();
	
		contentPane.add(viewProductBtn6);

		JButton viewProductBtn7 = new JButton();
	
		contentPane.add(viewProductBtn7);
	
		JButton viewProductBtn8 = new JButton();

		contentPane.add(viewProductBtn8);

		JButton viewProductBtn9 = new JButton();
	
		contentPane.add(viewProductBtn9);
	
		JButton viewProductBtn10 = new JButton();
	
		contentPane.add(viewProductBtn10);

		btns = new JButton[] { viewProductBtn1, viewProductBtn2, viewProductBtn3, viewProductBtn4, viewProductBtn5,
				viewProductBtn6, viewProductBtn7, viewProductBtn8, viewProductBtn9, viewProductBtn10 };

		for (int i = 0; i < btns.length; i++) {
			int index = i;
	
			btns[i].setContentAreaFilled(false); 
			btns[i].setBorderPainted(false);
			ImageIcon imgbid= new ImageIcon("img/godetail.png");
			btns[i].setIcon(imgbid);
			btns[i].addMouseListener(new MouseAdapter() {
			
			    @Override
			    public void mouseExited(MouseEvent e) {
				ImageIcon imgbid = new ImageIcon("img/godetail.png");
				btns[index].setIcon(imgbid);
				
			    }
			    
			    @Override
			    public void mouseEntered(MouseEvent e) {
				ImageIcon imgbid = new ImageIcon("img/godetail_1.png");
				btns[index].setIcon(imgbid);
				
			    }
		 
			});
			
			btns[i].addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					if (data.isCheckBtn()) {
						List<Product> products = timer.selectSearchProduct(data.getSearchText());
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
		previousEnroll.setBounds(450, 810, 110, 50);
		previousEnroll.setContentAreaFilled(false); 
		previousEnroll.setBorderPainted(false);
		ImageIcon imgpreviousEnroll = new ImageIcon("img/previous_1.png");
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

		contentPane.add(previousEnroll);

		lblNum1 = new JLabel(" - 1 - ");
		lblNum1.setBounds(560, 805, 80, 40);
		contentPane.add(lblNum1);

		nextEnroll = new JButton("다음");
		nextEnroll.setBounds(660, 810, 110, 50);
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
		contentPane.add(nextEnroll);

		previousEnroll.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (data.isCheckBtn()) {
					if (data.getIndexMainSearch() >= 10) {
						data.setIndexMainSearch(data.getIndexMainSearch() - 10);
					}
				} else {
					if (data.getIndexMain() >= 10) {
						data.setIndexMain(data.getIndexMain() - 10);
					}
				}
			}
		});

		nextEnroll.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (data.isCheckBtn()) {
					if (((timer.selectSearchProduct(data.getSearchText()).size() - 1) / 10) > (data.getIndexMainSearch() / 10)) {
						data.setIndexMainSearch(data.getIndexMainSearch() + 10);
					}
				} else {
					if (((timer.selectProduct().size() - 1) / 10) > (data.getIndexMain() / 10)) {
						data.setIndexMain(data.getIndexMain() + 10);
					}
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
		
		JButton returnBtn = new JButton();
		returnBtn.setBounds(750, 46, 130, 50);
		ImageIcon imgreturnBtn = new ImageIcon("img/Goback_1.png");
		returnBtn.setContentAreaFilled(false);
		returnBtn.setBorderPainted(false);
		returnBtn.setIcon(imgreturnBtn);

		returnBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseExited(MouseEvent e) {
				ImageIcon imgreturnBtn = new ImageIcon("img/Goback_1.png");
				returnBtn.setIcon(imgreturnBtn);

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				ImageIcon imgreturnBtn = new ImageIcon("img/Goback.png");
				returnBtn.setIcon(imgreturnBtn);

			}
		});

		returnBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				DataBase data = new DataBase();
				new AuctionFrame(data);
				frame.setVisible(false);
			}
		});

		contentPane.add(returnBtn);
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

		lblNum1.setText(" - " + 1 + " - ");
		lblNum1.setHorizontalAlignment(SwingConstants.CENTER);
		Font font2 = new Font("맑은 고딕", Font.BOLD, 22);
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
			List<Product> productList = timer.selectProduct();
			
			for (int i = data.getIndexMain(); i < productList.size(); i++) {
				count++;
				if (count == 11) {
					break;
				}
				switch (i % 10) {
				case 0: {
					lblPrice1.setText(formatInt(productList.get(i).getProductPriceNow()));
					lblPrice1.setPreferredSize(new Dimension(200, lblPrice1.getPreferredSize().height));
					lblPrice1.setHorizontalAlignment(SwingConstants.CENTER);

					lblName1.setText(productList.get(i).getProductName());
					lblName1.setPreferredSize(new Dimension(200, lblName1.getPreferredSize().height));
					lblName1.setHorizontalAlignment(SwingConstants.CENTER);

					ImageIcon imageIcon = ImageRetriever.llist.get(i).getImageicon();
					if (imageIcon != null) {
						lblImage1.setIcon(iconSize(imageIcon));
					}
					String result1 = duration(productList.get(i).getEndTime(), now);
					lblTime1.setText(result1);
					lblTime1.setPreferredSize(new Dimension(200, lblTime1.getPreferredSize().height));
					lblTime1.setHorizontalAlignment(SwingConstants.CENTER);

					if (durationTen(productList.get(i).getEndTime(), now)) {
						lblTime1.setForeground(Color.RED);
						lblTime1.setFont(lblTime1.getFont().deriveFont(15f));
					}

					btns[0].setVisible(true);
					list.add(new Auction(i, productList.get(i).getAuctionNo()));
					break;
				}
				case 1: {
					lblPrice2.setText(formatInt(productList.get(i).getProductPriceNow()));
					lblPrice2.setPreferredSize(new Dimension(200, lblPrice2.getPreferredSize().height));
					lblPrice2.setHorizontalAlignment(SwingConstants.CENTER);

					lblName2.setText(productList.get(i).getProductName());
					lblName2.setPreferredSize(new Dimension(200, lblName2.getPreferredSize().height));
					lblName2.setHorizontalAlignment(SwingConstants.CENTER);

					ImageIcon imageIcon = ImageRetriever.llist.get(i).getImageicon();
					if (imageIcon != null) {
						lblImage2.setIcon(iconSize(imageIcon));
					}
					String result2 = duration(productList.get(i).getEndTime(), now);
					lblTime2.setText(result2);
					lblTime2.setPreferredSize(new Dimension(200, lblTime2.getPreferredSize().height));
					lblTime2.setHorizontalAlignment(SwingConstants.CENTER);

					if (durationTen(productList.get(i).getEndTime(), now)) {
						lblTime1.setForeground(Color.RED);
						lblTime1.setFont(lblTime1.getFont().deriveFont(15f));
					}

					btns[1].setVisible(true);
					list.add(new Auction(i, productList.get(i).getAuctionNo()));
					break;
				}
				case 2: {
					lblPrice3.setText(formatInt(productList.get(i).getProductPriceNow()));
					lblPrice3.setPreferredSize(new Dimension(200, lblPrice3.getPreferredSize().height));
					lblPrice3.setHorizontalAlignment(SwingConstants.CENTER);

					lblName3.setText(productList.get(i).getProductName());
					lblName3.setPreferredSize(new Dimension(200, lblName3.getPreferredSize().height));
					lblName3.setHorizontalAlignment(SwingConstants.CENTER);

					ImageIcon imageIcon = ImageRetriever.llist.get(i).getImageicon();
					if (imageIcon != null) {
						lblImage3.setIcon(iconSize(imageIcon));
					}

					String result3 = duration(productList.get(i).getEndTime(), now);
					lblTime3.setText(result3);
					lblTime3.setPreferredSize(new Dimension(200, lblTime3.getPreferredSize().height));
					lblTime3.setHorizontalAlignment(SwingConstants.CENTER);

					if (durationTen(productList.get(i).getEndTime(), now)) {
						lblTime1.setForeground(Color.RED);
						lblTime1.setFont(lblTime1.getFont().deriveFont(15f));
					}

					btns[2].setVisible(true);
					list.add(new Auction(i, productList.get(i).getAuctionNo()));
					break;
				}
				case 3: {
					lblPrice4.setText(formatInt(productList.get(i).getProductPriceNow()));
					lblPrice4.setPreferredSize(new Dimension(200, lblPrice4.getPreferredSize().height));
					lblPrice4.setHorizontalAlignment(SwingConstants.CENTER);

					lblName4.setText(productList.get(i).getProductName());
					lblName4.setPreferredSize(new Dimension(200, lblName4.getPreferredSize().height));
					lblName4.setHorizontalAlignment(SwingConstants.CENTER);

					ImageIcon imageIcon = ImageRetriever.llist.get(i).getImageicon();
					if (imageIcon != null) {
						lblImage4.setIcon(iconSize(imageIcon));
					}

					String result4 = duration(productList.get(i).getEndTime(), now);
					lblTime4.setText(result4);
					lblTime4.setPreferredSize(new Dimension(200, lblTime4.getPreferredSize().height));
					lblTime4.setHorizontalAlignment(SwingConstants.CENTER);

					if (durationTen(productList.get(i).getEndTime(), now)) {
						lblTime1.setForeground(Color.RED);
						lblTime1.setFont(lblTime1.getFont().deriveFont(15f));
					}

					btns[3].setVisible(true);
					list.add(new Auction(i, productList.get(i).getAuctionNo()));
					break;
				}
				case 4: {
					lblPrice5.setText(formatInt(productList.get(i).getProductPriceNow()));
					lblPrice5.setPreferredSize(new Dimension(200, lblPrice5.getPreferredSize().height));
					lblPrice5.setHorizontalAlignment(SwingConstants.CENTER);

					lblName5.setText(productList.get(i).getProductName());
					lblName5.setPreferredSize(new Dimension(200, lblName5.getPreferredSize().height));
					lblName5.setHorizontalAlignment(SwingConstants.CENTER);

					ImageIcon imageIcon = ImageRetriever.llist.get(i).getImageicon();
					if (imageIcon != null) {
						lblImage5.setIcon(iconSize(imageIcon));
					}

					String result5 = duration(productList.get(i).getEndTime(), now);
					lblTime5.setText(result5);
					lblTime5.setPreferredSize(new Dimension(200, lblTime5.getPreferredSize().height));
					lblTime5.setHorizontalAlignment(SwingConstants.CENTER);

					if (durationTen(productList.get(i).getEndTime(), now)) {
						lblTime1.setForeground(Color.RED);
						lblTime1.setFont(lblTime1.getFont().deriveFont(15f));
					}

					btns[4].setVisible(true);
					list.add(new Auction(i, productList.get(i).getAuctionNo()));
					break;
				}
				case 5: {
					lblPrice6.setText(formatInt(productList.get(i).getProductPriceNow()));
					lblPrice6.setPreferredSize(new Dimension(200, lblPrice6.getPreferredSize().height));
					lblPrice6.setHorizontalAlignment(SwingConstants.CENTER);

					lblName6.setText(productList.get(i).getProductName());
					lblName6.setPreferredSize(new Dimension(200, lblName6.getPreferredSize().height));
					lblName6.setHorizontalAlignment(SwingConstants.CENTER);

					ImageIcon imageIcon = ImageRetriever.llist.get(i).getImageicon();
					if (imageIcon != null) {
						lblImage6.setIcon(iconSize(imageIcon));
					}

					String result6 = duration(productList.get(i).getEndTime(), now);
					lblTime6.setText(result6);
					lblTime6.setPreferredSize(new Dimension(200, lblTime6.getPreferredSize().height));
					lblTime6.setHorizontalAlignment(SwingConstants.CENTER);

					if (durationTen(productList.get(i).getEndTime(), now)) {
						lblTime1.setForeground(Color.RED);
						lblTime1.setFont(lblTime1.getFont().deriveFont(15f));
					}

					btns[5].setVisible(true);
					list.add(new Auction(i, productList.get(i).getAuctionNo()));
					break;
				}
				case 6: {
					lblPrice7.setText(formatInt(productList.get(i).getProductPriceNow()));
					lblPrice7.setPreferredSize(new Dimension(200, lblPrice7.getPreferredSize().height));
					lblPrice7.setHorizontalAlignment(SwingConstants.CENTER);

					lblName7.setText(productList.get(i).getProductName());
					lblName7.setPreferredSize(new Dimension(200, lblName7.getPreferredSize().height));
					lblName7.setHorizontalAlignment(SwingConstants.CENTER);

					ImageIcon imageIcon = ImageRetriever.llist.get(i).getImageicon();
					if (imageIcon != null) {
						lblImage7.setIcon(iconSize(imageIcon));
					}
					String result7 = duration(productList.get(i).getEndTime(), now);
					lblTime7.setText(result7);
					lblTime7.setPreferredSize(new Dimension(200, lblTime7.getPreferredSize().height));
					lblTime7.setHorizontalAlignment(SwingConstants.CENTER);

					if (durationTen(productList.get(i).getEndTime(), now)) {
						lblTime1.setForeground(Color.RED);
						lblTime1.setFont(lblTime1.getFont().deriveFont(15f));
					}

					btns[6].setVisible(true);
					list.add(new Auction(i, productList.get(i).getAuctionNo()));
					break;
				}
				case 7: {
					lblPrice8.setText(formatInt(productList.get(i).getProductPriceNow()));
					lblPrice8.setPreferredSize(new Dimension(200, lblPrice8.getPreferredSize().height));
					lblPrice8.setHorizontalAlignment(SwingConstants.CENTER);

					lblName8.setText(productList.get(i).getProductName());
					lblName8.setPreferredSize(new Dimension(200, lblName8.getPreferredSize().height));
					lblName8.setHorizontalAlignment(SwingConstants.CENTER);

					ImageIcon imageIcon = ImageRetriever.llist.get(i).getImageicon();
					if (imageIcon != null) {
						lblImage8.setIcon(iconSize(imageIcon));
					}
					String result8 = duration(productList.get(i).getEndTime(), now);
					lblTime8.setText(result8);
					lblTime8.setPreferredSize(new Dimension(200, lblTime8.getPreferredSize().height));
					lblTime8.setHorizontalAlignment(SwingConstants.CENTER);

					if (durationTen(productList.get(i).getEndTime(), now)) {
						lblTime1.setForeground(Color.RED);
						lblTime1.setFont(lblTime1.getFont().deriveFont(15f));
					}

					btns[7].setVisible(true);
					list.add(new Auction(i, productList.get(i).getAuctionNo()));
					break;
				}
				case 8: {
					lblPrice9.setText(formatInt(productList.get(i).getProductPriceNow()));
					lblPrice9.setPreferredSize(new Dimension(200, lblPrice9.getPreferredSize().height));
					lblPrice9.setHorizontalAlignment(SwingConstants.CENTER);

					lblName9.setText(productList.get(i).getProductName());
					lblName9.setPreferredSize(new Dimension(200, lblName9.getPreferredSize().height));
					lblName9.setHorizontalAlignment(SwingConstants.CENTER);

					ImageIcon imageIcon = ImageRetriever.llist.get(i).getImageicon();
					if (imageIcon != null) {
						lblImage9.setIcon(iconSize(imageIcon));
					}
					String result9 = duration(productList.get(i).getEndTime(), now);
					lblTime9.setText(result9);
					lblTime9.setPreferredSize(new Dimension(200, lblTime9.getPreferredSize().height));
					lblTime9.setHorizontalAlignment(SwingConstants.CENTER);

					if (durationTen(productList.get(i).getEndTime(), now)) {
						lblTime1.setForeground(Color.RED);
						lblTime1.setFont(lblTime1.getFont().deriveFont(15f));
					}

					btns[8].setVisible(true);
					list.add(new Auction(i, productList.get(i).getAuctionNo()));
					break;
				}
				case 9: {
					lblPrice10.setText(formatInt(productList.get(i).getProductPriceNow()));
					lblPrice10.setPreferredSize(new Dimension(200, lblPrice10.getPreferredSize().height));
					lblPrice10.setHorizontalAlignment(SwingConstants.CENTER);

					lblName10.setText(productList.get(i).getProductName());
					lblName10.setPreferredSize(new Dimension(200, lblName10.getPreferredSize().height));
					lblName10.setHorizontalAlignment(SwingConstants.CENTER);

					ImageIcon imageIcon = ImageRetriever.llist.get(i).getImageicon();
					if (imageIcon != null) {
						lblImage10.setIcon(iconSize(imageIcon));
					}
					String result10 = duration(productList.get(i).getEndTime(), now);
					lblTime10.setText(result10);
					lblTime10.setPreferredSize(new Dimension(200, lblTime10.getPreferredSize().height));
					lblTime10.setHorizontalAlignment(SwingConstants.CENTER);

					if (durationTen(productList.get(i).getEndTime(), now)) {
						lblTime1.setForeground(Color.RED);
						lblTime1.setFont(lblTime1.getFont().deriveFont(15f));
					}

					btns[9].setVisible(true);
					list.add(new Auction(i, productList.get(i).getAuctionNo()));
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

			lblNum1.setText(" - " + String.valueOf((data.getIndexMainSearch() / 10) + 1) + " - ");

			List<Product> productList = new ArrayList<Product>();
			productList = timer.selectSearchProduct(text);
			int count = 0;
			for (int i = data.getIndexMainSearch(); i < productList.size(); i++) {
				count++;
				if (count == 11) {
					break;
				}
				switch (i % 10) {

				case 0: {
					lblPrice1.setText(formatInt(productList.get(i).getProductPriceNow()));
					lblPrice1.setPreferredSize(new Dimension(200, lblPrice1.getPreferredSize().height));
					lblPrice1.setHorizontalAlignment(SwingConstants.CENTER);

					lblName1.setText(productList.get(i).getProductName());
					lblName1.setPreferredSize(new Dimension(200, lblName1.getPreferredSize().height));
					lblName1.setHorizontalAlignment(SwingConstants.CENTER);
					byte[] imageBytes = productList.get(i).getImage().getBytes(1,
							(int) productList.get(i).getImage().length());
					ImageIcon imageIcon = new ImageIcon(imageBytes);
					Image image = imageIcon.getImage().getScaledInstance(400, 400, Image.SCALE_SMOOTH);
					ImageIcon imageIcon2 = new ImageIcon(image);
					if (imageIcon2 != null) {
						lblImage1.setIcon(iconSize(imageIcon2));
					}

					String result1 = duration(productList.get(i).getEndTime(), now);
					lblTime1.setText(result1);
					lblTime1.setPreferredSize(new Dimension(200, lblTime1.getPreferredSize().height));
					lblTime1.setHorizontalAlignment(SwingConstants.CENTER);

					if (durationTen(productList.get(i).getEndTime(), now)) {
						lblTime1.setForeground(Color.RED);
						lblTime1.setFont(lblTime1.getFont().deriveFont(15f));
					}

					btns[0].setVisible(true);
					list.add(new Auction(i, productList.get(i).getAuctionNo()));
					break;
				}
				case 1: {
					lblPrice2.setText(formatInt(productList.get(i).getProductPriceNow()));
					lblPrice2.setPreferredSize(new Dimension(200, lblPrice2.getPreferredSize().height));
					lblPrice2.setHorizontalAlignment(SwingConstants.CENTER);

					lblName2.setText(productList.get(i).getProductName());
					lblName2.setPreferredSize(new Dimension(200, lblName2.getPreferredSize().height));
					lblName2.setHorizontalAlignment(SwingConstants.CENTER);

					byte[] imageBytes = productList.get(i).getImage().getBytes(1,
							(int) productList.get(i).getImage().length());
					ImageIcon imageIcon = new ImageIcon(imageBytes);
					Image image = imageIcon.getImage().getScaledInstance(400, 400, Image.SCALE_SMOOTH);
					ImageIcon imageIcon2 = new ImageIcon(image);
					if (imageIcon2 != null) {
						lblImage2.setIcon(iconSize(imageIcon2));
					}
					String result2 = duration(productList.get(i).getEndTime(), now);
					lblTime2.setText(result2);
					lblTime2.setPreferredSize(new Dimension(200, lblTime2.getPreferredSize().height));
					lblTime2.setHorizontalAlignment(SwingConstants.CENTER);

					if (durationTen(productList.get(i).getEndTime(), now)) {
						lblTime1.setForeground(Color.RED);
						lblTime1.setFont(lblTime1.getFont().deriveFont(15f));
					}

					btns[1].setVisible(true);
					list.add(new Auction(i, productList.get(i).getAuctionNo()));
					break;
				}
				case 2: {
					lblPrice3.setText(formatInt(productList.get(i).getProductPriceNow()));
					lblPrice3.setPreferredSize(new Dimension(200, lblPrice3.getPreferredSize().height));
					lblPrice3.setHorizontalAlignment(SwingConstants.CENTER);

					lblName3.setText(productList.get(i).getProductName());
					lblName3.setPreferredSize(new Dimension(200, lblName3.getPreferredSize().height));
					lblName3.setHorizontalAlignment(SwingConstants.CENTER);

					byte[] imageBytes = productList.get(i).getImage().getBytes(1,
							(int) productList.get(i).getImage().length());
					ImageIcon imageIcon = new ImageIcon(imageBytes);
					Image image = imageIcon.getImage().getScaledInstance(400, 400, Image.SCALE_SMOOTH);
					ImageIcon imageIcon2 = new ImageIcon(image);
					if (imageIcon2 != null) {
						lblImage3.setIcon(iconSize(imageIcon2));
					}

					String result3 = duration(productList.get(i).getEndTime(), now);
					lblTime3.setText(result3);
					lblTime3.setPreferredSize(new Dimension(200, lblTime3.getPreferredSize().height));
					lblTime3.setHorizontalAlignment(SwingConstants.CENTER);

					if (durationTen(productList.get(i).getEndTime(), now)) {
						lblTime1.setForeground(Color.RED);
						lblTime1.setFont(lblTime1.getFont().deriveFont(15f));
					}

					btns[2].setVisible(true);
					list.add(new Auction(i, productList.get(i).getAuctionNo()));
					break;
				}
				case 3: {
					lblPrice4.setText(formatInt(productList.get(i).getProductPriceNow()));
					lblPrice4.setPreferredSize(new Dimension(200, lblPrice4.getPreferredSize().height));
					lblPrice4.setHorizontalAlignment(SwingConstants.CENTER);

					lblName4.setText(productList.get(i).getProductName());
					lblName4.setPreferredSize(new Dimension(200, lblName4.getPreferredSize().height));
					lblName4.setHorizontalAlignment(SwingConstants.CENTER);

					byte[] imageBytes = productList.get(i).getImage().getBytes(1,
							(int) productList.get(i).getImage().length());
					ImageIcon imageIcon = new ImageIcon(imageBytes);
					Image image = imageIcon.getImage().getScaledInstance(400, 400, Image.SCALE_SMOOTH);
					ImageIcon imageIcon2 = new ImageIcon(image);
					if (imageIcon2 != null) {
						lblImage4.setIcon(iconSize(imageIcon2));
					}

					String result4 = duration(productList.get(i).getEndTime(), now);
					lblTime4.setText(result4);
					lblTime4.setPreferredSize(new Dimension(200, lblTime4.getPreferredSize().height));
					lblTime4.setHorizontalAlignment(SwingConstants.CENTER);

					if (durationTen(productList.get(i).getEndTime(), now)) {
						lblTime1.setForeground(Color.RED);
						lblTime1.setFont(lblTime1.getFont().deriveFont(15f));
					}

					btns[3].setVisible(true);
					list.add(new Auction(i, productList.get(i).getAuctionNo()));
					break;
				}
				case 4: {
					lblPrice5.setText(formatInt(productList.get(i).getProductPriceNow()));
					lblPrice5.setPreferredSize(new Dimension(200, lblPrice5.getPreferredSize().height));
					lblPrice5.setHorizontalAlignment(SwingConstants.CENTER);

					lblName5.setText(productList.get(i).getProductName());
					lblName5.setPreferredSize(new Dimension(200, lblName5.getPreferredSize().height));
					lblName5.setHorizontalAlignment(SwingConstants.CENTER);

					byte[] imageBytes = productList.get(i).getImage().getBytes(1,
							(int) productList.get(i).getImage().length());
					ImageIcon imageIcon = new ImageIcon(imageBytes);
					Image image = imageIcon.getImage().getScaledInstance(400, 400, Image.SCALE_SMOOTH);
					ImageIcon imageIcon2 = new ImageIcon(image);
					if (imageIcon2 != null) {
						lblImage5.setIcon(iconSize(imageIcon2));
					}

					String result5 = duration(productList.get(i).getEndTime(), now);
					lblTime5.setText(result5);
					lblTime5.setPreferredSize(new Dimension(200, lblTime5.getPreferredSize().height));
					lblTime5.setHorizontalAlignment(SwingConstants.CENTER);

					if (durationTen(productList.get(i).getEndTime(), now)) {
						lblTime1.setForeground(Color.RED);
						lblTime1.setFont(lblTime1.getFont().deriveFont(15f));
					}

					btns[4].setVisible(true);
					list.add(new Auction(i, productList.get(i).getAuctionNo()));
					break;
				}
				case 5: {
					lblPrice6.setText(formatInt(productList.get(i).getProductPriceNow()));
					lblPrice6.setPreferredSize(new Dimension(200, lblPrice6.getPreferredSize().height));
					lblPrice6.setHorizontalAlignment(SwingConstants.CENTER);

					lblName6.setText(productList.get(i).getProductName());
					lblName6.setPreferredSize(new Dimension(200, lblName6.getPreferredSize().height));
					lblName6.setHorizontalAlignment(SwingConstants.CENTER);

					byte[] imageBytes = productList.get(i).getImage().getBytes(1,
							(int) productList.get(i).getImage().length());
					ImageIcon imageIcon = new ImageIcon(imageBytes);
					Image image = imageIcon.getImage().getScaledInstance(400, 400, Image.SCALE_SMOOTH);
					ImageIcon imageIcon2 = new ImageIcon(image);
					if (imageIcon2 != null) {
						lblImage6.setIcon(iconSize(imageIcon2));
					}

					String result6 = duration(productList.get(i).getEndTime(), now);
					lblTime6.setText(result6);
					lblTime6.setPreferredSize(new Dimension(200, lblTime6.getPreferredSize().height));
					lblTime6.setHorizontalAlignment(SwingConstants.CENTER);

					if (durationTen(productList.get(i).getEndTime(), now)) {
						lblTime1.setForeground(Color.RED);
						lblTime1.setFont(lblTime1.getFont().deriveFont(15f));
					}

					btns[5].setVisible(true);
					list.add(new Auction(i, productList.get(i).getAuctionNo()));
					break;
				}
				case 6: {
					lblPrice7.setText(formatInt(productList.get(i).getProductPriceNow()));
					lblPrice7.setPreferredSize(new Dimension(200, lblPrice7.getPreferredSize().height));
					lblPrice7.setHorizontalAlignment(SwingConstants.CENTER);

					lblName7.setText(productList.get(i).getProductName());
					lblName7.setPreferredSize(new Dimension(200, lblName7.getPreferredSize().height));
					lblName7.setHorizontalAlignment(SwingConstants.CENTER);

					byte[] imageBytes = productList.get(i).getImage().getBytes(1,
							(int) productList.get(i).getImage().length());
					ImageIcon imageIcon = new ImageIcon(imageBytes);
					Image image = imageIcon.getImage().getScaledInstance(400, 400, Image.SCALE_SMOOTH);
					ImageIcon imageIcon2 = new ImageIcon(image);
					if (imageIcon2 != null) {
						lblImage7.setIcon(iconSize(imageIcon2));
					}
					String result7 = duration(productList.get(i).getEndTime(), now);
					lblTime7.setText(result7);
					lblTime7.setPreferredSize(new Dimension(200, lblTime7.getPreferredSize().height));
					lblTime7.setHorizontalAlignment(SwingConstants.CENTER);

					if (durationTen(productList.get(i).getEndTime(), now)) {
						lblTime1.setForeground(Color.RED);
						lblTime1.setFont(lblTime1.getFont().deriveFont(15f));
					}

					btns[6].setVisible(true);
					list.add(new Auction(i, productList.get(i).getAuctionNo()));
					break;
				}
				case 7: {
					lblPrice8.setText(formatInt(productList.get(i).getProductPriceNow()));
					lblPrice8.setPreferredSize(new Dimension(200, lblPrice8.getPreferredSize().height));
					lblPrice8.setHorizontalAlignment(SwingConstants.CENTER);

					lblName8.setText(productList.get(i).getProductName());
					lblName8.setPreferredSize(new Dimension(200, lblName8.getPreferredSize().height));
					lblName8.setHorizontalAlignment(SwingConstants.CENTER);

					byte[] imageBytes = productList.get(i).getImage().getBytes(1,
							(int) productList.get(i).getImage().length());
					ImageIcon imageIcon = new ImageIcon(imageBytes);
					Image image = imageIcon.getImage().getScaledInstance(400, 400, Image.SCALE_SMOOTH);
					ImageIcon imageIcon2 = new ImageIcon(image);
					if (imageIcon2 != null) {
						lblImage8.setIcon(iconSize(imageIcon2));
					}
					String result8 = duration(productList.get(i).getEndTime(), now);
					lblTime8.setText(result8);
					lblTime8.setPreferredSize(new Dimension(200, lblTime8.getPreferredSize().height));
					lblTime8.setHorizontalAlignment(SwingConstants.CENTER);

					if (durationTen(productList.get(i).getEndTime(), now)) {
						lblTime1.setForeground(Color.RED);
						lblTime1.setFont(lblTime1.getFont().deriveFont(15f));
					}

					btns[7].setVisible(true);
					list.add(new Auction(i, productList.get(i).getAuctionNo()));
					break;
				}
				case 8: {
					lblPrice9.setText(formatInt(productList.get(i).getProductPriceNow()));
					lblPrice9.setPreferredSize(new Dimension(200, lblPrice9.getPreferredSize().height));
					lblPrice9.setHorizontalAlignment(SwingConstants.CENTER);

					lblName9.setText(productList.get(i).getProductName());
					lblName9.setPreferredSize(new Dimension(200, lblName9.getPreferredSize().height));
					lblName9.setHorizontalAlignment(SwingConstants.CENTER);

					byte[] imageBytes = productList.get(i).getImage().getBytes(1,
							(int) productList.get(i).getImage().length());
					ImageIcon imageIcon = new ImageIcon(imageBytes);
					Image image = imageIcon.getImage().getScaledInstance(400, 400, Image.SCALE_SMOOTH);
					ImageIcon imageIcon2 = new ImageIcon(image);
					if (imageIcon2 != null) {
						lblImage9.setIcon(iconSize(imageIcon2));
					}
					String result9 = duration(productList.get(i).getEndTime(), now);
					lblTime9.setText(result9);
					lblTime9.setPreferredSize(new Dimension(200, lblTime9.getPreferredSize().height));
					lblTime9.setHorizontalAlignment(SwingConstants.CENTER);

					if (durationTen(productList.get(i).getEndTime(), now)) {
						lblTime1.setForeground(Color.RED);
						lblTime1.setFont(lblTime1.getFont().deriveFont(15f));
					}

					btns[8].setVisible(true);
					list.add(new Auction(i, productList.get(i).getAuctionNo()));
					break;
				}
				case 9: {
					lblPrice10.setText(formatInt(productList.get(i).getProductPriceNow()));
					lblPrice10.setPreferredSize(new Dimension(200, lblPrice10.getPreferredSize().height));
					lblPrice10.setHorizontalAlignment(SwingConstants.CENTER);

					lblName10.setText(productList.get(i).getProductName());
					lblName10.setPreferredSize(new Dimension(200, lblName10.getPreferredSize().height));
					lblName10.setHorizontalAlignment(SwingConstants.CENTER);

					byte[] imageBytes = productList.get(i).getImage().getBytes(1,
							(int) productList.get(i).getImage().length());
					ImageIcon imageIcon = new ImageIcon(imageBytes);
					Image image = imageIcon.getImage().getScaledInstance(400, 400, Image.SCALE_SMOOTH);
					ImageIcon imageIcon2 = new ImageIcon(image);
					if (imageIcon2 != null) {
						lblImage10.setIcon(iconSize(imageIcon2));
					}
					String result10 = duration(productList.get(i).getEndTime(), now);
					lblTime10.setText(result10);
					lblTime10.setPreferredSize(new Dimension(200, lblTime10.getPreferredSize().height));
					lblTime10.setHorizontalAlignment(SwingConstants.CENTER);

					if (durationTen(productList.get(i).getEndTime(), now)) {
						lblTime1.setForeground(Color.RED);
						lblTime1.setFont(lblTime1.getFont().deriveFont(15f));
					}

					btns[9].setVisible(true);
					list.add(new Auction(i, productList.get(i).getAuctionNo()));
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

					if (data.isCheckBtn()) {
						updateSearchLabel(now, data.getSearchText());
					} else {
						updatLabel(now);
					}
				}
			});
		}
	}
}