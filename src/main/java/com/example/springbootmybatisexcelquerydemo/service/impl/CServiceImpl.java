package com.example.springbootmybatisexcelquerydemo.service.impl;

import com.example.springbootmybatisexcelquerydemo.mapper.CMapper;
import com.example.springbootmybatisexcelquerydemo.model.B;
import com.example.springbootmybatisexcelquerydemo.model.C;
import com.example.springbootmybatisexcelquerydemo.service.CService;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;

@Service
public class CServiceImpl implements CService {


    @Autowired
    CMapper cMapper;

    @Override
    public List<C> searchByKeyWord(String b) {
        return cMapper.selectByAKeyword(b);
    }

    @Override
    public List<C> selectAllData() {
        return cMapper.selectAllData();
    }

    @Override
    public void calculateAllData(String b) {
        cMapper.calculateAllData(b);
    }

    @Override
    public byte[] exportDataToExcel() throws Exception {
        List<C> list = cMapper.selectAllData();
        System.out.println(list);

        // 创建 Excel 工作簿和工作表
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("C表");

        // 填充 Excel 数据
        int rowNum = 0;
        Row row = sheet.createRow(rowNum++);
        Field[] declaredFields = C.class.getDeclaredFields();
        row.createCell(0).setCellValue("序号");
        for (int i = 0; i < declaredFields.length; i++){
            row.createCell(i+1).setCellValue(declaredFields[i].getName());
        }

        for (C c : list){
            row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(rowNum-1);
            Field[] fields = c.getClass().getDeclaredFields();
            for (int i = 0; i < fields.length; i++){
                fields[i].setAccessible(true);
                Object o = fields[i].get(c);
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
