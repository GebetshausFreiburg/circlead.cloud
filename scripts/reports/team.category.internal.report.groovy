String category = PropertyUtil.getInstance().getApplicationDefaultTeamcategory()

name = "Team Category Analysis Report"
description = "Zeigt alle Teams der Kategorie '"+category+"' an bedarfsorientierten Wochenbericht."
	
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dmfs.rfc5545.Weekday;
import org.jsoup.nodes.Element;
import org.rogatio.circlead.control.CircleadRecurrenceRule;
import org.rogatio.circlead.control.synchronizer.ISynchronizer;
import org.rogatio.circlead.control.synchronizer.atlassian.AtlassianSynchronizer;
import org.rogatio.circlead.control.synchronizer.file.FileSynchronizer;
import org.rogatio.circlead.model.work.Team;
import org.rogatio.circlead.util.ObjectUtil;
import org.rogatio.circlead.util.PropertyUtil;
import org.rogatio.circlead.util.StringUtil;
import org.rogatio.circlead.view.renderer.ISynchronizerRendererEngine;


ISynchronizerRendererEngine renderer = synchronizer.getRenderer();
		Element element = new Element("p");

		List<Team> teams = R.getTeamsWithCategory(category);
		if (!ObjectUtil.isListNotNullAndEmpty(teams)) {
			element.appendText("Report not created, because not team found with category '"+category+"'");
			return element;
		}
		
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

boolean[][] writeable = ObjectUtil.createMatrix(24,8)
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
					for (Team team : teams) {
						if (StringUtil.isNotNullAndNotEmpty(team.getRecurrenceRule())) {
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
//								p.appendElement("b").appendText(team.getTeamType());
								
								if (synchronizer.getClass().getSimpleName().equals(AtlassianSynchronizer.class.getSimpleName())) {
									p.appendElement("b").append("<ac:link><ri:page ri:content-title=\"" + team.getTitle()
											+ "\" ri:version-at-save=\"1\"/><ac:plain-text-link-body><![CDATA[" + team.getTeamType()
											+ ""  + "]]></ac:plain-text-link-body></ac:link>");
								} else if (synchronizer.getClass().getSimpleName().equals(FileSynchronizer.class.getSimpleName())) {
									p.appendElement("b").appendElement("a").attr("href", "" + team.getId(synchronizer) + ".html")
											.appendText(team.getTeamType() );
								}
								
								String appendix = "";
								if (crr.isRecurrenceOdd() != null) {
									if (crr.isRecurrenceOdd() == true) {
										appendix = " (uKW)";
									} else {
										appendix = " (gKW)";
									}
									p.appendText(appendix);
								}
								
								if (team.isSpecialized()) {
									p.appendText(PropertyUtil.getInstance().getApplicationSpecializedChar());
								}
								
								if (team.getTeamSubtype() != null) {
									p = td.appendElement("p");
									p.appendText(team.getTeamSubtype());
								}
								p = td.appendElement("p");
								if (team.getTeamSize() == 1&&(!team.isSpecialized())) {
									renderer.addStatus(p, "Intern");
								} else {
									renderer.addStatus(p, "Extern");
								}
								if (team.getRedundance() < 1.0) {
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
