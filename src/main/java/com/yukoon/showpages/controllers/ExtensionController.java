package com.yukoon.showpages.controllers;

import com.yukoon.showpages.config.PathConfig;
import com.yukoon.showpages.entities.Extension;
import com.yukoon.showpages.entities.User;
import com.yukoon.showpages.services.ExtensionService;
import com.yukoon.showpages.utils.FileUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.List;
import java.util.Map;

@Controller
public class ExtensionController extends BasicController{
    @Autowired
    private ExtensionService extensionService;
    @Autowired
    private PathConfig pathConfig;

    private final static Integer HEIGHT = 0;
    private final static Integer WIDTH = 0;

    @ModelAttribute
    public void getExtension(@RequestParam(value = "id",required = false)Integer id, Map<String,Object> map) {
        //若为修改
        if (id !=null) {
            Extension extension = extensionService.findById(id);
            map.put("extension",extension);
        }
    }

    //后台查询所有扩展链接
    @RequiresRoles(value = {"admin","business"},logical = Logical.OR)
    @GetMapping("/extensions/{id}")
    public String findAllExtensionsByBusinessId(@PathVariable("id")Integer id , Map<String,Object> map) {
        User me = whoAmI();
        if (null != me && (me.getId() == id || "admin".equals(me.getRole().getRoleName()))) {
            List<Extension> extensions = extensionService.findAllbyBuesinessId(id);
            map.put("extensions",extensions);
            map.put("id",id);
            return "backend/extension_list";
        }else if (null != me){
            List<Extension> extensions = extensionService.findAllbyBuesinessId(me.getId());
            map.put("extensions",extensions);
            map.put("id",me.getId());
            return "backend/extension_list";
        }
        return "redirect:/logout";
    }

    //后台删除扩展链接
    @RequiresRoles(value = {"admin","business"},logical = Logical.OR)
    @GetMapping("/delextension/{id}")
    public String delExtension(@PathVariable("id")Integer id) {
        User me = whoAmI();
        Extension extension = extensionService.findById(id);
        if (null != me && (me.getId() == extension.getBusiness().getId() || "admin".equals(me.getRole().getRoleName()))) {
            //删除图片
            if (!"".equals(extension.getImgName()) && null != extension.getImgName()) {
                String filePath = pathConfig.getExtensionImgPath() + extension.getBusiness().getUsername() +"/" + extension.getImgName();
                FileUtil.delete(filePath);
            }
            //删除记录
            extensionService.delExtension(id);
            return "redirect:/extensions/" + extension.getBusiness().getId();
        }else{
            return "redirect:/extensions/" + me.getId();
        }
    }

    //后台前往添加扩展连接
    @RequiresRoles(value = {"admin","business"},logical = Logical.OR)
    @GetMapping("/addextension/{id}")
    public String toAddExtension(@PathVariable("id")Integer id , Map<String,Object> map,String uploadMsg) {
        User me = whoAmI();
        if (uploadMsg != null) {
            map.put("uploadMsg",uploadMsg);
        }
        if (null != me && (me.getId() == id || "admin".equals(me.getRole().getRoleName()))) {
            map.put("id",id);
            return "backend/extend_img_upload";
        }else if (null != me) {
            map.put("id",me.getId());
            return "backend/extend_img_upload";
        }
        return "redirect:/logout";
    }

    //后台前往编辑扩展链接
    @RequiresRoles(value = {"admin","business"},logical = Logical.OR)
    @GetMapping("/editextension/{id}")
    public String toEditExtension(@PathVariable("id")Integer id,Map<String,Object> map) {
        Extension extension = extensionService.findById(id);
        User me = whoAmI();
        if ( !("".equals(extension.getImgName())) || !(null == extension.getImgName())) {
            map.put("img_src","/" + me.getUsername() + "/" + extension.getImgName());
        }
        if (null != me && (me.getId() == extension.getBusiness().getId() || "admin".equals(me.getRole().getRoleName()))) {
            map.put("extension",extension);
            return "backend/extend_img_upload";
        }else if (null != me) {
            return "redirect:/extensions/" + me.getId();
        }
        return "redirect:/logout";
    }

    @RequiresRoles(value = {"admin","business"},logical = Logical.OR)
    @PutMapping("/extension")
    public String editExtension(@RequestParam("pic")MultipartFile pic, HttpServletRequest request,
                                Extension extension, Map<String,Object> map,RedirectAttributes attributes) {
        User me = whoAmI();
        String filePath;
        String fileName = pic.getOriginalFilename();
        String uploadMsg;
        if (null != me && (me.getId() == extension.getBusiness().getId() || "admin".equals(me.getRole().getRoleName()))) {
            extension = extensionService.saveExtension(extension);
            if (!"".equals(fileName)) {
                filePath = pathConfig.getExtensionImgPath() + me.getUsername() +"/";
                uploadMsg = "图片上传成功!";
                if (!FileUtil.isImg(fileName)){
                    uploadMsg = "该文件不是图片格式,请重新上传!";
                    attributes.addFlashAttribute("uploadMsg",uploadMsg);
                    return "redirect:/editextension/" + extension.getId();
                }
                //重命名文件
                fileName = extension.getImgName();
                try {
                    //上传图片
                    FileUtil.uploadFile(pic.getBytes(),filePath,fileName);
                    //调整图片大小
//                    filePath = filePath + fileName;
//                    FileUtil.resizeImg(filePath,WIDTH,HEIGHT);
                }catch (Exception e) {
                    uploadMsg = "图片上传出现错误,请重新上传!";
                    attributes.addFlashAttribute("uploadMsg",uploadMsg);
                    return "redirect:/editextension/" + extension.getId();
                }
            }
            return "redirect:/editextension/" + extension.getId();
        }else{
            return "redirect:/extensions/" + me.getId();
        }
    }

    @RequiresRoles(value = {"admin","business"},logical = Logical.OR)
    @PostMapping("/extension")
    public String addExtension(@RequestParam("pic")MultipartFile pic, HttpServletRequest request,
                               Extension extension, Map<String,Object> map,RedirectAttributes attributes) {
        User me = whoAmI();
        String filePath;
        String fileName = pic.getOriginalFilename();
        String uploadMsg;
        if (null != me && (me.getId() == extension.getBusiness().getId() || "admin".equals(me.getRole().getRoleName()))) {
            extension = extensionService.saveExtension(extension);
            if (!"".equals(fileName)) {
                filePath = pathConfig.getExtensionImgPath() + me.getUsername() +"/";
                uploadMsg = "图片上传成功!";
                if (!FileUtil.isImg(fileName)){
                    uploadMsg = "该文件不是图片格式,请重新上传!";
                    attributes.addFlashAttribute("uploadMsg",uploadMsg);
                    return "redirect:/addextension/" + me.getId();
                }
                //重命名文件
                fileName = "extension" + extension.getId() + "." + StringUtils.substringAfterLast(fileName,".");
                //记录图片名
                extension.setImgName(fileName);
                extension = extensionService.saveExtension(extension);
                try {
                    //上传图片
                    FileUtil.uploadFile(pic.getBytes(),filePath,fileName);
                    //调整图片大小
//                    filePath = filePath + fileName;
//                    FileUtil.resizeImg(filePath,WIDTH,HEIGHT);
                }catch (Exception e) {
                    uploadMsg = "图片上传出现错误,请重新上传!";
                    attributes.addFlashAttribute("uploadMsg",uploadMsg);
                    return "redirect:/addextension/" + me.getId();
                }
            }
            return "redirect:/editextension/" + extension.getId();
        }else{
            return "redirect:/extensions/" + me.getId();
        }
    }
}
