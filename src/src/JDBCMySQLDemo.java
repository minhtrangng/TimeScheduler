package src;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCMySQLDemo {
	
	public static void main(String[] args) {
		
		BufferedReader bufferReader = new BufferedReader(new InputStreamReader(System.in));
		
		System.out.println("Enter the User ID: ");
		
		int userID;
		try {
			userID = Integer.parseInt(bufferReader.readLine());
			JDBCMySQLDemo demo = new JDBCMySQLDemo();
			User user = demo.getUser(userID);
			System.out.println(user);
		}catch (NumberFormatException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public User getUser(int _userID) {
		ResultSet result = null;
		Connection connection = null;
		Statement statement = null;
		
		User user = null;
		String query = "SELECT * FROM logindata WHERE id=" + _userID;
		
		try {
			connection = JDBCMySQLConnection.getConnection();
			statement = connection.createStatement();
			result = statement.executeQuery(query);
			
			if(result.next()) {
				user = new NormalUser();
				user.setName(result.getString("username"));
				user.setPassword(result.getString("password"));
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if(connection != null) {
				try {
					connection.close();
				}catch (SQLException e) {
					e.printStackTrace();
				}
				
			}
		}
		return user;
	}

}
