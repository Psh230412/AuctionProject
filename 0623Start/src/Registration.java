import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;

public class Registration extends JFrame {

    private JPanel contentPane;
    private JTextField textField;
    private JTextField textField_1;
    private JTextField textField_2;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
	EventQueue.invokeLater(new Runnable() {
	    public void run() {
		try {
		    Registration frame = new Registration();
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
    public Registration() {
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setBounds(100, 100, 737, 572);
	contentPane = new JPanel();
	contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
	setContentPane(contentPane);
	contentPane.setLayout(null);
	
	JLabel lblNewLabel = new JLabel("이미지등록");
	lblNewLabel.setBounds(104, 293, 177, 31);
	contentPane.add(lblNewLabel);
	
	textField = new JTextField();
	textField.setBounds(287, 260, 391, 154);
	contentPane.add(textField);
	textField.setColumns(10);
	
	JButton btnNewButton = new JButton("등록하기");
	btnNewButton.setBounds(301, 483, 97, 23);
	contentPane.add(btnNewButton);
	
	JButton btnNewButton_1 = new JButton("메인화면가기");
	btnNewButton_1.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			new Aution();
		setVisible(false);
		}
	});
	btnNewButton_1.setBounds(35, 21, 97, 23);
	contentPane.add(btnNewButton_1);
	
	JLabel lblNewLabel_1 = new JLabel("제품 이름");
	lblNewLabel_1.setBounds(247, 97, 57, 15);
	contentPane.add(lblNewLabel_1);
	
	textField_1 = new JTextField();
	textField_1.setBounds(340, 91, 116, 21);
	contentPane.add(textField_1);
	textField_1.setColumns(10);
	
	JLabel lblNewLabel_1_1 = new JLabel("제품 가격");
	lblNewLabel_1_1.setBounds(247, 137, 57, 15);
	contentPane.add(lblNewLabel_1_1);
	
	textField_2 = new JTextField();
	textField_2.setColumns(10);
	textField_2.setBounds(340, 131, 116, 21);
	contentPane.add(textField_2);
	setLocationRelativeTo(null);
	setVisible(true);
    }

}
