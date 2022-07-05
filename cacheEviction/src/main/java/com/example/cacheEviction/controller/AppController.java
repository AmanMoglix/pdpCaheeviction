package com.example.cacheEviction.controller;

import com.example.cacheEviction.utils.ExcelModel;
import com.example.cacheEviction.utils.ExcelToBeanList;
import com.example.cacheEviction.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/clean/*")
public class AppController {

    @Autowired
    ExcelToBeanList excelToBeanList;

    @Autowired
    RedisUtils utils;

    @GetMapping("uploadFile")
    public String cleanCache(@RequestParam MultipartFile file){
       List<ExcelModel> msnList= excelToBeanList.getBeanList(file);
       List<ExcelModel> chunk= new ArrayList<>();
        for(int i=0; i<msnList.size(); i+=1000) {
            chunk = msnList.subList(i, i + Math.min(msnList.size()-i, 1000));


            for(ExcelModel excelModel:chunk){

           String resonse= utils.getApi("https://platformapiprod.moglix.com/cassandraApi/product/clearProductCacheById?productId=",excelModel.getMsn());
                System.out.println("response from  cache eviction : "+resonse );
            }
        }
        return null;
    }


}
