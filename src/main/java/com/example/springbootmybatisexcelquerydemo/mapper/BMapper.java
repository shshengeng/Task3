package com.example.springbootmybatisexcelquerydemo.mapper;

import com.example.springbootmybatisexcelquerydemo.model.A;
import com.example.springbootmybatisexcelquerydemo.model.B;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BMapper {

    int insertData(B b);
    int editData(B b);
    int deleteData(@Param("a") String a, @Param("c") String c);
    int deleteByC(@Param("c") String c);
    int importDataFromExcel(List<B> dataList);
    List<B> selectAllData();
}
