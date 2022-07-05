package com.example.cacheEviction.utils;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
@Component
public class ExcelToBeanList {
    public List<ExcelModel> getBeanList(MultipartFile files){
        String fileName= files.getOriginalFilename();
        Workbook workbook=null;
        try {
            if (files instanceof File) {
                FileInputStream excelInputStream = new FileInputStream((File) files);
                if(fileName.contains(".xlsx"))
                    workbook= new XSSFWorkbook(excelInputStream);
                else if (fileName.contains(".xls"))
                    workbook=new HSSFWorkbook(excelInputStream);
                else {
                    excelInputStream.close();
                    throw new RuntimeException("The file you have requested for must be in .xlsx or .xls format. ");
                }
            }
            if(files instanceof MultipartFile){

                byte[] fileByteArray=((MultipartFile) files).getBytes();
                InputStream finStream= new ByteArrayInputStream(fileByteArray);
                if(fileName.contains(".xlsx"))
                    workbook= new XSSFWorkbook(finStream);
                else if(fileName.contains(".xls"))
                    workbook= new HSSFWorkbook(finStream);
                else {
                    finStream.close();
                    throw new RuntimeException("The file you have requested for must be in .xlsx or .xls format. ");
                }

                ExcelModel excelModel= new ExcelModel();
                List<ExcelModel> list= new ArrayList<>();
                Sheet sheet= workbook.getSheetAt(0);
                FormulaEvaluator evaluator=workbook.getCreationHelper().createFormulaEvaluator();
                for(int i=1; i<sheet.getPhysicalNumberOfRows();i++){
                    excelModel= new ExcelModel();
                    Row row=sheet.getRow(i);
                    //if row is not empty
                    if(row!=null) {

                        Cell c0 = row.getCell(0, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
                        if (c0 != null) {
                            DataFormatter formatter = new DataFormatter();
                            String msn = formatter.formatCellValue(c0, evaluator).toString();
                            excelModel.setMsn(msn);
                        }

                        list.add(excelModel);
                    }
                }

                return list;
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }
}
