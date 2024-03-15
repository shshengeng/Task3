package com.example.springbootmybatisexcelquerydemo.controller;

import com.example.springbootmybatisexcelquerydemo.model.C;
import com.example.springbootmybatisexcelquerydemo.service.CService;
import com.example.springbootmybatisexcelquerydemo.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@RequestMapping("/c")
public class CController {


    @Autowired
    private CService cService;


    @GetMapping("/search/{b}")
    public ResponseResult searchByKeyword(@RequestParam String b) {
        List<C> result = cService.searchByKeyWord(b);
        if(result.size() > 0){
            return new ResponseResult(200, "search by keyword successfully", result);
        }else{
            return new ResponseResult(500, "no data is searched", result);
        }

    }

    @GetMapping("export")
    public ResponseEntity<byte[]> exportExcel(){
        try {
            // 将数据转换为 Excel 表格格式
            byte[] excelBytes = cService.exportDataToExcel();

            // 设置响应头，告诉客户端返回的是 Excel 文件
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            String encodedFileName = URLEncoder.encode("C表.xlsx", StandardCharsets.UTF_8);
            headers.setContentDispositionFormData("attachment", encodedFileName);

            // 返回 ResponseEntity 对象，包含响应头和 Excel 文件数据
            return new ResponseEntity<>(excelBytes, headers, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            // 返回错误的 ResponseEntity 对象
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("calculate")
    public ResponseResult calculateAllData(){
        cService.calculateAllData("A");
        return new ResponseResult(200, "calculate all data successfully", null);
    }



}
