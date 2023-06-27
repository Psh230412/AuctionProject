package secondweek;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ChangeInformationFrame extends JFrame {

	private JPanel contentPane;
	private JTextField nameText;
	private JTextField idText;
	private JTextField phoneNumber;
	private JTextField textField;
	private JTextField nowPassword;
	private JTextField newPassword;
	private JTextField newPasswordMatch;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ChangeInformationFrame frame = new ChangeInformationFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	public ChangeInformationFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 735, 609);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		nameText = new JTextField();
		nameText.setBounds(336, 22, 370, 34);
		contentPane.add(nameText);
		nameText.setColumns(10);
		
		idText = new JTextField();
		idText.setBounds(336, 66, 370, 34);
		contentPane.add(idText);
		idText.setColumns(10);
		
		JRadioButton manBtn = new JRadioButton("남성");
		manBtn.setBounds(596, 106, 49, 23);
		contentPane.add(manBtn);
		
		JRadioButton womanBtn = new JRadioButton("여성");
		womanBtn.setBounds(657, 106, 49, 23);
		contentPane.add(womanBtn);
		
		JComboBox bigArea = new JComboBox();
		bigArea.setBounds(336, 176, 118, 21);
		contentPane.add(bigArea);
		
		JComboBox mediumArea = new JComboBox();
		mediumArea.setBounds(466, 176, 118, 21);
		contentPane.add(mediumArea);
		
		JComboBox smallArea = new JComboBox();
		smallArea.setBounds(596, 176, 110, 21);
		contentPane.add(smallArea);
		
		phoneNumber = new JTextField();
		phoneNumber.setBounds(596, 135, 49, 21);
		contentPane.add(phoneNumber);
		phoneNumber.setColumns(10);
		
		JButton informationResetBtn = new JButton("초기화");
		informationResetBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		informationResetBtn.setBounds(534, 236, 84, 34);
		contentPane.add(informationResetBtn);
		
		JButton changeBtn = new JButton("저장");
		changeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		changeBtn.setBounds(624, 236, 82, 34);
		contentPane.add(changeBtn);
		
		textField = new JTextField();
		textField.setColumns(10);
		textField.setBounds(657, 135, 49, 21);
		contentPane.add(textField);
		
		nowPassword = new JTextField();
		nowPassword.setBounds(336, 352, 370, 34);
		contentPane.add(nowPassword);
		nowPassword.setColumns(10);
		
		newPassword = new JTextField();
		newPassword.setColumns(10);
		newPassword.setBounds(336, 396, 370, 34);
		contentPane.add(newPassword);
		
		newPasswordMatch = new JTextField();
		newPasswordMatch.setColumns(10);
		newPasswordMatch.setBounds(336, 442, 370, 34);
		contentPane.add(newPasswordMatch);
		
		JButton passwordResetBtn = new JButton("초기화");
		passwordResetBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		passwordResetBtn.setBounds(534, 510, 84, 34);
		contentPane.add(passwordResetBtn);
		
		JButton passwordChangeBtn = new JButton("저장");
		passwordChangeBtn.setBounds(624, 510, 82, 34);
		contentPane.add(passwordChangeBtn);
	}

}
