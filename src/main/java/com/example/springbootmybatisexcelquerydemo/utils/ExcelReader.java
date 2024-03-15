package com.example.springbootmybatisexcelquerydemo.utils;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ExcelReader<T> {

    public List<T> readExcelFileBuild(MultipartFile file, Class<T> clazz) throws IOException{

        try{
            List<T> entityList = new ArrayList<>();
            InputStream is = file.getInputStream();
            Workbook workbook = null;

            // 根据文件扩展名选择不同的 Workbook 类
            if (file.getOriginalFilename().endsWith(".xlsx")) {
                workbook = new XSSFWorkbook(is);
            } else if (file.getOriginalFilename().endsWith(".xls")) {
                workbook = new HSSFWorkbook(is);
            } else {
                throw new IllegalArgumentException("Unsupported file format");
            }

            Sheet sheet = workbook.getSheetAt(0);
            int j = 0;

            while (j < sheet.getLastRowNum()) {
                j++;
                Row row = sheet.getRow(j);
                List<Object> list = new ArrayList<>();
                int columnCount = row.getLastCellNum();

                for (int i = 1; i < columnCount; i++) {
                    Cell cell = row.getCell(i);
                    if (cell == null || cell.getCellTypeEnum() == CellType.BLANK){
                        list.add(null);
                    }else {
                        if(cell.getCellTypeEnum() == CellType.NUMERIC){
                            int intValue = (int) cell.getNumericCellValue();
                            list.add(String.valueOf(intValue));
                        }else{
                            String stringValue = cell.getStringCellValue();
                            list.add(stringValue);
                        }
                    }
                    System.out.println(list);
                }
                for (int i = 0; i < list.size(); i++) {
                    String name = list.get(i).getClass().getName();
                    System.out.println(name);

                }
                T entity = buildEntity(list, clazz);
                entityList.add(entity);
            }
            return entityList;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }



    public T buildEntity(List<Object> fieldValues, Class<T> clazz) throws Exception {
        T instance = clazz.newInstance(); // 使用默认构造函数创建对象

        Field[] fields = clazz.getDeclaredFields(); // 获取类的所有字段

        for (int i = 0; i < fields.length && i < fieldValues.size(); i++) {
            Field field = fields[i];
            field.setAccessible(true); // 设置字段可访问（私有字段也可以访问）

            // 设置字段的值
            field.set(instance, fieldValues.get(i));
        }
        return instance;
    }
}
