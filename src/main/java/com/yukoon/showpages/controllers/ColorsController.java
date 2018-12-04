package com.yukoon.showpages.controllers;

import com.yukoon.showpages.entities.Colors;
import com.yukoon.showpages.entities.User;
import com.yukoon.showpages.services.ColorService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

@Controller
public class ColorsController extends BasicController{
    @Autowired
    private ColorService colorService;

    @RequiresRoles(value = {"admin","business"},logical = Logical.OR)
    @GetMapping("/colors/{id}")
    public String toEditColors(@PathVariable("id")Integer id, Map<String,Object> map) {
        User me = whoAmI();
        if (null != me && (me.getId()==id || "admin".equals(me.getRole().getRoleName()))) {
            Colors colors = colorService.findByBusinessId(id);
            map.put("colors",colors);
            map.put("id",id);
        }else if (null != me){
            Colors colors = colorService.findByBusinessId(me.getId());
            map.put("colors",colors);
            map.put("id",me.getId());
        }
        return "redirect:/logout";
    }
}
