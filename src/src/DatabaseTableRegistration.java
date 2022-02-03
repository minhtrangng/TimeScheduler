package src;

import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class DatabaseTableRegistration extends JFrame{

	private JFrame frame;
	private JTable table;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DatabaseTableRegistration window = new DatabaseTableRegistration();
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
	
	public DatabaseTableRegistration() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setSize(1600, 600);
		//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Connection connection = null;
		ResultSet result =  null;
		PreparedStatement statement = null;
		ResultSetMetaData resultMetaData = null;
		
		/**
		 * In this class, the records from the database are output 
		 * in the form of the table
		 * In this case from the table logindata, which was created in MySQL
		 */
		
		try {
			connection = JDBCMySQLConnection.getConnection();
			statement = connection.prepareStatement("SELECT * FROM logindata");
			result = statement.executeQuery();
			resultMetaData = result.getMetaData();
			
			int col = resultMetaData.getColumnCount();
			Vector column = new Vector(col);
			
			for(int i=1; i<=col; i++) {
				column.add(resultMetaData.getColumnName(i));
			}
			Vector data = new Vector();
			Vector row = new Vector();
			
			while(result.next()) {
				row = new Vector(col);
				
				for(int i=1; i<=col; i++) {
					row.add(result.getString(i));
				}
				data.add(row);
			}
			
			JTable table = new JTable(data, column);
			JScrollPane pane = new JScrollPane(table);
			frame.getContentPane().add(pane);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}

}
