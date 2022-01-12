package src;

import java.util.Date;

public class Event {
 
	private Date date;
	private String activity;
	
	Event(){
		date = null;
		activity = null;
	}
	
	Event(int _year, int _month, int _day){
		if(_year < 2000 || _year > 2100) {
			System.out.println("Year is invalid");
			System.exit(1);
		}
		
		if(_month < 1 || _month > 12) {
			System.out.println("Month is invalid");
			System.exit(1);
		}
		
		if(_day < 1 || _day > 31) {
			System.out.println("Day is invalid");
		}
	}
	
	public void setDate(int _year, int _month, int _day) {
		date = new Date(_year-1900, _month, _day);
	}
	
	public Date getDate() {
		return date;
	}
	
	public void setActivity(String _activity) {
		activity = _activity;
	}
	
	public String getActivity() {
		return activity;
	}
	
	@Override
	public String toString() {
		return date + ": " + activity;
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof Event) {
			return true;
		}
		else {
			return false;
		}
	}
	
}
