package auction;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import database.DataBase;

import javax.swing.JLabel;
import javax.swing.JButton;

public class Mypage extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		DataBase data = new DataBase();
		new Mypage(data);
	}

	/**
	 * Create the frame.
	 */
	public Mypage(DataBase data) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 800, 700);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		setVisible(true);
		
		JLabel lblNewLabel = new JLabel("user_name");
		lblNewLabel.setBounds(107, 68, 86, 15);
		lblNewLabel.setText(data.getCurrentUser().getName());
		
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("등록물품 리스트");
		lblNewLabel_1.setBounds(108, 249, 108, 15);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_2 = new JLabel("낙찰물품 리스트");
		lblNewLabel_1_2.setBounds(325, 249, 108, 15);
		contentPane.add(lblNewLabel_1_2);
		
		JLabel lblNewLabel_1_2_1 = new JLabel("유물품 리스트");
		lblNewLabel_1_2_1.setBounds(527, 249, 108, 15);
		contentPane.add(lblNewLabel_1_2_1);
		
		JButton btnNewButton = new JButton("개인정보변경");
		btnNewButton.setBounds(91, 142, 120, 23);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("물품등록하기");
		btnNewButton_1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
//				new Registration();
//				setVisible(false);
			}
		});
		btnNewButton_1.setBounds(299, 68, 120, 23);
		contentPane.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("메인화면");
		btnNewButton_2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new AuctionFrame(data);
				setVisible(false);
			}
		});
		btnNewButton_2.setBounds(493, 64, 97, 23);
		contentPane.add(btnNewButton_2);
		;
	}

}
