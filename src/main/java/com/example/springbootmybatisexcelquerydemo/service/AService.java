package com.example.springbootmybatisexcelquerydemo.service;

import com.example.springbootmybatisexcelquerydemo.model.A;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


public interface AService {
    int insertData(A a);
    int editData(A a);
    int deleteData(String a);
    List<A> selectByAKeyword(String a);
    int importDataFromExcel(MultipartFile file) throws IOException;
}
