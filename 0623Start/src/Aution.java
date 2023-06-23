import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalTime;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

public class Aution extends JFrame {
//
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Aution frame = new Aution();
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
	
	class ClockFrame extends JFrame {
		public ClockFrame() {
			JLabel lbl = new JLabel(LocalTime.now().toString());
			
			add(lbl);
			
			Timer timer = new Timer(100, new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					lbl.setText(LocalTime.now().toString());
				}
			});
			timer.start();
			
			setSize(500, 500);
			setDefaultCloseOperation(EXIT_ON_CLOSE);
			setLocationRelativeTo(null);
			setVisible(true);
		}
	}
	public Aution() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 737, 572);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		setVisible(true);
	
		
		JLabel lblNewLabel = new JLabel("user");
		lblNewLabel.setBounds(100, 67, 57, 15);
		contentPane.add(lblNewLabel);
		
		JButton btnNewButton = new JButton("경매보기");
		btnNewButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new Detailpage();
				setVisible(false);
			}
		});
		btnNewButton.setBounds(311, 229, 97, 23);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("경매보기");
		btnNewButton_1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new Detailpage();
				setVisible(false);
			}
		});
		
		btnNewButton_1.setBounds(39, 229, 97, 23);
		contentPane.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("경매보기");
		btnNewButton_2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new Detailpage();
				setVisible(false);
			}
		});
		btnNewButton_2.setBounds(175, 229, 97, 23);
		contentPane.add(btnNewButton_2);
		
		JButton btnNewButton_3 = new JButton("경매보기");
		btnNewButton_3.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new Detailpage();
				setVisible(false);
			}
		});
		btnNewButton_3.setBounds(447, 229, 97, 23);
		contentPane.add(btnNewButton_3);
		
		JButton btnNewButton_3_1 = new JButton("경매보기");
		btnNewButton_3_1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new Detailpage();
				setVisible(false);
			}
		});
		btnNewButton_3_1.setBounds(583, 229, 97, 23);
		contentPane.add(btnNewButton_3_1);
		
		JButton btnNewButton_1_1 = new JButton("경매보기");
		btnNewButton_1_1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new Detailpage();
				setVisible(false);
			}
		});
		btnNewButton_1_1.setBounds(39, 422, 97, 23);
		contentPane.add(btnNewButton_1_1);
		
		JButton btnNewButton_2_1 = new JButton("경매보기");
		
		btnNewButton_2_1.setBounds(175, 422, 97, 23);
		contentPane.add(btnNewButton_2_1);
		
		JButton btnNewButton_4 = new JButton("경매보기");
		
		btnNewButton_4.setBounds(311, 422, 97, 23);
		contentPane.add(btnNewButton_4);
		
		JButton btnNewButton_3_2 = new JButton("경매보기");
		
		btnNewButton_3_2.setBounds(447, 422, 97, 23);
		contentPane.add(btnNewButton_3_2);
		
		JButton btnNewButton_3_1_1 = new JButton("경매보기");
		
		btnNewButton_3_1_1.setBounds(583, 422, 97, 23);
		contentPane.add(btnNewButton_3_1_1);
		
		JLabel lblNewLabel_1 = new JLabel("이미지");
		lblNewLabel_1.setBounds(64, 177, 57, 15);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("이미지");
		lblNewLabel_1_1.setBounds(197, 177, 57, 15);
		contentPane.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_2 = new JLabel("이미지");
		lblNewLabel_1_2.setBounds(336, 177, 57, 15);
		contentPane.add(lblNewLabel_1_2);
		
		JLabel lblNewLabel_1_2_1 = new JLabel("이미지");
		lblNewLabel_1_2_1.setBounds(473, 177, 57, 15);
		contentPane.add(lblNewLabel_1_2_1);
		
		JLabel lblNewLabel_1_2_1_1 = new JLabel("이미지");
		lblNewLabel_1_2_1_1.setBounds(604, 177, 57, 15);
		contentPane.add(lblNewLabel_1_2_1_1);
		
		JLabel lblNewLabel_1_3 = new JLabel("이미지");
		lblNewLabel_1_3.setBounds(64, 346, 57, 15);
		contentPane.add(lblNewLabel_1_3);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("이미지");
		lblNewLabel_1_1_1.setBounds(197, 346, 57, 15);
		contentPane.add(lblNewLabel_1_1_1);
		
		JLabel lblNewLabel_1_2_2 = new JLabel("이미지");
		lblNewLabel_1_2_2.setBounds(336, 346, 57, 15);
		contentPane.add(lblNewLabel_1_2_2);
		
		JLabel lblNewLabel_1_2_1_2 = new JLabel("이미지");
		lblNewLabel_1_2_1_2.setBounds(473, 346, 57, 15);
		contentPane.add(lblNewLabel_1_2_1_2);
		
		JLabel lblNewLabel_1_2_1_1_1 = new JLabel("이미지");
		lblNewLabel_1_2_1_1_1.setBounds(604, 346, 57, 15);
		contentPane.add(lblNewLabel_1_2_1_1_1);
		
		JLabel lblNewLabel_1_4 = new JLabel("00 : 00 : 00");
		lblNewLabel_1_4.setBounds(58, 262, 78, 15);
		contentPane.add(lblNewLabel_1_4);
		
		JLabel lblNewLabel_1_1_2 = new JLabel("00 : 00 : 00");
		lblNewLabel_1_1_2.setBounds(197, 262, 75, 15);
		contentPane.add(lblNewLabel_1_1_2);
		
		JLabel lblNewLabel_1_2_3 = new JLabel("00 : 00 : 00");
		lblNewLabel_1_2_3.setBounds(321, 262, 83, 15);
		contentPane.add(lblNewLabel_1_2_3);
		
		JLabel lblNewLabel_1_2_1_3 = new JLabel("00 : 00 : 00");
		lblNewLabel_1_2_1_3.setBounds(457, 262, 83, 15);
		contentPane.add(lblNewLabel_1_2_1_3);
		
		JLabel lblNewLabel_1_2_1_1_2 = new JLabel("00 : 00 : 00");
		lblNewLabel_1_2_1_1_2.setBounds(593, 262, 76, 15);
		contentPane.add(lblNewLabel_1_2_1_1_2);
		
		JLabel lblNewLabel_1_4_1 = new JLabel("00 : 00 : 00");
		lblNewLabel_1_4_1.setBounds(50, 471, 78, 15);
		contentPane.add(lblNewLabel_1_4_1);
		
		JLabel lblNewLabel_1_1_2_1 = new JLabel("00 : 00 : 00");
		lblNewLabel_1_1_2_1.setBounds(189, 471, 75, 15);
		contentPane.add(lblNewLabel_1_1_2_1);
		
		JLabel lblNewLabel_1_2_3_1 = new JLabel("00 : 00 : 00");
		lblNewLabel_1_2_3_1.setBounds(325, 471, 83, 15);
		contentPane.add(lblNewLabel_1_2_3_1);
		
		JLabel lblNewLabel_1_2_1_3_1 = new JLabel("00 : 00 : 00");
		lblNewLabel_1_2_1_3_1.setBounds(461, 471, 83, 15);
		contentPane.add(lblNewLabel_1_2_1_3_1);
		
		JLabel lblNewLabel_1_2_1_1_2_1 = new JLabel("00 : 00 : 00");
		lblNewLabel_1_2_1_1_2_1.setBounds(604, 471, 76, 15);
		contentPane.add(lblNewLabel_1_2_1_1_2_1);
		
		JButton btnNewButton_5 = new JButton("마이페이지");
		btnNewButton_5.setBounds(258, 36, 97, 23);
		btnNewButton_5.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new Mypage();
				setVisible(false);
			}
		});
		contentPane.add(btnNewButton_5);
		
		JButton btnNewButton_6 = new JButton("로그아웃");
		btnNewButton_6.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new Login();
				
				setVisible(false);
			}
		});
		btnNewButton_6.setBounds(258, 80, 97, 23);
		contentPane.add(btnNewButton_6);
		
		JLabel lblNewLabel_2 = new JLabel("현재가 8000원");
		lblNewLabel_2.setBounds(39, 287, 108, 15);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblNewLabel_2_1 = new JLabel("현재가 8000원");
		lblNewLabel_2_1.setBounds(186, 287, 108, 15);
		contentPane.add(lblNewLabel_2_1);
		
		JLabel lblNewLabel_2_1_1 = new JLabel("현재가 8000원");
		lblNewLabel_2_1_1.setBounds(319, 287, 108, 15);
		contentPane.add(lblNewLabel_2_1_1);
		
		JLabel lblNewLabel_2_1_2 = new JLabel("현재가 8000원");
		lblNewLabel_2_1_2.setBounds(454, 287, 108, 15);
		contentPane.add(lblNewLabel_2_1_2);
		
		JLabel lblNewLabel_2_1_3 = new JLabel("현재가 8000원");
		lblNewLabel_2_1_3.setBounds(587, 287, 108, 15);
		contentPane.add(lblNewLabel_2_1_3);
		
		JLabel lblNewLabel_2_2 = new JLabel("현재가 8000원");
		lblNewLabel_2_2.setBounds(39, 496, 108, 15);
		contentPane.add(lblNewLabel_2_2);
		
		JLabel lblNewLabel_2_1_4 = new JLabel("현재가 8000원");
		lblNewLabel_2_1_4.setBounds(186, 496, 108, 15);
		contentPane.add(lblNewLabel_2_1_4);
		
		JLabel lblNewLabel_2_1_1_1 = new JLabel("현재가 8000원");
		lblNewLabel_2_1_1_1.setBounds(319, 496, 108, 15);
		contentPane.add(lblNewLabel_2_1_1_1);
		
		JLabel lblNewLabel_2_1_2_1 = new JLabel("현재가 8000원");
		lblNewLabel_2_1_2_1.setBounds(454, 496, 108, 15);
		contentPane.add(lblNewLabel_2_1_2_1);
		
		JLabel lblNewLabel_2_1_3_1 = new JLabel("현재가 8000원");
		lblNewLabel_2_1_3_1.setBounds(587, 496, 108, 15);
		contentPane.add(lblNewLabel_2_1_3_1);
	}
}
