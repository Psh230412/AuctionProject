package secondweek;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class AuctionFrame extends JFrame {

	public AuctionFrame(DataBase data) {

		JPanel pnl = new JPanel();
		pnl.setLayout(null);
		pnl.setBackground(Color.RED);

		JLabel userLbl = new JLabel("여기에 로그인 중인 아이디 표시");
		userLbl.setText(data.getCurrentUser().getName());
		userLbl.setBounds(305, 50, 100, 20);

		JButton mypageBtn = new JButton("마이페이지");
		mypageBtn.setBounds(417, 50, 100, 20);
		mypageBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new Mypage(data);
				setVisible(false);
			}
		});

		JButton logoutBtn = new JButton("로그아웃");
		logoutBtn.setBounds(529, 50, 100, 20);
		logoutBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int choice = JOptionPane.showConfirmDialog(null, "로그아웃하시겠습니까?", "로그아웃", JOptionPane.YES_NO_OPTION);
				if (choice == JOptionPane.YES_OPTION) {
					JOptionPane.showMessageDialog(null, "로그아웃되었습니다.");
					DataBase newdata = new DataBase();
					new LoginFrame(newdata);
					setVisible(false);
				}
			}
		});

		JButton exitBtn = new JButton("종료");
		exitBtn.setBounds(641, 50, 100, 20);
		exitBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int choice = JOptionPane.showConfirmDialog(null, "종료하시겠습니까?", "종료", JOptionPane.YES_NO_OPTION);
				if (choice == JOptionPane.YES_OPTION) {
					setVisible(false);
					System.exit(0);
				}
			}
		});

		pnl.add(userLbl);
		pnl.add(mypageBtn);
		pnl.add(logoutBtn);
		pnl.add(exitBtn);

		JPanel pnl1 = new JPanel();
		JPanel pnl2 = new JPanel();
		JPanel pnl3 = new JPanel();
		JPanel pnl4 = new JPanel();
		JPanel pnl5 = new JPanel();
		JPanel pnl6 = new JPanel();
		JPanel pnl7 = new JPanel();
		JPanel pnl8 = new JPanel();
		JPanel pnl9 = new JPanel();
		JPanel pnl10 = new JPanel();
		JPanel[] pnls = { pnl1, pnl2, pnl3, pnl4, pnl5, pnl6, pnl7, pnl8, pnl9, pnl10 };

		int x = 25;
		int y = 150;
		for (int i = 0; i < pnls.length; i++) {

			pnls[i].setBounds(x, y, 130, 225);
			JPanel imagepnl = new JPanel();
			imagepnl.setPreferredSize(new Dimension(120, 120));
			imagepnl.setBackground(Color.BLUE);
			JLabel imageLbl = new JLabel("이미지");
			imagepnl.add(imageLbl);
			JButton viewProductBtn = new JButton("경매보기");
			JLabel timeLbl = new JLabel("00:00:00");

			JLabel priceLbl = new JLabel("현재가 8000원");

			pnls[i].add(imagepnl);
			pnls[i].add(viewProductBtn);
			pnls[i].add(timeLbl);
			pnls[i].add(priceLbl);

			pnl.add(pnls[i]);

			if ((i + 1) / 5 == 1) {
				y = 400;
			}
			if ((i + 1) == 5) {
				x = 25;
			} else {
				x += 155;
			}

		}

		getContentPane().add(pnl);

		setSize(800, 700);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		setLocationRelativeTo(null);
		setResizable(false);
	}

}
