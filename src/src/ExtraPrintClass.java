package src;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.time.*;

import javax.swing.JOptionPane;

/**
 * This Class exists to export the events of the current week into a txt-file
 * @author stefan
 */

public class ExtraPrintClass {
	
/**
 * This function sets the lower boundry of the week
 * @param _today
 * @return _monday
 */
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


/**
 * This function sets the upper boundry of the week
 * @param _today
 * @return _sunday
 */
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
		


/** 
 * This function creates a file in the project folder with the name this_week.txt
 * It also establishes a connection to the database and writes the necessary data into the txt-file
 * @param username
 */
	
		static void exportWeek(String username)
		{
			PreparedStatement preparedStatement = null;
			LocalDate today = LocalDate.of(2022,02,07);
			//LocalDate today = LocalDate.now();
			today = today.plusDays(3);
			String monday = getMonday(today); 
			String sunday = getSunday(today);		
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
			{	
				
				Connection connection = JDBCMySQLConnection.getConnection();
				PreparedStatement statement = connection.prepareStatement("SELECT * FROM eventdata WHERE username = '" + username + "' AND eventdate BETWEEN '" + monday + "' AND '" + sunday + "' ORDER BY eventdate ASC");
				ResultSet result = statement.executeQuery();
				try
				{
					FileWriter myWriter = new FileWriter("this_week.txt");
					myWriter.write("Appointments from " + monday + " to " + sunday +"\n");
					//if(!result.next()) {
						//return "There is no event this week!";
					//}
					//else {
						while (result.next())
							
						{
							myWriter.write("========================\n");
							//myWriter.write(rs.getString(3) + ":\n");
							today = today.parse(result.getString(3));
							myWriter.write(today.getDayOfWeek().name() + " " + today + "\n");
							myWriter.write("Activity: " + result.getString(4)+"\n");
							myWriter.write("Begintime: "+result.getString(5)+"\n");
							myWriter.write("Location: "+result.getString(7)+"\n");
							myWriter.write("Description: "+result.getString(6)+"\n");
							myWriter.write("Amount of participants: "+result.getInt(9)+"\n");			
						}
						myWriter.close();
						System.out.println("txt overwritten");
						//return "Weekly plan was successfully extracted!!";
					//}
					
				} catch (IOException e)
		 			{
						System.out.println("error");
						e.printStackTrace();
		 			}
				connection.close();
			}catch(Exception e){ System.out.println(e);}
			//return "There is no event this week!";
		}
}
