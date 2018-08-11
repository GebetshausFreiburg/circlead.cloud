/*
 * Circlead - Develop and structure evolutionary Organisations
 * 
 * @author Matthias Wegner
 * @version 0.1
 * @since 01.07.2018
 * 
 */
package org.rogatio.circlead.util;

import java.text.DateFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The Class StringUtil is a simple helper for text-parsing
 */
public class StringUtil {

	/** The Constant logger. */
	final static Logger logger = LogManager.getLogger(StringUtil.class);

	public static String join(List<String> list) {
		return String.join(",", list);
	}
	
	/**
	 * Count matches in string of given symbol
	 *
	 * @param string the string
	 * @param symbol the symbol
	 * @return the int
	 */
	public static int countMatches(String string, String symbol) {
		String[] s = string.split(symbol);
		int i = s.length - 1;
		return i;
	}

	/**
	 * Beautify a string by setting first letter to upper case
	 *
	 * @param s the s
	 * @return the string
	 */
	public static String beautify(String s) {
		return s.substring(0,1).toUpperCase()+s.substring(1, s.length());
	}
	
	/**
	 * Checks if string is not null and not empty.
	 *
	 * @param s the s
	 * @return true, if is not null and not empty
	 */
	public static boolean isNotNullAndNotEmpty(String s) {
		if (s != null) {
			if (!s.trim().equals("")) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Clean a list, so no null and empty value is inside. If null a empty list is returned.
	 *
	 * @param rawList the raw list
	 * @return the list
	 */
	public static List<String> clean(List<String> rawList) {
		List<String> list = new ArrayList<String>();
		if (rawList != null) {
			for (String syn : rawList) {
				if (StringUtil.isNotNullAndNotEmpty(syn)) {
					list.add(syn.trim());
				}
			}
		}
		return list;
	}

	/**
	 * Converts a string to a int value if possible. If exception occurs int is set to 0.
	 *
	 * @param value the value
	 * @return the int
	 */
	public static int toInt(String value) {
		if (value == null) {
			return 0;
		}
		try {
			return Integer.parseInt(value);
		} catch (Exception e) {
			return 0;
		}
	}

	/**
	 * Converts a xml-date to an java.util.Date.
	 *
	 * @param date the date
	 * @return the date
	 */
	public static Date toDate(String date) {
		try {
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.GERMANY);
			return format.parse(date);
		} catch (ParseException e) {
			logger.error(e);
		}
		return null;
	}

	/**
	 * Converts a java.util.Date to a xml-representation of a date
	 *
	 * @param date the date
	 * @return the string
	 */
	public static String fromDate(Date date) {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.GERMANY);
		return format.format(date);
	}
	
}
