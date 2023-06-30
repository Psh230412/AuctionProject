package secondweek;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.text.DocumentFilter.FilterBypass;
import javax.swing.text.PlainDocument;

public class SignupFrame extends JFrame {
	LoginSignupRepository repo;
	private UserInfoRepository repo2;
	private boolean idCondition;
	private boolean passwordCondition;
	private boolean nicknameCondition;
	private JButton signupBtn;
	private JLabel idIdentifyLbl;
	private JLabel passwordIdentifyLbl;
	private JLabel nicknameInfoLbl;

	private JTextField idTF;
	private JPasswordField passwordPF;
	private JPasswordField passwordPF2;
	private JTextField nicknameTF;
	private JTextField nameTF;
	private JTextField telFrontTF;
	private JTextField telCenterTF;
	private JTextField telBackTF;

	private JPanel signupPanel;

	private String selectedYear;
	private String selectedMonth;
	private String selectedDay;

	private String bigAreaComboStr;
	private String mediumAreaComboStr;
	private String smallAreaComboStr;

	private String genderStr;
	private JComboBox<String> bigAreaCombo;
	private JComboBox<String> mediumAreaCombo;
	private JComboBox<String> smallAreaCombo;
	private JComboBox<String> yearCombo;
	private JComboBox<String> monthCombo;
	private JComboBox<String> dayCombo;

	public SignupFrame() {
		repo = new LoginSignupRepository();
		repo2 = new UserInfoRepository();
		idCondition = false;
		passwordCondition = false;
		nicknameCondition = false;

		yearCombo = new JComboBox<>();
		monthCombo = new JComboBox<>();
		dayCombo = new JComboBox<>();

		JFrame frame = new JFrame();
		frame.setSize(1200, 800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		signupPanel = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);

				Toolkit toolkit = Toolkit.getDefaultToolkit();

				Image image = toolkit.getImage("img/joinPage.png");
				g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
			}
		};
		frame.setResizable(false);
		signupPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(signupPanel);
		signupPanel.setLayout(null);

		// -----------------------------------------------------------------------------
		// JLabel nameLbl = new JLabel("이름");
				// nameLbl.setBounds(100, 0, 60, 21);
				nameTF = new JTextField(10);
				nameTF.setBounds(650, 70, 240, 25);

				// JLabel nicknameLbl = new JLabel("닉네임");
				// nicknameLbl.setBounds(100, 50, 60, 21);
				nicknameTF = new JTextField(10);
				nicknameTF.setBounds(650, 168, 240, 25);

				JButton nicknameIdentifyBtn = new JButton("닉네임 확인");
				nicknameIdentifyBtn.setBounds(960, 163, 150, 55);
				ImageIcon imgnicknameIdenBtn = new ImageIcon("img/nickconfirm_1.png");
				nicknameIdentifyBtn.setContentAreaFilled(false);
				nicknameIdentifyBtn.setBorderPainted(false);
				nicknameIdentifyBtn.setIcon(imgnicknameIdenBtn);
				nicknameIdentifyBtn.setVisible(true);
				nicknameIdentifyBtn.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseExited(MouseEvent e) {
						ImageIcon imgnicknameIdenBtn = new ImageIcon(
								"img/nickconfirm_1.png");
						nicknameIdentifyBtn.setIcon(imgnicknameIdenBtn);

					}

					@Override
					public void mouseEntered(MouseEvent e) {
						ImageIcon imgnicknameIdenBtn = new ImageIcon(
								"img/nickconfirm.png");
						nicknameIdentifyBtn.setIcon(imgnicknameIdenBtn);

					}
				});
				nicknameInfoLbl = new JLabel("닉네임을 확인해주세요.");
				nicknameInfoLbl.setBounds(650, 194, 200, 21);

				nicknameIdentifyBtn.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						String nickname = nicknameTF.getText();
						nicknameCheck(nickname);
					}
				});

				// JLabel phoneLbl = new JLabel("전화번호");
				// phoneLbl.setBounds(600, 150, 60, 21);
				telFrontTF = new JTextField(5);
				telFrontTF.setBounds(650, 225, 100, 25);
				telCenterTF = new JTextField(5);
				telCenterTF.setBounds(795, 225, 120, 25);
				telBackTF = new JTextField(5);
				telBackTF.setBounds(970, 225, 100, 25);

				PlainDocument docCenter1 = (PlainDocument) telFrontTF.getDocument();
				docCenter1.setDocumentFilter(new NumberOnlyFilter(3));
				PlainDocument docCenter2 = (PlainDocument) telCenterTF.getDocument();
				docCenter2.setDocumentFilter(new NumberOnlyFilter(4));
				PlainDocument docCenter3 = (PlainDocument) telBackTF.getDocument();
				docCenter3.setDocumentFilter(new NumberOnlyFilter(4));

				// JLabel birthLbl = new JLabel("생년월일");
				// birthLbl.setBounds(100, 200, 80, 21);
				// signupPanel.add(birthLbl);
				yearCombo.setBounds(650, 370, 100, 25);
				signupPanel.add(yearCombo);
				monthCombo.setBounds(780, 370, 100, 25);
				signupPanel.add(monthCombo);
				dayCombo.setBounds(910, 370, 100, 25);
				signupPanel.add(dayCombo);

				// JLabel genderLbl = new JLabel("성별");
				// genderLbl.setBounds(100, 300, 80, 21);
				ImageIcon imgwomanlbl1 = new ImageIcon("img/girl.png");
				ImageIcon imgmanlbl1 = new ImageIcon("img/man.png");
				ImageIcon imgmanlbl = new ImageIcon("img/man_1.png");
				ImageIcon imgwomanlbl = new ImageIcon("img/girl_1.png");
				JRadioButton manBtn = new JRadioButton("남성");
				manBtn.setContentAreaFilled(false);
				manBtn.setBorderPainted(false);
				manBtn.setBounds(600, 261, 50, 50);
				manBtn.setIcon(imgmanlbl);
				manBtn.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseExited(MouseEvent e) {

						manBtn.setIcon(imgmanlbl);

					}

					@Override
					public void mouseEntered(MouseEvent e) {

						manBtn.setIcon(imgmanlbl1);

					}
				});

				JRadioButton womanBtn = new JRadioButton("여성");
				womanBtn.setContentAreaFilled(false);
				womanBtn.setBorderPainted(false);

				womanBtn.setIcon(imgwomanlbl);
				womanBtn.setBounds(650, 261, 50, 50);
				womanBtn.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseExited(MouseEvent e) {

						womanBtn.setIcon(imgwomanlbl);

					}

					@Override
					public void mouseEntered(MouseEvent e) {

						womanBtn.setIcon(imgwomanlbl1);

					}
				});

				ButtonGroup radioGroup = new ButtonGroup();
				radioGroup.add(manBtn);
				radioGroup.add(womanBtn);

				// JLabel addressLbl = new JLabel("주소");
				// addressLbl.setBounds(100, 350, 100, 25);
				bigAreaCombo = new JComboBox<>();
				bigAreaCombo.setBounds(650, 325, 100, 25);
				mediumAreaCombo = new JComboBox<>();
				mediumAreaCombo.setBounds(780, 325, 100, 25);
				smallAreaCombo = new JComboBox<>();
				smallAreaCombo.setBounds(910, 325, 100, 25);

		// 지역 대 분류
		List<String> bigAreas = new ArrayList<>();
		bigAreas.add("수도권");
		bigAreas.add("강원권");
		bigAreas.add("충청권");
		bigAreas.add("전라권");
		bigAreas.add("경상권");
		bigAreas.add("제주권");

		for (String bigArea : bigAreas) {
			bigAreaCombo.addItem(bigArea);
		}

		// 대분류 -> 중분류 연결
		bigAreaCombo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String selectedBigArea = (String) bigAreaCombo.getSelectedItem();
				bigAreaComboStr = selectedBigArea;

				mediumAreaCombo.removeAllItems();
				smallAreaCombo.removeAllItems();

				List<String> mediumAreas = repo2.getMediumAreas(selectedBigArea);
				for (String mediumArea : mediumAreas) {
					mediumAreaCombo.addItem(mediumArea);
				}

			}
		});

		// 중분류 -> 소분류 연결
		mediumAreaCombo.addActionListener(e -> {
			String selectedMediumArea = (String) mediumAreaCombo.getSelectedItem();
			mediumAreaComboStr = selectedMediumArea;
			smallAreaCombo.removeAllItems();

			if (selectedMediumArea != null) {
				List<String> smallAreas = repo2.getSmallAreas(selectedMediumArea);
				for (String smallArea : smallAreas) {
					smallAreaCombo.addItem(smallArea);
				}
			}
		});

		// signupPanel.add(nameLbl);
				signupPanel.add(nameTF);

				signupPanel.add(nicknameTF);
				// signupPanel.add(nicknameLbl);
				signupPanel.add(nicknameInfoLbl);
				signupPanel.add(nicknameIdentifyBtn);

				// signupPanel.add(phoneLbl);
				signupPanel.add(telFrontTF);
				signupPanel.add(telCenterTF);
				signupPanel.add(telBackTF);
				// signupPanel.add(genderLbl);
				signupPanel.add(manBtn);
				signupPanel.add(womanBtn);

				// signupPanel.add(addressLbl);
				signupPanel.add(bigAreaCombo);
				signupPanel.add(mediumAreaCombo);
				signupPanel.add(smallAreaCombo);

		// --------------------------------------------------------------------------------

		// DocumentFilter를 생성하여 영문자와 한글만 허용하도록 설정
		((AbstractDocument) nameTF.getDocument()).setDocumentFilter(new DocumentFilter() {
			@Override
			public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
					throws BadLocationException {
				String filteredText = filterInput(text);
				super.replace(fb, offset, length, filteredText, attrs);
			}

			@Override
			public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
					throws BadLocationException {
				String filteredText = filterInput(string);
				super.insertString(fb, offset, filteredText, attr);
			}

			private String filterInput(String input) {
				StringBuilder filtered = new StringBuilder();
				for (int i = 0; i < input.length(); i++) {
					char ch = input.charAt(i);
					if (Character.isLetter(ch) || isHangul(ch)) {
						filtered.append(ch);
					}
				}
				return filtered.toString();
			}

			private boolean isHangul(char ch) {
				return Character.UnicodeBlock.of(ch) == Character.UnicodeBlock.HANGUL_SYLLABLES
						|| Character.UnicodeBlock.of(ch) == Character.UnicodeBlock.HANGUL_COMPATIBILITY_JAMO
						|| Character.UnicodeBlock.of(ch) == Character.UnicodeBlock.HANGUL_JAMO;
			}
		});

		// 생년월일 콤보박스 리스너
		yearCombo.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					selectedYear = (String) yearCombo.getSelectedItem();
				}
			}
		});

		monthCombo.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					selectedMonth = (String) monthCombo.getSelectedItem();
				}
			}
		});

		dayCombo.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					selectedDay = (String) dayCombo.getSelectedItem();
				}
			}
		});

		// 성별 라디오버튼 리스너
		ActionListener radioListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JRadioButton selectedRadioButton = (JRadioButton) e.getSource();
				String selectedText = selectedRadioButton.getText();
				genderStr = selectedText;
			}
		};
		manBtn.addActionListener(radioListener);
		womanBtn.addActionListener(radioListener);

		smallAreaCombo.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					smallAreaComboStr = (String) smallAreaCombo.getSelectedItem();
				}
			}
		});

		// 연도 리스트
		List<String> yearStringList = new ArrayList<>();
		yearStringList.addAll(repo2.numberStringListReverse("연도", 2004, 1920));

		for (String year : yearStringList) {
			yearCombo.addItem(year);
		}

		// 대분류 -> 중분류 연결
		yearCombo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selectedYear = (String) yearCombo.getSelectedItem();

				monthCombo.removeAllItems();
				dayCombo.removeAllItems();

				List<String> mediumAreas = repo2.getMonthList();
				for (String mediumArea : mediumAreas) {
					monthCombo.addItem(mediumArea);
				}
			}
		});

		// 중분류 -> 소분류 연결
		monthCombo.addActionListener(e -> {
			String selectedMediumArea = (String) monthCombo.getSelectedItem();
			dayCombo.removeAllItems();

			if (selectedMediumArea != null) {
				List<String> smallAreas = repo2.getDayList(selectedYear, selectedMediumArea);
				for (String smallArea : smallAreas) {
					dayCombo.addItem(smallArea);
				}
			}
		});

		idIdentifyLbl = new JLabel("아이디를 확인해주세요.");
		idIdentifyLbl.setBounds(650, 135, 280, 35);
		JLabel idConditionLbl = new JLabel(
				"아이디 : 영소문자 필수, 숫자포함가능(10자이상 20자이하)");
		idConditionLbl.setBounds(650, 520, 450, 35);
		idConditionLbl.setFont(new Font("돋움", Font.PLAIN, 12));
		idConditionLbl.setForeground(Color.GRAY);

		JLabel passwordConditionLbl = new JLabel(
				"비밀번호 : 대문자, 소문자, 숫자 각 1개이상 필수포함(10자이상 20자이하)");
		passwordConditionLbl.setBounds(650, 550, 450, 35);
		passwordConditionLbl.setFont(new Font("돋움", Font.PLAIN, 12));
		passwordConditionLbl.setForeground(Color.GRAY);

		JLabel nameConditionLbl = new JLabel(
				"이름 : 영문자 or 한글로만 20자이하(영문-4자이상, 한글-2자이상)");
		nameConditionLbl.setBounds(650, 505, 600, 35);
		nameConditionLbl.setFont(new Font("돋움", Font.PLAIN, 12));
		nameConditionLbl.setForeground(Color.GRAY);

		JLabel nicknameConditionLbl = new JLabel(
				"닉네임 : 영대소문자, 한글, 숫자 포함 (4자이상 20자이하)");
		nicknameConditionLbl.setBounds(650, 535, 450, 35);
		nicknameConditionLbl.setFont(new Font("돋움", Font.PLAIN, 12));
		nicknameConditionLbl.setForeground(Color.GRAY);

		passwordIdentifyLbl = new JLabel("비밀번호를 확인해주세요.");
		passwordIdentifyLbl.setBounds(680, 484, 280, 35);
		initialLabel();

		idTF = new JTextField(10);
		idTF.setBounds(650, 113, 240, 25);
		passwordPF = new JPasswordField(10);
		passwordPF.setBounds(660, 423, 240, 25);
		passwordPF2 = new JPasswordField(10);
		passwordPF2.setBounds(660, 468, 240, 25);

		JButton idIdentifyBtn = new JButton();
		idIdentifyBtn.setBounds(950, 109, 150, 55);
		ImageIcon imgidconfirm = new ImageIcon("img/idconfirm_1.png");
		idIdentifyBtn.setContentAreaFilled(false);
		idIdentifyBtn.setBorderPainted(false);
		idIdentifyBtn.setIcon(imgidconfirm);

		JButton passwordIdentifyBtn = new JButton();
		ImageIcon imgpass = new ImageIcon("img/pwconfirm_1.png");
		passwordIdentifyBtn.setBounds(950, 461, 150, 55);
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
		signupBtn.setBounds(690, 630, 330, 82);
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

				// 아이디
				String id = idTF.getText();
				idCheck(id);
				// 비번, 비번확인
				String password = new String(passwordPF.getPassword());
				String password2 = new String(passwordPF2.getPassword());
				passwordCheck(password, password2);
				// 이름
				String name = nameTF.getText();
				if (!nameCheck(name)) {
					JOptionPane.showMessageDialog(null, "입력 정보를 다시 확인해주세요.이름");
					return;
				}
				// 닉네임
				String nickname = nicknameTF.getText();
				nicknameCheck(nickname);
				// 전화번호
				String phone1 = telFrontTF.getText();
				String phone2 = telCenterTF.getText();
				String phone3 = telBackTF.getText();
				String phonenumber = "";
				if (phone1 != null && phone2 != null && phone3 != null) {
					phonenumber = phonenumConcat(phone1, phone2, phone3);
				} else {
					// 취소
					JOptionPane.showMessageDialog(null, "입력 정보를 다시 확인해주세요.전번");
					return;
				}
				// 생년월일
				LocalDate date = null;
				if (selectedYear != null && selectedMonth != null && selectedDay != null) {
					if (!selectedYear.equals("연도") && !selectedMonth.equals("월") && !selectedDay.equals("일")) { 
						date = dayCheck(selectedYear, selectedMonth, selectedDay); 
					}
				} else {
					JOptionPane.showMessageDialog(null, "입력 정보를 다시 확인해주세요.생년월일");
					return;
				}
				// 성별
				int genderInt = -1;
				if (genderStr != null) {
					genderInt = genderParse(genderStr);
				}
				if (genderInt == -1) {
					JOptionPane.showMessageDialog(null, "입력 정보를 다시 확인해주세요.성별");
					return;
				}
				// 주소
				if (bigAreaComboStr == null || mediumAreaComboStr == null || smallAreaComboStr == null) {
					JOptionPane.showMessageDialog(null, "입력 정보를 다시 확인해주세요.주소");
					return;
				}

				if (idCondition && passwordCondition && nicknameCondition) {
					int result = repo.insertUserInfo(id, password, nickname, name, date, genderInt, phonenumber,
							bigAreaComboStr, mediumAreaComboStr, smallAreaComboStr);
					if (result != 0) {
						JOptionPane.showMessageDialog(null, "회원가입되었습니다.");
						DataBase data = new DataBase();
						new LoginFrame(data);
						frame.setVisible(false);
					}
				} else {
					JOptionPane.showMessageDialog(null, "입력 정보를 다시 확인해주세요.아이디비번");
					initialLabel();
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
				frame.setVisible(false);
			}
		});

		signupPanel.add(idTF);
		signupPanel.add(idIdentifyLbl);
		signupPanel.add(passwordPF);
		signupPanel.add(passwordPF2);
		signupPanel.add(passwordIdentifyLbl);
		signupPanel.add(signupBtn);
		signupPanel.add(returnBtn);
		signupPanel.add(idIdentifyBtn);
		signupPanel.add(passwordIdentifyBtn);
		signupPanel.add(idConditionLbl);
		signupPanel.add(passwordConditionLbl);
		signupPanel.add(nameConditionLbl);
		signupPanel.add(nicknameConditionLbl);

		frame.getContentPane().add(signupPanel);
		frame.setVisible(true);
	}

	private void idCheck(String id) {
		if ((id.length() != 0) && repo.matchId(id) && repo.searchId(id) == null) {
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
		if ((password.length() != 0) && (password2.length() != 0) && repo.matchPassword(password)
				&& repo.matchPassword(password2) && password.equals(password2)) {
			passwordIdentifyLbl.setText("사용 가능한 비밀번호입니다.");
			passwordIdentifyLbl.setForeground(Color.GREEN);
			passwordCondition = true;
		} else {
			passwordIdentifyLbl.setText("올바른 비밀번호를 입력하세요.");
			passwordIdentifyLbl.setForeground(Color.RED);
			passwordCondition = false;
		}
	}

	private boolean nameCheck(String name) {
		if ((name.length() != 0) && repo.matchName(name)) {
			return true;
		} else {
			return false;
		}
	}

	private void nicknameCheck(String nickName) {
		if ((nickName.length() != 0) && (repo.matchNickName(nickName)) && (repo.searchNickName(nickName) == 0)) {
			nicknameInfoLbl.setText("사용 가능한 닉네임입니다.");
			nicknameInfoLbl.setForeground(Color.GREEN);
			nicknameCondition = true;
		} else {
			nicknameInfoLbl.setText("사용할 수 없는 닉네임입니다. 다른 닉네임을 입력하세요.");
			nicknameInfoLbl.setForeground(Color.RED);
			nicknameCondition = false;
		}
	}

	private void initialLabel() {
		idIdentifyLbl.setText("아이디를 확인해주세요.");
		idIdentifyLbl.setFont(new Font("돋움", Font.PLAIN, 12));
		idIdentifyLbl.setForeground(Color.RED);
		passwordIdentifyLbl.setText("비밀번호를 확인해주세요.");
		passwordIdentifyLbl.setFont(new Font("돋움", Font.PLAIN, 12));
		passwordIdentifyLbl.setForeground(Color.RED);
		nicknameInfoLbl.setText("닉네임을 확인해주세요.");
		nicknameInfoLbl.setFont(new Font("돋움", Font.PLAIN, 12));
		nicknameInfoLbl.setForeground(Color.RED);
	}

	private String phonenumConcat(String num1, String num2, String num3) {
		return num1 + num2 + num3;
	}

	private LocalDate dayCheck(String year, String month, String day) { 
		LocalDate date = null;
		int yearint = Integer.parseInt(year);
		int monthint = Integer.parseInt(month);
		int dayint = Integer.parseInt(day);
		date = LocalDate.of(yearint, monthint, dayint);
		return date;
	}

	private int genderParse(String str) {
		if (str.equals("남성")) {
			return 1;
		} else {
			return 0;
		}
	}

}
