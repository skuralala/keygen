package com.cxm.resource;

import com.cxm.entity.OrgCode;
import com.cxm.entity.User;
import com.cxm.service.MainService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class MainResource {

    @Autowired
    private MainService mainService;

    @RequestMapping("/checkcode")
    public String validate(@RequestParam(value = "code") String code) {
        String md5code = "";
        OrgCode orgCode = mainService.findByCode(code);
        if (orgCode != null) {
            byte[] secretBytes = null;
            try {
                // 生成一个MD5加密计算摘要
                MessageDigest md = MessageDigest.getInstance("MD5");
                //对字符串进行加密
                md.update(code.getBytes());
                //获得加密后的数据
                secretBytes = md.digest();
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException("没有md5这个算法！");
            }
            //将加密后的数据转换为16进制数字
            md5code = new BigInteger(1, secretBytes).toString(16);// 16进制数字
            // 如果生成数字未满32位，需要前面补0
            for (int i = 0; i < 32 - md5code.length(); i++) {
                md5code = "0" + md5code;
            }
            md5code = md5code.substring(8, 24).toUpperCase();
        }
        return md5code.length() == 16 ? md5code.substring(0, 4) + "-" + md5code.substring(4, 8) + "-" + md5code.substring(8, 12) + "-" + md5code.substring(12) : "";
    }

    @RequestMapping("/auth")
    public String auth(@RequestParam(value = "username") String username, @RequestParam(value = "password") String password) {
        User user = mainService.findByNameAndPwd(username, password);
        if (user != null) {
            return "success";
        } else {
            return "fail";
        }
    }

    @RequestMapping("/enterprise")
    public String getEnterprise() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
//        System.out.println(objectMapper.writeValueAsString(mainService.findAllCode()));
        return objectMapper.writeValueAsString(mainService.findAllCode());
    }

    @RequestMapping("/addCode")
    public String newCode(@RequestParam(value = "code") String code) {
        OrgCode orgCode = mainService.findByCode(code);
        if (orgCode != null) return "代码已存在";
        mainService.insertCode(code);
        return "ok";
    }

    @PostMapping("/upload")
    @ResponseBody
    public String upload(@RequestParam("filename") MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            return "上传失败，请选择文件";
        }
        InputStream inputStream = file.getInputStream();
        Workbook workbook = new XSSFWorkbook(inputStream);
        Sheet sheet = workbook.getSheetAt(0);
        List list = new ArrayList();
        for (int i = 3; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            OrgCode orgCode = new OrgCode();
            String code = row.getCell(2).getStringCellValue();
            orgCode.setCode(code);
            list.add(orgCode);
        }
        mainService.insertList(list);
        workbook.close();
        inputStream.close();
        return "导入成功";
    }

}
