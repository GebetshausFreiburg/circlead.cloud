/*
 * Circlead - Develop and structure evolutionary Organisations
 * 
 * @author Matthias Wegner
 * @version 0.1
 * @since 01.07.2018
 * 
 */
package org.rogatio.circlead.view.report;

import java.util.ArrayList;
import java.util.List;

import org.dmfs.rfc5545.Weekday;
import org.jsoup.nodes.Element;
import org.rogatio.circlead.control.Repository;
import org.rogatio.circlead.control.synchronizer.ISynchronizer;
import org.rogatio.circlead.control.synchronizer.atlassian.AtlassianSynchronizer;
import org.rogatio.circlead.control.synchronizer.file.FileSynchronizer;
import org.rogatio.circlead.model.work.Team;
import org.rogatio.circlead.util.CircleadRecurrenceRule;
import org.rogatio.circlead.util.StringUtil;
import org.rogatio.circlead.view.ISynchronizerRendererEngine;

public class TeamCategoryReport extends DefaultReport {

	private String category;

	public TeamCategoryReport(String category) {
		this.setName("Team Category Report");
		this.category = category;
	}

	@Override
	public List<String> getHead() {
		List<String> list = new ArrayList<String>();
		list.add(
				"<link href='http://fonts.googleapis.com/css?family=Open+Sans&subset=latin' rel='stylesheet' type='text/css'>");
		list.add("<link rel=\"stylesheet\" href=\"stylesCategoryReport.css\">");
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.rogatio.circlead.view.DefaultReport#render(org.rogatio.circlead.control.
	 * synchronizer.ISynchronizer)
	 */
	@Override
	public Element render(ISynchronizer synchronizer) {
		ISynchronizerRendererEngine renderer = synchronizer.getRenderer();
		Element element = new Element("p");

		if (synchronizer.getClass().getSimpleName().equals(AtlassianSynchronizer.class.getSimpleName())) {
			element.appendText("Not implementes for Atlassian Synchronizer");
		}

		if (synchronizer.getClass().getSimpleName().equals(FileSynchronizer.class.getSimpleName())) {
//			Document doc = new Document("");
//			doc.charset(Charset.forName("UTF-8"));
//			Element html = doc.appendElement("html");
//			Element head = html.appendElement("head");
//			head.append(
//					"<link href='http://fonts.googleapis.com/css?family=Open+Sans&subset=latin' rel='stylesheet' type='text/css'>");
//			head.append("<link rel=\"stylesheet\" href=\"stylesPrayHour.css\">");
//			head.append("<meta charset=\"utf-8\">");
//			Element body = html.appendElement("body");

			Element uiDiv = element.appendElement("div").attr("class", "ui");
			uiDiv.appendElement("nav").attr("class", "navbar app").appendText(category);
			uiDiv.appendElement("nav").attr("class", "navbar board").appendText("Wochenplan");
			Element listsDiv = uiDiv.appendElement("div").attr("class", "lists");

			List<Weekday> weekdays = getWeek();
			for (Weekday weekday : weekdays) {
				Element listDiv = listsDiv.appendElement("div").attr("class", "list");
				listDiv.appendElement("header").appendText(weekday.name());
				Element ul = listDiv.appendElement("ul");

				for (int hour = 0; hour < 24; hour++) {
					int durrationTime = setHour(weekday, hour, ul);
					if (durrationTime > 1) {
						hour++;
					}
				}

				listDiv.append("<footer>&nbsp;</footer>");
			}
		}

		return element;
	}

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

	private final String ONEHOUR = "onehour";
	private final String TWOHOUR = "twohour";

	private int setHour(Weekday weekday, int hour, Element ul) {
		List<Team> teams = Repository.getInstance().getTeamsWithCategory(category);
		boolean found = false;
		int durrationTime = 0;
		for (Team team : teams) {
			String r = team.getRecurrenceRule();
			CircleadRecurrenceRule crr = new CircleadRecurrenceRule(r);
			int h = crr.getHour();
			if (weekday == crr.getWeekday() && hour == h) {
				durrationTime = setHour(team, weekday, hour, ul);
				found = true;
			}
		}
		if (!found) {
			durrationTime = setHour(null, weekday, hour, ul);
		}
		return durrationTime;
	}

	private int setHour(Team team, Weekday weekday, int hour, Element ul) {
		String addition = "";
		String duration = ONEHOUR;
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
				duration = ONEHOUR;
				durrationTime = d;
			} else if (d == 2) {
				duration = TWOHOUR;
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
		//	spanTitle = "" + StringUtil.join(team.getTeamMembers()) + "";
		}

		li.appendElement("span").attr("title", spanTitle).appendText(h + ":00");
		if (StringUtil.isNotNullAndNotEmpty(s)) {
			li.appendText(s);
		}

		if (StringUtil.isNotNullAndNotEmpty(st)) {
			li.appendElement("span").appendText(st);
		}
		return durrationTime;
	}

}
