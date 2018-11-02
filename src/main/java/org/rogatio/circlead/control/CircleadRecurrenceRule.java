package org.rogatio.circlead.control;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
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
import org.rogatio.circlead.model.data.Timeslice;
import org.rogatio.circlead.util.ObjectUtil;
import org.rogatio.circlead.util.StringUtil;

/**
 * The Class CircleadRecurrenceRule allows reccurence-rule-manipulation.
 * 
 * @see https://www.textmagic.com/free-tools/rrule-generator
 */
public class CircleadRecurrenceRule {

	/** The Constant WEEKDAYS contains the enum Weekday as ordered list, beginning on monday. */
	final static public List<Weekday> WEEKDAYS = new ArrayList<Weekday>();

	/** The Constant DAYOFWEEK2WEEKDAY maps 1 to monday, 2 to tuesday, .... */
	final static public Map<Integer, Weekday> DAYOFWEEK2WEEKDAY = new HashMap<Integer, Weekday>();

	/**
	 * The Constant WEEKDAY2DAYOFWEEK maps weekday-enum to number. monday is 1,
	 * tuesday is 2
	 */
	final static public Map<Weekday, Integer> WEEKDAY2DAYOFWEEK = new HashMap<Weekday, Integer>();

	/**  The Constant WEEKDAYS2GERMAN map enum weekday to german name of weekday. */
	final static public Map<Weekday, String> WEEKDAYS2GERMAN = new HashMap<Weekday, String>();

	static {
		WEEKDAYS.add(Weekday.MO);
		WEEKDAYS.add(Weekday.TU);
		WEEKDAYS.add(Weekday.WE);
		WEEKDAYS.add(Weekday.TH);
		WEEKDAYS.add(Weekday.FR);
		WEEKDAYS.add(Weekday.SA);
		WEEKDAYS.add(Weekday.SU);
		DAYOFWEEK2WEEKDAY.put(1, Weekday.MO);
		DAYOFWEEK2WEEKDAY.put(2, Weekday.TU);
		DAYOFWEEK2WEEKDAY.put(3, Weekday.WE);
		DAYOFWEEK2WEEKDAY.put(4, Weekday.TH);
		DAYOFWEEK2WEEKDAY.put(5, Weekday.FR);
		DAYOFWEEK2WEEKDAY.put(6, Weekday.SA);
		DAYOFWEEK2WEEKDAY.put(7, Weekday.SU);
		WEEKDAY2DAYOFWEEK.put(Weekday.MO, 1);
		WEEKDAY2DAYOFWEEK.put(Weekday.TU, 2);
		WEEKDAY2DAYOFWEEK.put(Weekday.WE, 3);
		WEEKDAY2DAYOFWEEK.put(Weekday.TH, 4);
		WEEKDAY2DAYOFWEEK.put(Weekday.FR, 5);
		WEEKDAY2DAYOFWEEK.put(Weekday.SA, 6);
		WEEKDAY2DAYOFWEEK.put(Weekday.SU, 7);
		WEEKDAYS2GERMAN.put(Weekday.MO, "Montag");
		WEEKDAYS2GERMAN.put(Weekday.TU, "Dienstag");
		WEEKDAYS2GERMAN.put(Weekday.WE, "Mittwoch");
		WEEKDAYS2GERMAN.put(Weekday.TH, "Donnerstag");
		WEEKDAYS2GERMAN.put(Weekday.FR, "Freitag");
		WEEKDAYS2GERMAN.put(Weekday.SA, "Samstag");
		WEEKDAYS2GERMAN.put(Weekday.SU, "Sonntag");
	}

	/** The Constant LOGGER. */
	final static Logger LOGGER = LogManager.getLogger(CircleadRecurrenceRule.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append(recurrenceRule.toString());

		if (durationunit != null) {
			sb.append(";" + durationunit + "=");
			sb.append(duration);
		}

		return sb.toString();
	}

	/** The basic recurrence rule. */
	private RecurrenceRule recurrenceRule;

	/**
	 * Instantiates a new circlead recurrence rule.
	 *
	 * @param rule the rule
	 */
	public CircleadRecurrenceRule(String rule) {
		recurrenceRule = this.convert(rule);
	}

	/**
	 * Gets the human readable rule
	 *
	 * @return the readable rule
	 */
	public String getReadableRule() {
		Weekday wkst = this.recurrenceRule.getWeekStart();

		StringBuilder sb = new StringBuilder();

		String dayname = WEEKDAYS2GERMAN.get(wkst);
		sb.append(dayname + ", ");

		List<Integer> x = this.recurrenceRule.getByPart(Part.BYHOUR);
		if (x != null) {
			int hour = x.get(0);
			sb.append(hour + " Uhr, ");
		}

		if (durationunit != null) {
			sb.append("Dauer " + duration + "h");
		}

		return sb.toString();
	}

	/**
	 * Sets the until date.
	 *
	 * @param year  the year
	 * @param month the month of the year. January is 1, february is 2, ...,
	 *              december is 12.
	 * @param day   the day
	 */
	public void setUntilDateNonAllDay(int year, int month, int day) {
		DateTime u = new DateTime(defaultTimeZone, year, month - 1, day, 0, 0, 0);
		recurrenceRule.setUntil(u);
	}

	/**
	 * Sets the start date.
	 *
	 * @param year  the year
	 * @param month the month of the year. January is 1, february is 2, ...,
	 *              december is 12.
	 * @param day   the day
	 */
	public void setStartDateNonAllDay(int year, int month, int day) {
		startDate = new DateTime(defaultTimeZone, year, month - 1, day, 0, 0, 0);
		System.out.println(startDate.isAllDay());
	}

	/**
	 * Sets the until date.
	 *
	 * @param until the new until date
	 */
	public void setUntilDateAllDay(DateTime until) {
		until = new DateTime(defaultTimeZone, until.getYear(), until.getMonth(), until.getDayOfMonth(), 0, 0, 0);
		recurrenceRule.setUntil(until);
	}

	/**
	 * Sets the week start.
	 *
	 * @param wkst the new week start
	 */
	public void setWeekStart(Weekday wkst) {
		recurrenceRule.setWeekStart(wkst);
	}

	/**
	 * Instantiates a new circlead recurrence rule.
	 *
	 * @param freq the freq
	 */
	public CircleadRecurrenceRule(Freq freq) {
		recurrenceRule = new RecurrenceRule(freq);
		this.type = this.TYPE_RFC5545;
	}

	/**
	 * Clean the recurrence-rule-representation from the prefix R or RRULE
	 *
	 * @param rule the rule
	 * @return the string
	 */
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

	/**
	 * Gets the basic recurrence rule.
	 *
	 * @param rule the rule
	 * @return the recurrence rule
	 */
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

	/**
	 * Sets the duration.
	 *
	 * @param durationunit the durationunit
	 * @param duration     the duration
	 */
	public void setDuration(Duration durationunit, int duration) {
		this.durationunit = durationunit.name();
		this.duration = duration;
	}

	/** The duration. */
	private int duration;

	/** The durationunit. */
	private String durationunit;

	/** The raw rule. */
	private String rawRule;

	/**
	 * Gets the raw rule.
	 *
	 * @return the raw rule
	 */
	public String getRawRule() {
		return rawRule;
	}

	/**
	 * Gets the allokation slices.
	 *
	 * @param freq the freq
	 * @return the allokation slices
	 */
	public List<Timeslice> getAllokationSlices(Freq freq) {
		return getAllokationSlices(freq.name());
	}

	/**
	 * Gets the allokation slices.
	 *
	 * @param freq the freq
	 * @return the allokation slices
	 */
	public List<Timeslice> getAllokationSlices(String freq) {
		List<Timeslice> list = null;
		if (freq.equals(Freq.DAILY.name())) {
			list = new ArrayList<Timeslice>();
			for (int i = 1; i <= 365; i++) {
				Timeslice val = getAllokation(Freq.DAILY.name(), i);
				if (val != null) {
					list.add(val);
				}
			}
		}
		if (freq.equals(Freq.WEEKLY.name())) {
			list = new ArrayList<Timeslice>();
			for (int i = 1; i <= 52; i++) {
				Timeslice val = getAllokation(Freq.WEEKLY.name(), i);
				if (val != null) {
					list.add(val);
				}
			}
		}
		if (freq.equals(Freq.MONTHLY.name())) {
			list = new ArrayList<Timeslice>();
			for (int i = 1; i <= 12; i++) {
				Timeslice val = getAllokation(Freq.MONTHLY.name(), i);
				if (val != null) {
					list.add(val);
				}
			}
		}
		if (freq.equals("QUATERLY")) {
			list = new ArrayList<Timeslice>();
			for (int i = 1; i <= 4; i++) {
				Timeslice val = getAllokation("QUATERLY", i);
				if (val != null) {
					list.add(val);
				}
			}
		}
		return list;
	}

	/**
	 * Gets the average allokation.
	 *
	 * @param freq the freq
	 * @return the average allokation
	 */
	public double getAverageAllokation(Freq freq) {
		return getAverageAllokation(freq.name());
	}

	/**
	 * Gets the allokation.
	 *
	 * @param freq     the freq
	 * @param interval the interval
	 * @return the allokation
	 */
	public Timeslice getAllokation(String freq, int interval) {
		if (freq.equals(Freq.DAILY.name())) {
			if (interval < 0 || interval > 365) {
				return null;
			}
		} else if (freq.equals(Freq.WEEKLY.name())) {
			if (interval < 0 || interval > 52) {
				return null;
			}
		} else if (freq.equals(Freq.MONTHLY.name())) {
			if (interval < 0 || interval > 12) {
				return null;
			}
		} else if (freq.equals("QUATERLY")) {
			if (interval < 0 || interval > 4) {
				return null;
			}
		} else {
			return null;
		}

		double allokationUnit = getAllokationUnit();

		Calendar start = Calendar.getInstance(defaultTimeZone);
		Calendar end = null;
		start.set(Calendar.MILLISECOND, 0);
		start.set(Calendar.SECOND, 0);
		start.set(Calendar.MINUTE, 0);
		start.set(Calendar.HOUR_OF_DAY, 0);
		if (freq.equals(Freq.DAILY.name())) {
			start.add(Calendar.DAY_OF_YEAR, interval - 1);
			end = Calendar.getInstance();
			end.setTime(start.getTime());
			end.add(Calendar.DAY_OF_YEAR, 1);
		}
		if (freq.equals(Freq.WEEKLY.name())) {
			start.add(Calendar.WEEK_OF_YEAR, interval - 1);
			start.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
			end = Calendar.getInstance();
			end.setTime(start.getTime());
			end.add(Calendar.DAY_OF_YEAR, 7);
		}
		if (freq.equals(Freq.MONTHLY.name())) {
			start.add(Calendar.MONTH, interval - 1);
			start.set(Calendar.DAY_OF_MONTH, 1);
			end = Calendar.getInstance();
			end.setTime(start.getTime());
			end.add(Calendar.MONTH, 1);
		}
		if (freq.equals("QUATERLY")) {
			if (start.get(Calendar.MONTH) >= 0 && start.get(Calendar.MONTH) <= 2) {
				start.set(Calendar.MONTH, 0);
				start.add(Calendar.MONTH, (interval - 1) * 3);
			}
			if (start.get(Calendar.MONTH) >= 3 && start.get(Calendar.MONTH) <= 5) {
				start.set(Calendar.MONTH, 3);
				start.add(Calendar.MONTH, (interval - 1) * 3);
			}
			if (start.get(Calendar.MONTH) >= 6 && start.get(Calendar.MONTH) <= 8) {
				start.set(Calendar.MONTH, 6);
				start.add(Calendar.MONTH, (interval - 1) * 3);
			}
			if (start.get(Calendar.MONTH) >= 9 && start.get(Calendar.MONTH) <= 11) {
				start.set(Calendar.MONTH, 9);
				start.add(Calendar.MONTH, (interval - 1) * 3);
			}
			start.set(Calendar.DAY_OF_MONTH, 1);
			end = Calendar.getInstance();
			end.setTime(start.getTime());
			end.add(Calendar.MONTH, 3);
		}

		double sum = 0;
		List<Event> events = this.getEventList(365);
		for (Event event : events) {
			if (event.getStartDate().after(start.getTime()) && event.getStartDate().before(end.getTime())) {
				sum += this.duration * allokationUnit;

			}
		}

		int unitValue = 0;
		String sliceStart = null;
		if (freq.equals(Freq.DAILY.name())) {
			SimpleDateFormat sdf = new SimpleDateFormat("yy");
			String date = sdf.format(start.getTime());
			sliceStart = (StringUtil.addSpace(date + "/D" + start.get(Calendar.DAY_OF_YEAR), 3, '0'));
			unitValue = start.get(Calendar.DAY_OF_YEAR);
		}
		if (freq.equals(Freq.WEEKLY.name())) {
			SimpleDateFormat sdf = new SimpleDateFormat("yy");
			String date = sdf.format(start.getTime());
			if (start.get(Calendar.YEAR) < end.get(Calendar.YEAR)) {
				date = sdf.format(end.getTime());
			}
			sliceStart = (StringUtil.addSpace(date + "/W" + start.get(Calendar.WEEK_OF_YEAR), 2, '0'));
			unitValue = start.get(Calendar.WEEK_OF_YEAR);
		}

		if (freq.equals(Freq.MONTHLY.name())) {
			SimpleDateFormat sdf = new SimpleDateFormat("yy/'M'MM");
			String date = sdf.format(start.getTime());
			sliceStart = (date);
			unitValue = start.get(Calendar.MONTH) + 1;
		}

		if (freq.equals("QUATERLY")) {
			if (start.get(Calendar.MONTH) >= 0 && start.get(Calendar.MONTH) <= 2) {
				SimpleDateFormat sdf = new SimpleDateFormat("yy/'Q1'");
				sliceStart = sdf.format(start.getTime());
				unitValue = 1;
			}
			if (start.get(Calendar.MONTH) >= 3 && start.get(Calendar.MONTH) <= 5) {
				SimpleDateFormat sdf = new SimpleDateFormat("yy/'Q2'");
				sliceStart = sdf.format(start.getTime());
				unitValue = 2;
			}
			if (start.get(Calendar.MONTH) >= 6 && start.get(Calendar.MONTH) <= 8) {
				SimpleDateFormat sdf = new SimpleDateFormat("yy/'Q3'");
				sliceStart = sdf.format(start.getTime());
				unitValue = 3;
			}
			if (start.get(Calendar.MONTH) >= 9 && start.get(Calendar.MONTH) <= 11) {
				SimpleDateFormat sdf = new SimpleDateFormat("yy/'Q4'");
				sliceStart = sdf.format(start.getTime());
				unitValue = 4;
			}

		}

		Timeslice ts = new Timeslice();
		ts.setStart(start);
		ts.setEnd(end);
		ts.setAllokation(sum);
		ts.setUnitValue(unitValue);
		ts.setSliceStart(sliceStart);
		ts.setFreq(freq);

		return ts;
	}

	/** The default time zone. */
	private TimeZone defaultTimeZone = TimeZone.getDefault();

	/**
	 * Sets the default time zone.
	 *
	 * @param timeZone the new default time zone
	 */
	public void setDefaultTimeZone(TimeZone timeZone) {
		defaultTimeZone = timeZone;
	}

	/**
	 * Gets the multiplier.
	 *
	 * @param freq the freq
	 * @return the multiplier
	 */
	private double getMultiplier(String freq) {
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
		return multiplier;
	}

	/**
	 * Gets the allokation unit.
	 *
	 * @return the allokation unit
	 */
	private double getAllokationUnit() {
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
		return allokationUnit;
	}

	/**
	 * Gets the divider.
	 *
	 * @return the divider
	 */
	private double getDivider() {
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

		return divider;
	}

	/**
	 * Gets the average allokation.
	 *
	 * @param freq the freq
	 * @return allokation in hours per week
	 */
	public double getAverageAllokation(String freq) {

		double multiplier = getMultiplier(freq);

		double allokationUnit = getAllokationUnit();
		if (allokationUnit == 0.0) {
			return 0.0;
		}

		double divider = getDivider();
		if (divider == 0.0) {
			return 0.0;
		}

		return multiplier * this.duration * allokationUnit / divider;
	}

	/**
	 * Gets the durationunit.
	 *
	 * @return the durationunit
	 */
	public String getDurationunit() {
		return this.durationunit;
	}

	/**
	 * Gets the duration.
	 *
	 * @return the duration
	 */
	public int getDuration() {
		return this.duration;
	}

	/**
	 * Gets the weekday.
	 *
	 * @return the weekday
	 */
	public Weekday getWeekday() {
		return this.recurrenceRule.getWeekStart();
	}

	/**
	 * Gets the hour.
	 *
	 * @return the hour
	 */
	public Integer getHour() {
		List<Integer> hours = this.recurrenceRule.getByPart(Part.BYHOUR);
		if (hours.size() > 0) {
			return hours.get(0);
		}
		return null;
	}

	/**
	 * Convert.
	 *
	 * @param s the s
	 * @return the recurrence rule
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

	/**
	 * The Enum Duration.
	 */
	public enum Duration {

		/** The durationbyhour. */
		DURATIONBYHOUR,
		/** The durationbyday. */
		DURATIONBYDAY,
		/** The durationbyweek. */
		DURATIONBYWEEK,
		/** The durationbymonth. */
		DURATIONBYMONTH,
		/** The durationbyquarter. */
		DURATIONBYQUARTER;
	}

	/** The durationbyhour. */
	private final String DURATIONBYHOUR = "DURATIONBYHOUR";

	/** The durationbyday. */
	private final String DURATIONBYDAY = "DURATIONBYDAY";

	/** The durationbyweek. */
	private final String DURATIONBYWEEK = "DURATIONBYWEEK";

	/** The durationbymonth. */
	private final String DURATIONBYMONTH = "DURATIONBYMONTH";

	/** The durationbyquarter. */
	private final String DURATIONBYQUARTER = "DURATIONBYQUARTER";

	/** The type circlead. */
	private final String TYPE_CIRCLEAD = "CIRCLEAD";

	/** The type rfc5545. */
	private final String TYPE_RFC5545 = "RFC5545";

	/** The type undefined. */
	private final String TYPE_UNDEFINED = "UNDEFINED";

	/** The type. */
	private String type;

	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * Gets the type.
	 *
	 * @param rule the rule
	 * @return the type
	 */
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

	/**
	 * Sets the count.
	 *
	 * @param count the new count
	 */
	public void setCount(int count) {
		recurrenceRule.setCount(count);
	}

	/**
	 * Checks if is infinite.
	 *
	 * @return true, if is infinite
	 */
	public boolean isInfinite() {
		return recurrenceRule.isInfinite();
	}

	/**
	 * Gets the end date.
	 *
	 * @param startDateTime the start date time
	 * @return the end date
	 */
	public DateTime getEndDate(DateTime startDateTime) {
		return getEndDate(startDateTime.toString());
	}

	/**
	 * Gets the end date.
	 *
	 * @param startDateTime the start date time
	 * @return the end date
	 */
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

			Calendar calendarDate = Calendar.getInstance(defaultTimeZone);
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

	/**
	 * Gets the event.
	 *
	 * @param startDate the start date
	 * @return the event
	 */
	public Event getEvent(String startDate) {
		return getEvent(DateTime.parse(startDate));
	}

	/**
	 * Gets the event.
	 *
	 * @param startDate the start date
	 * @return the event
	 */
	public Event getEvent(DateTime startDate) {
		DateTime s = startDate;
		DateTime e = this.getEndDate(s);
		Event evt = new Event(s, e, this);
		return evt;
	}

	/**
	 * Gets the event.
	 *
	 * @return the event
	 */
	public Event getEvent() {
		return getEvent(this.getStartDate());
	}

	/**
	 * Sets the start date non all day.
	 *
	 * @param startDate the new start date non all day
	 */
	public void setStartDateNonAllDay(DateTime startDate) {
		this.startDate = startDate;
	}

	/**
	 * Sets the until date.
	 *
	 * @param untilDate the new until date
	 */
	public void setUntilDate(String untilDate) {
		if (startDate == null) {
			DateTime u = DateTime.parse(defaultTimeZone, untilDate);
			this.setUntilDateNonAllDay(u.getYear(), u.getMonth() + 1, u.getDayOfMonth());
		} else {
			this.setUntilDateAllDay(untilDate);
		}

	}

	/**
	 * Sets the until date all day.
	 *
	 * @param untilDate the new until date all day
	 */
	public void setUntilDateAllDay(String untilDate) {
		DateTime u = DateTime.parse(untilDate);
		recurrenceRule.setUntil(u);
	}

	/**
	 * Sets the start date.
	 *
	 * @param startDate the new start date
	 */
	public void setStartDate(String startDate) {
		setStartDateAllDay(startDate);
	}

	/**
	 * Sets the start date.
	 *
	 * @param startDate the new start date
	 */
	public void setStartDateAllDay(String startDate) {
		this.startDate = DateTime.parse(startDate);
	}

	/** The start date. */
	private DateTime startDate;

	/**
	 * Gets the start date.
	 *
	 * @return the start date
	 */
	public DateTime getStartDate() {
		// Look for first date in recurrent rule
		List<DateTime> list = getStartDateList(1);
		// if valid, then return
		if (list.size() == 1) {
			return list.get(0);
		}
		// if something went wrong, return null
		return null;
	}

	/**
	 * Gets the until date.
	 *
	 * @return the until date
	 */
	public DateTime getUntilDate() {
		return recurrenceRule.getUntil();
	}

	/**
	 * Gets the start date.
	 *
	 * @param startDateTime the start date time
	 * @return the start date
	 */
	public DateTime getStartDate(String startDateTime) {
		List<DateTime> list = getStartDateList(startDateTime, 1);
		if (list.size() == 1) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * Gets the event list.
	 *
	 * @param maxInstances the max instances
	 * @return the event list
	 */
	public List<Event> getEventList(int maxInstances) {
		List<Event> events = new ArrayList<Event>();

		List<DateTime> list = null;
		if (this.startDate != null) {
			list = getStartDateList(startDate, maxInstances);
		} else {
			list = getStartDateList(DateTime.nowAndHere(), maxInstances);
		}

		for (DateTime dateTime : list) {
			Event e = this.getEvent(dateTime);
			events.add(e);
		}
		return events;
	}

	/**
	 * Gets the details.
	 *
	 * @return the details
	 */
	public String getDetails() {
		return this.getType() + " : " + this.getRawRule() + " : " + this.toString();
	}

	/**
	 * Gets the start date list.
	 *
	 * @param maxInstances the max instances
	 * @return the start date list
	 */
	public List<DateTime> getStartDateList(int maxInstances) {
		// use given start date for recurring rule if set. if not use today
		if (startDate != null) {
			return getStartDateList(startDate, maxInstances);
		} else {
			return getStartDateList(DateTime.nowAndHere(), maxInstances);
		}
	}

	/**
	 * Gets the event list.
	 *
	 * @param startDateTime the start date time
	 * @param maxInstances  the max instances
	 * @return the event list
	 */
	public List<Event> getEventList(DateTime startDateTime, int maxInstances) {
		return this.getEventList(startDateTime.toString(), maxInstances);
	}

	/**
	 * Gets the event list.
	 *
	 * @param startDateTime the start date time
	 * @param maxInstances  the max instances
	 * @return the event list
	 */
	public List<Event> getEventList(String startDateTime, int maxInstances) {
		List<Event> events = new ArrayList<Event>();
		List<DateTime> list = getStartDateList(startDateTime, maxInstances);
		for (DateTime dateTime : list) {
			Event e = this.getEvent(dateTime);
			events.add(e);
		}
		return events;
	}

	/**
	 * Gets the start date list.
	 *
	 * @param startDateTime the start date time
	 * @param maxInstances  the max instances
	 * @return the start date list
	 */
	public List<DateTime> getStartDateList(String startDateTime, int maxInstances) {
		if (startDateTime == null) {
			return getStartDateList(maxInstances);
		}

		return getStartDateList(DateTime.parse(startDateTime), maxInstances);
	}

	/**
	 * Gets the start by rule.
	 *
	 * @param startDateTime the start date time
	 * @return the start by rule
	 */
	private DateTime getStartByRule(DateTime startDateTime) {

		if (recurrenceRule == null) {
			return null;
		}

		Date date = convertDate(startDateTime);

		Calendar cx = Calendar.getInstance(defaultTimeZone);
		cx.setTime(date);

		Calendar c = null;

		for (int i = 0; i <= 7; i++) {
			cx.add(Calendar.DAY_OF_WEEK, 1);
			if (recurrenceRule.getWeekStart() != null) {
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
		}

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

		DateTime y = DateTime.parse(defaultTimeZone, x);
		return y;
	}

	/**
	 * Gets the start date list.
	 *
	 * @param startDateTime the start date time
	 * @param maxInstances  limit instances for rules that recur forever
	 * @return the start date list
	 */
	public List<DateTime> getStartDateList(DateTime startDateTime, int maxInstances) {
		startDateTime = getStartByRule(startDateTime);
		List<DateTime> list = new ArrayList<DateTime>();
		RecurrenceRuleIterator it = startDateIterator(startDateTime);
		while (it.hasNext() && (!recurrenceRule.isInfinite() || maxInstances-- > 0)) {
			DateTime nextInstance = it.nextDateTime();
			list.add(nextInstance);
		}
		return list;
	}

	/**
	 * Start date iterator.
	 *
	 * @param startDateTime the start date time
	 * @return the recurrence rule iterator
	 */
	public RecurrenceRuleIterator startDateIterator(String startDateTime) {
		return startDateIterator(DateTime.parse(startDateTime));
	}

	/**
	 * Start iterator from actual data and time.
	 *
	 * @return the recurrence rule iterator
	 */
	public RecurrenceRuleIterator startDateIterator() {
		return startDateIterator(DateTime.nowAndHere());
	}

	/**
	 * Start date iterator.
	 *
	 * @param start the start
	 * @return the recurrence rule iterator
	 */
	public RecurrenceRuleIterator startDateIterator(DateTime start) {
		return recurrenceRule.iterator(start);
	}

	/**
	 * Sets the interval.
	 *
	 * @param interval the new interval
	 */
	public void setInterval(int interval) {
		recurrenceRule.setInterval(interval);
	}

	/**
	 * Sets the by part.
	 *
	 * @param part   the part
	 * @param values the values
	 */
	public void setByPart(Part part, List<Integer> values) {
		try {
			recurrenceRule.setByPart(part, values);
		} catch (InvalidRecurrenceRuleException e) {
			LOGGER.error("Error on adding part to recurrence rule", e);
		}
	}

	/**
	 * Sets the by part.
	 *
	 * @param part   the part
	 * @param values the values
	 */
	public void setByPart(Part part, Integer... values) {
		try {
			recurrenceRule.setByPart(part, values);
		} catch (InvalidRecurrenceRuleException e) {
			LOGGER.error("Error on adding part to recurrence rule", e);
		}
	}

	/**
	 * The Class Event.
	 */
	public class Event {

		/** The start. */
		private DateTime start;

		/** The end. */
		private DateTime end;

		/** The crr. */
		private CircleadRecurrenceRule crr = null;

		/**
		 * Instantiates a new event.
		 *
		 * @param start the start
		 * @param end   the end
		 * @param crr   the crr
		 */
		private Event(DateTime start, DateTime end, CircleadRecurrenceRule crr) {
			this.start = start;
			this.end = end;
			this.crr = crr;
		}

		/**
		 * Gets the rule.
		 *
		 * @return the rule
		 */
		public CircleadRecurrenceRule getRule() {
			return crr;
		}

		/**
		 * Gets the start.
		 *
		 * @return the start
		 */
		public DateTime getStart() {
			return this.start;
		}

		/**
		 * Gets the end.
		 *
		 * @return the end
		 */
		public DateTime getEnd() {
			return this.end;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#toString()
		 */
		public String toString() {
			return "Event:" + start.toString() + " - " + end.toString();
		}

		/**
		 * Gets the end date.
		 *
		 * @return the end date
		 */
		public Date getEndDate() {
			return convertDate(end);
		}

		/**
		 * Gets the start date.
		 *
		 * @return the start date
		 */
		public Date getStartDate() {
			return convertDate(start);
		}

	}

	/**
	 * Convert date.
	 *
	 * @param date the date
	 * @return the date
	 */
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
