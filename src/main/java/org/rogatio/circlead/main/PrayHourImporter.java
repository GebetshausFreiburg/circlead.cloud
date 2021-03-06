package org.rogatio.circlead.main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.rogatio.circlead.control.Repository;
import org.rogatio.circlead.control.synchronizer.atlassian.AtlassianSynchronizer;
import org.rogatio.circlead.control.synchronizer.file.FileSynchronizer;
import org.rogatio.circlead.model.data.TeamEntry;
import org.rogatio.circlead.model.work.Team;
import org.rogatio.circlead.util.ExcelUtil;
import org.rogatio.circlead.util.ObjectUtil;
import org.rogatio.circlead.util.StringUtil;

/**
 * The Class PrayHourImporter.
 * 
 * @author Matthias Wegner
 */
public class PrayHourImporter {

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 * @throws EncryptedDocumentException the encrypted document exception
	 * @throws IOException                Signals that an I/O exception has
	 *                                    occurred.
	 * @throws InvalidFormatException     the invalid format exception
	 */
	public static void main(String[] args) throws EncryptedDocumentException, IOException, InvalidFormatException {

		boolean DRY = true;

		Repository repository = Repository.getInstance();
		FileSynchronizer fsynchronizer = new FileSynchronizer("data");
		AtlassianSynchronizer asynchronizer = new AtlassianSynchronizer();
		repository.addSynchronizer(asynchronizer);
		repository.addSynchronizer(fsynchronizer);

		XSSFWorkbook wb = ExcelUtil.readExcel("Database.xlsx");
		XSSFSheet sheet = ExcelUtil.getFirstSheet(wb);

		int counter = 0;

		for (Row row : sheet) {

			Team team = new Team();
			team.setCategory("Gebetsstunde");

			int hour = 0;
			String day = null;
			String dayFull = null;
			String duration = null;
			String type = null;
			String subtype = null;
			String teamlead = null;
			List<String> teamList = new ArrayList<String>();

			// Skip Header-Row
			if (counter > 0) {
				for (Cell cell : row) {
					if (ExcelUtil.isDateValue(cell)) {
						Calendar c = ExcelUtil.getDateValue(cell);
						int h = c.get(Calendar.HOUR_OF_DAY);
						String hv = ExcelUtil.getHeaderValue(cell);

						if ("Start".equals(hv)) {
							hour = h;
						}
					}

					if (ExcelUtil.isStringValue(cell)) {
						String cellValue = ExcelUtil.getStringValue(cell);
						String headerValue = ExcelUtil.getHeaderValue(cell);
						if (headerValue.startsWith("Team_Name") && StringUtil.isNotNullAndNotEmpty(cellValue)) {
							teamList.add(cellValue.trim());
						}

						if ("Schwerpunkt".equals(headerValue)) {
							if (StringUtil.isNotNullAndNotEmpty(cellValue)) {
								subtype = cellValue.trim();
							}
						}
						if ("Stundenleiter".equals(headerValue)) {
							if (StringUtil.isNotNullAndNotEmpty(cellValue)) {
								teamlead = cellValue.trim();
							}
						}
						if ("Art der Stunde".equals(headerValue)) {
							if (StringUtil.isNotNullAndNotEmpty(cellValue)) {
								type = cellValue.trim();
							}
						}

						if ("Dauer".equals(headerValue)) {
							duration = cellValue.trim().toUpperCase();
						}
						if ("Tag".equals(headerValue)) {
							if ("Montag".equals(cellValue)) {
								day = "MO";
								dayFull = "Mo";
							}
							if ("Dienstag".equals(cellValue)) {
								day = "TU";
								dayFull = "Di";
							}
							if ("Mittwoch".equals(cellValue)) {
								day = "WE";
								dayFull = "Mi";
							}
							if (cellValue.startsWith("Donner")) {
								day = "TH";
								dayFull = "Do";
							}
							if ("Freitag".equals(cellValue)) {
								day = "FR";
								dayFull = "Fr";
							}
							if ("Samstag".equals(cellValue)) {
								day = "SA";
								dayFull = "Sa";
							}
							if ("Sonntag".equals(cellValue)) {
								day = "SU";
								dayFull = "So";
							}
						}
					}
				}
			}

			if (StringUtil.isNotNullAndNotEmpty(type)) {
				String x = StringUtil.addSpace("" + hour, 2, '0');
				String rule = x + day + duration;

				team.setRecurrenceRule(rule);

				team.setTitle("Teamstunde " + dayFull + " " + x + "h");

				if (StringUtil.isNotNullAndNotEmpty(subtype)) {
					System.out.println(type + " (" + subtype + ") : " + rule);
					team.setTeamSubtype(subtype);
					team.setTeamType(type);
				} else {
					team.setTeamType(type);
					System.out.println(type + " : " + rule);
				}

				if (StringUtil.isNotNullAndNotEmpty(teamlead)) {
					System.out.println("   Teamleiter: " + teamlead);

					TeamEntry e = new TeamEntry();
					e.setNeeded(1);
					e.setRoleIdentifier("Teamleiter");
					e.setPersonIdentifiers(teamlead);
					e.setLevel("75%");

					team.addTeamEnty(e);

					e = new TeamEntry();
					e.setNeeded(1);
					e.setRoleIdentifier("Gebetstundenleiter");
					e.setPersonIdentifiers(teamlead);
					e.setLevel("100%");

					team.addTeamEnty(e);
				}

				if (ObjectUtil.isListNotNullAndEmpty(teamList)) {
					TeamEntry e = new TeamEntry();
					e.setNeeded(1);
					e.setRoleIdentifier("Gebetsstundenmitglied");
					e.setPersonIdentifiers(StringUtil.join(teamList));
					e.setLevel("100%");

					team.addTeamEnty(e);
				}

				if (!DRY) {
					repository.addWorkitem(team);
				}
				System.out.println("dry=" + DRY + ": " + team);
			}

			counter++;
		}
	}

}
