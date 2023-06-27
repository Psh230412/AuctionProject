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

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class SignupFrame extends JFrame {
	LoginSignupRepository repo;
	private boolean idCondition;
	private boolean passwordCondition;
	private JButton signupBtn;
	private JLabel idIdentifyLbl;
	private JLabel passwordIdentifyLbl;
	private JTextField idTF;
	private JPasswordField passwordPF;
	private JPasswordField passwordPF2;
	private JPanel signupPanel;
	
	public SignupFrame() {
		repo = new LoginSignupRepository();
		idCondition = false;
		passwordCondition = false;
		
		
		
		
		JFrame frame = new JFrame();
		frame.setSize(1200,800);
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
	        signupPanel = new JPanel(){

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);

			Toolkit toolkit = Toolkit.getDefaultToolkit();

			Image image = toolkit.getImage("img/joinPage.png");
			g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
		}
	};
	
	
	signupPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
	setContentPane(signupPanel);
	signupPanel.setLayout(null);
		
//		JLabel idLbl = new JLabel("아이디");
//		idLbl.setBounds(710, 500,  280, 35);
//		idLbl.setVisible(false);
//		JLabel passwordLbl = new JLabel("비밀번호");
//		passwordLbl.setBounds(710, 500,  280, 35);
//		passwordLbl.setVisible(false);
//		JLabel passwordLbl2 = new JLabel("비밀번호 확인");
//		passwordLbl2.setBounds(710, 500,  280, 35);
//		passwordLbl2.setVisible(false);
		idIdentifyLbl = new JLabel("아이디를 확인해주세요.");
		idIdentifyLbl.setBounds(650, 177,  280, 35);
		JLabel idpasswordConditionLbl = new JLabel("아이디와 비밀번호는 영문자 또는 숫자로 이루어져야 합니다.(15자 이내)");
		idpasswordConditionLbl.setBounds(630, 410,  450, 35);
		idpasswordConditionLbl.setFont(new Font("돋움", Font.PLAIN,12));
		idpasswordConditionLbl.setForeground(Color.GRAY);
		passwordIdentifyLbl = new JLabel("비밀번호를 확인해주세요.");
		passwordIdentifyLbl.setBounds(650, 386,  280, 35);
		initialLabel();

		idTF = new JTextField(10);
		idTF.setBounds(650, 147,  240, 35);
		passwordPF = new JPasswordField(10);
		passwordPF.setBounds(650, 247,  240, 35);
		passwordPF2 = new JPasswordField(10);
		passwordPF2.setBounds(650, 356,  240, 35);

		JButton idIdentifyBtn = new JButton();
		idIdentifyBtn.setBounds(900, 145, 150, 55);
		ImageIcon imgidconfirm = new ImageIcon("img/idconfirm_1.png");
		idIdentifyBtn.setContentAreaFilled(false); 
        idIdentifyBtn.setBorderPainted(false);
        idIdentifyBtn.setIcon(imgidconfirm);
    
        JButton passwordIdentifyBtn = new JButton();
       ImageIcon imgpass = new ImageIcon("img/pwconfirm_1.png");
		passwordIdentifyBtn.setBounds(900, 354, 150, 55);
		passwordIdentifyBtn.setIcon(imgpass);
		passwordIdentifyBtn.setVisible(true);
		passwordIdentifyBtn.setContentAreaFilled(false); 
		passwordIdentifyBtn.setBorderPainted(false);
		
	  
        idIdentifyBtn.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseExited(MouseEvent e) {
			ImageIcon imgidconfirm = new ImageIcon("img/idconfirm_1.png");
			idIdentifyBtn.setIcon(imgidconfirm);
			
		    }
		    
		    @Override
		    public void mouseEntered(MouseEvent e) {
			ImageIcon imgidconfirm = new ImageIcon("img/idconfirm.png");
			idIdentifyBtn.setIcon(imgidconfirm);
			
		    }
		});
	
		passwordIdentifyBtn.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseExited(MouseEvent e) {
			ImageIcon imgpass = new ImageIcon("img/pwconfirm_1.png");
			passwordIdentifyBtn.setIcon(imgpass);
			
		    }
		    
		    @Override
		    public void mouseEntered(MouseEvent e) {
			ImageIcon imgpass = new ImageIcon("img/pwconfirm.png");
			passwordIdentifyBtn.setIcon(imgpass);
			
		    }
		});
		idIdentifyBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String id = idTF.getText();
				idCheck(id);
			}
		});
		passwordIdentifyBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String password = new String(passwordPF.getPassword());
				String password2 = new String(passwordPF2.getPassword());
				passwordCheck(password, password2);
			}
		});

		  signupBtn = new JButton("회원가입");
		  signupBtn.setBounds(690, 470, 330, 82);
		  ImageIcon imgsignupBtn = new ImageIcon("img/join_1.png");
		  signupBtn.setContentAreaFilled(false); 
		  signupBtn.setBorderPainted(false);
		  signupBtn.setIcon(imgsignupBtn);
		  signupBtn.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseExited(MouseEvent e) {
			ImageIcon imgpass = new ImageIcon("img/join_1.png");
			signupBtn.setIcon(imgpass);
			
		    }
		    
		    @Override
		    public void mouseEntered(MouseEvent e) {
			ImageIcon imgpass = new ImageIcon("img/join.png");
			signupBtn.setIcon(imgpass);
			
		    }
		});
		    signupBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String id = idTF.getText();
				String password = new String(passwordPF.getPassword());
				String password2 = new String(passwordPF2.getPassword());
				idCheck(id);
				passwordCheck(password, password2);
				if (idCondition && passwordCondition) {
					int result = repo.insertIdPassword(id, password);
					if (result != 0) {
						JOptionPane.showMessageDialog(null, "회원가입되었습니다.");
						DataBase data = new DataBase();
						new LoginFrame(data);
						 frame.setVisible(false);
					}
				} else {
					JOptionPane.showMessageDialog(null, "아이디와 비밀번호를 확인해주세요.");
					initialLabel();
				}
			}
		});

		 JButton returnBtn = new JButton("뒤로가기");
		 returnBtn.setBounds(690, 580, 130, 50);
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
				new LoginFrame(data);
				 frame.setVisible(false);
			}
		});
		
		 JButton initTextBtn = new JButton("초기화");
		 initTextBtn.setBounds(880, 580, 130, 50);
		 ImageIcon imginitTextBtn = new ImageIcon("img/reset_1.png");
		 initTextBtn.setContentAreaFilled(false); 
		 initTextBtn.setBorderPainted(false);
		 initTextBtn.setIcon(imginitTextBtn);
		  
		 initTextBtn.addMouseListener(new MouseAdapter() {
			    @Override
			    public void mouseExited(MouseEvent e) {
				ImageIcon imginitTextBtn = new ImageIcon("img/reset_1.png");
				initTextBtn.setIcon(imginitTextBtn);
				
			    }
			    
			    @Override
			    public void mouseEntered(MouseEvent e) {
				ImageIcon imginitTextBtn = new ImageIcon("img/reset.png");
				initTextBtn.setIcon(imginitTextBtn);
				
			    }
			});
		
		initTextBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				initialTextField();
			}
		});
		
		//signupPanel.add(idLbl);
		signupPanel.add(idTF);
		signupPanel.add(idIdentifyLbl);
		//signupPanel.add(passwordLbl);
		signupPanel.add(passwordPF);
		//signupPanel.add(passwordLbl2);
		signupPanel.add(passwordPF2);
		signupPanel.add(passwordIdentifyLbl);
		signupPanel.add(signupBtn);
		signupPanel.add(returnBtn);
		signupPanel.add(initTextBtn);
		signupPanel.add(idIdentifyBtn);
		signupPanel.add(passwordIdentifyBtn);
		signupPanel.add(idpasswordConditionLbl);

		frame.getContentPane().add(signupPanel);
	    frame.setVisible(true);
	}

	private void idCheck(String id) {
		if ((id.length() != 0) && repo.isMatchesString(id) && repo.searchId(id) == null) {
			idIdentifyLbl.setText("사용 가능한 아이디입니다.");
			idIdentifyLbl.setForeground(Color.GREEN);
			idCondition = true;
		} else {
			idIdentifyLbl.setText("사용할 수 없는 아이디입니다. 다른 아이디를 입력하세요.");
			idIdentifyLbl.setForeground(Color.RED);
			idCondition = false;
		}
	}

	
	private void passwordCheck(String password, String password2) {
		if ((password.length() != 0) && (password2.length() != 0) && repo.isMatchesString(password)
				&& repo.isMatchesString(password2) && password.equals(password2)) {
			passwordIdentifyLbl.setText("사용 가능한 비밀번호입니다.");
			passwordIdentifyLbl.setForeground(Color.GREEN);
			passwordCondition = true;
		} else {
			passwordIdentifyLbl.setText("올바른 비밀번호를 입력하세요.");
			passwordIdentifyLbl.setForeground(Color.RED);
			passwordCondition = false;
		}
	}

	private void initialLabel() {
		idIdentifyLbl.setText("아이디를 확인해주세요.");
		idIdentifyLbl.setFont(new Font("돋움", Font.PLAIN,12));
		idIdentifyLbl.setForeground(Color.RED);
		passwordIdentifyLbl.setText("비밀번호를 확인해주세요.");
		passwordIdentifyLbl.setFont(new Font("돋움", Font.PLAIN,12));
		passwordIdentifyLbl.setForeground(Color.RED);
	}
	
	private void initialTextField() {
		idTF.setText("");
		passwordPF.setText("");
		passwordPF2.setText("");
		initialLabel();
	}

	public static void main(String[] args) {
	    new SignupFrame();
	}
}

