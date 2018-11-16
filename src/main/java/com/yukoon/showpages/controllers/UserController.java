package com.yukoon.showpages.controllers;

import com.yukoon.showpages.entities.Role;
import com.yukoon.showpages.entities.User;
import com.yukoon.showpages.services.RoleService;
import com.yukoon.showpages.services.UserService;
import com.yukoon.showpages.utils.EncodeUtil;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    private final static String BUSSINESS = "business";

    //获取User对象
    @ModelAttribute
    public void getReward(@RequestParam(value = "id",required = false)Integer id, Map<String,Object> map) {
        //若为修改
        if (id !=null) {
            User user = userService.findById(id);
            map.put("user",user);
        }
    }

    //后台前往添加商户
    @RequiresRoles("admin")
    @GetMapping("/user")
    public String toAddUser(){
        return "/backend/business_input";
    }

    //后台添加商户
    @RequiresRoles("admin")
    @PostMapping("/user")
    public String addUser(User user) {
        Role role = roleService.findByRolename(BUSSINESS);
        user.setRole(role).setPassword(EncodeUtil.encodePassword(user.getPassword(),user.getUsername()));
        user = userService.addUser(user);
        return "redirect:/admin_dashboard";
    }

    //后台前往编辑商户
    @RequiresRoles("admin")
    @GetMapping("/user/{id}")
    public String toEditUser(@PathVariable("id")Integer id, Map<String,Object> map){
        User user = userService.findById(id);
        map.put("user",user);
        return "/backend/business_input";
    }

    //后台编辑商户
    @RequiresRoles("admin")
    @PutMapping("/user")
    public String editUser(User user) {
        User temp = userService.findById(user.getId());
        if (user.getPassword().equals(temp.getPassword())) {
            user.setPassword(EncodeUtil.encodePassword(user.getPassword(),user.getUsername()));
        }
        userService.editUser(user);
        return "redirect:/admin_dashboard";
    }
}
