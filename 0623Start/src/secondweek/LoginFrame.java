package secondweek;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginFrame extends JFrame {
	LoginSignupRepository repo;
	User loginUser;

	public LoginFrame(DataBase data) {
		repo = new LoginSignupRepository();
		loginUser = null;

		JPanel pnl = new JPanel();
		pnl.setLayout(null);
		
		JLabel idLbl = new JLabel("아이디");
		idLbl.setBounds(156, 122, 102, 15);
		JLabel passwordLbl = new JLabel("비밀번호");
		passwordLbl.setBounds(156, 181, 102, 15);

		JTextField idTF = new JTextField(10);
		idTF.setBounds(310, 119, 116, 21);
		JPasswordField passwordPF = new JPasswordField(10);
		passwordPF.setBounds(310, 178, 116, 21);

		JButton loginBtn = new JButton("로그인");
		loginBtn.setBounds(310, 286, 102, 23);
		loginBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String id = idTF.getText();
				String password = new String(passwordPF.getPassword());
				if (loginCondition(id, password)) {
					data.setCurrentUser(loginUser);
					new AuctionFrame(data);
					setVisible(false);
				}
			}
		});

		JButton signupBtn = new JButton("회원가입");
		signupBtn.setBounds(156, 286, 102, 23);
		signupBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new SignupFrame();
				setVisible(false);
			}
		});
		
		JButton exitBtn = new JButton("종료");
		exitBtn.setBounds(478, 286, 102, 23);
		exitBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int choice = JOptionPane.showConfirmDialog(null, "종료하시겠습니까?", "종료", JOptionPane.YES_NO_OPTION);
				if (choice == JOptionPane.YES_OPTION) {
		            setVisible(false);
		            System.exit(0);
		        } 
			}
		});
		
		
		pnl.add(idLbl);
		pnl.add(idTF);
		pnl.add(passwordLbl);
		pnl.add(passwordPF);
		pnl.add(loginBtn);
		pnl.add(signupBtn);
		pnl.add(exitBtn);

		getContentPane().add(pnl);
		setSize(800, 700);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		setLocationRelativeTo(null);
		setResizable(false);
	}
	

	public boolean loginCondition(String id, String password) {
		// 1. 아이디와 비밀번호가 비어있으면 "아이디와 비밀번호를 입력하세요"
		if (id.length() == 0 || password.length() == 0) {
			JOptionPane.showMessageDialog(null, "아이디와 비밀번호를 입력하세요.");
			return false;
		}

		// 2. 아이디와 비밀번호는 영문자 또는 숫자만으로 15자 이내로 이루어져야 함
		if (!(repo.isMatchesString(id) && repo.isMatchesString(password))) {
			JOptionPane.showMessageDialog(null, "아이디 혹은 비밀번호가 틀렸습니다.");
			return false;
		}
		
		// 3. DB 검색 결과 해당하는 계정이 있어야함
		User user = repo.searchIdPassword(id, password);
		if (user == null) {
			JOptionPane.showMessageDialog(null, "아이디 혹은 비밀번호가 틀렸습니다.");
			return false;
		} else {
			JOptionPane.showMessageDialog(null, "로그인되었습니다.");
			loginUser = user;
			return true;
		}
	}

	public static void main(String[] args) {
		DataBase data = new DataBase();
		new LoginFrame(data);
	}
}
