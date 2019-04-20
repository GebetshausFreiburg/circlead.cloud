/*
 * Circlead - Develop and structure evolutionary Organisations
 * 
 * @author Matthias Wegner
 * @version 0.1
 * @since 01.07.2018
 * 
 */
package org.rogatio.circlead.util;

import java.io.IOException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.rogatio.circlead.control.Repository;
import org.rogatio.circlead.model.work.Activity;
import org.rogatio.circlead.model.work.IWorkitem;
import org.rogatio.circlead.model.work.Person;
import org.rogatio.circlead.model.work.Role;
import org.rogatio.circlead.model.work.Rolegroup;
import org.rogatio.circlead.model.work.Team;

/**
 * The Class StringUtil is a simple helper for text-parsing.
 * 
 * @author Matthias Wegner
 */
public class StringUtil {

	/** The Constant logger. */
	final static Logger LOGGER = LogManager.getLogger(StringUtil.class);

	/**
	 * Join comma-separated string to list of strings.
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
	
	 /**
 	 * Break line.
 	 *
 	 * @param s the s
 	 * @param length the length
 	 * @param breaktoken the breaktoken
 	 * @return the string
 	 */
 	public static String breakLine(String s, int length, String breaktoken) {
		 
	        if (s.length() <= length) {
	            return s;
	        }
	 
	        StringBuilder sb = new StringBuilder();
	        StringTokenizer st = new StringTokenizer(s, " ");
	        String line = new String();
	        while (st.hasMoreTokens()) {
	            String token = st.nextToken();
	            if ((line + token).length() > length) {
	                sb.append(line + breaktoken+"\n");
	                line = new String();
	            }
	            line = line + token + " ";
	        }
	 
	        sb.append(line);
	 
	        return sb.toString();
	    }
	 

	/**
	 * Adds the space.
	 *
	 * @param string the string
	 * @param space the space
	 * @param fill the fill
	 * @return the string
	 */
	public static String addSpace(String string, Integer space, String fill) {
		return addSpace(string, space, fill.charAt(0));
	}
	
	/**
	 * Adds the space.
	 *
	 * @param string the string
	 * @param space  the space
	 * @param fill   the fill
	 * @return the string
	 */
	public static String addSpace(String string, Integer space, char fill) {

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
	 * Detect charset.
	 *
	 * @param value the value
	 * @return the string
	 */
	public static String detectCharset(String value) {
		if (!isNotNullAndNotEmpty(value))
			return null;
		
		String detectedCharset = findCharset(value, Charset.availableCharsets().keySet());
		return detectedCharset;
	}
	
	/**
	 * Convert encoding.
	 *
	 * @param value the value
	 * @param toEncoding the to encoding
	 * @return the string
	 */
	public static String convertEncoding(String value, String toEncoding) {
		try {
			return new String(value.getBytes(detectCharset(value)), toEncoding);
		} catch (UnsupportedEncodingException e) {
		}
		return null;
	}
	
	/**
	 * Convert encoding.
	 *
	 * @param value the value
	 * @param fromEncoding the from encoding
	 * @param toEncoding the to encoding
	 * @return the string
	 */
	public static String convertEncoding(String value, String fromEncoding, String toEncoding) {
		try {
			return new String(value.getBytes(fromEncoding), toEncoding);
		} catch (UnsupportedEncodingException e) {
		}
		return null;
	}
	
	/**
	 * Find charset.
	 *
	 * @param value the value
	 * @param charsets the charsets
	 * @return the string
	 */
	private static String findCharset(String value, Set<String> charsets) {
		String probe = StandardCharsets.UTF_8.name();
		
		for (String c : charsets) {
			Charset charset = Charset.forName(c);
			if (charset != null) {
				if (value.equals(convertEncoding(convertEncoding(value, charset.name(), probe), probe, charset.name()))) {
					return c;
				}
			}
		}
		return StandardCharsets.UTF_8.name();
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

	/**
	 * Merge data with velocity template.
	 *
	 * @param templateFile the template file
	 * @return the string
	 */
	public static String mergeFromTemplate(String templateFile) {
		return mergeFromTemplate(templateFile, null);
	}
	
	/**
	 * Gets the next day.
	 *
	 * @param day the day
	 * @return the next day
	 */
	public static String getNextDay(String day) {
		if ("Montag".equalsIgnoreCase(day)) {
			return "Dienstag";
		}
		if ("Dienstag".equalsIgnoreCase(day)) {
			return "Mittwoch";
		}
		if ("Mittwoch".equalsIgnoreCase(day)) {
			return "Donnerstag";
		}
		if ("Donnerstag".equalsIgnoreCase(day)) {
			return "Freitag";
		}
		if ("Freitag".equalsIgnoreCase(day)) {
			return "Samstag";
		}
		if ("Samstag".equalsIgnoreCase(day)) {
			return "Sonntag";
		}
		if ("Sonntag".equalsIgnoreCase(day)) {
			return "Montag";
		}
		return null;
	}
	
	/**
	 * Evaluate template.
	 *
	 * @param content the content
	 * @param context the context
	 * @return the string
	 */
	public static String evaluateTemplate(String content, VelocityContext context ) {
		
		LOGGER.debug("Content has encoding "+StringUtil.detectCharset(content)+": "+content);
		
		StringWriter writer = new StringWriter();
		try {
			VelocityEngine velocityEngine = new VelocityEngine();		
			velocityEngine.init();
			velocityEngine.evaluate(context, writer, "log", content);
		} finally {
			try {
				writer.close();
			} catch (IOException e) {
				throw new AssertionError("StringWriter#close() should not throw IOException", e);
			}
		}
		
		return writer.toString();
	}
	
	/**
	 * Merge data with velocity template.
	 *
	 * @param templateFile the template file
	 * @param workitem the workitem
	 * @return the string
	 */
	public static String mergeFromTemplate(String templateFile, IWorkitem workitem) {
		StringWriter writer = new StringWriter();
		try {
			VelocityEngine velocityEngine = new VelocityEngine();
			velocityEngine.init();

			Template t = velocityEngine.getTemplate(templateFile);

			LOGGER.debug("Content has encoding "+t.getEncoding()+": "+templateFile);
			
			
			VelocityContext context = new VelocityContext();
			context.put("repository", Repository.getInstance());
			context.put("objectUtil", new ObjectUtil());
			context.put("stringUtil", new StringUtil());
			if (workitem!=null) {
				context.put("workitem", workitem);
				if (workitem instanceof Person) {
					context.put("person", workitem);
				}
				if (workitem instanceof Role) {
					context.put("role", workitem);
				}
				if (workitem instanceof Rolegroup) {
					context.put("rolegroup", workitem);
				}
				if (workitem instanceof Team) {
					context.put("team", workitem);
				}
				if (workitem instanceof Activity) {
					context.put("activity", workitem);
				}
			}
			
			t.merge(context, writer);

			return writer.toString();
		} finally {
			try {
				writer.close();
			} catch (IOException e) {
				throw new AssertionError("StringWriter#close() should not throw IOException", e);
			}
		}
	}
	
	/**
	 * Check if string contains insenstitive string.
	 *
	 * @param wantedStr the wanted string which is searched insensitive
	 * @param source the source string which should be searched
	 * @return true, if source successful contains wanted string insensitive
	 */
	public static boolean containsInsensitive(String wantedStr, String source) {
		return Pattern.compile(Pattern.quote(wantedStr), Pattern.CASE_INSENSITIVE).matcher(source).find();
	}

	/**
	 * Replace text which is insensitive target in source with '' .
	 *
	 * @param source the source which should be corrected
	 * @param target the target which should be replaced
	 * @return the string with the replaced insensitive target text
	 */
	public static String replaceInsensitive(String source, String target) {
		String replacement = "";
		String result = Pattern.compile(target, Pattern.LITERAL | Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE)
				.matcher(source).replaceAll(Matcher.quoteReplacement(replacement));
		return result;
	}

}
