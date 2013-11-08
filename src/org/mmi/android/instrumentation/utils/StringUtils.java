package org.mmi.android.instrumentation.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class StringUtils {
	
	private final static SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy_HH.mm.ss");
	
	/**
	 * 
	 * @param s
	 * @return
	 */
	public static int countWords(String s){
		String arr[] = s.split("[\\s,;()]+");

		return arr.length;
	}
	
	
	/**
	 * 
	 * @return
	 */
	public static String now() {
		Calendar cal = Calendar.getInstance();
		return sdf.format(cal.getTime());
	}
	
}
