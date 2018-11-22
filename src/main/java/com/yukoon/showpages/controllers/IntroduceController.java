package com.yukoon.showpages.controllers;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

@Controller
public class IntroduceController extends BasicController {

    @RequiresRoles(value = {"admin","business"},logical = Logical.OR)
    @GetMapping("/welcomeInfo/{id}")
    public String toEditWelcomeInfo(@PathVariable("id")Integer id, Map<String,Object> map) {
        return null;
    }
}
