// References: https://www.javaguides.net/2019/07/login-application-using-java-swing-jdbc-mysql-example-tutorial.html

package src;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.sql.*;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

import javax.mail.MessagingException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 * This class allow user to create a new account and use the registered information to login.
 * 
 * @author minhtrang &amp;&amp; henri d.
 *
 */

public class LoginWindow extends JFrame {

	private JFrame frame;
	private JTextField userNameTextField;
	private JPasswordField passwordField;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginWindow window = new LoginWindow();
					checkReminders();
					window.frame.setVisible(true);
					window.frame.setTitle("Time Scheduler");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		JDBCMySQLConnection dbConnection = new JDBCMySQLConnection();
		
	}
	
	/**
	 * The password needs to be securely saved and not as plaintext
	 * <p>
	 * This method will hash the entered password and 
	 * return the password as ciphertext in form of String
	 * @param password the entered password
	 * @return the hashed password
	 */
	
	public String encryptPass (String password) {
		try {
			//Retrieve instance of the encryptor of SHA-256
			MessageDigest digestor = MessageDigest.getInstance("SHA-256");
			
			// Retrieve bytes to encrypt
			byte[] encodedHash = digestor.digest(password.getBytes(StandardCharsets.UTF_8));
			StringBuilder encryptionValue = new StringBuilder(2 * encodedHash.length);
			
			// Perform encryption
			for(int i = 0; i < encodedHash.length; i++) {
				String hexVal = Integer.toHexString(0xff & encodedHash[i]);
				if(hexVal.length() == 1) {
					encryptionValue.append('0');
				}
				encryptionValue.append(hexVal);
			}
			
			// Return encrypted Value
			return encryptionValue.toString();
			
		}catch (Exception ex) {
			return ex.getMessage();
		}
	}
	
	/**
	 * This method setVisible make the frame appear on the screen 
	 * when the parameter has the 'true' value  
	 */
	public void setVisible(boolean visible){
	    frame.setVisible(visible);
	}
	
	/**
	 * Create the application.
	 */
	public LoginWindow() {
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
		//frame.getContentPane();
		
		
		JLabel userNameLabel = new JLabel("Username:");
		userNameLabel.setBounds(114, 30, 81, 14);
		frame.getContentPane().add(userNameLabel);
		//lblNewLabel.setText("Hello World!!");
		
		userNameTextField = new JTextField();
		userNameTextField.setBounds(114, 46, 140, 23);
		frame.getContentPane().add(userNameTextField);
		userNameTextField.setColumns(10);
		
		JLabel pwLabel = new JLabel("Password:");
		pwLabel.setBounds(114, 80, 81, 14);
		frame.getContentPane().add(pwLabel);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(114, 95, 140, 23);
		frame.getContentPane().add(passwordField);
		
		

		JButton loginBtn = new JButton("Login");
		loginBtn.setBounds(114, 140, 180, 32);
		frame.getContentPane().add(loginBtn);
		
		JButton registerBtn = new JButton("Register");
		registerBtn.setBounds(114, 194, 180, 32);
		frame.getContentPane().add(registerBtn);
		
		
		/**
		 * After the user cliked the login button, the compare process will be executed behind.
		 * <p>
		 * The typed-in username and password will be taken out and compare with the data in the database.
		 * The password was saved as ciphertext, because of that the entered password will be first hashed and then compared.
		 * <p>
		 * We will receive the same result if we hash two identical texts. 
		 * That makes hash process easier to use in compare to the other encrypt procedures
		 * <p>
		 * It is first checked if admin is trying to log in. 
		 * The admin will be redirected to the different window comparing with the normal users
		 * <p>
		 * If the user is not admin, the entered information will be verified.
		 * After examining, a dialogue window will appear with the corresponding annoucement
		 */
		// LOGIN BUTTON
		loginBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String userText = userNameTextField.getText();
				String pwText = passwordField.getText();
				
				// encrypt the entered password and then compare it to the one, which is stored in the database
				String encryptedPass = encryptPass(pwText);
				
				ResultSet result = null;
				Connection connection = null;
				//Statement statement = null;
				
				PreparedStatement statement = null;
				
				try {
					connection = JDBCMySQLConnection.getConnection();
					statement = connection.prepareStatement("SELECT * FROM logindata WHERE username=? and password=?");
					statement.setString(1, userText);
					statement.setString(2, encryptedPass);
					result = statement.executeQuery();
					
					if(result.next()) {
						if(userText.equalsIgnoreCase("admin")) {
							// If username == "admin" => go to window for admin
							dispose();
							frame.setVisible(false);
							AdminWindow adminWindow = new AdminWindow();
							adminWindow.setVisible(true);
						}
						else {
							/**
							 * After successfully loged in, the login window will disappear 
							 * and the new window (Date Picker Window) will be visible 
							 */
							dispose();
							JOptionPane.showMessageDialog(frame,  "Login Sucessful!!");
							frame.setVisible(false);
							DatePickerWindow datePicker= new DatePickerWindow();
							datePicker.setUserName(userText);
							datePicker.setVisible(true);
						}
						
					}
					else {
						/**
						 * Otherwise, the user will be announced that username or password is invalid
						 */
						JOptionPane.showMessageDialog(frame, "Invalid Username or Password!!");
					}
				} catch(SQLException sqlException) {
					sqlException.printStackTrace();
				}
				
				
			}
			}
		);
		
		/**
		 * In case the user does not have a account yet, he/she must create one.
		 * <p>
		 * Register button will lead to a new window (Registration Window), 
		 * where the user must type in couples information, for example: First name, last name, address,
		 * username, password, ...  
		 */
		// REGISTER BUTTON
		registerBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RegistrationWindow registration = new RegistrationWindow();
				registration.setVisible(true);
				
			}
		}
		);
		
		
	}
	
	
	/**
	 * 	  checkReminders() is supposed to look through all events in the event database and check if their reminder email
	 * 	  is due. This might happen too late depending on how often some user logs in, but is a workaround around some
	 * 	  problems we faced.
	 * 	  For every event, if its not scheduled for the next day, it checks if the duration between localTime.now() and
	 * 	  the scheduled beginTime is <= the desired reminder duration. If yes, the reminder email is sent.
	 *
	 * 	 @author henri d.
	 */
	private static void checkReminders() {
		Connection con = null;
		Statement stmt = null;
		DateLabelFormatter dlf = new DateLabelFormatter();

		//DateTimeFormatter dtfTime = DateTimeFormatter.ofPattern("HHmmss");
		LocalTime time = LocalTime.now();
		LocalDate date = LocalDate.now();
		int status = 0;

		try{
			con = JDBCMySQLConnection.getConnection();
			stmt = con.prepareStatement("SELECT * FROM eventdata WHERE emailstatus = 0");

			ResultSet rs = stmt.executeQuery("SELECT * FROM eventdata WHERE emailstatus = 0");
			while(rs.next()) {
				status = 0;
				LocalDate tempDate = rs.getDate("eventdate").toLocalDate();
				String beginTimeString = rs.getString("begintime");
				LocalTime tempTime = dlf.stringToTime(beginTimeString);


				String reminder = rs.getString("reminder");
				
				if(tempDate.isBefore(date)) {
					rs.updateInt("emailstatus", 1);
					continue;
				}

				// When the reminder is ready to be sent, set status to 1
				// If reminder is 7 days: event is on 7 days or less and hasnt happened yet
				if(reminder.equals("1 week") && ChronoUnit.DAYS.between(date, tempDate) <= 7 && ChronoUnit.DAYS.between(date, tempDate) >= 0) {
					status = 1;
				}
				// If reminder is 3 days: event is on 3 days or less and hasnt happened yet
				else if(reminder.equals("3 days") && ChronoUnit.DAYS.between(date, tempDate) <= 3 && ChronoUnit.DAYS.between(date, tempDate) >= 0) {
					status = 1;
					
				}
				// If reminder is 1 hour: event is on this day and happens in 1 hour or less and hasnt happened yet
				else if(reminder.equals("1 hour") && date.equals(tempDate) && ChronoUnit.MINUTES.between(time, tempTime) <= 60 && ChronoUnit.MINUTES.between(time, tempTime) >= 0) {
					status = 1;
				}
				// If reminder is 10 min: see above
				else if(reminder.equals("10 minutes") && date.equals(tempDate) && ChronoUnit.MINUTES.between(time, tempTime) <= 10 && ChronoUnit.MINUTES.between(time, tempTime) >= 0) {
					status = 1;
				}

				if(status == 1) {
					// now the email can be send as a reminder, unfortunately it can be too late
					// String date, String username, String eventName, String beginTime, int duration, JTextArea description, String location, ArrayList<String> emails
					//String eDate = dlf.valueToString(tempDate);
					String eDate = tempDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
					String username = rs.getString("username");
					String eventName = rs.getString("activity");
					String beginTime = rs.getString("begintime");
					int d = rs.getInt("eventduration");
					String description = rs.getString("description");
					String location = rs.getString("location");
					String p1 = rs.getString("participant1");
					String p2= rs.getString("participant2");
					String p3 = rs.getString("participant3");
					String p4 = rs.getString("participant4");
					String p5 = rs.getString("participant5");
					ArrayList<String> participants = new ArrayList<>();
					participants.add(p1);
					participants.add(p2);
					participants.add(p3);
					participants.add(p4);
					participants.add(p5);

					EmailSender emailSender = new EmailSender();
					emailSender.setContentLate(eDate, username, eventName, beginTime, d, description, location, participants);
					emailSender.sendMail();
					
					rs.updateInt("emailstatus", 1);
				}
			}
		}
		catch(SQLException sqlException) {
			sqlException.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
	
	
}
