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

public class Detailpage extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Detailpage frame = new Detailpage();
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
	public Detailpage() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 737, 572);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		setVisible(true);
		
		JLabel lblNewLabel = new JLabel("제품이미지");
		lblNewLabel.setBounds(101, 144, 158, 255);
		contentPane.add(lblNewLabel);
		
		JButton btnNewButton = new JButton("입찰하기");
		btnNewButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new Aution();
				
				setVisible(false);
			}
		});
		btnNewButton.setBounds(479, 402, 158, 61);
		contentPane.add(btnNewButton);
		
		JLabel lblNewLabel_1 = new JLabel("00 : 00 : 00");
		lblNewLabel_1.setFont(new Font("돋움", Font.BOLD, 16));
		lblNewLabel_1.setBounds(493, 208, 136, 45);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("남은 시간");
		lblNewLabel_2.setFont(new Font("돋움", Font.BOLD, 16));
		lblNewLabel_2.setBounds(493, 164, 130, 34);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel(" 현재가격 : 8,000 원");
		lblNewLabel_3.setFont(new Font("굴림", Font.BOLD, 17));
		lblNewLabel_3.setBounds(457, 331, 178, 39);
		contentPane.add(lblNewLabel_3);
	}

}
