package com.yukoon.showpages.services;

import com.yukoon.showpages.entities.Colors;
import com.yukoon.showpages.repos.ColorsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;

@Service
public class ColorService {
    @Autowired
    private ColorsRepo colorsRepo;

    private final static String INIT_COLOR = "#000000";

    @Transactional
    public Colors saveColors(Colors colors){
        if ("".equals(colors.getDetailsPageFontColor()) || null == colors.getDetailsPageFontColor()) {
            colors.setDetailsPageFontColor(INIT_COLOR);
        }
        if ("".equals(colors.getDetailsPageBackGroundColor()) || null == colors.getDetailsPageBackGroundColor()) {
            colors.setDetailsPageBackGroundColor(INIT_COLOR);
        }
        if ("".equals(colors.getWelcomePageFontColor()) || null == colors.getWelcomePageFontColor()) {
            colors.setWelcomePageFontColor(INIT_COLOR);
        }
        return colorsRepo.saveAndFlush(colors);
    }

    public Colors findByBusinessId(Integer id) {
        return colorsRepo.findByBusinessId(id);
    }

    public Colors findById(Integer id) {
        return colorsRepo.findOne(id);
    }
}
