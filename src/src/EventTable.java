package src;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.*;
import java.awt.*;

/**
 * This Class exists to create a table that lists every event of a user ordered by date.
 * in addition to listing every event it also colors the rows depending on the priority.
 * @author Stefan
 *
 */

public class EventTable {
	
private JFrame frame;
private JTable table;
private JPanel contentPane;

/**
 * Launch the Eventtable
 * @param username
 */

public static void EventTable(String username) {
	EventQueue.invokeLater(new Runnable() {
		public void run() {
			try {
				EventTable window = new EventTable(username);
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
 * constructor of the EventTable class
 * @param username
 */
public EventTable(String username) {
	initialize(username);
}

/**
 * Creating the EventTable and paint each row depending on the "priority" attribute
 * @param username
 */

private void initialize(String username) {
	frame = new JFrame();
	frame.setBounds(100, 100, 450, 300);
	frame.setSize(1600, 600);
	//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
	ResultSetMetaData resultMetaData = null;

//get the data for the table(row, column, and content of cells)
	try {
		
		Connection con = JDBCMySQLConnection.getConnection();
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM eventdata WHERE username = \""+ username +"\" ORDER BY eventdate ASC");
		resultMetaData = rs.getMetaData();
		
		int col = resultMetaData.getColumnCount();
		Vector column = new Vector(col);
		
		for(int i=1; i<=col; i++) {
			column.add(resultMetaData.getColumnName(i));
		}
		
		Vector data = new Vector();
		Vector row = new Vector();
	
		while(rs.next()) {
			row = new Vector(col);
			
			for(int i=1; i<=col; i++) {
				row.add(rs.getString(i));
			}
			data.add(row);
		}

		
//creating the table and paint each row depending on the "priority" attribute

		table = new JTable(data, column) 
		{
			 @Override
	         public Component prepareRenderer(TableCellRenderer renderer, int row, int column) 
			 {
				 Component c = super.prepareRenderer(renderer, row, column);
				 if (!isRowSelected(row)) 
				 {
					 String type = (String)getModel().getValueAt(row, 16);
					 if (type.equalsIgnoreCase("high")) {c.setBackground(new Color(255, 111, 0));}
					 if (type.equalsIgnoreCase("medium")) {c.setBackground(new Color(255, 232, 23));}
					 if (type.equalsIgnoreCase("low")) {c.setBackground(new Color(108, 255, 23));}
					 if (!type.equalsIgnoreCase("high")&&!type.equalsIgnoreCase("medium")&&!type.equalsIgnoreCase("low")) {c.setBackground(Color.WHITE);}
                 }
				 return c;
			 }
		};
		JScrollPane pane = new JScrollPane(table);
		frame.getContentPane().add(pane);
		
	}catch(Exception e) {
		e.printStackTrace();
	}
	
}

}
