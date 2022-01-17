package src;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;

public class NewEventWindow {

	private JFrame frame;
	private final JLabel dateLabel = new JLabel("DATE:");
	JLabel date = new JLabel("/ d a t e /");
	private JLabel eventLabel = new JLabel("EVENT");
	JTextField eventTxtField;
	private JLabel durationLabel = new JLabel("DURATION:");
	JTextField durationTxtField = new JTextField();
	//JFormattedTextField durationTxtField = new JFormattedTextField();
	private JLabel reminderLabel = new JLabel("REMINDER:");
	String[] reminderChoices = {"1 week", "3 days", "1 hour", "10 minutes"};
	JComboBox<String> reminderDropdown = new JComboBox(reminderChoices);
	private JButton saveBtn = new JButton("SAVE");
	private JLabel durationLabel1 = new JLabel("day(s)");
	private JButton cancelBtn = new JButton("CANCEL");
	private final JLabel usernameLabel = new JLabel("<user_name>");
	private final JLabel userLabel = new JLabel("USER: ");
	
	
	public void setDate(String value) {
		if(value != null) {
			date.setText(value);
		}
		else {
			date.setText("/ d a t e /");
		}
	}
	
	public void setUsername(String value) {
		usernameLabel.setText(value);
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					NewEventWindow window = new NewEventWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		JDBCMySQLConnection dbConnection = new JDBCMySQLConnection();
	}

	/**
	 * Create the application.
	 */
	public NewEventWindow() {
		initialize();
	}

	public void setVisible(boolean visible){
	    frame.setVisible(visible);
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
		
		
		usernameLabel.setBounds(330, 11, 89, 14);
		frame.getContentPane().add(usernameLabel);
		
		
		userLabel.setBounds(271, 11, 49, 14);
		frame.getContentPane().add(userLabel);
		
		
		dateLabel.setBounds(28, 30, 46, 14);
		frame.getContentPane().add(dateLabel);
		

		date.setBounds(131, 32, 111, 14);
		frame.getContentPane().add(date);
		
		eventLabel.setBounds(28, 71, 46, 14);
		frame.getContentPane().add(eventLabel);
		
		eventTxtField = new JTextField();
		eventTxtField.setBounds(131, 68, 196, 20);
		frame.getContentPane().add(eventTxtField);
		eventTxtField.setColumns(10);
		
		
		durationLabel.setBounds(28, 106, 74, 14);
		frame.getContentPane().add(durationLabel);
		
		
		durationTxtField.setBounds(131, 99, 46, 20);
		//durationTxtField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(java.text.NumberFormat.getIntegerInstance())));
		frame.getContentPane().add(durationTxtField);
		durationTxtField.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if(((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE)) {
					e.consume();
					// if the input is not a number, it will be ignored.
				}
			}
		});
		
		
		durationLabel1.setBounds(187, 106, 46, 14);
		frame.getContentPane().add(durationLabel1);
		
		
		reminderLabel.setBounds(28, 142, 74, 14);
		frame.getContentPane().add(reminderLabel);
		
		
		
		reminderDropdown.setBounds(131, 140, 102, 18);
		frame.getContentPane().add(reminderDropdown);
		
		
		saveBtn.setBounds(298, 206, 89, 23);
		frame.getContentPane().add(saveBtn);
		
		
		
		
		cancelBtn.setBounds(50, 206, 89, 23);
		frame.getContentPane().add(cancelBtn);
		
		
		cancelBtn.addActionListener((ActionListener) new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		
		
		
		
		// SAVE BUTTON:  SAVE THE INPUT DATA INTO THE DATABASE
		
		saveBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				String username = usernameLabel.getText();
				String eventDate = date.getText();
				String eventName = eventTxtField.getText();
				int eventDuration = Integer.parseInt(durationTxtField.getText());
				String eventReminder = (String) reminderDropdown.getSelectedItem();
				ResultSet result = null;
				Connection connection = null;
				
				PreparedStatement statement = null;
				
				try {
					connection = JDBCMySQLConnection.getConnection();
					
					//statement1 = connection.prepareStatement("")
					
					statement = connection.prepareStatement(
							"INSERT INTO eventdata(username, eventdate, activity, eventduration, reminder)"
							+ "VALUES(?, ?, ?, ?, ?)");
					statement.setString(1,  username);
					statement.setString(2, eventDate);
					statement.setString(3, eventName);
					statement.setInt(4, eventDuration);
					statement.setString(5, eventReminder);
					
					statement.executeUpdate();
					
					JOptionPane.showMessageDialog(frame, "Event is successfully saved!!");
				}catch(SQLException sqlException) {
					sqlException.printStackTrace();
				}
			}
			
		});
		
		
		
	}
}
