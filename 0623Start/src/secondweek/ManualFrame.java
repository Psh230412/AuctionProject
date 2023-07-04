package secondweek;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;

//public class ManualFrame extends JFrame {
//    public ManualFrame() {
//        setTitle("Image Panel Example");
//        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//        setSize(600, 500);
//        setLocationRelativeTo(null);
//
//        JPanel panel = new JPanel();
//        getContentPane().add(panel, BorderLayout.CENTER);
//        panel.setLayout(null);
//
//     
//        ImageIcon imageIcon = new ImageIcon("img/manual.gif");
//
//        // 이미지를 표시할 JLabel 생성
//        JLabel imageLabel = new JLabel(imageIcon);
//        imageLabel.setBounds(0, 0, imageIcon.getIconWidth(), imageIcon.getIconHeight());
//
//        // 패널에 이미지 레이블 추가
//        panel.add(imageLabel);
//
//        setVisible(true);
//    }
//
//}
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class ManualFrame {
    private JFrame frame;
    private JLabel imageLabel;
    private ArrayList<String> imagePaths;
    private int currentImageIndex;

    public ManualFrame() {
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(600, 500);
        frame.setLocationRelativeTo(null);

        imagePaths = new ArrayList<>();
        imagePaths.add("img/manu1.jpg");
        imagePaths.add("img/manu2.jpg");
        imagePaths.add("img/manu3.jpg");
        imagePaths.add("img/manu4.jpg");
        imagePaths.add("img/manu5.jpg");
        imagePaths.add("img/manu6.jpg");
        imagePaths.add("img/manu7.jpg");
        imagePaths.add("img/manu8.jpg");
        imagePaths.add("img/manu9.jpg");
        imagePaths.add("img/manu10.jpg");
        imagePaths.add("img/manu11.jpg");
        imagePaths.add("img/manu12.jpg");
      

        currentImageIndex = 0;

        imageLabel = new JLabel();
        updateImage();

        JButton nextButton = new JButton("Next");
  
    
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentImageIndex = (currentImageIndex + 1) % imagePaths.size();
                updateImage();
            }
        });

        frame.add(imageLabel);
        frame.add(nextButton, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    private void updateImage() {
        String imagePath = imagePaths.get(currentImageIndex);
        ImageIcon imageIcon = new ImageIcon(imagePath);
        imageLabel.setIcon(imageIcon);
        frame.revalidate();
    }


}
