package com.yukoon.showpages.controllers;

import com.yukoon.showpages.entities.User;
import com.yukoon.showpages.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

@Controller
public class FieldController {
    @Autowired
    private UserService userService;

    @GetMapping("/fields/{id}")
    public String findAllfieldsByUserId(@PathVariable("id")Integer id, Map<String,Object> map) {
        User user = userService.findById(id);
        map.put("user",user);
        return "/backend/field_input";
    }
}
