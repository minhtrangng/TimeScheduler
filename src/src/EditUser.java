package src;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

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
 * @author selma (with some idea from minhtrang)
 */

public class EditUser extends JFrame{

	private JFrame frame;
	private JPanel contentPane;
	
	public String dates[]
	        = {"1", "2", "3", "4", "5",
	            "6", "7", "8", "9", "10",
	            "11", "12", "13", "14", "15",
	            "16", "17", "18", "19", "20",
	            "21", "22", "23", "24", "25",
	            "26", "27", "28", "29", "30",
	            "31" };
	
	
	public String month[] 
    		= { "1", "2", "3", "4",
    	            "5", "6", "7", "8",
    	            "9", "10", "11", "12" };
	
	
	public String year[]
	        = {"1980", "1981", "1982", "1983", 
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
	 * Button search is created to search for the username when edited 
	 */
	
	JLabel whichUserLabel = new JLabel("Which user do you want to change?");
	JTextField whichUserTxt;
	JButton searchBtn = new JButton("SEARCH");
	
	String gender [] = {"Select one", "Male", "Female", "Other"};
    JLabel genderLabel = new JLabel("Gender:");
	JComboBox genderComboBox = new JComboBox(gender);
	String title [] = {"Select one", "Mr.", "Mrs."}; 
    JLabel titleLabel = new JLabel("Title:");
    JComboBox titleComboBox = new JComboBox(title);
	JTextField tffirstName = new JTextField();
	JTextField tflastName = new JTextField();
	JComboBox dateComboBox = new JComboBox(dates);
	JComboBox monthComboBox = new JComboBox(month);
	JComboBox yearComboBox = new JComboBox(year);
	JTextField tfadress = new JTextField();
	JTextField tfplz = new JTextField();
	JTextField tfcity = new JTextField();
	JTextField tfmobNumber = new JTextField();
	JTextField tfemail = new JTextField();
	JTextField tfuserName = new JTextField();
	JPasswordField tfpass = new JPasswordField();
	
	
	
	
	
	/**
	 * Launch the application.
	 */
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EditUser window = new EditUser();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void setVisible(boolean visible){
	    frame.setVisible(visible);
	}
	
	/**
	 * Create the application.
	 */
	
	public EditUser() {
		initialize();
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
		reg.setBounds(180, 0, 224, 48);
		contentPane.add(reg);
		
		// Searching for the desired user
		
        whichUserLabel.setBounds(20, 52, 224, 14);
        contentPane.add(whichUserLabel);
        
        
        whichUserTxt = new JTextField();
        whichUserTxt.setBounds(265, 49, 96, 20);
        contentPane.add(whichUserTxt);
        whichUserTxt.setColumns(10);
        
        
        searchBtn.setBounds(383, 48, 89, 23);
        contentPane.add(searchBtn);
		
	/**
         * After the user has been entered, 
         * the search button is pressed to search for the user
         * If the user is in the database, 
         * the textfields are filled with their data 
         * and the data is modified 
         */
        
        searchBtn.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		String searchingUser = whichUserTxt.getText();
        		
        		if(searchingUser == null) {
        			JOptionPane.showMessageDialog(frame,  "No username was entered! Please type in a name!");
        		}
        		else {
        			Connection connection = null;
        			ResultSet result = null;
        			PreparedStatement statement = null;
        			
        			try {
        				connection = JDBCMySQLConnection.getConnection();
        				statement = connection.prepareStatement("SELECT * FROM logindata WHERE username = '" + searchingUser + "'");
        				result = statement.executeQuery();
        				
        				if(result.next()) {
        					String usrName = result.getString("username");
        					String usrTitel = result.getString("title");
        					String usrFirstname = result.getString("firstname");
        					String usrLastname = result.getString("lastname");
        					String usrGender = result.getString("gender");
        					String usrEmail = result.getString("email");
        					java.sql.Date usrBirthday = result.getDate("birthday");
        					System.out.println(usrBirthday);
        					Calendar cal = Calendar.getInstance();
        					cal.setTime(usrBirthday);
        					int getDay = cal.get(Calendar.DAY_OF_MONTH);
        					int getMonth = cal.get(Calendar.MONTH);
        					int getYear = cal.get(Calendar.YEAR);
        					String usrDay = String.valueOf(getDay);
        					String usrMonth = String.valueOf(getMonth + 1);
        					String usrYear = String.valueOf(getYear);
        					String usrAddress = result.getString("address");
        					String usrPlz = result.getString("plz");
        					String usrCity = result.getString("city");
        					String usrMobile = result.getString("mobile");
        					
        					// Fill the formular with the data from database
        					genderComboBox.getModel().setSelectedItem(usrGender);
        					titleComboBox.getModel().setSelectedItem(usrTitel);
        					dateComboBox.getModel().setSelectedItem(usrDay);
        					monthComboBox.getModel().setSelectedItem(usrMonth);
        					yearComboBox.getModel().setSelectedItem(usrYear);
        					tffirstName.setText(usrFirstname);
        					tflastName.setText(usrLastname);
        					tfadress.setText(usrAddress);
        					tfplz.setText(usrPlz);
        					tfcity.setText(usrCity);
        					tfmobNumber.setText(usrMobile);
        					tfemail.setText(usrEmail);
        					tfuserName.setText(usrName);
        					
        				}
        			} catch(SQLException sqlException) {
    					sqlException.printStackTrace();
    				}
        		}
        	}
        });
		
		
		
		genderLabel.setFont(new Font("Thoma", Font.BOLD, 15));
		genderLabel.setBounds(20, 70, 80, 70);
		contentPane.add(genderLabel);
		
		
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
		
		
		genderComboBox.setBounds(180, 95, 200, 18);
		contentPane.add(genderComboBox);
		
		
		titleComboBox.setBounds(180, 125, 200, 18);
		contentPane.add(titleComboBox);
		
		tffirstName.setBounds(180, 158, 200, 20);
        tffirstName.setBackground(Color.WHITE);
		tffirstName.setForeground(Color.BLACK);
		contentPane.add(tffirstName);
		
		
		tflastName.setBounds(180, 188, 200, 20);
        tflastName.setBackground(Color.WHITE);
		tflastName.setForeground(Color.BLACK);
		contentPane.add(tflastName);
		
		
		dateComboBox.setBounds(180, 220, 40, 18);
		contentPane.add(dateComboBox);
		
		
		monthComboBox.setBounds(240, 220, 60, 18);
		contentPane.add(monthComboBox);
		
		
		yearComboBox.setBounds(320, 220, 60, 18);
		contentPane.add(yearComboBox);
		
		
		tfadress.setBounds(180, 248, 200, 20);
        tfadress.setBackground(Color.WHITE);
		tfadress.setForeground(Color.BLACK);
		contentPane.add(tfadress);
		
		
		tfplz.setBounds(180, 278, 200, 20);
        tfplz.setBackground(Color.WHITE);
		tfplz.setForeground(Color.BLACK);
		contentPane.add(tfplz);
		
		
		tfcity.setBounds(180, 308, 200, 20);
        tfcity.setBackground(Color.WHITE);
		tfcity.setForeground(Color.BLACK);
		contentPane.add(tfcity);
		
		
		tfmobNumber.setBounds(180, 338, 200, 20);
        tfmobNumber.setBackground(Color.WHITE);
		tfmobNumber.setForeground(Color.BLACK);
		contentPane.add(tfmobNumber);
		
		
		tfemail.setBounds(180, 368, 200, 20);
        tfemail.setBackground(Color.WHITE);
		tfemail.setForeground(Color.BLACK);
		contentPane.add(tfemail);
		
		
		tfuserName.setBounds(180, 398, 200, 20);
        tfuserName.setBackground(Color.WHITE);
		tfuserName.setForeground(Color.BLACK);
		contentPane.add(tfuserName);
		
		
		tfpass.setBounds(180, 428, 200, 20);
        tfpass.setBackground(Color.WHITE);
		tfpass.setForeground(Color.BLACK);
		contentPane.add(tfpass);
		
	/**
	 * The three(Back, Refresh and Update) buttons are created
	 * Back button=>goes back to admin panel window
	 * Refresh button=>the table from the database will be refreshed after the update,
	 * that is after what has been changed in the records 
	 * the class DatabaseTableRegistration will be called
	 * Update button=>establish connection to the database 
	 * so that records can be changed
	 * After the user has been successfully updated
	 * "User is updated" is displayed
	 */
		
		JButton backB = new JButton("Back");
        backB.setFont(new Font("Tahoma", Font.PLAIN, 10));
        backB.setBounds(50, 510, 115, 25);
        contentPane.add(backB);
        
        backB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	dispose();
            	frame.setVisible(false);
            }
        });
        
        JButton refreshB = new JButton("Refresh");
        refreshB.setFont(new Font("Tahoma", Font.PLAIN, 10));
        refreshB.setBounds(350, 510, 115, 25);
        contentPane.add(refreshB);
        
        refreshB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

        		DatabaseTableRegistration frame = new DatabaseTableRegistration();
        		//frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        		frame.pack();
        		frame.setVisible(true);
            }
        });
        
        JButton updateB = new JButton("Update");
        updateB.setFont(new Font("Tahoma", Font.PLAIN, 10));
        updateB.setBounds(200, 510, 115, 25);
        contentPane.add(updateB);
        
        updateB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	String usrDay = dateComboBox.getSelectedItem().toString();
            	String usrMonth = monthComboBox.getSelectedItem().toString();
            	String usrYear = yearComboBox.getSelectedItem().toString();
            	
            	String dateOfBirth = usrDay + "-" + usrMonth + "-" + usrYear;
            	SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            	java.util.Date javaBirthday = null;
            	try {
					javaBirthday = formatter.parse(dateOfBirth);
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            	java.sql.Date sqlBirthday = new java.sql.Date(javaBirthday.getTime());
            	
            	
            	Connection connection = null;
            	ResultSet result = null;
            	PreparedStatement statement =  null;
            	
            	try {
            		connection = JDBCMySQLConnection.getConnection();
                    statement = connection.prepareStatement("UPDATE logindata SET Gender = '" 
            		+genderComboBox.getSelectedItem().toString() 
            		+"', title = '" 
            		+titleComboBox.getSelectedItem().toString() 
            		+"', firstname = '"
            		+tffirstName.getText() 
            		+"', lastname = '"
            		+tflastName.getText() 
            		+"', birthday = '"
            		+ sqlBirthday
            		+"', address = '"+tfadress.getText() 
            		+"', plz = '"+tfplz.getText() 
            		+"', city = '"+tfcity.getText() 
            		+"', mobile = '"+tfmobNumber.getText() 
            		+"', email = '"+tfemail.getText() 
            		+"', username = '" +tfuserName.getText() 
                    +"' WHERE username = '" + whichUserTxt.getText() +"'");
                    
                    System.out.println(sqlBirthday);
                    
                    statement.executeUpdate();
                    
                    JOptionPane.showMessageDialog(frame, "User is updated");
            	}catch(Exception e1) {
            		System.out.println(e1);
            	}
           
            }
        });
		
	}
}
