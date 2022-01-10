// References: https://javahungry.blogspot.com/2013/06/calendar-implementation-gui-based.html

package src;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.GregorianCalendar;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTextField;

public class CalendarWindow extends JFrame{

	private JFrame frame;
	static Container pane;
	
	static JButton prevBtn, nextBtn;
	static JLabel monthLabel, yearLabel;
	
	static JTable calendarTable;
	static JComboBox yearCombo;
	static DefaultTableModel calendarTableModel;
	static JScrollPane scrollPaneCalendar;
	static JPanel panelCalendar;
	
	static int realYear, realMonth, realDay, currentYear, currentMonth;
	private JLabel lblNewLabel;
	
	static JTextField eventField;
	static JButton addEventBtn;
	static JLabel eventLabel;
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CalendarWindow window = new CalendarWindow();
					window.frame.setVisible(true);
					window.frame.setTitle("Calendar");
					window.frame.setContentPane(window.panelCalendar);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * Create the application.
	 */
	public CalendarWindow() {
		initialize();
	}
	
	public void setVisible(boolean visible){
	    frame.setVisible(visible);
	}
	


		
		/*try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch(ClassNotFoundException e) {}
		catch(InstantiationException e) {}
		catch(IllegalAccessException e) {}
		catch(UnsupportedLookAndFeelException e) {}*/
		
	
	/*public CalendarWindow() {
		
	}*/

	

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		//frame.setContentPane(new CalendarWindow().panelCalendar);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//frame.getContentPane().setLayout(null);
		frame.setSize(600, 650);
		pane = frame.getContentPane();
		pane.setLayout(null);
		
		
		// PREVIOUS BUTTON
		prevBtn = new JButton("<");
		/*prevBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});*/
		prevBtn.setBounds(66, 11, 78, 23);
		//frame.getContentPane().add(prevBtn);
		
		
		// NEXT BUTTON
		nextBtn = new JButton(">");
		nextBtn.setBounds(435, 11, 78, 23);
		//frame.getContentPane().add(nextBtn);
		
		
		// MONTH LABEL
		monthLabel = new JLabel("January");
		monthLabel.setBounds(177, 11, 270, 50);
		//frame.getContentPane().add(monthLabel);
		
		
		// YEAR LABEL
		yearLabel = new JLabel("Year: ");
		yearLabel.setBounds(66, 381, 46, 14);
		//frame.getContentPane().add(yearLabel);
		
		// YEAR COMBO
		yearCombo = new JComboBox();
		yearCombo.setBounds(122, 377, 90, 23);
		//frame.getContentPane().add(yearCombo);
		
		// EVENT FIELD
		eventField = new JTextField();
		eventField.setBounds(55, 466, 465, 110);
		//eventField.setColumns(10);		
		
		
		// ADD EVENT BUTTON
		addEventBtn = new JButton("Add new event");
		addEventBtn.setBounds(367, 398, 146, 23);
		
		
		// EVENT LABEL
		eventLabel = new JLabel("EVENT");
		eventLabel.setBounds(55, 448, 46, 14);
		
		
		// CALENDAR TABLE MODEL
		calendarTableModel = new DefaultTableModel() {
			public boolean isCellEditable(int rowIndex, int mCollIndex) {
				return false;
			}
		};
		
		
		// CALENDAR TABLE
		calendarTable = new JTable(calendarTableModel);
		
		
		// SCROLL PANE CALENDAR
		scrollPaneCalendar = new JScrollPane(calendarTable);
		
		
		// PANEL CALENDAR
		panelCalendar = new JPanel(null);
		
		
		// SET BORDER FOR PANEL CALENDAR
		panelCalendar.setBorder(BorderFactory.createTitledBorder("Calendar"));
		
		// REGISTER ACTION LISTENERS FOR BUTTONS: PREVIOUS/NEXT BUTTON, COMBOYEAR COMBO BOX
		prevBtn.addActionListener(new prevBtnAction());
		nextBtn.addActionListener(new nextBtnAction());
		yearCombo.addActionListener(new yearComboAction());
		
		
		// ADD CONTROLS TO PANE
		//pane.add(panelCalendar);
		panelCalendar.add(monthLabel);
		panelCalendar.add(yearLabel);
		panelCalendar.add(yearCombo);
		panelCalendar.add(prevBtn);
		panelCalendar.add(nextBtn);
		panelCalendar.add(scrollPaneCalendar);
		panelCalendar.add(eventField);
		panelCalendar.add(addEventBtn);
		panelCalendar.add(eventLabel);
		pane.add(panelCalendar);
		
		// SET BOUNDS
		panelCalendar.setBounds(0, 0, 594, 671);
		//frame.setContentPane(panelCalendar);
		scrollPaneCalendar.setBounds(66, 45, 447, 303);
		
		// MAKE FRAME VISIBLE
		frame.setResizable(false);
		
	
		
		// GET REAL MONTH/YEAR
		GregorianCalendar calendar = new GregorianCalendar(); // create calendar
		realDay = calendar.get(GregorianCalendar.DAY_OF_MONTH);
		realMonth = calendar.get(GregorianCalendar.MONTH);
		realYear = calendar.get(GregorianCalendar.YEAR);
		currentMonth = realMonth;
		currentYear = realYear;
		
		// ADD HEADERS - WEEKDAYS
		String[] headers = {"SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT"};
		for(int i=0; i<7; i++) {
			calendarTableModel.addColumn(headers[i]);
		}
		
		calendarTable.getParent().setBackground(calendarTable.getBackground());
		
		
		// NO RESIZE/REORDER
		calendarTable.setColumnSelectionAllowed(true);
		calendarTable.setRowSelectionAllowed(true);
		calendarTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		
		// SET ROW/COLUMN COUNT
		calendarTable.setRowHeight(38);
		
		
		
		calendarTableModel.setColumnCount(7);
		calendarTableModel.setRowCount(6);
		
		
		// POPULATE TABLE
		for(int i=realYear-100; i<=realYear+100; i++) {
			yearCombo.addItem(String.valueOf(i));
		}
		
		
		// REFRESH CALENDAR
		refreshCalendar(realMonth, realYear);
		
		
		// ADD EVENT TO CALENDAR CELL
		calendarTableModel.addTableModelListener(new TableModelListener() {
			@Override
			public void tableChanged(TableModelEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
		// USE ADD EVENT BUTTON TO ADD EVENT TO CALENDAR CELL
		/*addEventBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(eventField == null) {
					JOptionPane.showMessageDialog(frame, "Event is empty!");
				}
				else {
					String 
				}
			}
		});*/
	
	}
	
	
	// FUNCTION: REFRESCH CALENDAR
	public static void refreshCalendar(int month, int year) {
		String[] months = {"JANUARY", "FERBRUARY", "MARCH", "APRIL", "MAY", "JUNE", "JULY", "AUGUST", "SEPTEMBER",
				"OCTOBER", "NOVEMBER", "DECEMBER"};
		
		int numberOfDays, monthStart;
		
		// ALLOW/DISALLOW BUTTONS
		prevBtn.setEnabled(true);
		nextBtn.setEnabled(true);
		if(month == 0 && year <= realYear-10) {
			prevBtn.setEnabled(false);
		}
		
		if(month == 11 && year >= realYear+100) {
			nextBtn.setEnabled(false);
		}
		
		monthLabel.setText(months[month]);
		monthLabel.setBounds(160-monthLabel.getPreferredSize().width/2, 25, 180, 25); // Re-align label with calendar
		
		yearCombo.setSelectedItem(String.valueOf(year)); // Select the correct year in the combo box
		
		// Clear table
		for(int i=0; i<6; i++) {
			for(int j=0; j<7; j++) {
				calendarTableModel.setValueAt(null, i, j);
			}
		}
		
		// Get first day of month and number of days
		GregorianCalendar calendar = new GregorianCalendar(year, month, 1);
		numberOfDays = calendar.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
		monthStart = calendar.get(GregorianCalendar.DAY_OF_WEEK);
		
		// Draw calendar
		for(int i=1; i <= numberOfDays; i++) {
			int row = new Integer((i+monthStart-2)/7);
			int column = (i+monthStart-2)%7;
			calendarTableModel.setValueAt(i, row, column);
		}
		
		// Apply renderers
		calendarTable.setDefaultRenderer(calendarTable.getColumnClass(0), new tblCalendarRenderer());
		
		//calendarTable.addMouseListener(new java.awt.event.MouseAdapter())
	}
	
	
	static class tblCalendarRenderer extends DefaultTableCellRenderer {

		public Component getTableCellRendererComponent (JTable table, Object value, boolean selected, boolean focused,
				int row, int column) {
			Component cell = super.getTableCellRendererComponent(table, value, selected, focused, row, column);
			
			if(column == 0 || column == 6) {
				cell.setBackground(new Color(255,220,220));
			}
			else {
				cell.setBackground(new Color(255,255,255));
			}
			
			if(value != null) {
				if(Integer.parseInt(value.toString()) == realDay && currentMonth == realMonth && currentYear == realYear) {
					cell.setBackground(new Color(220,220,255));
				}
			}
			
			if(selected) {
				cell.setBackground(new Color(150,242,214));
			}
			
			setBorder(null);
			setForeground(Color.black);
			return cell;
		}
		
	}
	
	
	static class prevBtnAction implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if(currentMonth == 0) {
				currentMonth = 11;
				currentYear -= 1;
			}
			else {
				currentMonth -= 1;
			}
			refreshCalendar(currentMonth, currentYear);
		}
	}
	
	static class nextBtnAction implements ActionListener{
		public void actionPerformed (ActionEvent e) {
			if(currentMonth == 11) {
				currentMonth = 0;
				currentYear += 1;
			}
			else {
				currentMonth += 1;
			}
			refreshCalendar(currentMonth, currentYear);
		}
	}
	
	static class yearComboAction implements ActionListener{
		public void actionPerformed (ActionEvent e) {
			if(yearCombo.getSelectedItem() != null) {
				String b = yearCombo.getSelectedItem().toString();
				currentYear = Integer.parseInt(b);
				refreshCalendar(currentMonth, currentYear);
			}
		}
	}
	
	
	
	
}



























