
package secondweek;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import dbutil.DBUtil;

public class LoginFrame extends JFrame {
	LoginSignupRepository repo;
	User loginUser;
	private JPanel contentPane;
	private JFrame frame;

	public LoginFrame(DataBase data) {
		Connection conn = null;

		try {
			conn = DBUtil.getConnection();
			Cache.addAllIntoProductCacheMap(conn);
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		} finally {
			DBUtil.close(conn);
		}

		repo = new LoginSignupRepository();
		loginUser = null;

		frame = new JFrame();
		frame.setSize(1200, 800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		contentPane = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);

				Toolkit toolkit = Toolkit.getDefaultToolkit();

				Image image = toolkit.getImage("img/login.jpg");
				g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
			}
		};

		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel idLbl = new JLabel("아이디");
		idLbl.setBounds(720, 100, 180, 30);
		idLbl.setVisible(false);
		JLabel passwordLbl = new JLabel("비밀번호");
		passwordLbl.setBounds(720, 190, 180, 30);
		passwordLbl.setVisible(false);

		JTextField idTF = new JTextField(10);
		idTF.setBounds(760, 147, 280, 35);
		JPasswordField passwordPF = new JPasswordField(10);
		passwordPF.setBounds(760, 231, 280, 35);
		passwordPF.setOpaque(false);

		JButton loginBtn = new JButton("로그인");
		loginBtn.setBounds(760, 310, 300, 85);
		ImageIcon imglogin = new ImageIcon("img/login_1.png");
		loginBtn.setContentAreaFilled(false);
		loginBtn.setBorderPainted(false);
		loginBtn.setIcon(imglogin);
		loginBtn.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseExited(MouseEvent e) {
				ImageIcon imglogin = new ImageIcon("img/login_1.png");
				loginBtn.setIcon(imglogin);

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				ImageIcon imglogin = new ImageIcon("img/login.png");
				loginBtn.setIcon(imglogin);

			}
		});

		JButton signupBtn = new JButton("회원가입");
		signupBtn.setBounds(760, 390, 300, 85);
		ImageIcon imgsign = new ImageIcon("img/signup_1.png");
		signupBtn.setContentAreaFilled(false);
		signupBtn.setBorderPainted(false);
		signupBtn.setIcon(imgsign);
		signupBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseExited(MouseEvent e) {
				ImageIcon imgsign = new ImageIcon("img/signup_1.png");
				signupBtn.setIcon(imgsign);

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				ImageIcon imgsign = new ImageIcon("img/signup.png");
				signupBtn.setIcon(imgsign);

			}

		});
		JButton exitBtn = new JButton("종료");
		exitBtn.setBounds(760, 550, 300, 85);
		ImageIcon imgexit = new ImageIcon("img/exit_1.png");
		exitBtn.setContentAreaFilled(false);
		exitBtn.setBorderPainted(false);
		exitBtn.setIcon(imgexit);
		exitBtn.setVisible(true);
		exitBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseExited(MouseEvent e) {
				ImageIcon imgexit = new ImageIcon("img/exit_1.png");
				exitBtn.setIcon(imgexit);

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				ImageIcon imgexit = new ImageIcon("img/exit.png");
				exitBtn.setIcon(imgexit);

			}
		});

		loginBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String id = idTF.getText();
				String password = new String(passwordPF.getPassword());
				if (loginCondition(id, password)) {
					data.setCurrentUser(loginUser);
					Connection conn = null;
					try {
						conn = DBUtil.getConnection();
						Cache.putAllenrollCacheMap(data.getCurrentUser().getNo(), conn);
						Cache.putAllParticipateMap(data.getCurrentUser().getNo(), conn);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					new AuctionFrame(data);
					frame.dispose();
				}
			}
		});

		signupBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new SignupFrame();
				frame.dispose();
			}
		});

		exitBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int choice = JOptionPane.showConfirmDialog(null, "종료하시겠습니까?", "종료", JOptionPane.YES_NO_OPTION);
				if (choice == JOptionPane.YES_OPTION) {
					frame.dispose();
					System.exit(0);
				}
			}
		});

		JButton passwordSearchBtn = new JButton("아이디/비밀번호 찾기");
		passwordSearchBtn.setBounds(760, 470, 300, 85);
		ImageIcon imgfind = new ImageIcon("img/findinfo_1.png");
		passwordSearchBtn.setContentAreaFilled(false);
		passwordSearchBtn.setBorderPainted(false);
		passwordSearchBtn.setIcon(imgfind);
		passwordSearchBtn.setVisible(true);
		passwordSearchBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseExited(MouseEvent e) {
				ImageIcon imgfind = new ImageIcon("img/findinfo_1.png");
				passwordSearchBtn.setIcon(imgfind);

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				ImageIcon imgfind = new ImageIcon("img/findinfo.png");
				passwordSearchBtn.setIcon(imgfind);

			}
		});
		passwordSearchBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new PasswordSearchFrame(data);
				frame.dispose();
			}
		});
		JButton manualBtn = new JButton();
		manualBtn.setBounds(160, 200, 300, 85);
		ImageIcon imgmanual = new ImageIcon("img/manualBtn_1.png");
		manualBtn.setContentAreaFilled(false);
		manualBtn.setBorderPainted(false);
		manualBtn.setIcon(imgmanual);
		manualBtn.setVisible(true);
		manualBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseExited(MouseEvent e) {
				ImageIcon imgmanual = new ImageIcon("img/manualBtn_1.png");
				manualBtn.setIcon(imgmanual);

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				ImageIcon imgmanual = new ImageIcon("img/manualBtn.png");
				manualBtn.setIcon(imgmanual);

			}
		});
		manualBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new ManualFrame();
				
			}
		});
		contentPane.add(manualBtn);

		contentPane.add(idLbl);
		contentPane.add(idTF);
		contentPane.add(passwordLbl);
		contentPane.add(passwordPF);
		contentPane.add(loginBtn);
		contentPane.add(signupBtn);
		contentPane.add(exitBtn);
		contentPane.add(passwordSearchBtn);

		frame.getContentPane().add(contentPane);

		frame.setVisible(true);

	}

	public boolean loginCondition(String id, String password) {
		// 1. 아이디와 비밀번호가 비어있으면 "아이디와 비밀번호를 입력하세요"
		if (id.length() == 0 || password.length() == 0) {
			JOptionPane.showMessageDialog(null, "아이디와 비밀번호를 입력하세요.");
			return false;
		}

//		// 2. 아이디와 비밀번호는 영문자 또는 숫자만으로 15자 이내로 이루어져야 함
//		if (!(repo.isMatchesString(id) && repo.isMatchesString(password))) {
//			JOptionPane.showMessageDialog(null, "아이디 혹은 비밀번호가 틀렸습니다.");
//			return false;
//		}

		// 3. DB 검색 결과 해당하는 계정이 있어야함
		User user = repo.searchIdPassword(id, password);
		if (user == null) {
			JOptionPane.showMessageDialog(null, "아이디 혹은 비밀번호가 틀렸습니다.");
			return false;
		} else {
			JOptionPane.showMessageDialog(null, "로그인되었습니다.");
			loginUser = user;
			
			frame.setVisible(false);
			return true;
		}
	}

	public static void main(String[] args) {
		DataBase data = new DataBase();
		new LoginFrame(data);
	}
}
