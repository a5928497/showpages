package com.yukoon.showpages.services;

import com.yukoon.showpages.entities.Excel;
import com.yukoon.showpages.entities.Results;
import com.yukoon.showpages.utils.ExcelUtil;
import org.apache.el.parser.ParseException;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class DownloadService {
    @Autowired
    private ResultsService resultsService;

    public XSSFWorkbook exportExcel(List list, List<Excel> excels, String excelName, Class clazz) throws InvocationTargetException, ClassNotFoundException, IntrospectionException, ParseException, IllegalAccessException {
        Map<Integer,List<Excel>> map = new LinkedHashMap<>();
        XSSFWorkbook xssfWorkbook = null;
        map.put(0,excels);
        xssfWorkbook = ExcelUtil.createExcelFile(clazz,list,map,excelName);
        return xssfWorkbook;
    }

    //根据某条活动记录导出帮助者名单
    public XSSFWorkbook exportAllResultsByGameInfoId(Integer businessId) throws ClassNotFoundException, IntrospectionException, IllegalAccessException, ParseException, InvocationTargetException {
        List<Results> list = resultsService.findAllByBusinessId(businessId);
        List<Excel> excels = new ArrayList<>();
        //设置标题栏
        excels.add(new Excel("字段1","key1",0));
        excels.add(new Excel("回复1","value1",0));
        excels.add(new Excel("字段2","key2",0));
        excels.add(new Excel("回复2","value2",0));
        excels.add(new Excel("字段3","key3",0));
        excels.add(new Excel("回复3","value3",0));
        excels.add(new Excel("字段4","key4",0));
        excels.add(new Excel("回复4","value4",0));
        excels.add(new Excel("字段5","key5",0));
        excels.add(new Excel("回复5","value5",0));
        excels.add(new Excel("字段6","key6",0));
        excels.add(new Excel("回复6","value6",0));
        excels.add(new Excel("字段7","key7",0));
        excels.add(new Excel("回复7","value7",0));
        String excelName = list.get(0).getBusiness().getTitle()+"的统计";
        return exportExcel(list,excels,excelName,Results.class);
    }
}
