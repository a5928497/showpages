package com.yukoon.showpages.controllers;

import com.yukoon.showpages.entities.Colors;
import com.yukoon.showpages.entities.CustomField;
import com.yukoon.showpages.entities.User;
import com.yukoon.showpages.services.ColorService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
public class ColorsController extends BasicController{
    @Autowired
    private ColorService colorService;

    @ModelAttribute
    public void getColors(@RequestParam(value = "id",required = false)Integer id, Map<String,Object> map) {
        //若为修改
        if (id !=null) {
            Colors colors = colorService.findById(id);
            map.put("colors",colors);
        }
    }

    @RequiresRoles(value = {"admin","business"},logical = Logical.OR)
    @GetMapping("/colors/{id}")
    public String toEditColors(@PathVariable("id")Integer id, Map<String,Object> map) {
        User me = whoAmI();
        System.out.println(me);
        if (null != me && (me.getId()==id || "admin".equals(me.getRole().getRoleName()))) {
            Colors colors = colorService.findByBusinessId(id);
            map.put("colors",colors);
            map.put("id",id);
            return "backend/colors_input";
        }else if (null != me){
            Colors colors = colorService.findByBusinessId(me.getId());
            map.put("colors",colors);
            map.put("id",me.getId());
            return "backend/colors_input";
        }
        return "redirect:/logout";
    }

    @RequiresRoles(value = {"admin","business"},logical = Logical.OR)
    @PostMapping("/colors")
    public String addColors(Colors colors) {
        User me = whoAmI();
        if (null != me && (me.getId() == colors.getBusiness().getId() || "admin".equals(me.getRole().getRoleName()))) {
            colors = colorService.saveColors(colors);
            return "redirect:/colors/" + colors.getBusiness().getId();
        }
        return "redirect:/colors/" + me.getId();
    }

    @RequiresRoles(value = {"admin","business"},logical = Logical.OR)
    @PutMapping("/colors")
    public String editColors(Colors colors) {
        User me = whoAmI();
        if (null != me && (me.getId() == colors.getBusiness().getId() || "admin".equals(me.getRole().getRoleName()))) {
            colors = colorService.saveColors(colors);
            return "redirect:/colors/" + colors.getBusiness().getId();
        }
        return "redirect:/colors/" + me.getId();
    }
}
