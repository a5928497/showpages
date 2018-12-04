package com.yukoon.showpages.controllers;

import com.yukoon.showpages.entities.Colors;
import com.yukoon.showpages.entities.Field2Custom;
import com.yukoon.showpages.entities.User;
import com.yukoon.showpages.entities.WelcomeInfo;
import com.yukoon.showpages.services.ColorService;
import com.yukoon.showpages.services.Field2CustomService;
import com.yukoon.showpages.services.UserService;
import com.yukoon.showpages.services.WelcomeInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Map;

@Controller
public class PublicController {

	@Autowired
	private WelcomeInfoService welcomeInfoService;
	@Autowired
	private UserService userService;
	@Autowired
	private Field2CustomService field2CustomService;
	@Autowired
	private ColorService colorService;

	//前台访问具体客户介绍页
	@GetMapping("/introduce/{businessName}")
	public String toIntroducePage(@PathVariable("businessName")String businessName, Map<String,Object> map) {
		User business = userService.findByUsername(businessName);
		WelcomeInfo welcomeInfo = welcomeInfoService.findByBusinessId(business.getId());
		Colors colors = colorService.findByBusinessId(business.getId());
		map.put("colors",colors);
		map.put("business",business);
		map.put("paragraphs",welcomeInfo.getParagraph().split(","));
		return "/public/welcome_page";
	}

	//前台访问具体客户详情页
	@GetMapping("/details/{businessName}")
	public String toDetailsPage(@PathVariable("businessName")String businessName, Map<String,Object> map)	{
		User business = userService.findByUsername(businessName);
		List<Field2Custom> field2Customs = field2CustomService.getAllField2CutsomByBusinessId(business.getId());
		Colors colors = colorService.findByBusinessId(business.getId());
		map.put("colors",colors);
		map.put("field2Customs",field2Customs);
		map.put("business",business);
		return "/public/details_page";
	}
}
