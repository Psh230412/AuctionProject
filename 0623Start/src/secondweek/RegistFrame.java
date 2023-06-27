package secondweek;

import java.awt.EventQueue;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.imageio.IIOException;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import dbutil.DBUtil;

public class RegistFrame extends JFrame {
	private JPanel contentPane;
	private JTextField detailBox;
	private JTextField productNameInput;
	private JTextField productPriceInput;
	private LocalDate ldt;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RegistFrame frame = new RegistFrame(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public RegistFrame(DataBase data) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 737, 572);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JTextField imageRoot = new JTextField("이미지등록");
		imageRoot.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				imageRoot.setText("");
			}
		});
		imageRoot.setBounds(104, 293, 177, 31);
		contentPane.add(imageRoot);

		JButton imageBtn = new JButton("파일 경로");
		imageBtn.setBounds(104, 253, 100, 30);
		contentPane.add(imageBtn);
		imageBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter("Image files",
						ImageIO.getReaderFileSuffixes());
				fileChooser.setFileFilter(filter);
				int returnValue = fileChooser.showOpenDialog(null);
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					File selectedFile = fileChooser.getSelectedFile();
					imageRoot.setText(selectedFile.getAbsolutePath());
				}
			}
		});
		detailBox = new JTextField(); // 상세정보 입력칸
		detailBox.setBounds(287, 260, 391, 154);
		contentPane.add(detailBox);
		detailBox.setColumns(10);

		JButton registrationBtn = new JButton("등록하기");
		registrationBtn.setBounds(301, 483, 97, 23);
		contentPane.add(registrationBtn);

		String[] auctionTimeOptions = { "1시간", "4시간", "24시간", "사용자 정의" };
		JComboBox<String> auctionTimeBox = new JComboBox<>(auctionTimeOptions);
		auctionTimeBox.setBounds(240, 200, 70, 21); // 위치와 크기를 설정해주세요.
		contentPane.add(auctionTimeBox);

		JTextField hourInput = new JTextField();
		hourInput.setBounds(340, 200, 100, 21); // 위치와 크기를 설정해주세요.
		contentPane.add(hourInput);

		JLabel hourLabel = new JLabel("사용자 입력(시간)");
		hourLabel.setBounds(450, 200, 100, 21);
		contentPane.add(hourLabel);

		JTextField minuteInput = new JTextField();
		minuteInput.setBounds(340, 230, 100, 21); // 위치와 크기를 설정해주세요.
		contentPane.add(minuteInput);

		JLabel minuteLabel = new JLabel("사용자 입력(분)");
		minuteLabel.setBounds(450, 230, 100, 21);
		contentPane.add(minuteLabel);

		registrationBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int response = JOptionPane.showConfirmDialog(null, "정말로 등록하시겠습니까?", "예",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if (response == JOptionPane.YES_OPTION) {
					Connection conn = null;
					PreparedStatement inputProduct = null;
					PreparedStatement inputProductDate = null;
					PreparedStatement recentProductId = null;
					PreparedStatement inputTogether = null;
					PreparedStatement recentEnrollId = null;
					PreparedStatement inputAuctionSetNo = null;
					ResultSet getRecentProductId = null; // 물건의 정보값(id) 가져오기
					ResultSet getRecentSetNo = null; // 물건의 정보값(id) 가져오기

					try {
						conn = DBUtil.getConnection();

						// 물건정보 입력 (이름,상세정보,시작가격,이미지파일(경로)
						String path = imageRoot.getText();
						String productname = productNameInput.getText();
						String detailinfo = detailBox.getText();
						Integer initialPrice = Integer.valueOf(productPriceInput.getText());
						File imageFile = new File(path); // 사용자가 입력한 파일 경로
						// 파일 용량 제한 (2mb)
						if (imageFile.length() > 2 * 1024 * 1024) { // 파일의 크기가 2MB보다 클때
							JOptionPane.showMessageDialog(null, "파일이 너무 큽니다. 2MB 이하의 파일을 선택해주세요.");
							return;
						}
						// 파일 크기 100,100으로 조절
						BufferedImage originalImage = ImageIO.read(imageFile);
						BufferedImage resizedImage = new BufferedImage(100, 100, originalImage.getType());
						Graphics2D g = resizedImage.createGraphics();
						g.drawImage(originalImage, 0, 0, 100, 100, null);
						g.dispose();
						// Convert the resized image to a byte array
						ByteArrayOutputStream baos = new ByteArrayOutputStream();
						ImageIO.write(resizedImage, "jpg", baos);

						byte[] imageInByte = baos.toByteArray();
						// 정보 sql에 등록
						inputProduct = conn.prepareStatement(
								"insert into product(productname, initialprice, detailinfo, image) values (?,?,?,?)");
						inputProduct.setString(1, productname);
						inputProduct.setObject(2, initialPrice, Types.INTEGER);
						inputProduct.setString(3, detailinfo);
						inputProduct.setBytes(4, imageInByte);

						inputProduct.executeUpdate();
						// 옥션의 시작시간 , 마감시간 추가
						inputProductDate = conn.prepareStatement(
								"insert into auction(starttime, deadline) values (?, ?)",
								Statement.RETURN_GENERATED_KEYS);
						LocalDateTime now = LocalDateTime.now(); // 현재 시간
						Timestamp timestampNow = Timestamp.valueOf(now); // LocalDateTime을 Timestamp로 변환
						inputProductDate.setTimestamp(1, timestampNow);
						// 마감시간 콤보박스 and 시간 직접 입력
						String selectedOption = (String) auctionTimeBox.getSelectedItem();
						LocalDateTime deadline;

						if ("사용자 정의".equals(selectedOption)) {
							int hoursToAdd = Integer.parseInt(hourInput.getText()); // 사용자가 입력한 시간을 가져옵니다.
							int minutesToAdd = Integer.parseInt(minuteInput.getText()); // 사용자가 입력한 분을 가져옵니다.
							deadline = now.plusHours(hoursToAdd).plusMinutes(minutesToAdd); // 종료 시간
						} else {
							int hoursToAdd;
							switch (selectedOption) {
							case "1시간":
								hoursToAdd = 1;
								break;
							case "4시간":
								hoursToAdd = 4;
								break;
							case "24시간":
								hoursToAdd = 24;
								break;
							default:
								throw new IllegalArgumentException("존재하지않는 옵션입니다: " + selectedOption);
							}
							deadline = now.plusHours(hoursToAdd);
						}

						Timestamp timestampDeadline = Timestamp.valueOf(deadline); // LocalDateTime을 Timestamp로 변환
						inputProductDate.setTimestamp(2, timestampDeadline);
						inputProductDate.executeUpdate();

						// date가 저장된 옥션의 키값
						ResultSet rs = inputProductDate.getGeneratedKeys();
						int auctionId = 0;
						if (rs.next()) {
							auctionId = rs.getInt(1);
						}

						// 로그인한 유저의 id값과 / 물건의 id값을 구하기 enrollmentinfo에 등록 (두개의 값의 id가 setno)
						int currentLoginUserId = data.getCurrentUser().getNo();
						recentProductId = conn.prepareStatement("SELECT MAX(productno) from product");
						getRecentProductId = recentProductId.executeQuery();
						while (getRecentProductId.next()) {
							int productNum = getRecentProductId.getInt("MAX(productno)");

							inputTogether = conn
									.prepareStatement("insert into enrollmentinfo(userno,productno) values(?,?)");
							inputTogether.setInt(1, currentLoginUserId);
							inputTogether.setInt(2, productNum);
							inputTogether.executeUpdate();
						}
						// 그 최신으로 등록된 enrollmentinfo의 setno를 auction의 최근 정보에 등록(최근 등록된 경매에)
						recentEnrollId = conn.prepareStatement("SELECT MAX(setno) from enrollmentinfo");
						getRecentSetNo = recentEnrollId.executeQuery();
						while (getRecentSetNo.next()) {
							int setNo = getRecentSetNo.getInt("MAX(setno)");
							inputAuctionSetNo = conn
									.prepareStatement("UPDATE auction SET setno = ? WHERE auctionno = ?");
							inputAuctionSetNo.setInt(1, setNo);
							inputAuctionSetNo.setInt(2, auctionId);
							inputAuctionSetNo.executeUpdate();
						}
					} catch (NumberFormatException e2) {
						JOptionPane.showMessageDialog(null, "올바르게 입력해주십시오", "입력오류", JOptionPane.WARNING_MESSAGE);
						e2.printStackTrace();
					} catch (IIOException e2) {	
						JOptionPane.showMessageDialog(null, "파일의 경로가 잘못되었습니다.", "입력오류", JOptionPane.WARNING_MESSAGE);
						e2.printStackTrace();
					} catch (IOException e2) {
						e2.printStackTrace();
					} catch (SQLException e2) {
						e2.printStackTrace();
					} finally {
						DBUtil.close(inputAuctionSetNo);
						DBUtil.close(getRecentSetNo);
						DBUtil.close(recentEnrollId);
						DBUtil.close(inputTogether);
						DBUtil.close(getRecentProductId);
						DBUtil.close(recentProductId);
						DBUtil.close(inputProductDate);
						DBUtil.close(inputProduct);
						DBUtil.close(conn);
					}
				} else {
				}
			}
		});
		JButton returnMain = new JButton("메인화면가기");
		returnMain.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new AuctionFrame(data);
				setVisible(false);
			}
		});
		returnMain.setBounds(35, 21, 97, 23);
		contentPane.add(returnMain);

		JLabel productName = new JLabel("제품 이름");
		productName.setBounds(247, 97, 57, 15);
		contentPane.add(productName);

		productNameInput = new JTextField();
		productNameInput.setBounds(340, 91, 116, 21);
		contentPane.add(productNameInput);
		productNameInput.setColumns(10);

		JLabel productPrice = new JLabel("제품 가격");
		productPrice.setBounds(247, 137, 57, 15);
		contentPane.add(productPrice);

		productPriceInput = new JTextField();
		productPriceInput.setColumns(10);
		productPriceInput.setBounds(340, 131, 116, 21);
		contentPane.add(productPriceInput);

		setLocationRelativeTo(null);
		setVisible(true);
	}

}