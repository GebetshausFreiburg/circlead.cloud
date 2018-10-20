/*
 * Circlead - Develop and structure evolutionary Organisations
 * 
 * @author Matthias Wegner
 * @version 0.1
 * @since 01.07.2018
 * 
 */
package org.rogatio.circlead.view.report;

import static org.rogatio.circlead.model.Parameter.ACTIVITYID;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.dmfs.rfc5545.Weekday;
import org.jsoup.nodes.Element;
import org.rogatio.circlead.control.Repository;
import org.rogatio.circlead.control.synchronizer.ISynchronizer;
import org.rogatio.circlead.model.WorkitemStatusParameter;
import org.rogatio.circlead.model.work.IWorkitem;
import org.rogatio.circlead.model.work.Role;
import org.rogatio.circlead.model.work.Rolegroup;
import org.rogatio.circlead.model.work.Team;
import org.rogatio.circlead.util.CircleadRecurrenceRule;
import org.rogatio.circlead.util.ExcelUtil;
import org.rogatio.circlead.util.ObjectUtil;
import org.rogatio.circlead.util.StringUtil;
import org.rogatio.circlead.view.ISynchronizerRendererEngine;

/**
 * The Class ReworkReport.
 */
public class TeamCategegoryInternalReport extends DefaultReport {

	private String category;

	public TeamCategegoryInternalReport(String category) {
		this.setName("Team Category Analysis Report");
		this.category = category;
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

		Element table = new Element("table");
		table.attr("class", "wrapped");
		Element tbody = table.appendElement("tbody");

		Element tr = tbody.appendElement("tr");

		for (int i = 0; i <= 7; i++) {
			if (i > 0) {
				String s = CircleadRecurrenceRule.WEEKDAYS2GERMAN.get(CircleadRecurrenceRule.DAYOFWEEK2WEEKDAY.get(i));
				tr.appendElement("th").attr("colspan", "1").appendText(s);
			} else {
				tr.appendElement("th").attr("colspan", "1").appendText("");
			}
		}

		boolean writeable[][] = new boolean[24][8];
		for (int i = 0; i < 24; i++) {
			for (int j = 0; j <= 7; j++) {
				writeable[i][j] = true;
			}
		}

		tr = tbody.appendElement("tr");
		for (int i = 0; i < 24; i++) {
			tr = tbody.appendElement("tr");
			for (int j = 0; j <= 7; j++) {
				if (j == 0) {
					String s = StringUtil.addSpace(i + "", 2, '0') + ":00";
					tr.appendElement("th").attr("colspan", "1").appendText(s);
				} else {
					boolean found = false;
					List<Team> teams = Repository.getInstance().getTeamsWithCategory(category);
					for (Team team : teams) {
						if (team.getRecurrenceRule() != null) {
							CircleadRecurrenceRule crr = new CircleadRecurrenceRule(team.getRecurrenceRule());

							Weekday wd = crr.getWeekday();
							int hour = crr.getHour();
							int pos = CircleadRecurrenceRule.WEEKDAY2DAYOFWEEK.get(wd);

							if (writeable[i][j] && (j == pos && i == hour)) {
								Element td = tr.appendElement("td");

								if (crr.getDuration() == 2) {
									td.attr("rowspan", "2");
									writeable[i + 1][j] = false;
								} else {
									td.attr("colspan", "1");
								}

								Element p = td.appendElement("p");
								p.appendElement("b").appendText(team.getTeamType());
								if (team.getTeamSubtype() != null) {
									p = td.appendElement("p");
									p.appendText(team.getTeamSubtype());
								}
								p = td.appendElement("p");
								if (team.getTeamSize() == 1) {
									renderer.addStatus(p, "Intern");
								} else {
									renderer.addStatus(p, "Extern");
								}
								if (team.getTeamRedundance() < 1.0) {
									p = td.appendElement("p");
									renderer.addStatus(p, "Unterbesetzt");
								}
								found = true;
							}
						}
					}
					if (writeable[i][j] && !found) {
						Element td = tr.appendElement("td").attr("colspan", "1");
						renderer.addStatus(td, "Unbesetzt");
					}
				}
			}

		}

		table.appendTo(element);

		return element;
	}

}
