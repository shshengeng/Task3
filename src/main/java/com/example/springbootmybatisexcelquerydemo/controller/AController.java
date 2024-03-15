package com.example.springbootmybatisexcelquerydemo.controller;


import com.example.springbootmybatisexcelquerydemo.model.A;
import com.example.springbootmybatisexcelquerydemo.service.AService;
import com.example.springbootmybatisexcelquerydemo.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/a")
public class AController {

    @Autowired
    private AService aService;

    @PostMapping("/insert")
    public ResponseResult insertData(@RequestBody A a) {
        int result = aService.insertData(a);
        if (result > 0) {
            return new ResponseResult(200, "Data inserted successfully", null);
        } else {
            return new ResponseResult(500, "Failed to insert data", null);
        }
    }

    @PostMapping("/edit")
    public ResponseResult editData(@RequestBody A a) {
        int result = aService.editData(a);
        if (result > 0) {
            return new ResponseResult(200, "Data edited successfully", null);
        } else {
            return new ResponseResult(500, "Failed to edit data", null);
        }
    }


    @DeleteMapping("/delete/{a}")
    public ResponseResult deleteData(@PathVariable String a) {
        int result = aService.deleteData(a);
        if (result > 0) {
            return new ResponseResult(200, "Data deleted successfully", null);
        } else {
            return new ResponseResult(500, "Failed to delete data", null);
        }
    }


    @GetMapping("/search")
    public ResponseResult searchByKeyword(@RequestParam String a) {
        List<A> result = aService.selectByAKeyword(a);
        if (result.size() > 0){
            return new ResponseResult(200, "search by keyword successfully", result);
        }else {
            return new ResponseResult(500, "no data is searched", result);
        }

    }

    @PostMapping("/import")
    public ResponseResult importExcel(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return new ResponseResult(400, "File is empty", null);
        }

        try {
            int result = aService.importDataFromExcel(file);

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
}
