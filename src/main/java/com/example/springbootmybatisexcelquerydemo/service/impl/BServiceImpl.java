package com.example.springbootmybatisexcelquerydemo.service.impl;
import com.example.springbootmybatisexcelquerydemo.mapper.BMapper;
import com.example.springbootmybatisexcelquerydemo.model.B;
import com.example.springbootmybatisexcelquerydemo.service.BService;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class BServiceImpl implements BService {

    @Autowired
    BMapper bMapper;

    @Override
    public int insertData(B b) {
        return bMapper.insertData(b);
    }

    @Override
    public int editData(B b) {
        return bMapper.editData(b);
    }

    @Override
    public int deleteData(String a, String c) {
        return bMapper.deleteData(a, c);
    }

    @Override
    public int deleteDataByC(String c) {
        return bMapper.deleteByC(c);
    }

    @Override
    public int importDataFromExcel(MultipartFile file) throws IOException {

        List<B> list = new ArrayList<>();

        // 从 MultipartFile 中获取输入流
        Workbook workbook = new XSSFWorkbook(file.getInputStream());

        // 获取第一个工作表
        Sheet sheet = workbook.getSheetAt(0);

        // 迭代行
        Iterator<Row> rowIterator = sheet.iterator();
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            if (row.getRowNum() == 0) {
                // 跳过表头行
                continue;
            }

            B b = new B();
            b.setA(row.getCell(1).getStringCellValue());
            b.setB(row.getCell(2).getStringCellValue());
            b.setC(row.getCell(3).getStringCellValue());
            b.setD(row.getCell(4).getStringCellValue());
            b.setE(row.getCell(5).getStringCellValue());
            b.setF(row.getCell(6).getStringCellValue());
            b.setAa((int) row.getCell(7).getNumericCellValue());
            b.setCorrectAa((int) row.getCell(8).getNumericCellValue());
            b.setBb((int) row.getCell(9).getNumericCellValue());
            b.setCorrectBb((int) row.getCell(10).getNumericCellValue());
            b.setCc((int) row.getCell(11).getNumericCellValue());
            b.setCorrectCc((int) row.getCell(12).getNumericCellValue());
            b.setDd((int) row.getCell(13).getNumericCellValue());
            b.setCorrectDd((int) row.getCell(14).getNumericCellValue());
            b.setEe((int) row.getCell(15).getNumericCellValue());
            b.setCorrectEe((int) row.getCell(16).getNumericCellValue());
            list.add(b);
        }
        bMapper.importDataFromExcel(list);
        workbook.close();
        return list.size();
    }

    @Override
    public byte[] exportDataToExcel() throws Exception{
        List<B> list = bMapper.selectAllData();

        // 创建 Excel 工作簿和工作表
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("B表");

        // 填充 Excel 数据
        int rowNum = 0;
        Row row = sheet.createRow(rowNum++);
        Field[] declaredFields = B.class.getDeclaredFields();
        row.createCell(0).setCellValue("序号");
        for (int i = 0; i < declaredFields.length; i++){
            row.createCell(i+1).setCellValue(declaredFields[i].getName());
        }

        for (B b : list){
            row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(rowNum-1);
            Field[] fields = b.getClass().getDeclaredFields();
            for (int i = 0; i < fields.length; i++){
                fields[i].setAccessible(true);
                Object o = fields[i].get(b);
                if(o != null){
                    row.createCell(i+1).setCellValue(String.valueOf(o));
                }
            }
        }

        // 将 Excel 文件写入到 ByteArrayOutputStream
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        outputStream.close();

        return outputStream.toByteArray();
    }
}
