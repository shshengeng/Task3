package com.example.springbootmybatisexcelquerydemo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.springbootmybatisexcelquerydemo.mapper")
public class SpringbootMybatisExcelQueryDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootMybatisExcelQueryDemoApplication.class, args);
    }

}
