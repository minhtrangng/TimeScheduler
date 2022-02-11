package src;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Calendar;

import javax.swing.JFormattedTextField.AbstractFormatter;

/**
 * This class is used along with the JavaSwing component JDatePicker return the clicked date.
 * 
 * @author minhtrang &amp;&amp; henri d.
 */
public class DateLabelFormatter extends AbstractFormatter {
	
	private String datePattern = "yyyy-MM-dd";
	private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

	@Override
	public Object stringToValue(String text) throws ParseException {
		// TODO Auto-generated method stub
		return dateFormatter.parseObject(text);
	}

	@Override
	public String valueToString(Object value) throws ParseException {
		// TODO Auto-generated method stub
		if(value != null) {
			Calendar cal = (Calendar) value;
			return dateFormatter.format(cal.getTime());
		}
		return "";
	}
	
	/**
	 * Converts a begintime String from the database (format: HH:mm) to a LocalTime (format: HH:mm:ss) so it can be
	 * compared with other LocalTime values
	 *
	 * @param str
	 * @return tmpTime
	 */
	public LocalTime stringToTime(String str) {
		String tmp = "";

		if(str.charAt(1) == ':') { // in case format is H:MM
			tmp += "0";
			tmp += str.substring(0,1);
			tmp += ":";
			tmp += str.substring(2,4);
			tmp += ":00";
		}
		else {
			tmp += str.substring(0,2);
			tmp += ":";
			tmp += str.substring(3,5);
			tmp += ":00";
		}
		LocalTime tmpTime = LocalTime.parse(tmp);


		return tmpTime;
	}

}
