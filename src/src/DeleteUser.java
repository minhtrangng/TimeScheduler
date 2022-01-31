package src;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;



public class DeleteUser {

	private JFrame frame;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DeleteUser window = new DeleteUser();
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
	public DeleteUser() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		
		contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        frame.setContentPane(contentPane);
        contentPane.setLayout(null);
        
		JLabel UserName = new JLabel("Username: ");
		UserName.setFont(new Font("Thoma", Font.BOLD, 12));
		UserName.setBounds(94, 21, 80, 33);
		contentPane.add(UserName);
		
		JTextField unTextField = new JTextField();
		unTextField.setBounds(184, 23, 110, 30);
        unTextField.setBackground(Color.WHITE);
		unTextField.setForeground(Color.BLACK);
		contentPane.add(unTextField);
		
		
		JButton Refresh = new JButton("Refresh");
        Refresh.setFont(new Font("Tahoma", Font.PLAIN, 10));
        Refresh.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
              	DatabaseTableRegistration frame = new DatabaseTableRegistration();
        		//frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        		frame.pack();
        		frame.setVisible(true);
        		
            }
        });
        
        Refresh.setBounds(302, 100, 80, 20);
        contentPane.add(Refresh);
        
    	JButton backB = new JButton("Back");
        backB.setFont(new Font("Tahoma", Font.PLAIN, 10));
        backB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
        		frame.dispose();	
            }
        });
        
        backB.setBounds(47, 100, 80, 20);
        contentPane.add(backB);
		
		JButton Delete = new JButton("Delete");
        Delete.setFont(new Font("Tahoma", Font.PLAIN, 10));
        Delete.setBounds(176, 100, 80, 20);
        contentPane.add(Delete);
        
        Delete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event){
            	
            	Connection connection;
            	ResultSet result;
            	PreparedStatement checkStatement;
            	PreparedStatement deleteStatement;
            	
            	
            	try { 
                	connection = JDBCMySQLConnection.getConnection();
                    checkStatement = connection.prepareStatement("SELECT * FROM logindata WHERE Username=?");
                    checkStatement.setString(1, unTextField.getText());
                    result = checkStatement.executeQuery();
                    if(!result.next()) {
                    	JOptionPane.showMessageDialog(null, "Username doesn't exist");
                    
                    }else {
                   
                    deleteStatement = connection.prepareStatement("DELETE FROM logindata WHERE Username= '" + unTextField.getText() + "'");
                    deleteStatement.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Username deleted");  
           
                    }
                } catch(SQLException sqlException) {
					sqlException.printStackTrace();
				} 
            }  
           
        
            });

        
        
	}

}
