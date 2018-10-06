package org.rogatio.circlead.util;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtil {

	final static Logger LOGGER = LogManager.getLogger(ExcelUtil.class);

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

	public static XSSFSheet getFirstSheet(XSSFWorkbook wb) {
		return wb.getSheetAt(0);
	}

	public static XSSFRow getFirstRow(XSSFSheet sheet) {
		return sheet.getRow(0);
	}

	public static XSSFRow getFirstRow(XSSFWorkbook wb, String sheetName) {
		XSSFSheet sheet = wb.getSheet(sheetName);
		return sheet.getRow(0);
	}

	public static boolean isStringValue(Cell cell) {
		if (cell.getCellType() == CellType.STRING) {
			return true;
		}
		return false;
	}

	public static String getHeaderValue(Cell cell) {
		XSSFSheet s = (XSSFSheet)cell.getSheet();
		XSSFRow r = (XSSFRow)getFirstRow(s);
		return getHeaderValue(cell, r);
	}
	
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

	public static Cell getHeaderCell(Cell cell, XSSFRow headerRow) {
		int index = cell.getColumnIndex();
		return headerRow.getCell(index);
	}

	public static String getStringValue(Cell cell) {
		if (cell.getCellType() == CellType.STRING) {
			return cell.getStringCellValue();
		}
		return null;
	}

	public static double getNumericValue(Cell cell) {
		if (cell.getCellType() == CellType.NUMERIC) {
			if (!HSSFDateUtil.isCellDateFormatted(cell)) {
				return cell.getNumericCellValue();
			}
		}
		return 0;
	}

	public static boolean isNumericValue(Cell cell) {
		if (cell.getCellType() == CellType.NUMERIC) {
			if (!HSSFDateUtil.isCellDateFormatted(cell)) {
				return true;
			}
		}
		return false;
	}

	public static boolean isDateValue(Cell cell) {
		if (cell.getCellType() == CellType.NUMERIC) {
			if (HSSFDateUtil.isCellDateFormatted(cell)) {
				return true;
			}
		}
		return false;
	}

	public static Calendar getDateValue(Cell cell) {
		if (isDateValue(cell)) {
			Date d = cell.getDateCellValue();
			Calendar c = Calendar.getInstance();
			c.setTime(d);
			return c;
		}
		return null;
	}

}
