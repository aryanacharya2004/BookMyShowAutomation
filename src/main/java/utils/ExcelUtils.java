package utils;

import base.ConfigLoader;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

public class ExcelUtils {

    public static Map<String,String> getRowByKey(String sheetName, String keyColumn, String keyValue){
        try(FileInputStream fis = new FileInputStream(ConfigLoader.get("excelPath"));
            Workbook wb = new XSSFWorkbook(fis)){
            Sheet sheet = wb.getSheet(sheetName);
            if(sheet==null) throw new RuntimeException("Sheet not found: " + sheetName);

            Row header = sheet.getRow(0);
            Map<String,Integer> colIndex = new HashMap<>();
            for(Cell c : header) colIndex.put(c.getStringCellValue().trim(), c.getColumnIndex());

            int keyCol = colIndex.get(keyColumn);
            for(int i=1;i<=sheet.getLastRowNum();i++){
                Row r = sheet.getRow(i);
                if(r==null) continue;
                Cell keyCell = r.getCell(keyCol);
                String val = keyCell==null? "" : keyCell.toString();
                if(val.equalsIgnoreCase(keyValue)){
                    Map<String,String> rowMap = new HashMap<>();
                    for(Map.Entry<String,Integer> e : colIndex.entrySet()){
                        Cell cell = r.getCell(e.getValue());
                        rowMap.put(e.getKey(), cell==null? "" : cell.toString());
                    }
                    return rowMap;
                }
            }
            throw new RuntimeException("Key not found: " + keyValue);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
