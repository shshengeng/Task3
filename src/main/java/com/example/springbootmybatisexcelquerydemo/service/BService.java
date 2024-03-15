package com.example.springbootmybatisexcelquerydemo.service;

import com.example.springbootmybatisexcelquerydemo.model.B;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface BService {

    int insertData(B b);
    int editData(B b);
    int deleteData(String a, String c);
    int deleteDataByC(String c);
    int importDataFromExcel(MultipartFile file) throws IOException;
    byte[] exportDataToExcel() throws Exception;
}
