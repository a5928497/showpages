package com.yukoon.showpages.controllers;

import com.yukoon.showpages.entities.User;
import com.yukoon.showpages.entities.WelcomeInfo;
import com.yukoon.showpages.services.WelcomeInfoService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class WelcomeInfoController extends BasicController {

    @Autowired
    private WelcomeInfoService welcomeInfoService;

    //获取WelcomeInfo对象
    @ModelAttribute
    public void getReward(@RequestParam(value = "id",required = false)Integer id, Map<String,Object> map) {
        //若为修改
        if (id !=null) {
            WelcomeInfo welcomeInfo = welcomeInfoService.findById(id);
            map.put("welcomeInfo",welcomeInfo);
        }
    }

    @RequiresRoles(value = {"admin","business"},logical = Logical.OR)
    @GetMapping("/welcomeInfo/{id}")
    public String toEditWelcomeInfo(@PathVariable("id")Integer id, Map<String,Object> map) {
        User me = whoAmI();
        if (null != me && (id == me.getId() || "admin".equals(me.getRole().getRoleName()))) {
            WelcomeInfo welcomeInfo = welcomeInfoService.findByBusinessId(id);
            if (null != welcomeInfo) {
                map.put("welcomeInfo",welcomeInfo);
            }
            map.put("id",id);
            return "backend/welcomeInfo_input";
        }
        //非法访问则返回用户所在控制台界面
        return "redirect:/bus_dashboard/" + me.getId();
    }

    @RequiresRoles(value = {"admin","business"},logical = Logical.OR)
    @PostMapping("/welcomeInfo")
    public String addWelcomeInfo(WelcomeInfo welcomeInfo) {
        User me = whoAmI();
        if (null != me && (welcomeInfo.getBusiness().getId() == me.getId() || "admin".equals(me.getRole().getRoleName()))) {
            welcomeInfo = welcomeInfoService.saveWelcomeInfo(welcomeInfo);
            return "redirect:/welcomeInfo/" + welcomeInfo.getBusiness().getId();
        }
        //非法访问则返回用户所在控制台界面
        return "redirect:/bus_dashboard/" + me.getId();
    }

    @RequiresRoles(value = {"admin","business"},logical = Logical.OR)
    @PutMapping("/welcomeInfo")
    public String editWelcomeInfo(WelcomeInfo welcomeInfo) {
        User me = whoAmI();
        if (null != me && (welcomeInfo.getBusiness().getId() == me.getId() || "admin".equals(me.getRole().getRoleName()))) {
            welcomeInfo = welcomeInfoService.saveWelcomeInfo(welcomeInfo);
            return "redirect:/welcomeInfo/" + welcomeInfo.getBusiness().getId();
        }
        //非法访问则返回用户所在控制台界面
        return "redirect:/bus_dashboard/" + me.getId();
    }
}
