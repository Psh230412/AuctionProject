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
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import dbutil.DBUtil;

public class RegistFrame extends JFrame {
   private JPanel contentPane;
   private JTextField detailBox;
   private JTextField productNameInput;
   private JTextField productPriceInput;
   private LocalDate ldt;
   /**
    * Launch the application.
    */
   public static void main(String[] args) {
      EventQueue.invokeLater(new Runnable() {
         public void run() {
            try {
        	RegistFrame frame = new RegistFrame();
               frame.setVisible(true);
            } catch (Exception e) {
               e.printStackTrace();
            }
         }
      });
   }

   /**
    * Create the frame.
    */
   public RegistFrame() {
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

      detailBox = new JTextField(); // 상세정보 입력칸
      detailBox.setBounds(287, 260, 391, 154);
      contentPane.add(detailBox);
      detailBox.setColumns(10);
      JLabel lbl = new JLabel("파일 경로를 설정해주십시오.");
      contentPane.add(lbl);
      lbl.setBounds(100, 200, 100, 100);

      JButton registBtn = new JButton("등록하기");
      registBtn.setBounds(301, 483, 97, 23);
      contentPane.add(registBtn);
      
      String[] auctionTimeOptions = {"1시간", "4시간", "24시간", "사용자 정의"};
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
      
      
      
      registBtn.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            Connection conn = null;
            PreparedStatement stmt = null;
            PreparedStatement stmt2 = null;
            PreparedStatement stmt3 = null;
            try {

               conn = DBUtil.getConnection();
               
               
               String path = imageRoot.getText();
               String productname = productNameInput.getText();
               String detailinfo = detailBox.getText();
               Integer initialPrice = Integer.valueOf(productPriceInput.getText());
               File imageFile = new File(path); // 사용자가 입력한 파일 경로
               if(imageFile.length() > 2 * 1024 * 1024) { // if file size is more than 2MB
                   JOptionPane.showMessageDialog(null, "파일이 너무 큽니다. 2MB 이하의 파일을 선택해주세요.");
                   return;
               }
               BufferedImage originalImage = ImageIO.read(imageFile);
               BufferedImage resizedImage = new BufferedImage(100, 100, originalImage.getType());
               Graphics2D g = resizedImage.createGraphics();
               g.drawImage(originalImage, 0, 0, 100, 100, null);
               
               g.dispose();
               // Convert the resized image to a byte array
               ByteArrayOutputStream baos = new ByteArrayOutputStream();
               ImageIO.write(resizedImage, "jpg", baos);
               
               byte[] imageInByte = baos.toByteArray();

               stmt = conn.prepareStatement(
                     "insert into product(productname, initialprice, detailinfo, image) values (?,?,?,?)");
               stmt.setString(1, productname);
               stmt.setObject(2, initialPrice , Types.INTEGER);
               stmt.setString(3, detailinfo);
               stmt.setBytes(4, imageInByte);
               
               stmt.executeUpdate();
               // start 
               // dead 
               stmt2 = conn.prepareStatement("insert into auction(starttime, deadline) values (?, ?)");

               LocalDateTime now = LocalDateTime.now(); // 현재 시간
               Timestamp timestampNow = Timestamp.valueOf(now);  // LocalDateTime을 Timestamp로 변환
               stmt2.setTimestamp(1, timestampNow);

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
                           throw new IllegalArgumentException("Unexpected option: " + selectedOption);
                   }
                   deadline = now.plusHours(hoursToAdd);
               }

               Timestamp timestampDeadline = Timestamp.valueOf(deadline);  // LocalDateTime을 Timestamp로 변환
               stmt2.setTimestamp(2, timestampDeadline);

               stmt2.executeUpdate();
               
               // 로그인 / 물건의 정보
               
               
               
            } catch (IOException e2) {
               e2.printStackTrace();
            } catch (SQLException e2) {
               e2.printStackTrace();
            } finally {
               DBUtil.close(stmt);
               DBUtil.close(conn);
            }
         }
      });

      
      JButton returnMain = new JButton("메인화면가기");
      returnMain.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
             new AuctionFrame(null);
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