package org.rogatio.circlead.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFPrintSetup;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.rogatio.circlead.model.Parameter;

/**
 * The Class ExcelUtil allows writing Data to Microsoft-Excel
 * 
 * @author Matthias Wegner
 */
public class ExcelUtil {

	/** The Constant LOGGER. */
	private final static Logger LOGGER = LogManager.getLogger(ExcelUtil.class);

	/**
	 * Adds the color background.
	 *
	 * @param style the style
	 * @param r the r
	 * @param g the g
	 * @param b the b
	 * @return the XSSF cell style
	 */
	public static XSSFCellStyle addColorBackground(XSSFCellStyle style, byte r, byte g, byte b) {
		@SuppressWarnings("deprecation")
		XSSFColor color = new XSSFColor();
		color.setRGB(new byte[] { r, g, b });
		style.setFillForegroundColor(color);
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		return style;
	}

	/**
	 * Write excel.
	 *
	 * @param filename the filename
	 * @param dataMap the data map
	 * @param headerRow the header row
	 */
	public static void writeExcel(String filename, List<Map<Parameter, Object>> dataMap, List<Parameter> headerRow) {
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("data");

		if (headerRow == null) {
			List<Parameter> fields = new ArrayList<Parameter>();
			for (Map<Parameter, Object> rowData : dataMap) {
				for (Parameter parameter : rowData.keySet()) {
					if (!fields.contains(parameter)) {
						fields.add(parameter);
					}
				}
			}
			headerRow = fields;
		}

		int rowCounter = 0;

		XSSFRow row = sheet.createRow(rowCounter);
		for (Parameter p : headerRow) {
			XSSFCell cell = row.createCell(headerRow.indexOf(p));
			XSSFCellStyle HEADERSTYLE = workbook.createCellStyle();
			HEADERSTYLE.setAlignment(org.apache.poi.ss.usermodel.HorizontalAlignment.LEFT);
			HEADERSTYLE.setVerticalAlignment(org.apache.poi.ss.usermodel.VerticalAlignment.TOP);
			ExcelUtil.addColorBackground(HEADERSTYLE, (byte) 60, (byte) 60, (byte) 60);
			cell.setCellStyle(HEADERSTYLE);
//			cell.setCellValue(p.toString());
			cell.setCellValue(ExcelUtil.getRichString(p.toString(), workbook, true, 12));
		}

		int maxColumn = headerRow.size();
		int maxRow = 0;

		for (Map<Parameter, Object> rowData : dataMap) {
			rowCounter++;

			maxRow = Math.max(maxRow, rowCounter);
			XSSFRow dataRow = sheet.createRow(rowCounter);

			for (int i = 0; i < headerRow.size(); i++) {

				Set<Parameter> keys = rowData.keySet();
				for (Parameter parameter : keys) {
					if (headerRow.get(i) == parameter) {
						XSSFCell cell = dataRow.createCell(i);

						XSSFCellStyle cellStyle = workbook.createCellStyle();
						cellStyle.setAlignment(org.apache.poi.ss.usermodel.HorizontalAlignment.LEFT);
						cellStyle.setVerticalAlignment(org.apache.poi.ss.usermodel.VerticalAlignment.TOP);
						cellStyle.setWrapText(true);

						Object val = rowData.get(parameter);
						if (val instanceof String) {
							cell.setCellValue((String) val);
						}
						if (val instanceof Date) {
							cell.setCellValue((Date) val);
						}
						if (val instanceof Integer) {
							cell.setCellValue((Integer) val);
						}
						if (val instanceof Double) {
							cell.setCellValue((Double) val);
						}

					}
				}

			}
		}

		File dir = new File("exports");
		if (!dir.exists()) {
			dir.mkdir();
		}

		if (filename.endsWith(".xlsx")) {
			filename = filename.replace(".xlsx", "");
		}

		setPrintArea(workbook, sheet, maxRow, maxColumn, false);

		try {
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

	/**
	 * Sets the print area.
	 *
	 * @param workbook the workbook
	 * @param spreadsheet the spreadsheet
	 * @param maxRow the max row
	 * @param maxColumn the max column
	 * @param fitToPage the fit to page
	 */
	public static void setPrintArea(XSSFWorkbook workbook, XSSFSheet spreadsheet, int maxRow, int maxColumn,
			boolean fitToPage) {
		workbook.setPrintArea(workbook.getSheetIndex(spreadsheet), // sheet index
				0, // start column
				maxColumn, // end column
				0, // start row
				maxRow // end row
		);

		// set paper size
		spreadsheet.getPrintSetup().setPaperSize(XSSFPrintSetup.A4_PAPERSIZE);
		spreadsheet.setFitToPage(fitToPage);
		spreadsheet.getPrintSetup().setLandscape(true);

		// set display grid lines or not
		spreadsheet.setDisplayGridlines(true);

		// set print grid lines or not
		spreadsheet.setPrintGridlines(true);
	}

	/**
	 * Read excel.
	 *
	 * @param fileName the file name
	 * @return the XSSF workbook
	 */
	public static XSSFWorkbook readExcel(String fileName) {
		OPCPackage pkg = null;
		try {
			pkg = OPCPackage.open(new File(fileName));
			return new XSSFWorkbook(pkg);
		} catch (InvalidFormatException e) {
			LOGGER.error(e);
		} catch (IOException e) {
			LOGGER.error(e);
		} finally {
			try {
				pkg.close();
			} catch (IOException e) {
				LOGGER.error(e);
			}
		}
		return null;
	}

	/**
	 * Gets the first sheet.
	 *
	 * @param wb the wb
	 * @return the first sheet
	 */
	public static XSSFSheet getFirstSheet(XSSFWorkbook wb) {
		return wb.getSheetAt(0);
	}

	/**
	 * Gets the first row.
	 *
	 * @param sheet the sheet
	 * @return the first row
	 */
	public static XSSFRow getFirstRow(XSSFSheet sheet) {
		return sheet.getRow(0);
	}

	/**
	 * Gets the first row.
	 *
	 * @param wb the wb
	 * @param sheetName the sheet name
	 * @return the first row
	 */
	public static XSSFRow getFirstRow(XSSFWorkbook wb, String sheetName) {
		XSSFSheet sheet = wb.getSheet(sheetName);
		return sheet.getRow(0);
	}

	/**
	 * Checks if is string value.
	 *
	 * @param cell the cell
	 * @return true, if is string value
	 */
	public static boolean isStringValue(Cell cell) {
		if (cell.getCellType() == CellType.STRING) {
			return true;
		}
		return false;
	}

	/**
	 * Gets the header value.
	 *
	 * @param cell the cell
	 * @return the header value
	 */
	public static String getHeaderValue(Cell cell) {
		XSSFSheet s = (XSSFSheet) cell.getSheet();
		XSSFRow r = (XSSFRow) getFirstRow(s);
		return getHeaderValue(cell, r);
	}

	/**
	 * Gets the header value.
	 *
	 * @param cell the cell
	 * @param headerRow the header row
	 * @return the header value
	 */
	public static String getHeaderValue(Cell cell, XSSFRow headerRow) {
		Cell hc = getHeaderCell(cell, headerRow);
		if (isDateValue(hc)) {
			return getDateValue(hc).toString();
		}
		if (isNumericValue(hc)) {
			return getNumericValue(hc) + "";
		}
		if (isStringValue(hc)) {
			return getStringValue(hc).toString();
		}
		return null;
	}

	/**
	 * Gets the header cell.
	 *
	 * @param cell the cell
	 * @param headerRow the header row
	 * @return the header cell
	 */
	public static Cell getHeaderCell(Cell cell, XSSFRow headerRow) {
		int index = cell.getColumnIndex();
		return headerRow.getCell(index);
	}

	/**
	 * Gets the string value.
	 *
	 * @param cell the cell
	 * @return the string value
	 */
	public static String getStringValue(Cell cell) {
		if (cell.getCellType() == CellType.STRING) {
			return cell.getStringCellValue();
		}
		return null;
	}

	/**
	 * Gets the numeric value.
	 *
	 * @param cell the cell
	 * @return the numeric value
	 */
	public static double getNumericValue(Cell cell) {
		if (cell.getCellType() == CellType.NUMERIC) {
			if (!HSSFDateUtil.isCellDateFormatted(cell)) {
				return cell.getNumericCellValue();
			}
		}
		return 0;
	}

	/**
	 * Checks if is numeric value.
	 *
	 * @param cell the cell
	 * @return true, if is numeric value
	 */
	public static boolean isNumericValue(Cell cell) {
		if (cell.getCellType() == CellType.NUMERIC) {
			if (!HSSFDateUtil.isCellDateFormatted(cell)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Checks if is date value.
	 *
	 * @param cell the cell
	 * @return true, if is date value
	 */
	public static boolean isDateValue(Cell cell) {
		if (cell.getCellType() == CellType.NUMERIC) {
			if (HSSFDateUtil.isCellDateFormatted(cell)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Gets the date value.
	 *
	 * @param cell the cell
	 * @return the date value
	 */
	public static Calendar getDateValue(Cell cell) {
		if (isDateValue(cell)) {
			Date d = cell.getDateCellValue();
			Calendar c = Calendar.getInstance();
			c.setTime(d);
			return c;
		}
		return null;
	}

	/**
	 * Gets the rich string.
	 *
	 * @param content the content
	 * @param workbook the workbook
	 * @param bold the bold
	 * @param textHeight the text height
	 * @return the rich string
	 */
	public static XSSFRichTextString getRichString(String content, XSSFWorkbook workbook, boolean bold,
			int textHeight) {
		XSSFRichTextString rts = new XSSFRichTextString(content);
		XSSFFont fontBold = workbook.createFont();
		fontBold.setBold(bold);
		fontBold.setFontHeight(textHeight);
		rts.applyFont(fontBold);
		return rts;
	}

	/**
	 * Gets the bold style.
	 *
	 * @param workbook the workbook
	 * @return the bold style
	 */
	public static XSSFCellStyle getBoldStyle(XSSFWorkbook workbook) {
		XSSFCellStyle style = workbook.createCellStyle();
//	        style.setBorderTop(BorderStyle.DOUBLE);
//	        style.setBorderBottom(BorderStyle.THIN);
//	        style.setFillBackgroundColor(HSSFColor.GREY_25_PERCENT.index);

		// We also define the font that we are going to use for displaying the
		// data of the cell. We set the font to ARIAL with 20pt in size and
		// make it BOLD and give blue as the color.
		XSSFFont font = workbook.createFont();
		font.setFontName(HSSFFont.FONT_ARIAL);
//	        font.setFontHeightInPoints((short) 20);
		font.setBold(true);
//	        font.setColor(XSSFColor.toXSSFColor(org.apache.poi.ss.usermodel.Color.));
		style.setFont(font);
		return style;
	}

}
