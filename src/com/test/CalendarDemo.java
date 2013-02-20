package com.test;

import javax.swing.JPanel;
import com.toedter.calendar.JCalendar;
import com.toedter.calendar.JDateChooser;
import com.toedter.components.JSpinField;
import com.toedter.calendar.JYearChooser;

public class CalendarDemo extends JPanel{
	public CalendarDemo() {
		
		JDateChooser dateChooser = new JDateChooser();
		add(dateChooser);
	}

}
