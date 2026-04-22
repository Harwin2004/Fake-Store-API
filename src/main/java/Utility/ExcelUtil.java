package Utility;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtil {

    public static String getCellData(String path, String sheetName, int rowNum, int colNum) throws IOException {

        FileInputStream fis = new FileInputStream(path);
        Workbook wb = new XSSFWorkbook(fis);

        Sheet sheet = wb.getSheet(sheetName);
        Row row = sheet.getRow(rowNum);
        Cell cell = row.getCell(colNum);

        DataFormatter df = new DataFormatter();
        String data = df.formatCellValue(cell);

        wb.close();
        fis.close();

        return data;
    }
}