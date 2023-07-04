package secondweek;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;

public class ManualFrame extends JFrame {
    public ManualFrame() {
        setTitle("Image Panel Example");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 500);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        getContentPane().add(panel, BorderLayout.CENTER);
        panel.setLayout(null);

     
        ImageIcon imageIcon = new ImageIcon("img/manual.gif");

        // 이미지를 표시할 JLabel 생성
        JLabel imageLabel = new JLabel(imageIcon);
        imageLabel.setBounds(0, 0, imageIcon.getIconWidth(), imageIcon.getIconHeight());

        // 패널에 이미지 레이블 추가
        panel.add(imageLabel);

        setVisible(true);
    }

}
