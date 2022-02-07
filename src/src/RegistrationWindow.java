//References: https://www.javaguides.net/2019/07/registration-form-using-java-swing-jdbc-mysql-example-tutorial.html?m=1

package src;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

/**
 * 
 * @author selma
 *
 */
public class RegistrationWindow {

	private JFrame frame;
	
	private JPanel contentPane;
	private JLabel label, ID, Username;
	
    public String dates[]
	        = {"-", "1", "2", "3", "4", "5",
	            "6", "7", "8", "9", "10",
	            "11", "12", "13", "14", "15",
	            "16", "17", "18", "19", "20",
	            "21", "22", "23", "24", "25",
	            "26", "27", "28", "29", "30",
	            "31" };

    public String month[] 
    		= {"-", "1", "2", "3", "4",
    	            "5", "6", "7", "8",
    	            "9", "10", "11", "12" };
    
	public String year[]
	        = { "-", "1980", "1981", "1982", "1983", 
	        	"1984", "1985", "1986", "1987", 
	        	"1988", "1989", "1990", "1991", 
	        	"1992", "1993", "1994", "1995", 
	        	"1996", "1997", "1998","1999", 
	        	"2000", "2001", "2002","2003", 
	        	"2004", "2005", "2006","2007",
	        	"2008", "2009", "2010","2011", 
	        	"2012", "2013", "2014","2015", 
	        	"2016", "2017", "2018","2019", 
	        	"2020", "2021", "2022" };

	/**
	 * Launch the application.
	 */
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RegistrationWindow window = new RegistrationWindow();
					window.frame.setVisible(true);
					window.frame.setTitle("Registration");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * Encryption
	 * @param password
	 * @return encrypted value
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
	 * Create the application.
	 */
	
	public RegistrationWindow() {
		initialize();
	}
	
	public void setVisible(boolean visible){
	    frame.setVisible(visible);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setSize(550, 650);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        frame.setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel reg = new JLabel("User registration");
		reg.setFont(new Font("Thoma", Font.BOLD, 25));
		reg.setBounds(190, 1, 273, 93);
		contentPane.add(reg);
		
		String gender [] = {"Select one", "Male", "Female", "Other"};
        JLabel genderLabel = new JLabel("Gender:");
		genderLabel.setFont(new Font("Thoma", Font.BOLD, 15));
		genderLabel.setBounds(20, 70, 80, 70);
		contentPane.add(genderLabel);
		
		String title [] = {"Select one", "Mr.", "Mrs."}; 
	    JLabel titleLabel = new JLabel("Title:");
		titleLabel.setFont(new Font("Thoma", Font.BOLD, 15));
		titleLabel.setBounds(20, 90, 273, 93);
		contentPane.add(titleLabel);
			
		JLabel firstName = new JLabel("First Name:");
		firstName.setFont(new Font("Thoma", Font.BOLD, 15));
		firstName.setBounds(20, 120, 273, 93);
		contentPane.add(firstName);
				
		JLabel lastNameLabel = new JLabel("Last Name:");
		lastNameLabel.setFont(new Font("Thoma", Font.BOLD, 15));
		
		lastNameLabel.setBounds(20, 150, 273, 93);
		contentPane.add(lastNameLabel);
					
		
		JLabel date = new JLabel("Date of birth:");
		date.setFont(new Font("Thoma", Font.BOLD, 15));
		date.setBounds(20, 180, 273, 93);
		contentPane.add(date);
						
		JLabel adress = new JLabel("Adress:");
		adress.setFont(new Font("Thoma", Font.BOLD, 15));
		adress.setBounds(20, 210, 273, 93);
		contentPane.add(adress);
	
		JLabel plz = new JLabel("PLZ:");
		plz.setFont(new Font("Thoma", Font.BOLD, 15));
		plz.setBounds(20, 240, 273, 93);
		contentPane.add(plz);
				
		JLabel city = new JLabel("City:");
		city.setFont(new Font("Thoma", Font.BOLD, 15));
		city.setBounds(20, 270, 273, 93);
		contentPane.add(city);
		
		JLabel mobnumber = new JLabel("Mobile:");
		mobnumber.setFont(new Font("Thoma", Font.BOLD, 15));
		mobnumber.setBounds(20, 300, 273, 93);
		contentPane.add(mobnumber);
		
		JLabel email = new JLabel("Email:");
		email.setFont(new Font("Thoma", Font.BOLD, 15));
		email.setBounds(20, 330, 273, 93);
		contentPane.add(email);
		
		JLabel userName = new JLabel("Username:");
		userName.setFont(new Font("Thoma", Font.BOLD, 15));
		userName.setBounds(20, 360, 273, 93);
		contentPane.add(userName);
						
		JLabel pass = new JLabel("Password:");
		pass.setFont(new Font("Thoma", Font.BOLD, 15));
		pass.setBounds(20, 390, 273, 93);
		contentPane.add(pass);
        
		JComboBox genderComboBox = new JComboBox(gender);
		genderComboBox.setBounds(180, 95, 200, 18);
		contentPane.add(genderComboBox);
		
		JComboBox titleComboBox = new JComboBox(title);
		titleComboBox.setBounds(180, 125, 200, 18);
		contentPane.add(titleComboBox);
		
		JTextField tffirstName = new JTextField();
		tffirstName.setBounds(180, 158, 200, 20);
        tffirstName.setBackground(Color.WHITE);
		tffirstName.setForeground(Color.BLACK);
		contentPane.add(tffirstName);
		
		JTextField tflastName = new JTextField();
		tflastName.setBounds(180, 188, 200, 20);
        tflastName.setBackground(Color.WHITE);
		tflastName.setForeground(Color.BLACK);
		contentPane.add(tflastName);
		
		JComboBox dateComboBox = new JComboBox(dates);
		dateComboBox.setBounds(180, 220, 40, 18);
		contentPane.add(dateComboBox);
		
		JComboBox monthComboBox = new JComboBox(month);
		monthComboBox.setBounds(240, 220, 60, 18);
		contentPane.add(monthComboBox);
		
		JComboBox yearComboBox = new JComboBox(year);
		yearComboBox.setBounds(320, 220, 60, 18);
		contentPane.add(yearComboBox);
		
		JTextField tfadress = new JTextField();
		tfadress.setBounds(180, 248, 200, 20);
        tfadress.setBackground(Color.WHITE);
		tfadress.setForeground(Color.BLACK);
		contentPane.add(tfadress);
		
		JTextField tfplz = new JTextField();
		tfplz.setBounds(180, 278, 200, 20);
        tfplz.setBackground(Color.WHITE);
		tfplz.setForeground(Color.BLACK);
		contentPane.add(tfplz);
		
		JTextField tfcity = new JTextField();
		tfcity.setBounds(180, 308, 200, 20);
        tfcity.setBackground(Color.WHITE);
		tfcity.setForeground(Color.BLACK);
		contentPane.add(tfcity);
		
		JTextField tfmobNumber = new JTextField();
		tfmobNumber.setBounds(180, 338, 200, 20);
        tfmobNumber.setBackground(Color.WHITE);
		tfmobNumber.setForeground(Color.BLACK);
		contentPane.add(tfmobNumber);
		
		JTextField tfemail = new JTextField();
		tfemail.setBounds(180, 368, 200, 20);
        tfemail.setBackground(Color.WHITE);
		tfemail.setForeground(Color.BLACK);
		contentPane.add(tfemail);
		
		JTextField tfuserName = new JTextField();
		tfuserName.setBounds(180, 398, 200, 20);
        tfuserName.setBackground(Color.WHITE);
		tfuserName.setForeground(Color.BLACK);
		contentPane.add(tfuserName);
		
		JPasswordField tfpass = new JPasswordField();
		tfpass.setBounds(180, 428, 200, 20);
        tfpass.setBackground(Color.WHITE);
		tfpass.setForeground(Color.BLACK);
		contentPane.add(tfpass);
	
		
	/**
	 * Return button was created, 
	 * with which is redirected to login window
	 */
		
		JButton returnB = new JButton("Return");
        returnB.setFont(new Font("Tahoma", Font.PLAIN, 10));
        returnB.setBounds(200, 510, 115, 25);
        contentPane.add(returnB);
        returnB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	//should go to login window
            	frame.dispose();
            	LoginWindow loginWindow = new LoginWindow();
            	loginWindow.setVisible(true);
            }
        });
        
        /**
         * Register button is created
         * After which components are set and location with setBounds() method
         * Button is added to the JPanel
         * The ActionListener class is created,
         * where should also implement the actionPerformed(ActionEvent e) method
         * Password was encrypted
         * In this method the connection to the database is created
         * After the user has entered all the required data in the registration window,
         * this data is stored in the database
         * With the if else branches the whole error messages are output,
         * where they also occur 
         */ 
       
        JButton registerB = new JButton("Register");
        registerB.setBounds(350, 510, 115, 25);  
        registerB.setFont(new Font("Tahoma", Font.PLAIN, 10));
        contentPane.add(registerB);
        
        registerB.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String username = tfuserName.getText();
				String gender = genderComboBox.getSelectedItem().toString();
				String title = titleComboBox.getSelectedItem().toString();
				String firstName = tffirstName.getText();
				String lastName = tflastName.getText();
				String day = dateComboBox.getSelectedItem().toString();
				String month = monthComboBox.getSelectedItem().toString();
				String year = yearComboBox.getSelectedItem().toString();
				String email = tfemail.getText();
				String dateOfBirth = day + "-" + month + "-" + year;
				String adress = tfadress.getText();
				String plz = tfplz.getText();
				String city = tfcity.getText();
				String mobNummer = tfmobNumber.getText();
				
				String password = tfpass.getText();
				//String repeatPassword = tfrepeatPass.getText();
				
				String encryptedPass = encryptPass(password);
				
				SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
				java.util.Date javaBirthday = null;
				try {
					javaBirthday = formatter.parse(dateOfBirth);
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				java.sql.Date sqlBirthday = new java.sql.Date(javaBirthday.getTime());
				
				
				ResultSet result = null;
				ResultSet checkResult = null;
				
				Connection connection = null;
				
				PreparedStatement checkStatement = null;
				PreparedStatement saveStatement = null;
				
				try {
					connection = JDBCMySQLConnection.getConnection();
					
					checkStatement = connection.prepareStatement("SELECT username FROM logindata WHERE username = '" + username + "'");
					checkResult = checkStatement.executeQuery();
					
					if(checkResult.next()) {
						JOptionPane.showMessageDialog(frame, "This username is already exists. Please try with other name!");
						
					}
					else {
						try {
							saveStatement = connection.prepareStatement("INSERT INTO logindata(username, title, firstname, lastname, gender, email, password, birthday, address, PLZ, city, mobile)"
									+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
							saveStatement.setString(1, username);
							saveStatement.setString(2, title);
							saveStatement.setString(3, firstName);
							saveStatement.setString(4, lastName);
							saveStatement.setString(5, gender);
							saveStatement.setString(6, email);
							saveStatement.setString(7, encryptedPass);
							saveStatement.setDate(8, sqlBirthday);
							saveStatement.setString(9, adress);
							saveStatement.setString(10, plz);
							saveStatement.setString(11, city);
							saveStatement.setString(12, mobNummer);
							
							if(genderComboBox.getSelectedItem().toString() == "Select one" || 
									titleComboBox.getSelectedItem().toString() == "Select one" || 
									tffirstName.getText().isEmpty() || 
									tflastName.getText().isEmpty() || 
									dateComboBox.getSelectedItem().toString() == "-" || 
									monthComboBox.getSelectedItem().toString() == "-" || 
									yearComboBox.getSelectedItem().toString() == "-" || 
									tfadress.getText().isEmpty() || 
									tfplz.getText().isEmpty() || 
									tfcity.getText().isEmpty() || 
									tfmobNumber.getText().isEmpty() || 
									tfemail.getText().isEmpty() || 
									tfuserName.getText().isEmpty() || 
									tfpass.getText().isEmpty()  
									//tfrepeatPass.getText().isEmpty()
									) {
								JOptionPane.showMessageDialog(frame, "Some fields are empty! Please fill out all fields.");
							}
							else {
								saveStatement.executeUpdate();
								JOptionPane.showMessageDialog(frame, "Data registered successfully!!");
							}
							
						}catch(SQLException sqlException){
							sqlException.printStackTrace();
						}
					}
				}catch(SQLException sqlException){
					sqlException.printStackTrace();
				}
				
			}
        	
        });
        
	}

}
