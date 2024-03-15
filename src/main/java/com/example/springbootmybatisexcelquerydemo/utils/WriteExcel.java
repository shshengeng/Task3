package com.example.springbootmybatisexcelquerydemo.utils;


import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.util.List;

public class WriteExcel<T> {

    public void writeDataToExcel(List<T> list) throws Exception{

        // 创建 Excel 工作簿和工作表
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Table Data");

        // 填充 Excel 数据
        int rowNum = 0;
        T ob = list.get(0);
        Row row = sheet.createRow(rowNum++);
        Field[] fields = ob.getClass().getDeclaredFields();
        row.createCell(0).setCellValue("序号");
        for (int i = 0; i < fields.length; i++){
            row.createCell(i+1).setCellValue(fields[i].getName());
        }
        for (T entity : list) {
            row = sheet.createRow(rowNum++);
            Field[] declaredFields = entity.getClass().getDeclaredFields();
            for (int i = 0; i < declaredFields.length; i++){
                declaredFields[i].setAccessible(true);
                Object o = declaredFields[i].get(entity);
                if(o != null){
                    row.createCell(i+1).setCellValue(String.valueOf(o));
                }else{
                    row.createCell(i+1).setCellValue("");
                }
            }
        }

        // 保存 Excel 文件
        FileOutputStream fileOut = new FileOutputStream("TableData.xlsx");
        workbook.write(fileOut);
        fileOut.close();

        System.out.println("Excel file created successfully!");
    }
}
