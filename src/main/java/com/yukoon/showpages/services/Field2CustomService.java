package com.yukoon.showpages.services;

import com.yukoon.showpages.entities.CustomField;
import com.yukoon.showpages.entities.Field2Custom;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class Field2CustomService {
    @Autowired
    private CustomFieldService customFieldService;

    public List<Field2Custom> getAllField2CutsomByBusinessId(Integer businessId) {
        List<CustomField> customFields = customFieldService.findAllByBusinessId(businessId);
        List<Field2Custom> field2Customs = new ArrayList<>();
        for (CustomField customField : customFields) {
            field2Customs.add(fieldHandler(fieldConvertor(customField)));
        }
        //根据顺序进行排序
        Collections.sort(field2Customs, new Comparator<Field2Custom>() {
            @Override
            public int compare(Field2Custom o1, Field2Custom o2) {
                return o1.getOrder().compareTo(o2.getOrder());
            }
        });
        return field2Customs;
    }

    private Field2Custom fieldHandler(Field2Custom field2Custom) {
        switch (field2Custom.getType()) {
            case 6:
                return timesResolver(field2Custom);
            default:
                return field2Custom;
        }
    }

    private Field2Custom fieldConvertor(CustomField customField) {
        Field2Custom field2Custom = new Field2Custom();
        field2Custom.setType(customField.getType()).setOrder(customField.getOrder()).setTitle(customField.getTitle()).setCondition(customField.getCondition());
        return field2Custom;
    }

    private Field2Custom timesResolver(Field2Custom field2Custom) {
        String[] conditions = field2Custom.getCondition().split(",");
        Integer interval = Integer.valueOf(conditions[0]);
        StringBuffer stringBuffer = new StringBuffer();
        //
        for (int i = 1;i<conditions.length;i++) {
            Integer totalTime = 0;
            Integer beginHour = Integer.valueOf(StringUtils.substringBefore(conditions[i],":"));
            Integer overHour = Integer.valueOf(StringUtils.substringAfter(StringUtils.substringBeforeLast(conditions[i], ":"),"-"));
            Integer beginMin = Integer.valueOf(StringUtils.substringBefore(StringUtils.substringAfter(conditions[i],":"),"-"));
            Integer overMin = Integer.valueOf(StringUtils.substringAfterLast(conditions[i],":"));
            totalTime = (overHour - beginHour) * 60;
            totalTime = totalTime + (overMin - beginMin);
            for (int j = 0;j<(totalTime/interval);j++) {
                if (j == (totalTime/interval)-1 && (totalTime%interval) == 0 ) {
                    overMin = Integer.valueOf(StringUtils.substringAfterLast(conditions[i],":"));
                    overHour = Integer.valueOf(StringUtils.substringAfter(StringUtils.substringBeforeLast(conditions[i], ":"),"-"));;
                }else {
                    overMin = beginMin + (interval -1);
                    overHour = beginHour;
                    if (overMin >= 60) {
                        overHour = beginHour + (interval/60);
                        overMin = overMin + (interval%60) - interval;
                        overHour = overMin >= 60?overHour +1:overHour;
                        overMin = overMin >=60? overMin - 60: overMin;
                    }
                }
                stringBuffer.append(beginHour + ":" + singleHandler(beginMin) + "-" + overHour + ":" + singleHandler(overMin)+ ",");
                //生成下一个时间段
                beginMin = overMin + 1;
                beginHour = overHour;
                if (beginMin >= 60) {
                    beginHour = beginHour + 1;
                    beginMin = beginMin-60;
                }
            }
            if (totalTime%interval != 0) {
                beginHour = overHour;
                if (beginMin >= 60 || beginMin == 0) {
                    beginHour = beginHour + 1;
                    beginMin = beginMin >=60?beginMin - 60:beginMin;
                }
                overMin = Integer.valueOf(StringUtils.substringAfterLast(conditions[i],":"));
                overHour = Integer.valueOf(StringUtils.substringAfter(StringUtils.substringBeforeLast(conditions[i], ":"),"-"));;
                stringBuffer.append(beginHour + ":" + singleHandler(beginMin) + "-" + overHour + ":" + singleHandler(overMin) + ",");
            }
        }
        String result = StringUtils.substringBeforeLast(stringBuffer.toString(),",");
        field2Custom.setCondition(result);
        return field2Custom;
    }

    private String singleHandler(Integer num) {
        return num < 10?"0"+num: String.valueOf(num);
    }

    public static void main(String[] args) {
        Field2Custom field2Custom = new Field2Custom();
//        field2Custom.setCondition("45,16:00-17:33");
        field2Custom.setCondition("30,16:00-17:30,18:30-19:30");
        new Field2CustomService().timesResolver(field2Custom);
    }
}
