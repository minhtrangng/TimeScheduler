package src;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JFormattedTextField.AbstractFormatter;

/**
 * This class is used along with the JavaSwing component JDatePicker return the clicked date.
 * <p>
 * Reference: <a href="https://www.youtube.com/watch?v=4h8MhUtLV38">
 * How to build a Date Time Picker in Java(Last accessed: 05.02.2022)</a>
 *  
 * @author minhtrang
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

}
