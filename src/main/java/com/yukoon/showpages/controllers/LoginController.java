package com.yukoon.showpages.controllers;

import com.yukoon.showpages.entities.Permission;
import com.yukoon.showpages.entities.Role;
import com.yukoon.showpages.entities.User;
import com.yukoon.showpages.services.PermissionService;
import com.yukoon.showpages.services.RoleService;
import com.yukoon.showpages.services.UserService;
import com.yukoon.showpages.utils.EncodeUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class LoginController {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PermissionService permissionService;

    //前往后台登录界面
    @GetMapping("/backend")
    public String toBackend() {
        //若没找到管理员，则初始化系统
        if (userService.findAllAdmin().size() == 0) {
            initAdmin();
        }
        return "/backend/backend";
    }

    //前往后台管理员界面
    @RequiresRoles("admin")
    @GetMapping("/admin_dashboard")
    public String toAdminDashboard(Map<String,Object> map) {
        List<User> users = userService.findAllBusiness();
        map.put("users",users);
        return "/backend/admin_dashboard";
    }

    //前往后台商家界面
    @RequiresRoles({"admin","business"})
    @GetMapping("/bus_dashboard")
    public String toBusDashboard() {
        return "/backend/bus_dashboard";
    }

    //处理登录请求
    @PostMapping("/login")
    public String login(User user,String flag,String url) {
        //获得subject
        Subject currentUser = SecurityUtils.getSubject();
        if(!currentUser.isAuthenticated()){
            //把用户名密码封装为Token对象
            String username = user.getUsername();
            UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username, EncodeUtil.encodePassword(user.getPassword(),username));
            //设置token的rememberme
            usernamePasswordToken.setRememberMe(true);
            try {
                //执行登录
                currentUser.login(usernamePasswordToken);
            }catch (AuthenticationException ae){
                System.out.println("登陆失败:"+ae.toString());
                if (null != flag && flag.equals("bg")) {
                    //若是从后台登录，返回backend登录
                    return "redirect:/backend";
                }
//                else {
//                    //若是从前台登录，返回前台登录
//                    return "redirect:/login";
//                }
            }
        }
        user = userService.findByUsername(user.getUsername());
        switch (user.getRole().getRoleName()) {
            case "admin" :
                return "redirect:/admin_dashboard";
            case "bussiness" :
                return "redirect:/bus_dashboard";
            default:
                return "redirect:"+url;
        }
    }

    //初始化角色，权限并创建一个管理员
    private void initAdmin() {
        //添加角色
        Role admin = new Role().setRoleName("admin");
        Role business = new Role().setRoleName("business");
        Role custom = new Role().setRoleName("custom");
        admin = roleService.addRole(admin);
        business = roleService.addRole(business);
        custom = roleService.addRole(custom);
        //添加权限
        List<Permission> permissions = new ArrayList<>();
        permissions.add(new Permission().setPermName("admin_query").setRole(admin));
        permissions.add(new Permission().setPermName("admin_add").setRole(admin));
        permissions.add(new Permission().setPermName("admin_del").setRole(admin));
        permissions.add(new Permission().setPermName("admin_update").setRole(admin));
        permissions.add(new Permission().setPermName("bus_query").setRole(business));
        permissions.add(new Permission().setPermName("bus_add").setRole(business));
        permissions.add(new Permission().setPermName("bus_del").setRole(business));
        permissions.add(new Permission().setPermName("bus_update").setRole(business));
        for (Permission temp : permissions) {
            permissionService.addPermission(temp);
        }
        //添加管理员
        User user_admin = new User().setUsername("admin").setPassword(EncodeUtil
                .encodePassword("admin","admin"))
                .setRole(admin).setTitle("管理员");
        user_admin = userService.addUser(user_admin);
    }
}
