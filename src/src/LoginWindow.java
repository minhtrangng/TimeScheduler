// References: https://www.javaguides.net/2019/07/login-application-using-java-swing-jdbc-mysql-example-tutorial.html

package src;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

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
		
		
		// LOGIN BUTTON
		loginBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String userText = userNameTextField.getText();
				String pwText = passwordField.getText();
				
				ResultSet result = null;
				Connection connection = null;
				//Statement statement = null;
				
				PreparedStatement statement = null;
				
				try {
					connection = JDBCMySQLConnection.getConnection();
					statement = connection.prepareStatement("SELECT * FROM logindata WHERE username=? and password=?");
					statement.setString(1, userText);
					statement.setString(2, pwText);
					result = statement.executeQuery();
					
					if(result.next()) {
						dispose();
						JOptionPane.showMessageDialog(frame,  "Login Sucessful!!");
						frame.setVisible(false);
						DatePickerTesting datePicker= new DatePickerTesting();
						datePicker.setUserName(userText);
						datePicker.setVisible(true);
					}
					else {
						JOptionPane.showMessageDialog(frame, "Invalid Username or Password!!");
					}
				} catch(SQLException sqlException) {
					sqlException.printStackTrace();
				}
				
				
			}
			}
		);
		
		
		// REGISTER BUTTON
		registerBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String userNameText = userNameTextField.getText();
				String pwText = passwordField.getText();
				
				//ResultSet result = null;
				Connection connection = null;
				PreparedStatement statement = null;
				
				try {
					connection = JDBCMySQLConnection.getConnection();
					statement = connection.prepareStatement("INSERT INTO logindata(username, password)"
							+ "VALUES (?,?)");
					statement.setString(1, userNameText);
					statement.setString(2, pwText);
					
					statement.executeUpdate();
					
					JOptionPane.showMessageDialog(frame, "Registerd Successful!!\nNow you can try to login!!");
				} catch(SQLException sqlException) {
					sqlException.printStackTrace();
				}
			}
		}
		);
		
		
	}
	
	
}
