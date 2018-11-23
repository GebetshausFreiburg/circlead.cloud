package org.rogatio.circlead.main;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.dmfs.rfc5545.Weekday;
import org.rogatio.circlead.control.CircleadRecurrenceRule;
import org.rogatio.circlead.control.Repository;
import org.rogatio.circlead.model.work.Team;
import org.rogatio.circlead.util.ExcelUtil;
import org.rogatio.circlead.util.StringUtil;

/**
 * The Class PrayHourExporter.
 * 
 * @author Matthias Wegner
 */
public class PrayHourExporter {

	/** The Constant LOGGER. */
	final static Logger LOGGER = LogManager.getLogger(PrayHourExporter.class);

	/** The workbook. */
	final XSSFWorkbook workbook = new XSSFWorkbook();

	/** The headerstyle. */
	final XSSFCellStyle HEADERSTYLE = workbook.createCellStyle();

	/**
	 * Instantiates a new pray hour exporter.
	 */
	public PrayHourExporter() {
		HEADERSTYLE.setAlignment(org.apache.poi.ss.usermodel.HorizontalAlignment.CENTER);
		HEADERSTYLE.setVerticalAlignment(org.apache.poi.ss.usermodel.VerticalAlignment.TOP);
		ExcelUtil.addColorBackground(HEADERSTYLE, (byte) 200, (byte) 200, (byte) 200);

		addSheet(PrayHourExporter.MODE_EXTERN);
		addSheet(PrayHourExporter.MODE_INTERN);
		addSheet(PrayHourExporter.MODE_NEED);
		addSheet(PrayHourExporter.MODE_DETAIL);

	}

	/** The mode intern. */
	public static String MODE_INTERN = new String("Intern");

	/** The mode extern. */
	public static String MODE_EXTERN = new String("Extern");

	/** The mode detail. */
	public static String MODE_DETAIL = new String("Detailliert");

	/** The mode need. */
	public static String MODE_NEED = new String("Bedarf");

	/**
	 * Adds the weekdays.
	 *
	 * @param sheet the sheet
	 */
	private void addWeekdays(XSSFSheet sheet) {
		XSSFRow rowOfDays = sheet.createRow(0);

		for (int i = 0; i <= 7; i++) {
			XSSFCell cell = rowOfDays.createCell(i);

			cell.setCellStyle(HEADERSTYLE);
			if (i > 0) {
				cell.setCellValue(ExcelUtil.getRichString(
						CircleadRecurrenceRule.WEEKDAYS2GERMAN.get(CircleadRecurrenceRule.DAYOFWEEK2WEEKDAY.get(i)),
						workbook, true, 12));
			} else {
				cell.setCellValue(" ");
			}
		}
	}

	/**
	 * Post processing.
	 *
	 * @param sheet the sheet
	 */
	private void postProcessing(XSSFSheet sheet) {
		for (int i = 0; i <= 24; i++) {
			XSSFRow row = sheet.getRow(i);
			for (int j = 1; j <= 7; j++) {
				XSSFCell cell = row.getCell(j);
				boolean create = false;
				if (cell != null) {
					if (!StringUtil.isNotNullAndNotEmpty(cell.getStringCellValue())) {
						create = true;
					}
				} else {
					create = true;
				}
				if (create) {
					XSSFCellStyle cs = ExcelUtil.addColorBackground(workbook.createCellStyle(), (byte) 253, (byte) 106,
							(byte) 2);
					cell = row.createCell(j);
					cell.setCellStyle(cs);
					cell.setCellValue(" ");
				}
			}
		}
	}

	/**
	 * Adds the dayhours.
	 *
	 * @param sheet the sheet
	 */
	private void addDayhours(XSSFSheet sheet) {
		for (int i = 0; i < 24; i++) {
			sheet.setColumnWidth(0, 7 * 256);
			XSSFRow row = sheet.createRow(i + 1);
//			sheet.setColumnWidth(0, 3 * 256);
//			row = sheet.createRow(i + 1);
			XSSFCell cell = row.createCell(0);
			XSSFCellStyle s = HEADERSTYLE;
			cell.setCellStyle(s);
			cell.setCellValue(ExcelUtil.getRichString(StringUtil.addSpace(i + "", 2, '0') + ":00", workbook, true, 12));
		}
	}

	/**
	 * Adds the sheet.
	 *
	 * @param mode the mode
	 */
	public void addSheet(String mode) {
		String category = "Gebetsstunde";
		XSSFSheet sheet = workbook.createSheet(mode);

		addWeekdays(sheet);
		addDayhours(sheet);

		List<Team> teams = Repository.getInstance().getTeamsWithCategory(category);
		for (Team team : teams) {
			if (team.getRecurrenceRule() != null) {
				CircleadRecurrenceRule crr = new CircleadRecurrenceRule(team.getRecurrenceRule());

				Weekday wd = crr.getWeekday();
				int hour = crr.getHour();

				XSSFRow row = sheet.getRow(hour + 1);
				if (mode.equals(MODE_DETAIL)) {
					row.setHeight((short) (256 * 4));
				} else {
					row.setHeight((short) (256 * 2));
				}

				XSSFCell cell = null;
				int pos = CircleadRecurrenceRule.WEEKDAY2DAYOFWEEK.get(wd);
				sheet.setColumnWidth(pos, 20 * 256);

				XSSFRichTextString rts = new XSSFRichTextString();

				cell = row.getCell(pos);

				if (cell != null) {
					if (cell.getRichStringCellValue() != null) {
						rts = cell.getRichStringCellValue();
						rts.append("\n");
					}
				}

				cell = row.createCell(pos);

				if (crr.getDuration() == 2) {
					sheet.addMergedRegion(new CellRangeAddress(hour + 1, hour + 2, pos, pos));
				}

				String appendix = "";
				if (crr.isRecurrenceOdd() != null) {
					if (crr.isRecurrenceOdd() == true) {
						appendix = " (uKW)";
					} else {
						appendix = " (gKW)";
					}
				}

				if (team.getTeamType() != null) {
					XSSFFont fontBold = workbook.createFont();
					fontBold.setBold(true); // set bold
					fontBold.setFontHeight(10); // add font size
					rts.append(team.getTeamType() + "" + appendix, fontBold);
				}
				if (team.getTeamSubtype() != null) {
					rts.append("\n" + team.getTeamSubtype());
				}

				if (cell != null) {
					XSSFCellStyle cellStyle = workbook.createCellStyle();
					cellStyle.setAlignment(org.apache.poi.ss.usermodel.HorizontalAlignment.CENTER);
					cellStyle.setVerticalAlignment(org.apache.poi.ss.usermodel.VerticalAlignment.TOP);
					cellStyle.setWrapText(true);

					if (mode.equals(MODE_NEED)) {
						if (team.getRedundance() < 1.0) {
							ExcelUtil.addColorBackground(cellStyle, (byte) 255, (byte) 255, (byte) 0);
						}
						if (team.getTeamSize() < 2 && (!team.isSpecialized())) {
							ExcelUtil.addColorBackground(cellStyle, (byte) 255, (byte) 0, (byte) 0);
						}
					}
					if (mode.equals(MODE_INTERN)) {
						if ((team.getTeamSize() < 2) && (!team.isSpecialized())) {
							ExcelUtil.addColorBackground(cellStyle, (byte) 240, (byte) 240, (byte) 240);
						}
					}
					if (mode.equals(MODE_EXTERN)) {
						if ((team.getTeamSize() > 1) || (!team.isSpecialized())) {
							cell.setCellStyle(cellStyle);
							cell.setCellValue(rts);
						}
					}
					if (mode.equals(MODE_DETAIL)) {
						XSSFFont small = workbook.createFont();
						small.setFontHeight(6);
						rts.append("\n" + StringUtil.join(team.getTeamMembers()), small);
					}

					if (!mode.equals(MODE_EXTERN)) {
						cell.setCellStyle(cellStyle);
						cell.setCellValue(rts);
					}
				}
			}
		}

		if (mode.equals(MODE_NEED)) {
			postProcessing(sheet);
		}

		ExcelUtil.setPrintArea(workbook, sheet, 24, 7, true);
	}

	/**
	 * Export.
	 *
	 * @param filename the filename
	 */
	public void export(String filename) {
		try {
			LOGGER.debug("Export PrayHours to '" + filename + ".xlsx'");
			FileOutputStream out = new FileOutputStream(new File("exports" + File.separatorChar + filename + ".xlsx"));
			workbook.write(out);
			out.close();
		} catch (Exception e) {
			LOGGER.error(e);
		} finally {
			try {
				workbook.close();
			} catch (IOException e) {
				LOGGER.error(e);
			}
		}
	}

}
