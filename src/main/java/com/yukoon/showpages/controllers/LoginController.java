package com.yukoon.showpages.controllers;

import com.yukoon.showpages.entities.Permission;
import com.yukoon.showpages.entities.Results;
import com.yukoon.showpages.entities.Role;
import com.yukoon.showpages.entities.User;
import com.yukoon.showpages.services.PermissionService;
import com.yukoon.showpages.services.ResultsService;
import com.yukoon.showpages.services.RoleService;
import com.yukoon.showpages.services.UserService;
import com.yukoon.showpages.utils.EncodeUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class LoginController extends BasicController{
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private ResultsService resultsService;

    //前往后台登录界面
    @GetMapping("/backend")
    public String toBackend() {
        //若没找到管理员，则初始化系统
        if (userService.findAllAdmin().size() == 0) {
            initAdmin();
        }
        return "backend/backend";
    }

    //前往后台管理员界面
    @RequiresRoles("admin")
    @GetMapping("/admin_dashboard")
    public String toAdminDashboard(Map<String,Object> map) {
        List<User> users = userService.findAllBusiness();
        map.put("users",users);
        return "backend/admin_dashboard";
    }

    //前往后台商家界面
    @RequiresRoles(value = {"admin","business"},logical = Logical.OR)
    @GetMapping("/bus_dashboard/{id}")
    public String toBusDashboard(@PathVariable("id")Integer id,Map<String,Object> map) {
        User me = whoAmI();
        if (null != me && (me.getId() == id || "admin".equals(me.getRole().getRoleName()))) {
            User user = userService.findById(id);
            List<Results> newResults =resultsService.findNewsByBussinessId(id);
            List<Results> allResults = resultsService.findAllByBusinessId(id);
            map.put("user",user);
            map.put("newNum",newResults.size());
            map.put("totalNum",allResults.size());
            return "backend/bus_dashboard";
        }else if (null != me) {
            List<Results> newResults =resultsService.findNewsByBussinessId(me.getId());
            List<Results> allResults = resultsService.findAllByBusinessId(me.getId());
            map.put("user",me);
            map.put("newNum",newResults.size());
            map.put("totalNum",allResults.size());
            return "backend/bus_dashboard";
        }
        return "redirect:/logout";
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
                return errorHandle(flag);
            }
        }
        user = userService.findByUsername(user.getUsername());
        if (user.getStatus() == 1) {
            switch (user.getRole().getRoleName()) {
                case "admin" :
                    return "redirect:/admin_dashboard";
                case "business" :
                    return "redirect:/bus_dashboard/" + user.getId();
                default:
                    return "redirect:"+url;
            }
        }else {
            return errorHandle(flag);
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
                .setRole(admin).setTitle("管理员").setStatus(1);
        user_admin = userService.addUser(user_admin);
    }

    //出现错误返回函数
    private String errorHandle(String flag) {
        String result = null;
        if (null != flag && flag.equals("bg")) {
            //若是从后台登录，返回backend登录
           result =  "redirect:/backend";
        }
//        else {
//            //若是从前台登录，返回前台登录
//            return "redirect:/login";
//        }
        return result;
    }
}
