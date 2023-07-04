package secondweek;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.LocalDateTime;
import java.util.Arrays;

import javax.imageio.IIOException;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Icon;
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
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.text.PlainDocument;
import javax.swing.text.DocumentFilter.FilterBypass;

import dbutil.DBUtil;
import secondweek.AuctionFrame.NumberOnlyFilter;

public class RegistFrame extends JFrame {
	private JPanel contentPane;
	private JTextArea detailBox;
	private JTextField productNameInput;
	private JTextField productPriceInput;
	private JComboBox<String> classificationBox = new JComboBox();
	private JFrame frame;
	private static final int MAX_IMAGES = 4;
	private JLabel[] smallImageLabels = new JLabel[MAX_IMAGES];
	private JLabel[] bigImageLabels = new JLabel[MAX_IMAGES];
	private JLabel[] fileSizeLabels = new JLabel[MAX_IMAGES];

	private JLabel mainImageLabel;
	
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

	public RegistFrame(DataBase data) {

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

				Image image = toolkit.getImage("img/registPage.png");
				g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
			}
		};
	//	contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		for (int i = 0; i < MAX_IMAGES; i++) {
			fileSizeLabels[i] = new JLabel();
			fileSizeLabels[i].setBounds(10, 665 + 15 * i, 400, 20); // 위치와 크기를 설정합니다.
			contentPane.add(fileSizeLabels[i]);
		}
		
		JTextField imageRoot = new JTextField();
		imageRoot.setVisible(false);
		contentPane.add(imageRoot);
		
		JTextField imageRoot2 = new JTextField();
		imageRoot2.setVisible(false);
		contentPane.add(imageRoot2);
		
		JTextField imageRoot3 = new JTextField();
		imageRoot3.setVisible(false);
		contentPane.add(imageRoot3);
		
		JTextField imageRoot4 = new JTextField();
		imageRoot4.setVisible(false);
		contentPane.add(imageRoot4);

		mainImageLabel = new JLabel();
		mainImageLabel.setBounds(240, 100, 400, 400);
		contentPane.add(mainImageLabel);
		for (int i = 0; i < MAX_IMAGES; i++) {
		    smallImageLabels[i] = new JLabel();
		    smallImageLabels[i].setBounds(95, 76 + 110 * i, 100, 100);
		    smallImageLabels[i].setBorder(BorderFactory.createEmptyBorder());
		    smallImageLabels[i].addMouseListener(new MouseAdapter() {
		        @Override
		        public void mouseClicked(MouseEvent e) {
		            JLabel clickedLabel = (JLabel) e.getSource();
		            int index = Arrays.asList(smallImageLabels).indexOf(clickedLabel);
		            if (smallImageLabels[index].getIcon() != null) {
		                mainImageLabel.setIcon(bigImageLabels[index].getIcon());

		                for (JLabel label : smallImageLabels) {
		                    label.setBorder(BorderFactory.createEmptyBorder());
		                }

		                clickedLabel.setBorder(BorderFactory.createLineBorder(Color.RED, 5));

		                if (fileSizeLabels[index].getForeground() == Color.RED) {
		                    JFileChooser fileChooser = new JFileChooser();
		                    FileNameExtensionFilter filter = new FileNameExtensionFilter("Image files",
		                            ImageIO.getReaderFileSuffixes());
		                    fileChooser.setFileFilter(filter);
		                    int returnValue = fileChooser.showOpenDialog(null);
		                    if (returnValue == JFileChooser.APPROVE_OPTION) {
		                        File selectedFile = fileChooser.getSelectedFile();

		                        double bytes = selectedFile.length();
		                        double megabytes = (bytes / 1024) / 1024;
		                        fileSizeLabels[index].setText(String.format("File: %s Size: %.2f MB", selectedFile.getName(), megabytes));
		                        fileSizeLabels[index].setForeground(megabytes > 2 ? Color.RED : Color.BLACK);
		                        
		                        
		                        try {
		                            BufferedImage originalImage = ImageIO.read(selectedFile);
		                            int type = originalImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : originalImage.getType();

		                            BufferedImage bigResizedImage = resizeImage(originalImage, type, 400, 400);
		                            bigImageLabels[index].setIcon(new ImageIcon(bigResizedImage));

		                            BufferedImage smallResizedImage = resizeImage(originalImage, type, 100, 100);
		                            smallImageLabels[index].setIcon(new ImageIcon(smallResizedImage));
		                            
		                            mainImageLabel.setIcon(new ImageIcon(bigResizedImage));

		                            switch (index) {
		                            case 0:
		                                imageRoot.setText(selectedFile.getAbsolutePath());
		                                break;
		                            case 1:
		                                imageRoot2.setText(selectedFile.getAbsolutePath());
		                                break;
		                            case 2:
		                                imageRoot3.setText(selectedFile.getAbsolutePath());
		                                break;
		                            case 3:
		                                imageRoot4.setText(selectedFile.getAbsolutePath());
		                                break;
		                            }
		                        } catch (IOException e1) {
		                            e1.printStackTrace();
		                        }
		                    }
		                }
		            }
		        }
		    });
		    contentPane.add(smallImageLabels[i]);

		    bigImageLabels[i] = new JLabel();

		    fileSizeLabels[i] = new JLabel();
		    fileSizeLabels[i].setBounds(220, 600 + 20 * i, 400, 100);
		    contentPane.add(fileSizeLabels[i]);
		}


		JLabel imageVolume = new JLabel("각 파일의 크기는 2mb를 넘을수 없습니다");
		imageVolume.setBounds(220, 600, 400, 50);
		contentPane.add(imageVolume);

		Font myFont1 = new Font("Serif", Font.BOLD, 15);
		imageVolume.setFont(myFont1);

		JButton imageBtn = new JButton();
		imageBtn.setBounds(300, 525, 207, 80);
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
		        fileChooser.setMultiSelectionEnabled(true);
		        int returnValue = fileChooser.showOpenDialog(null);
		        if (returnValue == JFileChooser.APPROVE_OPTION) {
		        	  // 다시 선택했을때 리셋
		            for (int i = 0; i < MAX_IMAGES; i++) {
		                fileSizeLabels[i].setText("");
		                bigImageLabels[i].setIcon(null);
		                smallImageLabels[i].setIcon(null);
		            }
		            imageRoot.setText("");
		            imageRoot2.setText("");
		            imageRoot3.setText("");
		            imageRoot4.setText("");
		        	
		        	
		            File[] selectedFiles = fileChooser.getSelectedFiles();

		            for (int i = 0; i < selectedFiles.length && i < MAX_IMAGES; i++) {
		                try {
		                    File selectedFile = selectedFiles[i];

		                    double bytes = selectedFile.length();
		                    double megabytes = (bytes / 1024) / 1024;
		                    fileSizeLabels[i].setText(String.format("File: %s Size: %.2f MB", selectedFile.getName(), megabytes));
		                    fileSizeLabels[i].setForeground(megabytes > 2 ? Color.RED : Color.BLACK);

		                    BufferedImage originalImage = ImageIO.read(selectedFile);
		                    int type = originalImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : originalImage.getType();

		                    BufferedImage bigResizedImage = resizeImage(originalImage, type, 400, 400);
		                    bigImageLabels[i].setIcon(new ImageIcon(bigResizedImage));
		                    
		                    if (i == 0) {
		                        mainImageLabel.setIcon(bigImageLabels[i].getIcon());
		                    }

		                    BufferedImage smallResizedImage = resizeImage(originalImage, type, 100, 100);
		                    smallImageLabels[i].setIcon(new ImageIcon(smallResizedImage));

		                    switch (i) {
		                    case 0:
		                        imageRoot.setText(selectedFile.getAbsolutePath());
		                        break;
		                    case 1:
		                        imageRoot2.setText(selectedFile.getAbsolutePath());
		                        break;
		                    case 2:
		                        imageRoot3.setText(selectedFile.getAbsolutePath());
		                        break;
		                    case 3:
		                        imageRoot4.setText(selectedFile.getAbsolutePath());
		                        break;
		                    }
		                } catch (IOException ex) {
		                    ex.printStackTrace();
		                }
		            }
		        }
		    }
		});
		detailBox = new JTextArea(5, 20); // 상세정보 입력칸
		detailBox.setBounds(730, 360, 350, 180);
		contentPane.add(detailBox);
		detailBox.setColumns(10);
		detailBox.setLineWrap(true);

		classificationBox.setBounds(825, 80, 200, 35);
		classificationBox.addItem("전자제품");
		classificationBox.addItem("식품");
		classificationBox.addItem("자동차");
		classificationBox.addItem("부동산");
		classificationBox.addItem("귀금속");
		classificationBox.addItem("잡화");
		classificationBox.addItem("의류&악세서리");
		classificationBox.addItem("운동기구");
		classificationBox.addItem("가구&인테리어");
		classificationBox.addItem("도서");
		classificationBox.addItem("기타");
		contentPane.add(classificationBox);

		JButton registrationBtn = new JButton();
		registrationBtn.setBounds(725, 570, 350, 80);
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
				frame.dispose();
			}
		});

		contentPane.add(registrationBtn);

		String[] auctionTimeOptions = { "1시간", "4시간", "24시간" };
		JComboBox<String> auctionTimeBox = new JComboBox<>(auctionTimeOptions);
		auctionTimeBox.setBounds(825, 230, 190, 25); // 위치와 크기를 설정해주세요.
		contentPane.add(auctionTimeBox);

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
					Connection connForCopy = null;
					PreparedStatement stmt = null;

					try {
						conn = DBUtil.getConnection();

						// 물건정보 입력 (이름,상세정보,시작가격,이미지파일(경로)
						String category = (String) classificationBox.getSelectedItem();
						if (category == null || category.isEmpty()) {
						    JOptionPane.showMessageDialog(null, "카테고리를 선택해주세요.");
						    return;
						}
						String productname = productNameInput.getText();
						String detailinfo = detailBox.getText();

						String sanitizedText = detailinfo.replace("\n", "").replace("\r", "");
						Integer initialPrice = Integer.valueOf(productPriceInput.getText());

						 String path = imageRoot.getText();
				         String path2 = imageRoot2.getText();
				         String path3 = imageRoot3.getText();
				         String path4 = imageRoot4.getText();
				            
				            File imageFile = null;
				            File imageFile2 = null;
				            File imageFile3 = null;
				            File imageFile4 = null;

				            byte[] imageBytes = null;
				            byte[] imageBytes2 = null;
				            byte[] imageBytes3 = null;
				            byte[] imageBytes4 = null;

				            if (!path.isEmpty()) {
				                imageFile = new File(path); // 사용자가 입력한 파일 경로
				                if (imageFile.exists() && imageFile.length() > 2 * 1024 * 1024) {
									JOptionPane.showMessageDialog(null, "파일이 너무 큽니다. 2MB 이하의 파일을 선택해주세요.");
									return;
								}
				                imageBytes = Files.readAllBytes(imageFile.toPath());
				            }
				            if (!path2.isEmpty()) {
				                imageFile2 = new File(path2); // 사용자가 입력한 파일 경로
				                if (imageFile2.exists() && imageFile2.length() > 2 * 1024 * 1024) {
									JOptionPane.showMessageDialog(null, "파일이 너무 큽니다. 2MB 이하의 파일을 선택해주세요.");
									return;
								}
				                imageBytes2 = Files.readAllBytes(imageFile2.toPath());
				            }
				            if (!path3.isEmpty()) {
				                imageFile3 = new File(path3); // 사용자가 입력한 파일 경로
				                if (imageFile3.exists() && imageFile3.length() > 2 * 1024 * 1024) {
									JOptionPane.showMessageDialog(null, "파일이 너무 큽니다. 2MB 이하의 파일을 선택해주세요.");
									return;
								}
				                imageBytes3 = Files.readAllBytes(imageFile3.toPath());
				            }
				            if (!path4.isEmpty()) {
				                imageFile4 = new File(path4); // 사용자가 입력한 파일 경로
				                if (imageFile4.exists() && imageFile4.length() > 2 * 1024 * 1024) {
									JOptionPane.showMessageDialog(null, "파일이 너무 큽니다. 2MB 이하의 파일을 선택해주세요.");
									return;
								}
				                imageBytes4 = Files.readAllBytes(imageFile4.toPath());
				            }
					
						// 정보 sql에 등록
						inputProduct = conn.prepareStatement(
								"insert into product(productname, initialprice, detailinfo, image,subimage1,subimage2,subimage3,category) values (?,?,?,?,?,?,?,?)");
						inputProduct.setString(1, productname);
						inputProduct.setObject(2, initialPrice, Types.INTEGER);
						inputProduct.setString(3, sanitizedText);
						inputProduct.setBytes(4, imageBytes);
						inputProduct.setBytes(5, imageBytes2);
						inputProduct.setBytes(6, imageBytes3);
						inputProduct.setBytes(7, imageBytes4);
						inputProduct.setString(8, category);

						inputProduct.executeUpdate();
						// 옥션의 시작시간 , 마감시간 추가
						inputProductDate = conn.prepareStatement(
								"insert into auction(starttime, deadline, finalprice) values (?, ?, ?)",
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
						inputProductDate.setObject(3, initialPrice, Types.INTEGER);
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
							inputAuctionSetNo.setObject(2, auctionId);
							inputAuctionSetNo.executeUpdate();
						}

//						물건 등록하면 cacheMap에다가 추가
						Cache.putCacheMap(auctionId, imageBytes, imageBytes2, imageBytes3, imageBytes4);
						Cache.putProductCacheMap();

						

						stmt = conn.prepareStatement("INSERT INTO copy_auction\r\n"
								+ "						SELECT *\r\n" + "						FROM auction\r\n"
								+ "						WHERE auctionno > (SELECT MAX(auctionno) \r\n"
								+ "						               FROM copy_auction);");

						stmt.executeUpdate();

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
						DBUtil.close(stmt);
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
					return;
				}
			}
		});
		JButton mainBtn = new JButton();
		mainBtn.setBounds(970, 680, 130, 50);
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
		productNameInput = new JTextField();
		productNameInput.setBounds(825, 138, 200, 35);
		contentPane.add(productNameInput);
		productNameInput.setColumns(10);
		productPriceInput = new JTextField();
		productPriceInput.setColumns(10);
		productPriceInput.setBounds(825, 200, 200, 35);
		PlainDocument docPrice = (PlainDocument) productPriceInput.getDocument();
		docPrice.setDocumentFilter(new NumberOnlyFilter(15));
		
		contentPane.add(productPriceInput);

		setLocationRelativeTo(null);
		frame.getContentPane().add(contentPane);

		frame.setVisible(true);
	}

	public void swapImages(JLabel mainImage, JLabel smallImage) {
		Icon tempIcon = mainImage.getIcon();
		mainImage.setIcon(smallImage.getIcon());
		smallImage.setIcon(tempIcon);

		Image smallImg = ((ImageIcon) smallImage.getIcon()).getImage();
		Image resizedSmallImg = smallImg.getScaledInstance(smallImage.getWidth(), smallImage.getHeight(),
				java.awt.Image.SCALE_SMOOTH);
		smallImage.setIcon(new ImageIcon(resizedSmallImg));

		Image mainImg = ((ImageIcon) mainImage.getIcon()).getImage();
		Image resizedMainImg = mainImg.getScaledInstance(mainImage.getWidth(), mainImage.getHeight(),
				java.awt.Image.SCALE_SMOOTH);
		mainImage.setIcon(new ImageIcon(resizedMainImg));
	}

	public static BufferedImage resizeImage(BufferedImage originalImage, int type, int width, int height) {
		BufferedImage resizedImage = new BufferedImage(width, height, type);
		Graphics2D g = resizedImage.createGraphics();

		g.drawImage(originalImage, 0, 0, width, height, null);
		g.dispose();
		return resizedImage;
	}

}