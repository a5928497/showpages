package com.yukoon.showpages.controllers;

import com.yukoon.showpages.config.PathConfig;
import com.yukoon.showpages.entities.Extension;
import com.yukoon.showpages.entities.User;
import com.yukoon.showpages.services.ExtensionService;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
public class ExtensionController extends BasicController{
    @Autowired
    private ExtensionService extensionService;
    @Autowired
    private PathConfig pathConfig;

    @RequiresRoles(value = {"admin","business"},logical = Logical.OR)
    @GetMapping("/extensions/{id}")
    public String findAllExtensionsByBusinessId(@PathVariable("id")Integer id , Map<String,Object> map) {
        User me = whoAmI();
        if (null != me && (me.getId() == id || "admin".equals(me.getRole().getRoleName()))) {
            List<Extension> extensions = extensionService.findAllbyBuesinessId(id);
            map.put("extensions",extensions);
            map.put("id",id);
            return "/backend/extension_list";
        }else if (null != me){
            List<Extension> extensions = extensionService.findAllbyBuesinessId(me.getId());
            map.put("extensions",extensions);
            map.put("id",me.getId());
            return "/backend/extension_list";
        }
        return "redirect:/logout";
    }

    @RequiresRoles(value = {"admin","business"},logical = Logical.OR)
    @GetMapping("/addextension/{id}")
    public String toAddExtension(@PathVariable("id")Integer id , Map<String,Object> map,String uploadMsg) {
        User me = whoAmI();
        if (uploadMsg != null) {
            map.put("uploadMsg",uploadMsg);
        }
        if (null != me && (me.getId() == id || "admin".equals(me.getRole().getRoleName()))) {
            map.put("id",id);
            return "/backend/extend_img_upload";
        }else if (null != me) {
            map.put("id",me.getId());
            return "/backend/extend_img_upload";
        }
        return "redirect:/logout";
    }

    @RequiresRoles(value = {"admin","business"},logical = Logical.OR)
    @PostMapping("/extension")
    public String addExtension(@RequestParam("pic")MultipartFile pic, HttpServletRequest request,
                               Extension extension, Map<String,Object> map) {
        User me = whoAmI();
        if (null != me && (me.getId() == extension.getBusiness().getId() || "admin".equals(me.getRole().getRoleName()))) {

        }else if (null != me && null != extension.getBusiness().getId()) {
//            String filePath = pathConfig.getWelcomePageImgPath() + StringUtils.substringBeforeLast(themeImg,"/")+"/";
            String fileName = pic.getOriginalFilename();
        }
        return null;
    }
}
