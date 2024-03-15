package com.example.springbootmybatisexcelquerydemo.service;

import com.example.springbootmybatisexcelquerydemo.model.C;

import java.util.List;

public interface CService {

    List<C> searchByKeyWord(String b);
    List<C> selectAllData();
    void calculateAllData(String b);
    byte[] exportDataToExcel() throws Exception;
}
