name = "Team Category Report"
description = "Zeigt die Teams der Kategorie '" + PropertyUtil.getApplicationDefaultTeamcategory() + "' an und stellt diese in einem stundengenauen Wochenplan dar."
stylesheet = "stylesCategoryReport.css"

import java.util.ArrayList;
import java.util.List;
import org.dmfs.rfc5545.Weekday;
import org.jsoup.nodes.Element;
import org.rogatio.circlead.control.CircleadRecurrenceRule;
import org.rogatio.circlead.control.synchronizer.ISynchronizer;
import org.rogatio.circlead.control.synchronizer.atlassian.AtlassianSynchronizer;
import org.rogatio.circlead.control.synchronizer.file.FileSynchronizer;
import org.rogatio.circlead.model.work.Team;
import org.rogatio.circlead.util.StringUtil;

/**
	 * Gets the week.
	 *
	 * @return the week
	 */
	private List<Weekday> getWeek() {
		List<Weekday> weekdays = new ArrayList<Weekday>();
		weekdays.add(Weekday.MO);
		weekdays.add(Weekday.TU);
		weekdays.add(Weekday.WE);
		weekdays.add(Weekday.TH);
		weekdays.add(Weekday.FR);
		weekdays.add(Weekday.SA);
		weekdays.add(Weekday.SU);
		return weekdays;
	}

	/**
	 * Sets the hour.
	 *
	 * @param weekday the weekday
	 * @param hour    the hour
	 * @param ul      the ul
	 * @return the int
	 */
	private int setHour(Weekday weekday, int hour, Element ul) {
		List<Team> teams = R.getTeamsWithCategory(PropertyUtil.getApplicationDefaultTeamcategory());
		boolean found = false;
		int durrationTime = 0;
		for (Team team : teams) {
			String r = team.getRecurrenceRule();
			if (StringUtil.isNotNullAndNotEmpty(r)) {
				CircleadRecurrenceRule crr = new CircleadRecurrenceRule(r);
				Integer intValue = crr.getHour();
				int h = 0;
				if (intValue != null) {
					h = crr.getHour();
				}
				if (weekday == crr.getWeekday() && hour == h) {
					durrationTime = setHour(team, weekday, hour, ul);
					found = true;
				}
			}
		}
		if (!found) {
			durrationTime = setHour(null, weekday, hour, ul);
		}
		return durrationTime;
	}

	/**
	 * Sets the hour.
	 *
	 * @param team    the team
	 * @param weekday the weekday
	 * @param hour    the hour
	 * @param ul      the ul
	 * @return the int
	 */
	private int setHour(Team team, Weekday weekday, int hour, Element ul) {
		String addition = "";
		String duration = "onehour";
		String st = "";
		String s = "";

		int durrationTime = 0;

		if (team == null) {
			addition = " empty";
		} else {
			st = team.getTeamSubtype();
			s = team.getTeamType();
			String r = team.getRecurrenceRule();
			CircleadRecurrenceRule crr = new CircleadRecurrenceRule(r);
			int d = crr.getDuration();
			if (d == 1) {
				duration = "onehour";
				durrationTime = d;
			} else if (d == 2) {
				duration = "twohour";
				durrationTime = d;
			} else if (d == 3) {
				duration = "threehour";
				durrationTime = d;
			}
		}

		if (team != null) {
			if (team.getTeamSize() < 2) {
				addition = " internal";
			}
		}

		Element li = ul.appendElement("li").attr("class", duration + addition);
		String h = StringUtil.addSpace(hour + "", 2, '0');

		String spanTitle = "";
		if (team != null) {
			// spanTitle = "" + StringUtil.join(team.getTeamMembers()) + "";
		}

		li.appendElement("span").attr("title", spanTitle).appendText(h + ":00");
		if (StringUtil.isNotNullAndNotEmpty(s)) {
			li.appendText(" "+s);
		}

		if (StringUtil.isNotNullAndNotEmpty(st)) {
			li.appendElement("span").appendText(" "+st);
		}
		return durrationTime;
	}


Element element = new Element("p");

		if (synchronizer.getClass().getSimpleName().equals(AtlassianSynchronizer.class.getSimpleName())) {
			element.appendText("Not implementes for Atlassian Synchronizer");
		}

		if (synchronizer.getClass().getSimpleName().equals(FileSynchronizer.class.getSimpleName())) {
			Element uiDiv = element.appendElement("div").attr("class", "ui");
			uiDiv.appendElement("nav").attr("class", "navbar app").appendText(PropertyUtil.getApplicationDefaultTeamcategory());
			uiDiv.appendElement("nav").attr("class", "navbar board").appendText("Wochenplan");
			Element listsDiv = uiDiv.appendElement("div").attr("class", "lists");

			List<Weekday> weekdays = getWeek();
			for (Weekday weekday : weekdays) {
				Element listDiv = listsDiv.appendElement("div").attr("class", "list");
				listDiv.appendElement("header").appendText(weekday.name());
				Element ul = listDiv.appendElement("ul");

				for (int hour = 0; hour < 24; hour++) {
					int durrationTime = setHour(weekday, hour, ul);
					if (durrationTime == 2) {
						hour++;
					}
                                        if (durrationTime == 3) {
						hour++;
                                                hour++;
					}
				}

				listDiv.append("<footer>&nbsp;</footer>");
			}
		}

		return element;
