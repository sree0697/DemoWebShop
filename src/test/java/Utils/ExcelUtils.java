package Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.Reporter;

public class ExcelUtils {

	private static XSSFSheet Sheet;

	private static XSSFWorkbook WorkBook;

	private static XSSFCell Cell;
	
	static int totalRows = 0;
	static int totalColumns = 0;

     /**
      * Method Usage : This method takes row Num and column Num as arguments and return respective cell value in excel
      * @param rowNumber
      * @param columnNumber
      * @return particular cell value in String
      */
		public static String getCellData(int rowNumber, int columnNumber) {
			
			Cell = Sheet.getRow(rowNumber).getCell(columnNumber);
			if (Cell.getCellType() == CellType.NUMERIC) {
				//If cell Type is Numeric, it needs to be converted to String. casting double to int
				int value = (int) Math.round(Cell.getNumericCellValue());
				//converting int to String 
				return String.valueOf(value);
			}
			return String.valueOf(Cell.getStringCellValue());
		}
     
		/**
		 * Method Usage : This Method reads excel file and prints total rows & columns.
		 * 
		 * @param excelPath - Path of the excel file from which data needs to be read.
		 * @param SheetName - sheet of the excel.
		 * @throws Exception
		 */
		public static void setExcelFile(String excelPath, String SheetName) throws Exception {

			File file = new File(excelPath);
			FileInputStream ExcelFile = new FileInputStream(file);

			WorkBook = new XSSFWorkbook(ExcelFile);
			Sheet = WorkBook.getSheet(SheetName);

			// Total columns in excel
			totalColumns = Sheet.getRow(0).getLastCellNum();
			// Total rows in excel
			totalRows = Sheet.getLastRowNum() - Sheet.getFirstRowNum();

			Reporter.log("Total Rows in excel " + totalRows);
			Reporter.log("Total Columns in excel " + totalColumns);
		}

		public String[][] DataArray(String excelPath, String Sheet) throws Exception {

			int row = 0;
			int col = 0;
			// creating 2D array
			setExcelFile(excelPath, Sheet);

			String[][] excelData = new String[totalRows][totalColumns];

			for (int i = 1; i <= totalRows; row++, i++) {
				for (int j = 0; j < totalColumns; col++, j++) {
					// storing excel data into array
					excelData[row][col] = getCellData(i, j);
				}
			}
			return excelData;

		}

}
