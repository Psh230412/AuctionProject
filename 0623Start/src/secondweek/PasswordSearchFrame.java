package secondweek;

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
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.text.PlainDocument;

public class PasswordSearchFrame extends JFrame {
	private LoginSignupRepository repo;
	private JPanel contentPane;
	private JFrame frame;

	public PasswordSearchFrame(DataBase data) {
		repo = new LoginSignupRepository();
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

				Image image = toolkit.getImage("img/findinfoPage.png");
				g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
			}
		};

		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);


		JLabel idInfoLbl = new JLabel("아이디 찾기는 관리자에게 문의해 주시기 바랍니다.");
		idInfoLbl.setBounds(200, 106, 367, 15);
		JLabel managerInfoLbl = new JLabel("관리자 Tel : 02-000-0000");
		managerInfoLbl.setBounds(200, 143, 144, 15);

//		JLabel passwordInfoLbl = new JLabel("비밀번호 찾기");
//		passwordInfoLbl.setBounds(650, 284, 85, 15);
//		JLabel idLbl = new JLabel("아이디");
//		idLbl.setBounds(650, 341, 48, 15);
//		JLabel nameLbl = new JLabel("이름");
//		nameLbl.setBounds(650, 416, 48, 15);
//		JLabel telLbl = new JLabel("전화번호");
//		telLbl.setBounds(650, 497, 60, 15);

		JTextField idTF = new JTextField(10);
		idTF.setBounds(650, 342, 200, 30);
		JTextField nameTF = new JTextField(10);
		nameTF.setBounds(650, 230, 200, 30);

		JTextField telFrontTF = new JTextField(5);
		telFrontTF.setBounds(650, 465, 100, 30);
		JTextField telCenterTF = new JTextField(5);
		telCenterTF.setBounds(780, 465, 100, 30);
		JTextField telBackTF = new JTextField(5);
		telBackTF.setBounds(915, 465, 100, 30);

		PlainDocument docCenter1 = (PlainDocument) telFrontTF.getDocument();
		docCenter1.setDocumentFilter(new NumberOnlyFilter(3));

		PlainDocument docCenter2 = (PlainDocument) telCenterTF.getDocument();
		docCenter2.setDocumentFilter(new NumberOnlyFilter(4));

		PlainDocument docCenter3 = (PlainDocument) telBackTF.getDocument();
		docCenter3.setDocumentFilter(new NumberOnlyFilter(4));

		JButton identifyBtn = new JButton("확인");
		identifyBtn.setBounds(750, 572, 130, 63);
		ImageIcon imgidentifyBtn = new ImageIcon("img/findconfirm_1.png");
		identifyBtn.setContentAreaFilled(false);
		identifyBtn.setBorderPainted(false);
		identifyBtn.setIcon(imgidentifyBtn);

		identifyBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseExited(MouseEvent e) {
				ImageIcon imgidentifyBtn = new ImageIcon("img/findconfirm_1.png");
				identifyBtn.setIcon(imgidentifyBtn);

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				ImageIcon imgidentifyBtn = new ImageIcon("img/findconfirm.png");
				identifyBtn.setIcon(imgidentifyBtn);

			}
		});
		
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

		JButton returnBtn = new JButton();
		returnBtn.setBounds(50, 25, 130, 50);
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
				frame.dispose();
			}
		});
		contentPane.setLayout(null);

		contentPane.add(idInfoLbl);
		contentPane.add(managerInfoLbl);
//		contentPane.add(passwordInfoLbl);
//		contentPane.add(idLbl);
		contentPane.add(idTF);
//		contentPane.add(nameLbl);
		contentPane.add(nameTF);
//		contentPane.add(telLbl);
		contentPane.add(telFrontTF);
		contentPane.add(telCenterTF);
		contentPane.add(telBackTF);
		contentPane.add(identifyBtn);
		contentPane.add(returnBtn);


		frame.getContentPane().add(contentPane);

		frame.setVisible(true);
	}
}
