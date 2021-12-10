package src;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;

public class HelloWorldWindow {

	private JFrame frame;
	private JTextField textField;

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
		
		JButton btnNewButton = new JButton("New button");
		btnNewButton.setBounds(172, 161, 89, 23);
		frame.getContentPane().add(btnNewButton);
		btnNewButton.setText("OK");
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setBounds(172, 87, 81, 14);
		frame.getContentPane().add(lblNewLabel);
		lblNewLabel.setText("Hello World!!");
		
		
	}
}
