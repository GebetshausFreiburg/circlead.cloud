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

// TODO: Auto-generated Javadoc
/**
 * The Class StringUtil is a simple helper for text-parsing.
 */
public class StringUtil {

	/** The Constant logger. */
	final static Logger LOGGER = LogManager.getLogger(StringUtil.class);

	/**
	 * Default java-method to join comma-separated string to list od strings.
	 *
	 * @param list the list
	 * @return the string
	 */
	public static String join(List<String> list) {
		return String.join(",", list);
	}

	/**
	 * Convert comma-separated string to list of strings. Only add item if found
	 * string between commas is not empty or null
	 *
	 * @param string the string
	 * @return the list
	 */
	public static List<String> toList(String string) {
		List<String> identifiers = new ArrayList<String>();

		if (StringUtil.isNotNullAndNotEmpty(string)) {

			if (string.contains(",")) {
				String[] i = string.split(",");
				for (int j = 0; j < i.length; j++) {
					String id = i[j];
					identifiers.add(id.trim());
				}
			} else {
				identifiers.add(string.trim());
			}

			return identifiers;
		}
		return null;
	}

	/**
	 * Count matches in string of given symbol.
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
	 * Beautify a string by setting first letter to upper case.
	 *
	 * @param s the s
	 * @return the string
	 */
	public static String beautify(String s) {
		return s.substring(0, 1).toUpperCase() + s.substring(1, s.length());
	}

	public static String addSpace(String string, int space, char fill) {
		 
        int spacer = 0;
        StringBuffer sb = new StringBuffer();
 
        if (space > string.length())
            spacer = space - string.length();
 
        for (int i = 0; i < spacer; i++) {
            sb.append(fill);
        }
 
        return sb.append(string).toString();
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
	 * Clean a list, so no null and empty value is inside. If null a empty list is
	 * returned.
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
	 * Converts a string to a int value if possible. If exception occurs int is set
	 * to 0.
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
	 * Converts a xml-date to an java.util.Date, i.e. "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
	 *
	 * @param date the date
	 * @param f    the f
	 * @return the date
	 */
	public static Date toDate(String date, String f) {
		try {
			DateFormat format = new SimpleDateFormat(f, Locale.GERMANY);
			return format.parse(date);
		} catch (ParseException e) {
			LOGGER.error(e);
		}
		return null;
	}

	/**
	 * Converts a java.util.Date to a xml-representation of a date
	 *
	 * @param date the date
	 * @param f    the f
	 * @return the string
	 */
	public static String fromDate(Date date, String f) {
		DateFormat format = new SimpleDateFormat(f, Locale.GERMANY);
		return format.format(date);
	}

}
