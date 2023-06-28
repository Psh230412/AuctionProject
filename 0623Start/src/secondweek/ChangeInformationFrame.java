package secondweek;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.text.PlainDocument;

import com.sun.prism.paint.Color;

import dbutil.DBUtil;

public class ChangeInformationFrame extends JFrame {

	private JPanel contentPane;
	private JTextField nickNameText;
	private JTextField idText;
	private JTextField phoneNumberCenter;
	private JTextField phoneNumberLast;
	private JTextField nowPassword;
	private JTextField newPassword;
	private JTextField newPasswordMatch;
	private int selectedGender;
	private JRadioButton manBtn;
	private JRadioButton womanBtn;
	private JComboBox<String> bigAreaCombo = new JComboBox();
	private JComboBox<String> mediumAreaCombo = new JComboBox();
	private JComboBox<String> smallAreaCombo = new JComboBox();

	public int getSelectedGender() {
		return selectedGender;
	}

	class NumberOnlyFilter extends DocumentFilter {
		private int maxLength; // 최대 입력 길이

		public NumberOnlyFilter(int maxLength) {
			this.maxLength = maxLength;
		}

		@Override
		public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
				throws BadLocationException {
			// 입력 문자열이 숫자인 경우에만 삽입을 허용합니다.
			if (string.matches("\\d+") && (fb.getDocument().getLength() + string.length()) <= maxLength) {
				super.insertString(fb, offset, string, attr);
			}
		}

		@Override
		public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
				throws BadLocationException {
			// 입력 문자열이 숫자인 경우에만 대체를 허용합니다.
			if (text.matches("\\d+") && (fb.getDocument().getLength() - length + text.length()) <= maxLength) {
				super.replace(fb, offset, length, text, attrs);
			}
		}
	}

	// 중복되는 닉네임 있는지 검색후 해당하는 개수 반환
	public int searchNickName(String newNickName) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "SELECT COUNT(*) AS count FROM user WHERE nickname = ?";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, newNickName);
			rs = stmt.executeQuery();
			if (rs.next()) {
				return rs.getInt("count");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs);
			DBUtil.close(stmt);
			DBUtil.close(conn);
		}
		return 1;
	}

	// 생성 가능한 닉네임인지?
	public boolean correctNickName(String newNickName) {
		if (searchNickName(newNickName) == 0 && matchNickName(newNickName)) {
			return true;
		} else {
			return false;
		}
	}

	// 닉네임 조건 - 영대소문자 or 한글 or 숫자 포함 4자리이상 20자리이하
	public boolean matchNickName(String newNickName) {
		Pattern p = Pattern.compile("[\\w\\uAC00-\\uD7AF]{4,20}");
		Matcher m = p.matcher(newNickName);
		return m.matches();
	}

	// 비밀번호 조건 - 영대소문자, 숫자 각 1개씩 필수포함 10자리이상 20자리이하
	public boolean MatchPassword(String newPassword) {
		Pattern p = Pattern.compile("[a-z+A-z+0-9+]{10,20}");
		Matcher m = p.matcher(newPassword);
		return m.matches();
	}

	public void returnOriginal(DataBase data) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "SELECT * FROM user where userno = ?";
			stmt = conn.prepareStatement(sql);
			int nowUser = data.getCurrentUser().getNo();
			stmt.setInt(1, nowUser);
			rs = stmt.executeQuery();
			if (rs.next()) {
				String originalNickName =  rs.getString("nickname");
				nickNameText.setText(originalNickName);
				
			    int gender = rs.getInt("gender");
	            if (gender == 1) {
	                manBtn.setSelected(true); // 남성 선택
	            } else if (gender == 0) {
	                womanBtn.setSelected(true); // 여성 선택
	            }
	            
				String originalPhoneNumber = rs.getString("phonenumber");
				String middle = originalPhoneNumber.substring(3,7);
				String last = originalPhoneNumber.substring(7,11);
				phoneNumberCenter.setText(middle);
				phoneNumberLast.setText(last);
				
				String bigArea = rs.getString("bigarea");
				String mediumArea = rs.getString("mediumarea");
				String detailArea = rs.getString("detailarea");
				if(bigAreaCombo.getItemCount() != 0 && bigAreaCombo.getSelectedItem().equals(bigArea)) {
				    bigAreaCombo.setSelectedItem(bigArea);
				}
				if(mediumAreaCombo.getItemCount() != 0 && mediumAreaCombo.getSelectedItem().equals(mediumArea)) {
				    mediumAreaCombo.setSelectedItem(mediumArea);
				}
				if(smallAreaCombo.getItemCount() != 0 && smallAreaCombo.getSelectedItem().equals(detailArea)) {
				    smallAreaCombo.setSelectedItem(detailArea);
				}
				
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs);
			DBUtil.close(stmt);
			DBUtil.close(conn);
		}
	}

	public ChangeInformationFrame(DataBase data) {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 735, 609);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setVisible(true);

		nickNameText = new JTextField();
		nickNameText.setBounds(336, 22, 370, 34);
		contentPane.add(nickNameText);
		nickNameText.setColumns(10);

		idText = new JTextField();
		idText.setBounds(336, 66, 370, 34);
		contentPane.add(idText);
		idText.setColumns(10);

		JLabel errorLabel = new JLabel("닉네임을 확인해주십시오.");
		errorLabel.setBounds(10, 10, 200, 30);
		contentPane.add(errorLabel);

		JButton errorCheckBtn = new JButton();
		errorCheckBtn.setBounds(10, 50, 50, 50);

		errorCheckBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String nickName = nickNameText.getText();
				if (matchNickName(nickName) == false) {
					errorLabel.setText("닉네임은 영대소문자 or 한글 or 숫자 포함 4자리이상 20자리이하여야 합니다.");
				} else if (searchNickName(nickName) != 0) {
					errorLabel.setText("이미 존재하는 닉네임입니다.");
				} else if (correctNickName(nickName) == true) {
					errorLabel.setText("사용가능한 닉네임 입니다.");
				}
			}
		});
		contentPane.add(errorCheckBtn);

		manBtn = new JRadioButton("남성");
		manBtn.setBounds(596, 106, 49, 23);
		contentPane.add(manBtn);

		womanBtn = new JRadioButton("여성");
		womanBtn.setBounds(657, 106, 49, 23);
		contentPane.add(womanBtn);
		ButtonGroup genderGroup = new ButtonGroup();
		genderGroup.add(manBtn);
		genderGroup.add(womanBtn);

		manBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 남성 선택 시 1 반환
				selectedGender = 1;
			}
		});

		womanBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 여성 선택 시 0 반환
				selectedGender = 0;
			}
		});
		bigAreaCombo.setBounds(336, 176, 118, 21);
		contentPane.add(bigAreaCombo);
		mediumAreaCombo.setBounds(466, 176, 118, 21);
		contentPane.add(mediumAreaCombo);
		smallAreaCombo.setBounds(596, 176, 110, 21);
		contentPane.add(smallAreaCombo);
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

				mediumAreaCombo.removeAllItems();
				smallAreaCombo.removeAllItems();

				List<String> mediumAreas = getMediumAreas(selectedBigArea);
				for (String mediumArea : mediumAreas) {
					mediumAreaCombo.addItem(mediumArea);
				}

			}
		});

		// 중분류 -> 소분류 연결
		mediumAreaCombo.addActionListener(e -> {
			String selectedMediumArea = (String) mediumAreaCombo.getSelectedItem();
			smallAreaCombo.removeAllItems();

			if (selectedMediumArea != null) {
				List<String> smallAreas = getSmallAreas(selectedMediumArea);
				for (String smallArea : smallAreas) {
					smallAreaCombo.addItem(smallArea);
				}
			}
		});
		// 휴대폰 번호 입력
		phoneNumberCenter = new JTextField();
		phoneNumberCenter.setBounds(596, 135, 49, 21);
		contentPane.add(phoneNumberCenter);
		phoneNumberCenter.setColumns(10);

		PlainDocument docCenter = (PlainDocument) phoneNumberCenter.getDocument();
		docCenter.setDocumentFilter(new NumberOnlyFilter(4));

		phoneNumberLast = new JTextField();
		phoneNumberLast.setColumns(10);
		phoneNumberLast.setBounds(657, 135, 49, 21);
		contentPane.add(phoneNumberLast);

		PlainDocument docLast = (PlainDocument) phoneNumberLast.getDocument();
		docLast.setDocumentFilter(new NumberOnlyFilter(4));

		JButton informationResetBtn = new JButton("초기화");
		informationResetBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		informationResetBtn.setBounds(534, 236, 84, 34);
		contentPane.add(informationResetBtn);

		JButton changeBtn = new JButton("변경하기");
		changeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int response = JOptionPane.showConfirmDialog(null, "정말로 변경하시겠습니까?", "예", JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE);
				if (response == JOptionPane.YES_OPTION) {
					Connection conn = null;
					PreparedStatement changeUserInformation = null;
					try {
						conn = DBUtil.getConnection();

						String nickName = nickNameText.getText();
						if (correctNickName(nickName) == false) {
							JOptionPane.showMessageDialog(null, "아이디를 다시 확인바랍니다", "입력오류", JOptionPane.WARNING_MESSAGE);
							return;
						}
						String phoneNumber = "010" + phoneNumberCenter.getText() + phoneNumberLast.getText();
						String bigArea = (String) bigAreaCombo.getSelectedItem();
						String mediumArea = (String) mediumAreaCombo.getSelectedItem();
						String detailArea = (String) smallAreaCombo.getSelectedItem();
						int loginUser = data.getCurrentUser().getNo();

						changeUserInformation = conn
								.prepareStatement("update user set nickname = ?, phonenumber = ?, bigarea = ?,"
										+ "mediumarea = ?, detailarea = ?  where userno = ?");

						changeUserInformation.setString(1, nickName);
						// changeUserInformation.setString(2, id);
						changeUserInformation.setString(2, phoneNumber);
						changeUserInformation.setString(3, bigArea);
						changeUserInformation.setString(4, mediumArea);
						changeUserInformation.setString(5, detailArea);
						changeUserInformation.setInt(6, loginUser);

						changeUserInformation.executeUpdate();
					} catch (NumberFormatException e2) {
						JOptionPane.showMessageDialog(null, "올바르게 입력해주십시오", "입력오류", JOptionPane.WARNING_MESSAGE);
						e2.printStackTrace();
					} catch (SQLException e2) {
						e2.printStackTrace();
					} finally {
						DBUtil.close(changeUserInformation);
						DBUtil.close(conn);
					}
				} else {
				}
			}
		});
		changeBtn.setBounds(624, 236, 82, 34);
		contentPane.add(changeBtn);

		nowPassword = new JTextField();
		nowPassword.setBounds(336, 352, 370, 34);
		contentPane.add(nowPassword);
		nowPassword.setColumns(10);

		newPassword = new JTextField();
		newPassword.setColumns(10);
		newPassword.setBounds(336, 396, 370, 34);
		contentPane.add(newPassword);

		newPasswordMatch = new JTextField();
		newPasswordMatch.setColumns(10);
		newPasswordMatch.setBounds(336, 442, 370, 34);
		contentPane.add(newPasswordMatch);

		JButton passwordResetBtn = new JButton("초기화");
		passwordResetBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		passwordResetBtn.setBounds(534, 510, 84, 34);
		contentPane.add(passwordResetBtn);

		JButton passwordChangeBtn = new JButton("저장");
		passwordChangeBtn.setBounds(624, 510, 82, 34);
		contentPane.add(passwordChangeBtn);

		returnOriginal(data);

	}

	private static List<String> getMediumAreas(String bigArea) {
		List<String> mediumAreas = new ArrayList<>();
		if (bigArea.equals("수도권")) {
			Collections.addAll(mediumAreas, "서울특별시", "경기도", "인천광역시");
		} else if (bigArea.equals("강원권")) {
			Collections.addAll(mediumAreas, "강원도");
		} else if (bigArea.equals("충청권")) {
			Collections.addAll(mediumAreas, "충청북도", "충청남도", "대전광역시", "세종특별자치시");
		} else if (bigArea.equals("전라권")) {
			Collections.addAll(mediumAreas, "전라북도", "전라남도", "광주광역시");
		} else if (bigArea.equals("경상권")) {
			Collections.addAll(mediumAreas, "경상북도", "경상남도", "부산광역시", "대구광역시", "울산광역시");
		} else if (bigArea.equals("제주권")) {
			Collections.addAll(mediumAreas, "제주특별자치도");
		}
		return mediumAreas;
	}

	private static List<String> getSmallAreas(String mediumArea) {
		List<String> smallAreas = new ArrayList<>();
		// 예시: 서울특별시일 경우에는 강남구, 강동구, 강북구 등을 추가
		if (mediumArea.equals("서울특별시")) {
			Collections.addAll(smallAreas, "강남구", "강동구", "강북구", "강서구", "관악구", "광진구", "구로구", "금천구", "노원구", "도봉구", "동대문구",
					"동작구", "마포구", "서대문구", "서초구", "성동구", "성북구", "송파구", "양천구", "영등포구", "용산구", "은평구", "종로구", "중구", "중랑구");
		} else if (mediumArea.equals("경기도")) {
			Collections.addAll(smallAreas, "수원시 장안구", "수원시 권선구", "수원시 팔달구", "수원시 영통구", "성남시 수정구", "성남시 중원구", "성남시 분당구",
					"의정부시", "안양시 만안구", "안양시 동안구", "부천시", "광명시", "평택시", "동두천시", "안산시 상록구", "안산시 단원구", "고양시 덕양구",
					"고양시 일산동구", "고양시 일산서구", "과천시", "구리시", "남양주시", "오산시", "시흥시", "군포시", "의왕시", "하남시", "용인시 처인구",
					"용인시 기흥구", "용인시 수지구", "파주시", "이천시", "안성시", "김포시", "화성시", "광주시", "양주시", "포천시", "여주시", "연천군", "가평군",
					"양평군");
		} else if (mediumArea.equals("인천광역시")) {
			Collections.addAll(smallAreas, "계양구", "미추홀구", "남동구", "동구", "부평구", "서구", "연수구", "중구", "강화군", "옹진군");
		} else if (mediumArea.equals("강원도")) {
			Collections.addAll(smallAreas, "춘천시", "원주시", "강릉시", "동해시", "태백시", "속초시", "삼척시", "홍천군", "횡성군", "영월군", "평창군",
					"정선군", "철원군", "화천군", "양구군", "인제군", "고성군", "양양군");
		} else if (mediumArea.equals("충청북도")) {
			Collections.addAll(smallAreas, "청주시 상당구", "청주시 서원구", "청주시 흥덕구", "청주시 청원구", "충주시", "제천시", "보은군", "옥천군",
					"영동군", "증평군", "진천군", "괴산군", "음성군", "단양군");
		} else if (mediumArea.equals("충청남도")) {
			Collections.addAll(smallAreas, "천안시 동남구", "천안시 서북구", "공주시", "보령시", "아산시", "서산시", "논산시", "계룡시", "당진시", "금산군",
					"부여군", "서천군", "청양군", "홍성군", "예산군", "태안군");
		} else if (mediumArea.equals("대전광역시")) {
			Collections.addAll(smallAreas, "대덕구", "동구", "서구", "유성구", "중구");
		} else if (mediumArea.equals("세종특별자치시")) {
			Collections.addAll(smallAreas, "세종특별자치시");
		} else if (mediumArea.equals("전라북도")) {
			Collections.addAll(smallAreas, "전주시 완산구", "전주시 덕진구", "군산시", "익산시", "정읍시", "남원시", "김제시", "완주군", "진안군", "무주군",
					"장수군", "임실군", "순창군", "고창군", "부안군");
		} else if (mediumArea.equals("전라남도")) {
			Collections.addAll(smallAreas, "목포시", "여수시", "순천시", "나주시", "광양시", "담양군", "곡성군", "구례군", "고흥군", "보성군", "화순군",
					"장흥군", "강진군", "해남군", "영암군", "무안군", "함평군", "영광군", "장성군", "완도군", "진도군", "신안군");
		} else if (mediumArea.equals("광주광역시")) {
			Collections.addAll(smallAreas, "광산구", "남구", "동구", "북구", "서구");
		} else if (mediumArea.equals("경상북도")) {
			Collections.addAll(smallAreas, "포항시 남구", "포항시 북구", "경주시", "김천시", "안동시", "구미시", "영주시", "영천시", "상주시", "문경시",
					"경산시", "군위군", "의성군", "청송군", "영양군", "영덕군", "청도군", "고령군", "성주군", "칠곡군", "예천군", "봉화군", "울진군", "울릉군");
		} else if (mediumArea.equals("경상남도")) {
			Collections.addAll(smallAreas, "창원시 의창구", "창원시 성산구", "창원시 마산합포구", "창원시 마산,회원구", "창원시 진해구", "진주시", "통영시",
					"사천시", "김해시", "밀양시", "거제시", "양산시", "의령군", "함안군", "창녕군", "고성군", "남해군", "하동군", "산청군", "함양군", "거창군",
					"합천군");
		} else if (mediumArea.equals("부산광역시")) {
			Collections.addAll(smallAreas, "강서구", "금정구", "남구", "동구", "동래구", "부산진구", "북구", "사상구", "사하구", "서구", "수영구",
					"연제구", "영도구", "중구", "해운대구", "기장군");
		} else if (mediumArea.equals("대구광역시")) {
			Collections.addAll(smallAreas, "남구", "달서구", "동구", "북구", "서구", "수성구", "중구", "달성군");
		} else if (mediumArea.equals("울산광역시")) {
			Collections.addAll(smallAreas, "남구", "동구", "북구", "중구", "울주군");
		} else if (mediumArea.equals("제주특별자치도")) {
			Collections.addAll(smallAreas, "서귀포시", "제주시");
		}
		return smallAreas;
	}
}
