package secondweek;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DetailFrame extends JFrame {

	private JPanel contentPane;

	/**
//	 * Launch the application.
//	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//				    DetailFrame frame = new DetailFrame();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}
//
////	/**
	
//	 * Create the frame.
//	 */
	public DetailFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 900, 800);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		setVisible(true);
		
		JLabel imgLb = new JLabel("제품이미지");
		imgLb.setBounds(101, 144, 158, 255);
		contentPane.add(imgLb);
		
		JButton getAuctionBtn = new JButton("입찰하기");
		getAuctionBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new AuctionFrame(null);
				
				setVisible(false);
			}
		});
		getAuctionBtn.setBounds(479, 402, 158, 61);
		contentPane.add(getAuctionBtn);
		
		JLabel timeLb = new JLabel("00 : 00 : 00");
		timeLb.setFont(new Font("돋움", Font.BOLD, 16));
		timeLb.setBounds(493, 208, 136, 45);
		contentPane.add(timeLb);
		
		JLabel leftTimeLb = new JLabel("남은 시간");
		leftTimeLb.setFont(new Font("돋움", Font.BOLD, 16));
		leftTimeLb.setBounds(493, 164, 130, 34);
		contentPane.add(leftTimeLb);
		
		JLabel priceLb = new JLabel(" 현재가격 : 8,000 원");
		priceLb.setFont(new Font("굴림", Font.BOLD, 17));
		priceLb.setBounds(457, 331, 178, 39);
		contentPane.add(priceLb);
	}

}