package secondweek;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.PlainDocument;

public class PasswordSearchFrame extends JFrame {
	private LoginSignupRepository repo;

	public PasswordSearchFrame(DataBase data) {
		repo = new LoginSignupRepository();

		JPanel pnl = new JPanel();

		JLabel idInfoLbl = new JLabel("아이디 찾기는 관리자에게 문의해 주시기 바랍니다.");
		idInfoLbl.setBounds(164, 106, 367, 15);
		JLabel managerInfoLbl = new JLabel("관리자 Tel : 02-000-0000");
		managerInfoLbl.setBounds(164, 143, 144, 15);

		JLabel passwordInfoLbl = new JLabel("비밀번호 찾기");
		passwordInfoLbl.setBounds(164, 274, 85, 15);
		JLabel idLbl = new JLabel("아이디");
		idLbl.setBounds(201, 341, 48, 15);
		JLabel nameLbl = new JLabel("이름");
		nameLbl.setBounds(201, 416, 48, 15);
		JLabel telLbl = new JLabel("전화번호");
		telLbl.setBounds(201, 497, 60, 15);

		JTextField idTF = new JTextField(10);
		idTF.setBounds(273, 338, 116, 21);
		JTextField nameTF = new JTextField(10);
		nameTF.setBounds(273, 413, 116, 21);

		JTextField telFrontTF = new JTextField(5);
		telFrontTF.setBounds(273, 494, 60, 21);
		JTextField telCenterTF = new JTextField(5);
		telCenterTF.setBounds(358, 494, 61, 21);
		JTextField telBackTF = new JTextField(5);
		telBackTF.setBounds(443, 494, 60, 21);

		PlainDocument docCenter1 = (PlainDocument) telFrontTF.getDocument();
		docCenter1.setDocumentFilter(new NumberOnlyFilter(3));

		PlainDocument docCenter2 = (PlainDocument) telCenterTF.getDocument();
		docCenter2.setDocumentFilter(new NumberOnlyFilter(4));

		PlainDocument docCenter3 = (PlainDocument) telBackTF.getDocument();
		docCenter3.setDocumentFilter(new NumberOnlyFilter(4));

		JButton identifyBtn = new JButton("확인");
		identifyBtn.setBounds(436, 572, 95, 23);
		identifyBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if ((telFrontTF.getText().length() == 3) && (telCenterTF.getText().length() == 4) && (telBackTF.getText().length() == 4)) {
					String idStr = idTF.getText();
					String nameStr = nameTF.getText();
					String numStr = telFrontTF.getText();
					numStr = numStr.concat(telCenterTF.getText());
					numStr = numStr.concat(telBackTF.getText());
					String password = repo.passwordSearch(idStr, nameStr, numStr);
					
					if (password == null) {
						JOptionPane.showMessageDialog(null, "등록된 유저 정보가 없습니다.");
					} else {
						JOptionPane.showMessageDialog(null, "귀하의 비밀번호는 " + password + " 입니다.");
					}
				} else {
					JOptionPane.showMessageDialog(null, "등록된 유저 정보가 없습니다.");
				}
			}
		});

		JButton returnLoginBtn = new JButton("뒤로가기");
		returnLoginBtn.setBounds(84, 572, 95, 23);
		returnLoginBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				DataBase data = new DataBase();
				new LoginFrame(data);
				setVisible(false);
			}
		});
		pnl.setLayout(null);

		pnl.add(idInfoLbl);
		pnl.add(managerInfoLbl);
		pnl.add(passwordInfoLbl);
		pnl.add(idLbl);
		pnl.add(idTF);
		pnl.add(nameLbl);
		pnl.add(nameTF);
		pnl.add(telLbl);
		pnl.add(telFrontTF);
		pnl.add(telCenterTF);
		pnl.add(telBackTF);
		pnl.add(identifyBtn);
		pnl.add(returnLoginBtn);

		getContentPane().add(pnl);

		setSize(800, 700);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		setLocationRelativeTo(null);
		setResizable(false);
	}
}
