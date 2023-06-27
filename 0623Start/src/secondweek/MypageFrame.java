package secondweek;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JButton;

public class MypageFrame extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		DataBase data = new DataBase();
		new MypageFrame(data);
	}

	
	/**
	 * Create the frame.
	 */
	public MypageFrame(DataBase data) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 800, 700);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		setVisible(true);
		
		JLabel userNameLb = new JLabel("user_name");
		userNameLb.setBounds(107, 68, 86, 15);
		userNameLb.setText(data.getCurrentUser().getName());
		
		contentPane.add(userNameLb);
		
		JLabel registListLb = new JLabel("등록물품 리스트");
		registListLb.setBounds(108, 249, 108, 15);
		contentPane.add(registListLb);
		
		JLabel auctionListLb = new JLabel("경매 중 물품 리스트");
		auctionListLb.setBounds(325, 249, 108, 15);
		contentPane.add(auctionListLb);
		
		JLabel resultListLb = new JLabel("낙찰/유찰물품 리스트");
		resultListLb.setBounds(527, 249, 108, 15);
		contentPane.add(resultListLb);
		
		JButton resetBtn = new JButton("개인정보변경");
		resetBtn.setBounds(91, 142, 120, 23);
		contentPane.add(resetBtn);
		
		JButton resgistBtn = new JButton("물품등록하기");
		resgistBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new RegistFrame(data);
				setVisible(false);
			}
		});
		resgistBtn.setBounds(299, 68, 120, 23);
		contentPane.add(resgistBtn);
		
		JButton mainBtn = new JButton("메인화면");
		mainBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new AuctionFrame(data);
				setVisible(false);
			}
		});
		mainBtn.setBounds(493, 64, 97, 23);
		contentPane.add(mainBtn);
		;
	}

}
