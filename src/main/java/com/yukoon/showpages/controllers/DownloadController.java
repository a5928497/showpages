package com.yukoon.showpages.controllers;

import com.yukoon.showpages.entities.User;
import com.yukoon.showpages.services.DownloadService;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
public class DownloadController extends BasicController{
    @Autowired
    private DownloadService downloadService;

    @RequiresRoles(value = {"admin","business"},logical = Logical.OR)
    @ResponseBody
    @GetMapping("/exportresults/{id}")
    public void exportResults(@PathVariable("id")Integer id,HttpServletRequest request,HttpServletResponse response) {
        User me = whoAmI();
        if (null != me && (me.getId() == id || "admin".equals(me.getRole().getRoleName()))) {
            try {
                XSSFWorkbook workbook = downloadService.exportAllResultsByGameInfoId(id);
                String fileName = me.getTitle();
                export(workbook,fileName,request,response);
            }catch (Exception e) {
                e.printStackTrace();
            }
        }else if (null != me) {
            try {
                XSSFWorkbook workbook = downloadService.exportAllResultsByGameInfoId(me.getId());
                String fileName = me.getTitle();
                export(workbook,fileName,request,response);
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void export(XSSFWorkbook workbook,String fileName,HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException {
        response.reset(); //清除buffer缓存
        Map<String,Object> map = new HashMap<String,Object>();
        request.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename="+new String(fileName.getBytes(),"UTF-8")+".xlsx");
        response.setContentType("application/vnd.ms-excel;charset=UTF-8");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        try {
            OutputStream out = response.getOutputStream();
            BufferedOutputStream bout = new BufferedOutputStream(out);
            bout.flush();
            workbook.write(bout);
            bout.close();
        }catch (Exception e) {
            e.printStackTrace();
            System.out.println("失败");
        }
        System.out.println("成功");
    }
}
