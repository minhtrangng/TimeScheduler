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
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

/**
 * 
 * @author selma
 *
 */
public class AdminWindow extends JFrame{

	private JFrame frame;
	private JPanel contentPane;
	
	private JLabel label, ID, Username;
	 
	/**
	 * Launch the application.
	 */
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminWindow window = new AdminWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
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
	
	public AdminWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setSize(600, 450);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        frame.setContentPane(contentPane);
        contentPane.setLayout(null);
		
        JLabel lAdmin = new JLabel("Admin Panel");
		lAdmin.setFont(new Font("Thoma", Font.BOLD, 30));
		lAdmin.setBounds(73, 113, 193, 52);
		contentPane.add(lAdmin);
		
	/**
	 * Access of all user button created
	 * After pressing the button, 
	 * in the method actionPerformed(ActionEvent e) 
	 * the class DatabaseTableRegistration is called
	 * =>table with all information about user is displayed 
	 */
		
		JButton Access = new JButton("Access of all user");
        Access.setFont(new Font("Tahoma", Font.PLAIN, 10));
        Access.setBounds(420, 80, 115, 25);
        contentPane.add(Access);
        
        Access.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	DatabaseTableRegistration allUser = new DatabaseTableRegistration();
        		//allUser.pack();
        		allUser.setVisible(true);
            }
        });

        
        /**
         * Edit user button created, 
         * After pressing the button 
         * in the method actionPeformed(ActionEvent e) 
         * the class EditUser is called 
         */
		
        JButton Edit = new JButton("Edit user");
        Edit.setFont(new Font("Tahoma", Font.PLAIN, 10));
        Edit.setBounds(420, 150, 115, 25);
        contentPane.add(Edit);
        
        Edit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // do something
            	EditUser editWindow = new EditUser();
            	editWindow.setVisible(true);
            }
        });
		
        
      	/**
         * Delete user button created, 
         * After pressing the button 
         * in the method actionPeformed(ActionEvent e) 
         * the class DeleteUser is called
         */

        JButton Delete = new JButton("Delete user");
        Delete.setFont(new Font("Tahoma", Font.PLAIN, 10));
        Delete.setBounds(420, 220, 115, 25);
        contentPane.add(Delete);
        
        
        Delete.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				DeleteUser deleteWindow = new DeleteUser();
				deleteWindow.setVisible(true);
			}
        	
        });
        
        
	/**
         * Logout button=> with JOptionPane.showConfirmDialog window 
         * three options are offered (Yes, No and Cancel) 
         * If yes button was pressed, then login window will be opened again 
         * and admin/user can login again
         * and if no or cancel is pressed, admin panel window is displayed 
         */
        
        JButton Logout = new JButton("Logout");
        Logout.setFont(new Font("Tahoma", Font.PLAIN, 10));
        Logout.setBounds(420, 290, 115, 25);
        contentPane.add(Logout);
        
        Logout.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		int a = JOptionPane.showConfirmDialog(Logout, "Are you sure?");
            
        			if (a == JOptionPane.YES_OPTION) {
        				dispose();
        				frame.setVisible(false);
        				LoginWindow loginWindow = new LoginWindow();
        				loginWindow.setVisible(true);
        			}
        	}
    });
        
	}
	
}
