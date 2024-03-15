package com.example.springbootmybatisexcelquerydemo.mapper;


import com.example.springbootmybatisexcelquerydemo.model.C;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CMapper {

    List<C> selectByAKeyword(@Param("b") String b);
    List<C> selectAllData();
    int calculateAllData(@Param("b") String b);
}
