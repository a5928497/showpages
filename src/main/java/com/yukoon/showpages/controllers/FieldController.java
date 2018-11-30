package com.yukoon.showpages.controllers;

import com.yukoon.showpages.entities.CustomField;
import com.yukoon.showpages.entities.User;
import com.yukoon.showpages.services.CustomFieldService;
import com.yukoon.showpages.services.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Null;
import java.util.List;
import java.util.Map;

@Controller
public class FieldController extends BasicController{
    @Autowired
    private UserService userService;
    @Autowired
    private CustomFieldService customFieldService;

    //获取User对象
    @ModelAttribute
    public void getReward(@RequestParam(value = "id",required = false)Integer id, Map<String,Object> map) {
        //若为修改
        if (id !=null) {
            CustomField customField = customFieldService.findById(id);
            map.put("customField",customField);
        }
    }


    @RequiresRoles(value = {"admin","business"},logical = Logical.OR)
    @GetMapping("/fields/{id}")
    public String findAllfieldsByUserId(@PathVariable("id")Integer id,Map<String,Object> map) {
        User me = whoAmI();
        List<CustomField> fields = null;
        if (null != me && (me.getId() == id || "admin".equals(me.getRole().getRoleName()))) {
            fields = customFieldService.findAllByBusinessId(id);
        }else if (null != me) {
            id = me.getId();
            fields = customFieldService.findAllByBusinessId(me.getId());
        }
        map.put("id",id);
        map.put("fields",fields);
        return "/backend/fields_list";
    }

    @RequiresRoles(value = {"admin","business"},logical = Logical.OR)
    @GetMapping("/addfield/{id}")
    public String toAddField(@PathVariable("id")Integer id, Map<String,Object> map) {
        User me = whoAmI();
        if (null != me && me.getRole().getRoleName().equals("business")) {
            map.put("user",me);
            return "/backend/field_input";
        }else if ("admin".equals(me.getRole().getRoleName())) {
            User user = userService.findById(id);
            map.put("user",user);
            return "/backend/field_input";
        }
        return "redirect:/logout";
    }

    @RequiresRoles(value = {"admin","business"},logical = Logical.OR)
    @PostMapping("/field")
    public String addCustomField(CustomField customField) {
        User me = whoAmI();
        if (null != me && (me.getId() == customField.getBusiness().getId() || "admin".equals(me.getRole().getRoleName()))) {
            customField.setStatus(1);
            customField = customFieldService.saveField(customField);
            return "redirect:/fields/" + customField.getBusiness().getId();
        }
        return "redirect:/logout";
    }

    @RequiresRoles(value = {"admin","business"},logical = Logical.OR)
    @GetMapping("/editfield/{field_id}")
    public String toEditField(@PathVariable("field_id")Integer field_id, Map<String,Object> map) {
        User me = whoAmI();
        CustomField customField = customFieldService.findById(field_id);
        if (null != me && (me.getId() == customField.getBusiness().getId() || "admin".equals(me.getRole().getRoleName()))) {
            map.put("customField",customField);
            return "/backend/field_input";
        }
        return "redirect:/logout";
    }

    @RequiresRoles(value = {"admin","business"},logical = Logical.OR)
    @GetMapping("/delfield/{id}")
    public String delField(@PathVariable("id")Integer id) {
        User me = whoAmI();
        CustomField customField = customFieldService.findById(id);
        if (customField.getBusiness().getId() == me.getId() || "admin".equals(me.getRole().getRoleName())) {
            customField.setStatus(0);
            customFieldService.saveField(customField);
        }
        return "redirect:/fields/" + customField.getBusiness().getId();
    }
}
