package com.example.springbootmybatisexcelquerydemo.controller;

import com.example.springbootmybatisexcelquerydemo.model.B;
import com.example.springbootmybatisexcelquerydemo.service.BService;
import com.example.springbootmybatisexcelquerydemo.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;


@RestController
@RequestMapping("/b")
public class BController {

    @Autowired
    private BService bService;

    @PostMapping("/insert")
    public ResponseResult insertData(@RequestBody B b) {
        int result = bService.insertData(b);
        if (result > 0) {
            return new ResponseResult(200, "Data inserted successfully", null);
        } else {
            return new ResponseResult(500, "Failed to insert data", null);
        }
    }

    @PostMapping("/edit")
    public ResponseResult editData(@RequestBody B b) {
        int result = bService.editData(b);
        if (result > 0) {
            return new ResponseResult(200, "Data edited successfully", null);
        } else {
            return new ResponseResult(500, "Failed to edit data", null);
        }
    }


    @DeleteMapping("/delete/{a}/{c}")
    public ResponseResult deleteData(@PathVariable String a, @PathVariable String c) {
        try {
            int result = bService.deleteData(a, c);
            if (result > 0) {
                return new ResponseResult(200, "Data deleted successfully", null);
            } else {
                return new ResponseResult(500, "Failed to delete data", null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseResult(500, "An error occurred while deleting data", null);
        }
    }

    @DeleteMapping("/delete/{c}")
    public ResponseResult deleteDataByC(@PathVariable String c) {
        try {
            int result = bService.deleteDataByC(c);
            if (result > 0) {
                return new ResponseResult(200, "Data deleted successfully", null);
            } else {
                return new ResponseResult(500, "Failed to delete data", null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseResult(500, "An error occurred while deleting data", null);
        }
    }


    @PostMapping("/import")
    public ResponseResult importExcel(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return new ResponseResult(400, "File is empty", null);
        }

        try {
            int result = bService.importDataFromExcel(file);

            if (result > 0) {
                return new ResponseResult(200, "Data imported successfully", null);
            } else {
                return new ResponseResult(500, "Failed to import data", null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseResult(500, "Failed to upload file", null);
        }
    }

    @GetMapping("/export")
    public ResponseEntity<byte[]> exportExcel() {
        try {
            // 将数据转换为 Excel 表格格式
            byte[] excelBytes = bService.exportDataToExcel();

            // 设置响应头，告诉客户端返回的是 Excel 文件
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            String encodedFileName = URLEncoder.encode("B表.xlsx", StandardCharsets.UTF_8);
            headers.setContentDispositionFormData("attachment", encodedFileName);


            // 返回 ResponseEntity 对象，包含响应头和 Excel 文件数据
            return new ResponseEntity<>(excelBytes, headers, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            // 返回错误的 ResponseEntity 对象
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}