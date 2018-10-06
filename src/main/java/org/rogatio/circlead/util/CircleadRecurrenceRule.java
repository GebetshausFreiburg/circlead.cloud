package org.rogatio.circlead.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dmfs.rfc5545.DateTime;
import org.dmfs.rfc5545.Weekday;
import org.dmfs.rfc5545.recur.Freq;
import org.dmfs.rfc5545.recur.InvalidRecurrenceRuleException;
import org.dmfs.rfc5545.recur.RecurrenceRule;
import org.dmfs.rfc5545.recur.RecurrenceRule.Part;
import org.dmfs.rfc5545.recur.RecurrenceRuleIterator;

public class CircleadRecurrenceRule {

	final static Logger LOGGER = LogManager.getLogger(CircleadRecurrenceRule.class);

	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append(recurrenceRule.toString());

		if (durationunit != null) {
			sb.append(";" + durationunit + "=");
			sb.append(duration);
		}

		return sb.toString();
	}

	private RecurrenceRule recurrenceRule;

	public CircleadRecurrenceRule(String rule) {
		recurrenceRule = this.convert(rule);
	}

	public void setWeekStart(Weekday wkst) {
		recurrenceRule.setWeekStart(wkst);
	}

	public CircleadRecurrenceRule(Freq freq) {
		recurrenceRule = new RecurrenceRule(freq);
		this.type = this.TYPE_RFC5545;
	}

	private String clean(String rule) {
		if (rule.startsWith("R=")) {
			rule = rule.substring(2, rule.length());
		}
		if (rule.startsWith("R:")) {
			rule = rule.substring(2, rule.length());
		}
		if (rule.startsWith("RRULE=")) {
			rule = rule.substring(6, rule.length());
		}
		if (rule.startsWith("RRULE:")) {
			rule = rule.substring(6, rule.length());
		}
		return rule.toUpperCase();
	}

	private RecurrenceRule getRecurrenceRule(String rule) {
		try {
			RecurrenceRule rrule = new RecurrenceRule(clean(rule));
			rrule.setByPart(Part.BYMINUTE, 0);
			rrule.setByPart(Part.BYSECOND, 0);
			return rrule;
		} catch (InvalidRecurrenceRuleException e) {
			return null;
		}
	}

	public void setDuration(Duration durationunit, int duration) {
		this.durationunit = durationunit.name();
		this.duration = duration;
	}

	private int duration;
	private String durationunit;

	private String rawRule;

	public String getRawRule() {
		return rawRule;
	}

	public double getAverageAllokation(Freq freq) {
		return getAverageAllokation(freq.name());
	}

	public double getAverageAllokation(String freq) {

		double multiplier = 0;
		if (freq.equals(Freq.SECONDLY.name())) {
			multiplier = 1.0 / (60.0 * 60.0);
		} else if (freq.equals(Freq.MINUTELY.name())) {
			multiplier = 1.0 / 60.0;
		} else if (freq.equals(Freq.HOURLY.name())) {
			multiplier = 1.0;
		} else if (freq.equals(Freq.DAILY.name())) {
			multiplier = 1.0 * 8.0;
		} else if (freq.equals(Freq.WEEKLY.name())) {
			multiplier = 1.0 * 8.0 * 5.0;
		} else if (freq.equals(Freq.MONTHLY.name())) {
			multiplier = 1.0 * 8.0 * 5.0 * 4.0;
		} else if (freq.equals("QUATERLY") || freq.equals("QUATER")) {
			multiplier = 8.0 * (220.0 / 4.0);
		} else if (freq.equals(Freq.YEARLY.name())) {
			multiplier = 8.0 * 220.0;
		}

		double allokationUnit = 0.0;
		if (this.durationunit != null) {
			if (this.durationunit.equals(DURATIONBYHOUR)) {
				allokationUnit = 1.0;
			} else if (this.durationunit.equals(DURATIONBYDAY)) {
				allokationUnit = 8.0;
			} else if (this.durationunit.equals(DURATIONBYWEEK)) {
				allokationUnit = 40.0;
			} else if (this.durationunit.equals(DURATIONBYMONTH)) {
				allokationUnit = 160.0;
			} else if (this.durationunit.equals(DURATIONBYQUARTER)) {
				allokationUnit = (160.0 * 3.0);
			}
		} else {
			return 0.0;
		}

		double divider = 0;
		if (this.recurrenceRule.getFreq() == Freq.SECONDLY) {
			divider = 1.0 / (60.0 * 60.0);
		}
		if (this.recurrenceRule.getFreq() == Freq.MINUTELY) {
			divider = 1.0 / 60.0;
		}
		if (this.recurrenceRule.getFreq() == Freq.HOURLY) {
			divider = 1.0;
		}
		if (this.recurrenceRule.getFreq() == Freq.DAILY) {
			divider = 8.0 * 1.0;
		}
		if (this.recurrenceRule.getFreq() == Freq.WEEKLY) {
			divider = 8.0 * 5.0;
			
			/*List<Integer> daysInMonth = this.recurrenceRule.getByPart(Part.BYDAY);
			if (ObjectUtil.isListNotNullAndEmpty(daysInMonth)) {
				divider /= daysInMonth.size();
			}*/
		}
		if (this.recurrenceRule.getFreq() == Freq.MONTHLY && this.recurrenceRule.getInterval() == 1) {
			divider = 8.0 * 20.0;
			
			List<Integer> daysInMonth = this.recurrenceRule.getByPart(Part.BYMONTHDAY);
			if (ObjectUtil.isListNotNullAndEmpty(daysInMonth)) {
				divider /= daysInMonth.size();
			}
		}
		if (this.recurrenceRule.getFreq() == Freq.MONTHLY && this.recurrenceRule.getInterval() == 3) {
			divider = 8.0 * (220.0 / 4.0);
		}
		if (this.recurrenceRule.getFreq() == Freq.YEARLY) {
			divider = 8.0 * 220.0;
			
			List<Integer> daysInYear = this.recurrenceRule.getByPart(Part.BYYEARDAY);
			if (ObjectUtil.isListNotNullAndEmpty(daysInYear)) {
				divider /= daysInYear.size();
			}
			
			List<Integer> weeksInYear = this.recurrenceRule.getByPart(Part.BYWEEKNO);
			if (ObjectUtil.isListNotNullAndEmpty(weeksInYear)) {
				divider /= weeksInYear.size();
			}
			
			List<Integer> monthsInYear = this.recurrenceRule.getByPart(Part.BYMONTH);
			if (ObjectUtil.isListNotNullAndEmpty(monthsInYear)) {
				divider /= monthsInYear.size();
			}
		}

		if (divider == 0.0) {
			return 0.0;
		}

		return multiplier * this.duration * allokationUnit / divider;
	}

	/*
	 * public double getAllokation(String freq) {
	 * 
	 * double divider = 0;
	 * 
	 * if (freq.equals(Freq.SECONDLY.name())) { divider = 11.0 * 4.0 * 5.0 * 8.0 *
	 * 60.0 * 60.0; } else if (freq.equals(Freq.MINUTELY.name())) { divider = 11.0 *
	 * 4.0 * 5.0 * 8.0 * 60.0; } else if (freq.equals(Freq.HOURLY.name())) { divider
	 * = 11.0 * 4.0 * 5.0 * 8.0; } else if (freq.equals(Freq.DAILY.name())) {
	 * divider = 11.0 * 4.0 * 5.0; } else if (freq.equals(Freq.WEEKLY.name())) {
	 * divider = 11.0 * 4.0; } else if (freq.equals(Freq.MONTHLY.name())) { divider
	 * = 11.0; } else if (freq.equals("QUATERLY") || freq.equals("QUATER")) {
	 * divider = 11.0 / 4.0; } else if (freq.equals(Freq.YEARLY.name())) { divider =
	 * 1.0; }
	 * 
	 * divider = divider / (11.0 * 4 * 5);
	 * 
	 * System.out.println("divider" + divider);
	 * 
	 * double allokationUnit = 0.0; if (this.durationunit.equals(DURATIONBYHOUR)) {
	 * allokationUnit = 1.0; } else if (this.durationunit.equals(DURATIONBYDAY)) {
	 * allokationUnit = 8.0; } else if (this.durationunit.equals(DURATIONBYWEEK)) {
	 * allokationUnit = 40.0; } else if (this.durationunit.equals(DURATIONBYMONTH))
	 * { allokationUnit = 160.0; } else if
	 * (this.durationunit.equals(DURATIONBYQUARTER)) { allokationUnit = (160.0 *
	 * 3.0); }
	 * 
	 * System.out.println("allocUnit" + allokationUnit);
	 * 
	 * double amount = 0; // System.out.println(recurrenceRule.getFreq()); if
	 * (this.recurrenceRule.getFreq() == Freq.SECONDLY) { amount = 1.0 * 60.0 *
	 * 60.0; } if (this.recurrenceRule.getFreq() == Freq.MINUTELY) { amount = 1.0 *
	 * 60.0; } if (this.recurrenceRule.getFreq() == Freq.HOURLY) { amount = 1.0; }
	 * if (this.recurrenceRule.getFreq() == Freq.DAILY) { amount = 1.0 / 8.0; } if
	 * (this.recurrenceRule.getFreq() == Freq.WEEKLY) { amount = 1.0 / 40.0; } if
	 * (this.recurrenceRule.getFreq() == Freq.MONTHLY &&
	 * this.recurrenceRule.getInterval() == 1) { amount = 1.0 / (40.0 * 4.0); } if
	 * (this.recurrenceRule.getFreq() == Freq.MONTHLY &&
	 * this.recurrenceRule.getInterval() == 3) { amount = 1.0 / (40.0 * 4.0 * 3.0);
	 * } if (this.recurrenceRule.getFreq() == Freq.YEARLY) { amount = 1.0 / (40.0 *
	 * 4.0 * 12.0); }
	 * 
	 * System.out.println("amount" + amount); System.out.println("duration" +
	 * duration);
	 * 
	 * amount = 1.0; // divider = 1.0 ;
	 * 
	 * return amount * this.duration * allokationUnit * divider; }
	 */

	private RecurrenceRule convert(String s) {

		s = clean(s);

		rawRule = s;

		if (getType(s).equals(TYPE_CIRCLEAD)) {

			RecurrenceRule rule = null;

			if (!s.contains("/")) {
				s = s.trim() + "/W";
			}

			String[] oc = s.split("/");
			String duration = oc[0];
			String period = oc[1];
//			System.out.println(s);
//System.out.println(duration);

			if (duration.endsWith("H")) {
				durationunit = DURATIONBYHOUR;
			}
			if (duration.endsWith("D")) {
				durationunit = DURATIONBYDAY;
			}
			if (duration.endsWith("W")) {
				durationunit = DURATIONBYWEEK;
			}
			if (duration.endsWith("M")) {
				durationunit = DURATIONBYMONTH;
			}
			if (duration.endsWith("Q")) {
				durationunit = DURATIONBYQUARTER;
			}
//			System.out.println(durationunit);

			int hour = 0;
			String d = duration;
			for (Weekday wd : Weekday.values()) {
				if (d.contains(wd.name())) {
					int idx = d.indexOf(wd.name());
					String x = d.substring(0, idx);
					try {
						hour = Integer.parseInt(x);
					} catch (Exception e) {
					}
					d = d.substring(idx, d.length());
				}
				d = d.replace(wd.name(), "");
			}

			d = d.replace("H", "").replace("D", "").replace("W", "").replace("M", "").replace("Q", "");

			this.duration = Integer.parseInt(d);

			if (period.contains("D")) {
				rule = new RecurrenceRule(Freq.DAILY);
			}
			if (period.contains("W")) {
				rule = new RecurrenceRule(Freq.WEEKLY);
			}
			if (period.contains("M")) {
				rule = new RecurrenceRule(Freq.MONTHLY);
			}
			if (period.contains("Q")) {
				rule = new RecurrenceRule(Freq.MONTHLY);
				rule.setInterval(3);
			}
			if (period.contains("Y")) {
				rule = new RecurrenceRule(Freq.YEARLY);
			}

			if (duration.contains(Weekday.SU.name())) {
				rule.setWeekStart(Weekday.SU, true);
			}

			if (duration.contains(Weekday.MO.name())) {
				rule.setWeekStart(Weekday.MO, true);
			}

			if (duration.contains(Weekday.TU.name())) {
				rule.setWeekStart(Weekday.TU, true);
			}

			if (duration.contains(Weekday.WE.name())) {
				rule.setWeekStart(Weekday.WE, true);
			}

			if (duration.contains(Weekday.TH.name())) {
				rule.setWeekStart(Weekday.TH, true);
			}

			if (duration.contains(Weekday.FR.name())) {
				rule.setWeekStart(Weekday.FR, true);
			}

			if (duration.contains(Weekday.SA.name())) {
				rule.setWeekStart(Weekday.SA, true);
			}

			try {
				rule.setByPart(Part.BYHOUR, hour);
				rule.setByPart(Part.BYMINUTE, 0);
				rule.setByPart(Part.BYSECOND, 0);
			} catch (InvalidRecurrenceRuleException e) {
			}

			return rule;
		}

		if (getType(s).equals(TYPE_RFC5545)) {

			String durationPattern = "(" + DURATIONBYHOUR + "|" + DURATIONBYDAY + "|" + DURATIONBYWEEK + "|"
					+ DURATIONBYMONTH + "|" + DURATIONBYQUARTER + ")=[0-9]+";

			Pattern pattern = Pattern.compile(durationPattern);
			Matcher matcher = pattern.matcher(s);

			String foundDuration = null;
			while (matcher.find()) {
				foundDuration = s.substring(matcher.start(), matcher.end());
			}

			if (foundDuration != null) {
				if (foundDuration.contains(DURATIONBYHOUR)) {
					durationunit = DURATIONBYHOUR;
					this.duration = Integer.parseInt(foundDuration.replace(DURATIONBYHOUR + "=", ""));
				}
				if (foundDuration.contains(DURATIONBYDAY)) {
					durationunit = DURATIONBYDAY;
					this.duration = Integer.parseInt(foundDuration.replace(DURATIONBYDAY + "=", ""));
				}
				if (foundDuration.contains(DURATIONBYWEEK)) {
					durationunit = DURATIONBYWEEK;
					this.duration = Integer.parseInt(foundDuration.replace(DURATIONBYWEEK + "=", ""));
				}
				if (foundDuration.contains(DURATIONBYMONTH)) {
					durationunit = DURATIONBYMONTH;
					this.duration = Integer.parseInt(foundDuration.replace(DURATIONBYMONTH + "=", ""));
				}
				if (foundDuration.contains(DURATIONBYQUARTER)) {
					durationunit = DURATIONBYQUARTER;
					this.duration = Integer.parseInt(foundDuration.replace(DURATIONBYQUARTER + "=", ""));
				}

				// Create regular RRULE
				s = s.replace(foundDuration, "").replace(";;", "");
				if (s.endsWith(";")) {
					s = s.substring(0, s.length() - 1);
				}
			}

			return getRecurrenceRule(s);
		}

		return null;
	}

	public enum Duration {
		DURATIONBYHOUR, DURATIONBYDAY, DURATIONBYWEEK, DURATIONBYMONTH, DURATIONBYQUARTER;
	}

	private final String DURATIONBYHOUR = "DURATIONBYHOUR";
	private final String DURATIONBYDAY = "DURATIONBYDAY";
	private final String DURATIONBYWEEK = "DURATIONBYWEEK";
	private final String DURATIONBYMONTH = "DURATIONBYMONTH";
	private final String DURATIONBYQUARTER = "DURATIONBYQUARTER";

	private final String TYPE_CIRCLEAD = "CIRCLEAD";
	private final String TYPE_RFC5545 = "RFC5545";
	private final String TYPE_UNDEFINED = "UNDEFINED";

	private String type;

	public String getType() {
		return type;
	}

	private String getType(String rule) {
		final String circleadTypeRegex = "([0-9]+)?(SU|MO|TU|WE|TH|FR|SA)?([0-9]+)(H|D|W|M|Q)(/(D|W|M|Q|Y))?";

		if (clean(rule).matches(circleadTypeRegex)) {
			type = TYPE_CIRCLEAD;
			return TYPE_CIRCLEAD;
		}

		if (clean(rule).startsWith("FREQ=")) {
			// See https://tools.ietf.org/html/rfc5545#page-37
			type = TYPE_RFC5545;
			return TYPE_RFC5545;
		}

		type = TYPE_UNDEFINED;
		return TYPE_UNDEFINED;
	}

	public void setCount(int count) {
		recurrenceRule.setCount(count);
	}

	public boolean isInfinite() {
		return recurrenceRule.isInfinite();
	}

	public DateTime getEndDate(DateTime startDateTime) {
		return getEndDate(startDateTime.toString());
	}

	public DateTime getEndDate(String startDateTime) {
		String startRepresentation = startDateTime;

		DateFormat df = null;
		if (startRepresentation.contains("T")) {
			startRepresentation = startRepresentation.replace("T", "");
			df = new SimpleDateFormat("yyyyMMddHHmmss");
		} else {
			df = new SimpleDateFormat("yyyyMMdd");
		}

		try {
			Date parsedDate = df.parse(startRepresentation);

			Calendar calendarDate = Calendar.getInstance();
			calendarDate.setTime(parsedDate);

			if (durationunit != null) {
				if (durationunit.equals(DURATIONBYHOUR)) {
					calendarDate.add(Calendar.HOUR, duration);
				}
				if (durationunit.equals(DURATIONBYDAY)) {
					calendarDate.add(Calendar.DAY_OF_YEAR, duration);
				}
				if (durationunit.equals(DURATIONBYWEEK)) {
					calendarDate.add(Calendar.DAY_OF_YEAR, 7 * duration);
				}
				if (durationunit.equals(DURATIONBYMONTH)) {
					calendarDate.add(Calendar.MONTH, duration);
				}
				if (durationunit.equals(DURATIONBYQUARTER)) {
					calendarDate.add(Calendar.MONTH, 3 * duration);
				}
			}

			DateFormat dfout = new SimpleDateFormat("yyyyMMdd'T'HHmmss");
			String x = dfout.format(calendarDate.getTime());

			if (x.endsWith("T000000")) {
				x = x.replace("T000000", "");
			}

			DateTime y = DateTime.parse(x);

			return y;
		} catch (ParseException e) {
			LOGGER.error("Error on creation of End-DateTime", e);
		}

		return null;
	}

	public Event getEvent(String startDate) {
		return getEvent(DateTime.parse(startDate));
	}

	public Event getEvent(DateTime startDate) {
		DateTime s = startDate;
		DateTime e = this.getEndDate(s);
		Event evt = new Event(s, e, this);
		return evt;
	}

	public Event getEvent() {
		return getEvent(this.getStartDate());
	}

	public DateTime getStartDate() {
		List<DateTime> list = getStartDateList(1);
		if (list.size() == 1) {
			return list.get(0);
		}
		return null;
	}

	public DateTime getStartDate(String startDateTime) {
		List<DateTime> list = getStartDateList(startDateTime, 1);
		if (list.size() == 1) {
			return list.get(0);
		}
		return null;
	}

	public List<Event> getEventList(int maxInstances) {
		List<Event> events = new ArrayList<Event>();
		List<DateTime> list = getStartDateList(DateTime.nowAndHere(), maxInstances);
		for (DateTime dateTime : list) {
			Event e = this.getEvent(dateTime);
			events.add(e);
		}
		return events;
	}

	public String getDetails() {
		return this.getType() + " : " + this.getRawRule() + " : " + this.toString();
	}

	public List<DateTime> getStartDateList(int maxInstances) {
		return getStartDateList(DateTime.nowAndHere(), maxInstances);
	}

	public List<Event> getEventList(DateTime startDateTime, int maxInstances) {
		return this.getEventList(startDateTime.toString(), maxInstances);
	}

	public List<Event> getEventList(String startDateTime, int maxInstances) {
		List<Event> events = new ArrayList<Event>();
		List<DateTime> list = getStartDateList(startDateTime, maxInstances);
		for (DateTime dateTime : list) {
			Event e = this.getEvent(dateTime);
			events.add(e);
		}
		return events;
	}

	public List<DateTime> getStartDateList(String startDateTime, int maxInstances) {
		if (startDateTime == null) {
			return getStartDateList(maxInstances);
		}

		return getStartDateList(DateTime.parse(startDateTime), maxInstances);
	}

	private DateTime getStartByRule(DateTime startDateTime) {
		Date date = convertDate(startDateTime);

		Calendar cx = Calendar.getInstance();
		cx.setTime(date);

		Calendar c = null;

//		System.out.println(recurrenceRule.getWeekStart());

		for (int i = 0; i <= 7; i++) {
//			if (c != null) {
//				System.out.println(i + " - " + c.getTime());
//			}
			cx.add(Calendar.DAY_OF_WEEK, 1);
			if (recurrenceRule.getWeekStart() == Weekday.MO) {
				if (cx.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
					c = (Calendar) cx.clone();
				}
			}
			if (recurrenceRule.getWeekStart() == Weekday.TU) {
				if (cx.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY) {
					c = (Calendar) cx.clone();
				}
			}
			if (recurrenceRule.getWeekStart() == Weekday.WE) {
				if (cx.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY) {
					c = (Calendar) cx.clone();
				}
			}
			if (recurrenceRule.getWeekStart() == Weekday.TH) {
				if (cx.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY) {
					c = (Calendar) cx.clone();
				}
			}
			if (recurrenceRule.getWeekStart() == Weekday.FR) {
				if (cx.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
					c = (Calendar) cx.clone();
				}
			}
			if (recurrenceRule.getWeekStart() == Weekday.SA) {
				if (cx.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
					c = (Calendar) cx.clone();
				}
			}
			if (recurrenceRule.getWeekStart() == Weekday.SU) {
				if (cx.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
					c = (Calendar) cx.clone();
				}
			}
		}

//		System.out.println(c.getTime()+" - FOUND");

		if (recurrenceRule.getFreq() == Freq.YEARLY) {
			c.add(Calendar.MONTH, -12);
		}
		if (recurrenceRule.getFreq() == Freq.MONTHLY) {
			c.add(Calendar.MONTH, -1);
		}
		if (recurrenceRule.getFreq() == Freq.WEEKLY) {
			c.add(Calendar.DAY_OF_WEEK, -7);
		}
		if (recurrenceRule.getFreq() == Freq.DAILY) {
			c.add(Calendar.DAY_OF_WEEK, -1);
		}

		DateFormat dfout = new SimpleDateFormat("yyyyMMdd'T'HHmmss");
		String x = dfout.format(c.getTime());
		if (x.endsWith("T000000")) {
			x = x.replace("T000000", "");
		}

		DateTime y = DateTime.parse(x);
//		System.out.println("FFF "+y);
		return y;

//		return null;
	}

	/**
	 * 
	 * @param startDateTime
	 * @param maxInstances  limit instances for rules that recur forever
	 */
	public List<DateTime> getStartDateList(DateTime startDateTime, int maxInstances) {

//		System.out.println("A: " + startDateTime);

		startDateTime = getStartByRule(startDateTime);

//		System.out.println("B: " + startDateTime);

		List<DateTime> list = new ArrayList<DateTime>();
		RecurrenceRuleIterator it = startDateIterator(startDateTime);
		while (it.hasNext() && (!recurrenceRule.isInfinite() || maxInstances-- > 0)) {
			DateTime nextInstance = it.nextDateTime();
			list.add(nextInstance);
		}
		return list;
	}

	/**
	 * 
	 * 
	 * @param dateTime Must be in Format yyyyMMdd or yyyyMMddThhmmss
	 * @return
	 */
	public RecurrenceRuleIterator startDateIterator(String startDateTime) {
		return startDateIterator(DateTime.parse(startDateTime));
	}

	/**
	 * Start iterator from actual data and time.
	 * 
	 * @return
	 */
	public RecurrenceRuleIterator startDateIterator() {
		return startDateIterator(DateTime.nowAndHere());
	}

	public RecurrenceRuleIterator startDateIterator(DateTime start) {
		return recurrenceRule.iterator(start);
	}

	public void setInterval(int interval) {
		recurrenceRule.setInterval(interval);
	}

	public void setByPart(Part part, List<Integer> values) {
		try {
			recurrenceRule.setByPart(part, values);
		} catch (InvalidRecurrenceRuleException e) {
			LOGGER.error("Error on adding part to recurrence rule", e);
		}
	}

	public void setByPart(Part part, Integer... values) {
		try {
			recurrenceRule.setByPart(part, values);
		} catch (InvalidRecurrenceRuleException e) {
			LOGGER.error("Error on adding part to recurrence rule", e);
		}
	}

	public class Event {
		private DateTime start;
		private DateTime end;
		private CircleadRecurrenceRule crr = null;

		private Event(DateTime start, DateTime end, CircleadRecurrenceRule crr) {
			this.start = start;
			this.end = end;
			this.crr = crr;
		}
		
		public CircleadRecurrenceRule getRule() {
			return crr;
		}

		public DateTime getStart() {
			return this.start;
		}

		public DateTime getEnd() {
			return this.end;
		}

		public String toString() {
			return "Event:" + start.toString() + " - " + end.toString();
		}

		public Date getEndDate() {
			return convertDate(end);
		}

		public Date getStartDate() {
			return convertDate(start);
		}

	}

	private Date convertDate(DateTime date) {
		String startRepresentation = date.toString();

		DateFormat df = null;
		if (startRepresentation.contains("T")) {
			startRepresentation = startRepresentation.replace("T", "");
			df = new SimpleDateFormat("yyyyMMddHHmmss");
		} else {
			df = new SimpleDateFormat("yyyyMMdd");
		}
		try {
			return df.parse(startRepresentation);
		} catch (ParseException e) {
			LOGGER.error("Error on creation of DateTime", e);
		}
		return null;
	}

}