package com.alfalahsoftech.alframe.util;

import java.util.Calendar;
import java.util.Date;

public class AFDateUtil {
	public static Date currentDate() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		java.sql.Date date2=new java.sql.Date(cal.getTimeInMillis());
	    return date2;
	}
	
	public static Date prevDay() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DAY_OF_MONTH,-1); //same with cal.add(Calendar. DAY_OF_YEAR,-1);
		java.sql.Date date2=new java.sql.Date(cal.getTimeInMillis());
	    return date2;
	}
	public static Date nextDay() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DAY_OF_MONTH,1); //same with cal.add(Calendar. DAY_OF_YEAR,-1);
		java.sql.Date date2=new java.sql.Date(cal.getTimeInMillis());
	    return date2;
	}
	
	public static Date last7Days() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DAY_OF_MONTH,-7); //same with cal.add(Calendar. DAY_OF_YEAR,-1);
		java.sql.Date date2=new java.sql.Date(cal.getTimeInMillis());
	    return date2;
	}
	
	public static Date offsetDate(int offset) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DAY_OF_MONTH,offset); //same with cal.add(Calendar. DAY_OF_YEAR,-1);
		java.sql.Date date2=new java.sql.Date(cal.getTimeInMillis());
	    return date2;
	}
	
	
}
