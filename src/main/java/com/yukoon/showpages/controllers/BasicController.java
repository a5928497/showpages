package com.yukoon.showpages.controllers;

import com.yukoon.showpages.entities.User;
import com.yukoon.showpages.services.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class BasicController {
	@Autowired
	private UserService userService;

	protected User whoAmI() {
		Subject currentUser = SecurityUtils.getSubject();
		User user = null;
		if(currentUser.isAuthenticated() || currentUser.isRemembered()) {
			String username = (String) currentUser.getPrincipal();
			user = userService.findByUsername(username);
		}
		return user;
	}
}
