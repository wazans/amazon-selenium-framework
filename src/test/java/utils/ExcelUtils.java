package utils;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;

public class ExcelUtils {

    public static Object[][] getTestData(String filePath, String sheetName) {
        // try-with-resources closes both the file stream and workbook automatically.
        // This avoids file locking issues when the same Excel file is reused in later runs.
        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            // Read the specific worksheet that the DataProvider wants to use.
            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                throw new IllegalArgumentException("Sheet not found: " + sheetName);
            }

            // Row 0 is treated as the header row, for example "keyword".
            // Physical rows/cells count only the rows and columns that actually contain data.
            int rows = sheet.getPhysicalNumberOfRows();
            int cols = sheet.getRow(0).getPhysicalNumberOfCells();

            // TestNG DataProvider expects Object[][]
            // Each row in this array becomes one separate test execution.
            Object[][] data = new Object[rows - 1][cols];

            for (int i = 1; i < rows; i++) {
                Row row = sheet.getRow(i);
                for (int j = 0; j < cols; j++) {
                    // i starts from 1 because row 0 is the header.
                    // We shift by -1 so the first data row goes into data[0].
                    // toString() is enough here because the current tests expect String input.
                    data[i - 1][j] = row.getCell(j).toString();
                }
            }

            // Example:
            // Excel rows:
            // laptop
            // mobile
            // headphones
            //
            // Returned Object[][]:
            // {{"laptop"}, {"mobile"}, {"headphones"}}
            return data;
        } catch (IOException e) {
            throw new RuntimeException("Failed to read test data from Excel: " + filePath, e);
        }
    }
}
