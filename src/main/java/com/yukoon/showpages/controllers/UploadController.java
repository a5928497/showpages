package com.yukoon.showpages.controllers;

import com.yukoon.showpages.config.PathConfig;
import com.yukoon.showpages.entities.User;
import com.yukoon.showpages.utils.FileUtil;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
public class UploadController extends BasicController {

    @Autowired
    private PathConfig pathConfig;

    //后台前往图片上传界面
    @RequiresRoles(value = {"admin","business"},logical = Logical.OR)
    @GetMapping("/themeupload/{id}")
    public String toThemeUpload(@PathVariable("id")Integer id, Map<String,Object> map,String uploadMsg) {
        if (null != uploadMsg) {
            map.put("uploadMsg",uploadMsg);
        }
        User me = whoAmI();
        map.put("user",me);
        if (null != me && (me.getId() == id || "admin".equals(me.getRole().getRoleName()))) {
            return "/backend/theme_img_upload";
        }
        return "redirect:/bus_dashboard/" + me.getId();
    }

    //后台主题图片上传
    @RequiresRoles(value = {"admin","business"},logical = Logical.OR)
    @PostMapping("/themeimgupload")
    public String uploadTheme(@RequestParam("pic")MultipartFile pic, HttpServletRequest request
            , String themeImg, RedirectAttributes attributes){
        String filePath = pathConfig.getWelcomePageImgPath();
        String fileName = pic.getOriginalFilename();
        String uploadMsg = "图片上传成功!";
        if (!FileUtil.isImg(fileName)){
            uploadMsg = "该文件不是图片格式,请重新上传!";
            attributes.addFlashAttribute("uploadMsg",uploadMsg);
            attributes.addFlashAttribute("prevImg",themeImg);
            return "redirect:/touploadthemeimg";
        }
        //重命名文件
        fileName = themeImg;
        try {
            //上传图片
            FileUtil.uploadFile(pic.getBytes(),filePath,fileName);
        }catch (Exception e) {
            uploadMsg = "图片上传出现错误,请重新上传!";
            attributes.addFlashAttribute("uploadMsg",uploadMsg);
            attributes.addFlashAttribute("prevImg",themeImg);
            return "redirect:/touploadthemeimg";
        }
        attributes.addFlashAttribute("uploadMsg",uploadMsg);
        attributes.addFlashAttribute("prevImg",themeImg);
        return "redirect:/touploadthemeimg";
    }
}
