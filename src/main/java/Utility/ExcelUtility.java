package Utility;

import java.io.FileInputStream;
import java.util.*;

import org.apache.poi.ss.usermodel.*;

public class ExcelUtility {

    public static List<Map<String, String>> getExcelData(String filePath, String sheetName) {

        List<Map<String, String>> dataList = new ArrayList<>();

        try {
            FileInputStream fis = new FileInputStream(filePath);
            Workbook wb = WorkbookFactory.create(fis);
            Sheet sheet = wb.getSheet(sheetName);

            Row header = sheet.getRow(0);

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {

                Row row = sheet.getRow(i);
                Map<String, String> map = new HashMap<>();

                for (int j = 0; j < header.getLastCellNum(); j++) {
                    String key = header.getCell(j).toString();
                    String value = row.getCell(j) == null ? "" : row.getCell(j).toString();
                    map.put(key, value);
                }

                dataList.add(map);
            }

            wb.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return dataList;
    }
}