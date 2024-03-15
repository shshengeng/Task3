package com.example.springbootmybatisexcelquerydemo.service.impl;

import com.example.springbootmybatisexcelquerydemo.mapper.AMapper;
import com.example.springbootmybatisexcelquerydemo.model.A;
import com.example.springbootmybatisexcelquerydemo.service.AService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class AServiceImpl implements AService {

    @Autowired
    AMapper aMapper;

    @Override
    public int insertData(A a) {
        return aMapper.insertData(a);
    }

    @Override
    public int editData(A a) {
        return aMapper.editData(a);
    }

    @Override
    public int deleteData(String a) {
        return aMapper.deleteData(a);
    }

    @Override
    public List<A> selectByAKeyword(String a) {
        return aMapper.selectByAKeyword(a);
    }

    @Override
    public int importDataFromExcel(MultipartFile file) throws IOException {

        List<A> aList = new ArrayList<>();

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

            // 读取每行的单元格数据并构建 A 对象
            A a = new A();
            a.setA(row.getCell(1).getStringCellValue());
            a.setB(row.getCell(2).getStringCellValue());
            a.setC(row.getCell(3).getStringCellValue());
            a.setD(row.getCell(4).getStringCellValue());
            a.setE(row.getCell(5).getStringCellValue());
            a.setF(row.getCell(6).getStringCellValue());
            a.setAa((int) row.getCell(7).getNumericCellValue());
            a.setBb((int) row.getCell(8).getNumericCellValue());
            a.setCc((int) row.getCell(9).getNumericCellValue());
            a.setDd((int) row.getCell(10).getNumericCellValue());
            a.setEe((int) row.getCell(11).getNumericCellValue());
            aList.add(a);
        }
        aMapper.importDataFromExcel(aList);
        workbook.close();
        return aList.size();
    }
}
