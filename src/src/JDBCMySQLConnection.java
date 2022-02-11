

package src;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * This class acts like a brigde between IDE Java Eclipse and the local database.
 *
 * 
 * @author minhtrang
 *
 */
public class JDBCMySQLConnection {

	private static JDBCMySQLConnection instance = new JDBCMySQLConnection();
	
	/**
	 * These four variables URL, user, password, driverClass 
	 * are constant and must match with the local database information.
	 */
	public static final String URL = "jdbc:mysql://localhost:3306/timescheduler";
	public static final String user = "root";
	public static final String password = "Mtrang1901";
	public static final String driverClass = "com.mysql.cj.jdbc.Driver";
	
	public JDBCMySQLConnection() {
		try {
			Class.forName(driverClass);
		}catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
	private Connection createConnection() {
		Connection connection = null;
		
		try {
			connection = DriverManager.getConnection(URL, user, password);
		} catch(SQLException e) {
			System.out.println("Error: Unable to connect to database!");
		}
		return connection;
	}
	
	/**
	 * The connection will be established by calling this method.
	 * @return the connection
	 */
	public static Connection getConnection() {
		return instance.createConnection();
	}
	
}
