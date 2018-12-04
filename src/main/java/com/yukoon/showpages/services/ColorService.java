package com.yukoon.showpages.services;

import com.yukoon.showpages.entities.Colors;
import com.yukoon.showpages.repos.ColorsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ColorService {
    @Autowired
    private ColorsRepo colorsRepo;

    @Transactional
    public Colors saveColors(Colors colors) {
        return colorsRepo.saveAndFlush(colors);
    }

    public Colors findByBusinessId(Integer id) {
        return colorsRepo.findByBusinessId(id);
    }
}
