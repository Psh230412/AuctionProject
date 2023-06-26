package secondweek;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

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

	public SignupFrame() {
		repo = new LoginSignupRepository();
		idCondition = false;
		passwordCondition = false;
		
		JPanel pnl = new JPanel();
		pnl.setLayout(null);
		
		JLabel idLbl = new JLabel("아이디");
		idLbl.setBounds(145, 194, 114, 15);
		JLabel passwordLbl = new JLabel("비밀번호");
		passwordLbl.setBounds(145, 314, 114, 15);
		JLabel passwordLbl2 = new JLabel("비밀번호 확인");
		passwordLbl2.setBounds(145, 374, 114, 15);
		idIdentifyLbl = new JLabel("아이디를 확인해주세요.");
		idIdentifyLbl.setBounds(271, 243, 400, 15);
		JLabel idpasswordConditionLbl = new JLabel("아이디와 비밀번호는 영문자 또는 숫자로 이루어져야 합니다.(15자 이내)");
		idpasswordConditionLbl.setBounds(145, 116, 476, 15);
		passwordIdentifyLbl = new JLabel("비밀번호를 확인해주세요.");
		passwordIdentifyLbl.setBounds(271, 416, 400, 15);
		initialLabel();

		idTF = new JTextField(10);
		idTF.setBounds(271, 191, 116, 21);
		passwordPF = new JPasswordField(10);
		passwordPF.setBounds(271, 311, 116, 21);
		passwordPF2 = new JPasswordField(10);
		passwordPF2.setBounds(271, 371, 116, 21);

		JButton idIdentifyBtn = new JButton("아이디 확인");
		idIdentifyBtn.setBounds(420, 190, 130, 23);
		idIdentifyBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String id = idTF.getText();
				idCheck(id);
			}
		});

		JButton passwordIdentifyBtn = new JButton("비밀번호 확인");
		passwordIdentifyBtn.setBounds(420, 370, 130, 23);
		passwordIdentifyBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String password = new String(passwordPF.getPassword());
				String password2 = new String(passwordPF2.getPassword());
				passwordCheck(password, password2);
			}
		});

		signupBtn = new JButton("회원가입");
		signupBtn.setBounds(434, 504, 116, 23);
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
						setVisible(false);
					}
				} else {
					JOptionPane.showMessageDialog(null, "아이디와 비밀번호를 확인해주세요.");
					initialLabel();
				}
			}
		});

		JButton returnBtn = new JButton("뒤로가기");
		returnBtn.setBounds(46, 612, 116, 23);
		returnBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				DataBase data = new DataBase();
				new LoginFrame(data);
				setVisible(false);
			}
		});
		
		JButton initTextBtn = new JButton("초기화");
		initTextBtn.setBounds(271, 504, 116, 23);
		initTextBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				initialTextField();
			}
		});
		

		pnl.add(idpasswordConditionLbl);
		pnl.add(idLbl);
		pnl.add(idTF);
		pnl.add(idIdentifyBtn);
		pnl.add(idIdentifyLbl);
		pnl.add(passwordLbl);
		pnl.add(passwordPF);
		pnl.add(passwordLbl2);
		pnl.add(passwordPF2);
		pnl.add(passwordIdentifyLbl);
		pnl.add(passwordIdentifyBtn);
		pnl.add(signupBtn);
		pnl.add(returnBtn);
		pnl.add(initTextBtn);

		getContentPane().add(pnl);
		setSize(800, 700);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		setLocationRelativeTo(null);
		setResizable(false);
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
		idIdentifyLbl.setForeground(Color.RED);
		passwordIdentifyLbl.setText("비밀번호를 확인해주세요.");
		passwordIdentifyLbl.setForeground(Color.RED);
	}
	
	private void initialTextField() {
		idTF.setText("");
		passwordPF.setText("");
		passwordPF2.setText("");
		initialLabel();
	}

}
