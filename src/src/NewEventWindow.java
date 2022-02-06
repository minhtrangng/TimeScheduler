package src;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.*;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;

/**
 * This class requires user to fulfill event information.  
 * 
 * @author minhtrang
 *
 */

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
	JLabel beginTimeLabel = new JLabel("BEGIN AT:");
	private JTextField beginHour;
	JLabel colonLabel = new JLabel(":");
	private JTextField beginMinute;
	JLabel descriptionLabel = new JLabel("DESCRIPTIONS:");
	private JTextField descriptionTxt;
	private final JLabel locationLabel = new JLabel("LOCATION:");
	private final JTextField locationTxt = new JTextField();
	private final JLabel participantLabel = new JLabel("NUMBER OF PARTICIPANT(s):");
	
	private ArrayList<JTextField> participantDataText;
	private String[] digits = {"0", "1", "2", "3", "4", "5"};
	JComboBox<String> participantDropdown = new JComboBox(digits);
	private ArrayList<String> participantEmail = new ArrayList<>();
	//private final JTextField textField = new JTextField();
	
	
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
					window.frame.setTitle("Add New Event");
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

	/**
	 * This method setVisible make the frame appear on the screen 
	 * when the parameter has the 'true' value  
	 */
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
		frame.setTitle("Add New Event");
		frame.setSize(520, 700);
		
		
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
		
		
		durationLabel.setBounds(28, 144, 74, 14);
		frame.getContentPane().add(durationLabel);
		
		
		durationTxtField.setBounds(131, 141, 46, 20);
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
		
		
		durationLabel1.setBounds(187, 144, 46, 14);
		frame.getContentPane().add(durationLabel1);
		
		
		reminderLabel.setBounds(28, 181, 74, 14);
		frame.getContentPane().add(reminderLabel);
		
		
		
		reminderDropdown.setBounds(131, 179, 102, 18);
		frame.getContentPane().add(reminderDropdown);
		
		
		saveBtn.setBounds(330, 575, 89, 23);
		frame.getContentPane().add(saveBtn);
		
		
		
		
		cancelBtn.setBounds(68, 575, 89, 23);
		frame.getContentPane().add(cancelBtn);
		
		
		beginTimeLabel.setBounds(28, 105, 74, 14);
		frame.getContentPane().add(beginTimeLabel);
		
		beginHour = new JTextField();
		beginHour.setBounds(131, 107, 26, 20);
		frame.getContentPane().add(beginHour);
		beginHour.setColumns(10);
		
		beginHour.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if(((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE)) {
					e.consume();
					// if the input is not a number, it will be ignored.
				}
			}
		});
		
		
		colonLabel.setBounds(159, 113, 14, 14);
		frame.getContentPane().add(colonLabel);
		
		beginMinute = new JTextField();
		beginMinute.setColumns(10);
		beginMinute.setBounds(167, 107, 26, 20);
		frame.getContentPane().add(beginMinute);
		
		
		beginMinute.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if(((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE)) {
					e.consume();
					// if the input is not a number, it will be ignored.
				}
			}
		});
		
		
		
		descriptionLabel.setBounds(28, 226, 93, 14);
		frame.getContentPane().add(descriptionLabel);
		
		descriptionTxt = new JTextField();
		descriptionTxt.setBounds(131, 223, 189, 61);
		frame.getContentPane().add(descriptionTxt);
		descriptionTxt.setColumns(10);
		
		locationLabel.setBounds(28, 303, 81, 14);
		frame.getContentPane().add(locationLabel);
		
		
		locationTxt.setBounds(131, 300, 189, 38);
		locationTxt.setColumns(10);
		frame.getContentPane().add(locationTxt);
		
		
		participantLabel.setBounds(28, 360, 165, 28);
		frame.getContentPane().add(participantLabel);
		
		
		participantDropdown.setBounds(246, 365, 74, 18);
		frame.getContentPane().add(participantDropdown);
		participantDropdown.setSelectedIndex(0);
		
		participantDropdown.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int participantNr = Integer.parseInt((String)participantDropdown.getSelectedItem());
				for(int i=0; i<participantNr; i++) {
					participantDataText.get(i).setVisible(true);
				}
				for(int i=participantNr; i<5; i++) {
					participantDataText.get(i).setVisible(false);
				}
			}
			
		});
		
		/*textField.setBounds(96, 404, 196, 20);
		textField.setColumns(10);
		frame.getContentPane().add(textField);*/
		
		participantDataText = new ArrayList<>();
		int i = 0;
		// initialize 5 fields for participant email addresses, invisible until specified
		for(int k=0; k<5; k++) {
			JTextField pD = new JTextField();
			pD.setBounds(96, 404 + i, 196, 20);
			pD.setVisible(false);
			participantDataText.add(pD);
			frame.getContentPane().add(pD);
			i += 25;
		}
		
		/**
		 * Click the CANCEl button to terminate the process and go back to the previous window.
		 */
		cancelBtn.addActionListener((ActionListener) new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		
		
		
		
		
		/**
		 * After clicked the SAVE button, 
		 * all the entered information will be saved 
		 * in the database along with the username, so that they can be fetched later. 
		 */
		saveBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				String username = usernameLabel.getText();
				String eDate = date.getText();
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				java.util.Date javaEventDate = null;
				try {
					javaEventDate = formatter.parse(eDate);
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				java.sql.Date sqlEventDate = new java.sql.Date(javaEventDate.getTime());
				String eventName = eventTxtField.getText();
				int eventDuration = Integer.parseInt(durationTxtField.getText());
				String eventReminder = (String) reminderDropdown.getSelectedItem();
				String beginHourValue = beginHour.getText();
				String beginMinuteValue = beginMinute.getText();
				String beginTimeValue = beginHourValue + ":" + beginMinuteValue;
				
				ResultSet result = null;
				Connection connection = null;
				
				PreparedStatement statement = null;
				
				try {
					connection = JDBCMySQLConnection.getConnection();
					
					//statement1 = connection.prepareStatement("")
					
					statement = connection.prepareStatement(
							"INSERT INTO eventdata(username, eventdate, activity, begintime, eventduration, reminder)"
							+ "VALUES(?, ?, ?, ?, ?, ?)");
					statement.setString(1,  username);
					statement.setDate(2, sqlEventDate);
					statement.setString(3, eventName);
					statement.setString(4, beginTimeValue);
					statement.setInt(5, eventDuration);
					statement.setString(6, eventReminder);
					
					statement.executeUpdate();
					
					JOptionPane.showMessageDialog(frame, "Event is successfully saved!!");
				}catch(SQLException sqlException) {
					sqlException.printStackTrace();
				}
				
				frame.dispose();;
				
			}
			
		});
		
		
		
	}
}
