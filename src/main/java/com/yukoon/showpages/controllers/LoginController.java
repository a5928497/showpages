package com.yukoon.showpages.controllers;

import com.yukoon.showpages.entities.Permission;
import com.yukoon.showpages.entities.Role;
import com.yukoon.showpages.entities.User;
import com.yukoon.showpages.services.PermissionService;
import com.yukoon.showpages.services.RoleService;
import com.yukoon.showpages.services.UserService;
import com.yukoon.showpages.utils.EncodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class LoginController {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PermissionService permissionService;

    @GetMapping("/backend")
    public String toBackend() {
        //若没找到管理员，则初始化系统
        if (userService.findAllAdmin().size() == 0) {
            initAdmin();
        }
        return "/backend/backend";
    }

    //初始化角色，权限并创建一个管理员
    private void initAdmin() {
        //添加角色
        Role admin = new Role().setRoleName("admin");
        Role business = new Role().setRoleName("business");
        admin = roleService.addRole(admin);
        business = roleService.addRole(business);
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
                .setRole(admin);
        user_admin = userService.addUser(user_admin);
    }
}
