package src;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;

public class HelloWorldWindow {

	private JFrame frame;
	//private JTextField textField;
	private JTextField userNameTextField;
	private JTextField pwTextField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HelloWorldWindow window = new HelloWorldWindow();
					window.frame.setVisible(true);
					window.frame.setTitle("Time Scheduler");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public HelloWorldWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton btnNewButton = new JButton("Login");
		btnNewButton.setBounds(114, 189, 180, 32);
		frame.getContentPane().add(btnNewButton);
		
		
		JLabel userNameLabel = new JLabel("Username:");
		userNameLabel.setBounds(114, 58, 81, 14);
		frame.getContentPane().add(userNameLabel);
		//lblNewLabel.setText("Hello World!!");
		
		userNameTextField = new JTextField();
		userNameTextField.setBounds(114, 74, 140, 23);
		frame.getContentPane().add(userNameTextField);
		userNameTextField.setColumns(10);
		
		JLabel pwLabel = new JLabel("Password:");
		pwLabel.setBounds(114, 108, 81, 14);
		frame.getContentPane().add(pwLabel);
		
		pwTextField = new JTextField();
		pwTextField.setBounds(114, 127, 140, 23);
		frame.getContentPane().add(pwTextField);
		pwTextField.setColumns(10);
		
		
	}
}
