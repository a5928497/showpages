package com.yukoon.showpages.controllers;

import com.yukoon.showpages.entities.Role;
import com.yukoon.showpages.entities.User;
import com.yukoon.showpages.services.RoleService;
import com.yukoon.showpages.services.UserService;
import com.yukoon.showpages.utils.EncodeUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;

@Controller
public class UserController extends BasicController {
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
        return "backend/business_input";
    }

    //后台添加商户
    @RequiresRoles("admin")
    @PostMapping("/user")
    public String addUser(User user) {
        Role role = roleService.findByRolename(BUSSINESS);
        user.setRole(role).setStatus(1)
				.setPassword(EncodeUtil.encodePassword(user.getPassword(),user.getUsername()));
        user = userService.addUser(user);
        return "redirect:/admin_dashboard";
    }

    //后台前往编辑商户
    @RequiresRoles("admin")
    @GetMapping("/user/{id}")
    public String toEditUser(@PathVariable("id")Integer id, Map<String,Object> map){
        User user = userService.findById(id);
        map.put("user",user);
        return "backend/business_input";
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

    //后台删除商户，并非真删除只是改变状态标记
	@RequiresRoles("admin")
	@GetMapping("/delusr/{id}")
	public String delUser(@PathVariable("id")Integer id) {
    	User user = userService.findById(id);
    	user.setStatus(0);
    	userService.editUser(user);
    	return "redirect:/admin_dashboard";
	}

	//后台前往修改密码
	@RequiresRoles(value = {"admin","business"},logical = Logical.OR)
	@GetMapping("/updatepsw")
	public String toUpdatePassword(Map<String,Object> map,String errMsg) {
    	if (null != errMsg) {
    		map.put("errMsg",errMsg);
		}
		//获得subject
		Subject currentUser = SecurityUtils.getSubject();
		if(currentUser.isAuthenticated() || currentUser.isRemembered()) {
			String username = (String) currentUser.getPrincipal();
			User user = userService.findByUsername(username);
			if ("admin".equals(user.getRole().getRoleName())) {
			    map.put("back_url","/admin_dashboard");
            }else {
			    map.put("back_url","/bus_dashboard/"+user.getId());
            }
			map.put("id",user.getId());
			return "backend/update_password_input";
		}else {
			return "redirect:/logout";
		}
	}

	//后台修改密码
	@RequiresRoles(value = {"admin","business"},logical = Logical.OR)
	@PutMapping("/updatepsw")
	public String updatePassword(String old_password, User user, RedirectAttributes redirectAttributes) {
    	//验证原密码
    	if (userService.vaildateOldPassword(old_password,user.getUsername())) {
    		//若正确
			user.setPassword(EncodeUtil.encodePassword(user.getPassword(),user.getUsername()));
			user = userService.editUser(user);
			if (user.getRole().getRoleName().equals("admin")) {
				return "redirect:/admin_dashboard";
			}
			return "redirect:/bus_dashboard/" + user.getId();
		}else {
    		redirectAttributes.addFlashAttribute("errMsg","原密码不正确！");
    		return "redirect:/updatepsw";
		}
	}

	//后台前往查询前台二维码及连接
    @RequiresRoles(value = {"admin","business"},logical = Logical.OR)
    @GetMapping("/qrcodes/{id}")
    public String toQRCodes(@PathVariable("id")Integer id, Map<String,Object> map) {
        User me = whoAmI();
        if (null != me) {
            map.put("details_url","/details/"+me.getUsername());
            map.put("introduce_url","/introduce/"+me.getUsername());
            map.put("id",me.getId());
            return "backend/QR_code";
        }else if ("admin".equals(me.getRole().getRoleName())) {
            User business = userService.findById(id);
            map.put("details_url","/details/"+business.getUsername());
            map.put("introduce_url","/introduce/"+business.getUsername());
            map.put("id",id);
            return "backend/QR_code";
        }
        return "redirect:/logout";
    }

	//后台检查账号是否唯一
    @ResponseBody
    @RequiresRoles(value = {"admin"})
    @GetMapping("/acccheck")
    public boolean accountCheck(String username) {
        return null == userService.findByUsername(username);
    }
}
