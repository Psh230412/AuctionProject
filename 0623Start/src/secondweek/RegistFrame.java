package secondweek;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.LocalDateTime;

import javax.imageio.IIOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import dbutil.DBUtil;

public class RegistFrame extends JFrame {
	private JPanel contentPane;
	private JTextArea detailBox;
	private JTextField productNameInput;
	private JTextField productPriceInput;
	private JFrame frame;
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
		 frame = new JFrame();
			frame.setSize(1200,800);
		        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		        contentPane = new JPanel(){

			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);

				Toolkit toolkit = Toolkit.getDefaultToolkit();

				Image image = toolkit.getImage("img/registPage.png");
				g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
			}
		};
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel registeredImage = new JLabel(); // 미리보기
		registeredImage.setBounds(150, 150, 400, 400);
		contentPane.add(registeredImage);

		JTextField imageRoot = new JTextField();
		imageRoot.setVisible(false);
		contentPane.add(imageRoot);

		JLabel imageVolume = new JLabel("0 / 2mb");

		imageVolume.setBounds(280, 625, 100, 50);
		contentPane.add(imageVolume);

		Font myFont1 = new Font("Serif", Font.BOLD, 20);
		imageVolume.setFont(myFont1);

		JButton imageBtn = new JButton("파일 경로");
		imageBtn.setBounds(220, 553, 250, 100);
		ImageIcon imgBtn = new ImageIcon("img/findimg_1.png");
		imageBtn.setContentAreaFilled(false); 
		imageBtn.setBorderPainted(false);
		imageBtn.setIcon(imgBtn);
		imageBtn.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseExited(MouseEvent e) {
			ImageIcon imgBtn = new ImageIcon("img/findimg_1.png");
			imageBtn.setIcon(imgBtn);
			
		    }
		    
		    @Override
		    public void mouseEntered(MouseEvent e) {
			ImageIcon imgBtn = new ImageIcon("img/findimg.png");
			imageBtn.setIcon(imgBtn);
			
		    }
		 
		});
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
					double bytes = selectedFile.length();
					double kilobytes = bytes / 1024;
					double megabytes = kilobytes / 1024;

					imageVolume.setText(String.format("%.2f / 2MB", megabytes));

					if (megabytes > 2) {
						imageVolume.setForeground(Color.RED);
					} else {
						imageVolume.setForeground(Color.BLACK);
					}
					BufferedImage originalImage;
					try {
						originalImage = ImageIO.read(selectedFile);

						int type = originalImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : originalImage.getType();

						BufferedImage resizedImage = resizeImage(originalImage, type);
						ImageIcon imageIcon = new ImageIcon(resizedImage);

						registeredImage.setIcon(imageIcon);

					} catch (IOException e1) {
						e1.printStackTrace();
					}

					imageRoot.setText(selectedFile.getAbsolutePath());
				}
			}

		});
		detailBox = new JTextArea(5,20); // 상세정보 입력칸
		detailBox.setBounds(660, 320, 388, 215);
		contentPane.add(detailBox);
		detailBox.setColumns(10);
		detailBox.setLineWrap(true);

		JButton registrationBtn = new JButton("등록하기");
		registrationBtn.setBounds(690, 580, 350, 80);
		ImageIcon imgregitBtn = new ImageIcon("img/regist_1.png");
		registrationBtn.setContentAreaFilled(false); 
		registrationBtn.setBorderPainted(false);
		registrationBtn.setIcon(imgregitBtn);
		registrationBtn.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseExited(MouseEvent e) {
			ImageIcon imgregitBtn = new ImageIcon("img/regist_1.png");
			registrationBtn.setIcon(imgregitBtn);
			
		    }
		    
		    @Override
		    public void mouseEntered(MouseEvent e) {
			ImageIcon imgregitBtn = new ImageIcon("img/regist.png");
			registrationBtn.setIcon(imgregitBtn);
			
		    }
		 
		});
		
		registrationBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new AuctionFrame(data);
				 frame.setVisible(false);
			}
		});
	
		contentPane.add(registrationBtn);

		String[] auctionTimeOptions = { "1시간", "4시간", "24시간" };
		JComboBox<String> auctionTimeBox = new JComboBox<>(auctionTimeOptions);
		auctionTimeBox.setBounds(825, 230, 190, 25); // 위치와 크기를 설정해주세요.
		contentPane.add(auctionTimeBox);

//		JLabel hourLabel = new JLabel("마감시간 선택");
//		hourLabel.setBounds(230, 170, 100, 21);
//		contentPane.add(hourLabel);

		registrationBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int response = JOptionPane.showConfirmDialog(null, "정말로 등록하시겠습니까?", "예", JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE);
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
						String sanitizedText = detailinfo.replace("\n", "").replace("\r", "");
						Integer initialPrice = Integer.valueOf(productPriceInput.getText());
						File imageFile = new File(path); // 사용자가 입력한 파일 경로
						// 파일 용량 제한 (2mb)
						if (imageFile.length() > 2 * 1024 * 1024) { // 파일의 크기가 3MB보다 클때
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
						inputProduct.setString(3, sanitizedText);
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
		JButton mainBtn = new JButton("메인화면");
		mainBtn.setBounds(75, 40, 150, 80);
		ImageIcon imgmain = new ImageIcon("img/gomain_1.png");
		mainBtn.setContentAreaFilled(false); 
		mainBtn.setBorderPainted(false);
		mainBtn.setIcon(imgmain);
		mainBtn.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseExited(MouseEvent e) {
			ImageIcon imgmain = new ImageIcon("img/gomain_1.png");
			mainBtn.setIcon(imgmain);
			
		    }
		    
		    @Override
		    public void mouseEntered(MouseEvent e) {
			ImageIcon imgmain = new ImageIcon("img/gomain.png");
			mainBtn.setIcon(imgmain);
			
		    }
		 
		});
		
		mainBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new AuctionFrame(data);
				 frame.setVisible(false);
			}
		});
	
		contentPane.add(mainBtn);

//		JLabel productName = new JLabel("제품 이름");
//		productName.setBounds(247, 97, 57, 15);
//		contentPane.add(productName);

		productNameInput = new JTextField();
		productNameInput.setBounds(825, 115, 200, 30);
		contentPane.add(productNameInput);
		productNameInput.setColumns(10);

//		JLabel productPrice = new JLabel("제품 가격");
//		productPrice.setBounds(247, 137, 57, 15);
//		contentPane.add(productPrice);

		productPriceInput = new JTextField();
		productPriceInput.setColumns(10);
		productPriceInput.setBounds(825, 170, 200, 30);
		contentPane.add(productPriceInput);

		setLocationRelativeTo(null);
		 frame.getContentPane().add(contentPane);

		   frame.setVisible(true);
	}

	public static BufferedImage resizeImage(BufferedImage originalImage, int type) {
		BufferedImage resizedImage = new BufferedImage(400, 400, type);
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(originalImage, 0, 0, 400, 400, null);
		g.dispose();

		return resizedImage;
	}

}