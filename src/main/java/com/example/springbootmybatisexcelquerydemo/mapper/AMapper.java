package com.example.springbootmybatisexcelquerydemo.mapper;

import com.example.springbootmybatisexcelquerydemo.model.A;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AMapper {

    int insertData(A a);
    int editData(A a);
    int deleteData(@Param("a") String a);
    List<A> selectByAKeyword(@Param("a") String a);
    int importDataFromExcel(List<A> dataList);

}
