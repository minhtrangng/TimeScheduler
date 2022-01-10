package src;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;

public class AddEventWindow {

	private JFrame frame;
	private JLabel eventLabel = new JLabel("Event:");
	JTextField eventTxtField;
	private JLabel durationLabel = new JLabel("Duration:");
	JFormattedTextField durationTxtField = new JFormattedTextField();
	private JLabel reminderLabel = new JLabel("Reminder:");
	String[] reminderChoices = {"1 week", "3 days", "1 hour", "10 minutes"};
	JComboBox<String> reminderDropdown = new JComboBox(reminderChoices);
	private JButton saveBtn = new JButton("SAVE");
	private JLabel durationLabel1 = new JLabel("day(s)");
	
	
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddEventWindow window = new AddEventWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public AddEventWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	@SuppressWarnings("deprecation")
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		
		eventLabel.setBounds(28, 34, 46, 14);
		frame.getContentPane().add(eventLabel);
		
		eventTxtField = new JTextField();
		eventTxtField.setBounds(131, 31, 196, 20);
		frame.getContentPane().add(eventTxtField);
		eventTxtField.setColumns(10);
		
		
		durationLabel.setBounds(28, 90, 74, 14);
		frame.getContentPane().add(durationLabel);
		
		
		durationTxtField.setBounds(131, 87, 46, 20);
		durationTxtField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(java.text.NumberFormat.getIntegerInstance())));
		frame.getContentPane().add(durationTxtField);
		
		
		durationLabel1.setBounds(187, 93, 46, 14);
		frame.getContentPane().add(durationLabel1);
		
		
		reminderLabel.setBounds(28, 151, 74, 14);
		frame.getContentPane().add(reminderLabel);
		
		
		
		reminderDropdown.setBounds(131, 149, 102, 18);
		frame.getContentPane().add(reminderDropdown);
		
		
		saveBtn.setBounds(298, 206, 89, 23);
		frame.getContentPane().add(saveBtn);
		
		
		
		
		
	}
}
