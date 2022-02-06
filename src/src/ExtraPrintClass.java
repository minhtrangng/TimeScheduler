package src;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.time.*;

public class ExtraPrintClass {
	
	// setting lower boundry of frame to date of monday	
		static String getMonday(LocalDate _today)
		{
			String _monday = "";
			while (_today.getDayOfWeek() != DayOfWeek.MONDAY)
			{
				_today = _today.minusDays(1);
			}
			_monday = _today.toString();
			return _monday;
		}


	// setting uper boundry of frame to date of monday	
		static String getSunday(LocalDate _today)
		{
			String _sunday= "";
			while (_today.getDayOfWeek() != DayOfWeek.SUNDAY)
			{
				_today = _today.plusDays(1);
			}
			_sunday = _today.toString();
			return _sunday;
		}
		

		static void exportWeek(String username)
		{
			PreparedStatement preparedStatement = null;
			LocalDate today = LocalDate.of(2022,01,25);
			//LocalDate today = LocalDate.now();
			today = today.plusDays(3);
			String monday = getMonday(today); 
			String sunday = getSunday(today);
	// create file		
			try {
				File myObj = new File("this_week.txt");
			    if (myObj.createNewFile()) {
			    	System.out.println("File created: " + myObj.getName());
			    } /*else {
			    	System.out.println("File already exists.");
			    }*/
			} catch (IOException e) {
				System.out.println("An error occurred.");
			    e.printStackTrace();
			}

			try
	//create database connection and query to get all events within boundry set by getMonday and getSunday
			{	
				//Class.forName("com.mysql.jdbc.Driver");
				//Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sonoo?characterEncoding=utf8","root","root");
				
				Connection connection = JDBCMySQLConnection.getConnection();
				PreparedStatement statement = connection.prepareStatement("SELECT * FROM eventdata WHERE username = '" + username + "' AND eventdate BETWEEN '" + monday + "' AND '" + sunday + "'");
				//Statement stmt = con.createStatement();
				//ResultSet rs = stmt.executeQuery("SELECT * FROM withdate WHERE username = \"" + username1 + "\" AND WHERE eventdate BETWEEN \"" + monday + "\" AND \"" + sunday +"\"");
				ResultSet result = statement.executeQuery();
				try
	// open file and start writing to it
				{
					FileWriter myWriter = new FileWriter("this_week.txt");
					myWriter.write("Appointments from " + monday + " to " + sunday +"\n");
					while (result.next())
						
	// loop through all events withinboundry and write the necessary information to file++
						{
							myWriter.write("========================\n");
//							myWriter.write(rs.getString(3) + ":\n");
							today = today.parse(result.getString(3));
							myWriter.write(today.getDayOfWeek().name() + today + "\n");
							myWriter.write("Activity: " + result.getString(4)+"\n");
							myWriter.write("Begintime: "+result.getString(5)+"\n");
							myWriter.write("Eventduration: "+result.getInt(6)+"\n");
							
						}
					myWriter.close();
					System.out.println("txt overwritten");
				} catch (IOException e)
		 			{
						System.out.println("error");
						e.printStackTrace();
		 			}
				connection.close();
			}catch(Exception e){ System.out.println(e);}
		}
	

}
