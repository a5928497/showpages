package com.yukoon.showpages.controllers;

import com.yukoon.showpages.entities.User;
import com.yukoon.showpages.entities.WelcomeInfo;
import com.yukoon.showpages.services.UserService;
import com.yukoon.showpages.services.WelcomeInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

@Controller
public class PublicController {

	@Autowired
	private WelcomeInfoService welcomeInfoService;
	@Autowired
	private UserService userService;

	//前台访问具体客户介绍页
	@GetMapping("/introduce/{businessName}")
	public String toIntroducePage(@PathVariable("businessName")String businessName, Map<String,Object> map) {
		User business = userService.findByUsername(businessName);
		WelcomeInfo welcomeInfo = welcomeInfoService.findByBusinessId(business.getId());
		map.put("business",business);
		map.put("paragraphs",welcomeInfo.getParagraph().split(","));
		return "/public/welcome_page";
	}

	//前台访问具体客户详情页
	@GetMapping("/details/{businessName}")
	public String toDetailsPage(@PathVariable("businessName")String businessName, Map<String,Object> map)	{
		User business = userService.findByUsername(businessName);
		map.put("business",business);
		return "/public/details_page";
	}
}
