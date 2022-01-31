package src;


import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.TextAttribute;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.Properties;

import javax.swing.JFrame;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import javax.swing.JToolBar;
import javax.swing.SpringLayout;
import javax.swing.JButton;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class DatePickerTesting {

	private JFrame frame;
	private SpringLayout springLayout;
	JLabel userNameLabel = new JLabel("<user_name>");
	JLabel userLabel = new JLabel("USER: ");
	
	JLabel dateLabel = new JLabel("DATE:");
	JLabel eventLabel = new JLabel("EVENT: ");
	JLabel eventInfo = new JLabel("(e v e n t i n f o ...)");
	JButton checkBtn = new JButton("CHECK!");
	JButton addEventBtn = new JButton("ADD NEW EVENT");
	
	JButton logoutBtn = new JButton("Logout");

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DatePickerTesting window = new DatePickerTesting();
					window.frame.setVisible(true);
					window.frame.setTitle("Calendar");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public void setUserName(String value) {
		userNameLabel.setText(value);
	} 

	/**
	 * Create the application.
	 */
	public DatePickerTesting() {
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
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(450,400);
		//frame.getContentPane().setLayout(null);
		//frame.setVisible(true);
		
		UtilDateModel model = new UtilDateModel();
		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		
		JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
		frame.getContentPane().setLayout(null);
		JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
		datePicker.setBounds(0, 338, 434, 23);
		SpringLayout sl_datePicker = (SpringLayout) datePicker.getLayout();
		frame.getContentPane().add(datePicker);
		
		
		userNameLabel.setBounds(301, 11, 102, 14);
		frame.getContentPane().add(userNameLabel);
		
		userLabel.setBounds(242, 11, 49, 14);
		frame.getContentPane().add(userLabel);
		
		
		
		dateLabel.setBounds(10, 313, 46, 14);
		frame.getContentPane().add(dateLabel);
		
		
		eventLabel.setBounds(26, 63, 70, 14);
		Font font = eventLabel.getFont();
		Map attributes = font.getAttributes();
		attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
		eventLabel.setFont(font.deriveFont(attributes));
		frame.getContentPane().add(eventLabel);
		
		
		eventInfo.setBounds(25, 89, 378, 31);
		eventInfo.setFont(new Font("San_Serif", Font.BOLD, 16));
		frame.getContentPane().add(eventInfo);
		
		
		addEventBtn.setBounds(217, 175, 150, 31);
		frame.getContentPane().add(addEventBtn);
		
		
		checkBtn.setBounds(10, 179, 89, 23);
		frame.getContentPane().add(checkBtn);
		
		
		logoutBtn.setBounds(311, 36, 89, 23);
		frame.getContentPane().add(logoutBtn);
		logoutBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				frame.dispose();
				frame.setVisible(false);
				LoginWindow datePicker = new LoginWindow();
				datePicker.setVisible(true);
			}
			
		});
		
	
		
		
		
		addEventBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(datePicker.getJFormattedTextField().getText() != "") {
					NewEventWindow newEvent = new NewEventWindow();
					newEvent.setDate(datePicker.getJFormattedTextField().getText());
					newEvent.setUsername(userNameLabel.getText());
					newEvent.setVisible(true);
				}
				else {
					JOptionPane.showMessageDialog(frame, "Please choose date first!!");
				}
			}
		});
		
		checkBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(datePicker.getJFormattedTextField().getText() != "") {
					String username = userNameLabel.getText();
					String eventDate = datePicker.getJFormattedTextField().getText();
					
					ResultSet result = null;
					Connection connection = null;
					
					PreparedStatement statement = null;
					
					try {
						connection = JDBCMySQLConnection.getConnection();
						statement = connection.prepareStatement("SELECT * FROM eventdata WHERE username = '" + username + "' AND eventdate = '" + eventDate + "'");
						result = statement.executeQuery();
						
						while(result.next()) {
							String usrName = result.getString("username");
							String eventContent = result.getString("activity");
							int eventDuration = result.getInt("eventduration");
							String eventReminder = result.getString("reminder");
							
							if(username.equalsIgnoreCase(usrName)) {
								//eventInfo.setText(eventContent + " | Duration: " + eventDuration + " | Remind me after: " + eventReminder);
								eventInfo.setText(eventContent);
							}
						}
						
					}catch(SQLException sqlException) {
						sqlException.printStackTrace();
					}
				}
				
			}
			
		});
		
		
		
	}
}
