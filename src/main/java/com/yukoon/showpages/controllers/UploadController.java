package com.yukoon.showpages.controllers;

import com.yukoon.showpages.entities.User;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

@Controller
public class UploadController extends BasicController {

    //后台前往图片上传界面
    @RequiresRoles(value = {"admin","business"},logical = Logical.OR)
    @GetMapping("/themeupload/{id}")
    public String toThemeUpload(@PathVariable("id")Integer id, Map<String,Object> map) {
        User me = whoAmI();
        map.put("user",me);
        if (null != me && (me.getId() == id || "admin".equals(me.getRole().getRoleName()))) {
            return "/backend/theme_img_upload";
        }
        return "redirect:/bus_dashboard/" + me.getId();
    }
}
