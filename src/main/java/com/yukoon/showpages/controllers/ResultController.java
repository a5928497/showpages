package com.yukoon.showpages.controllers;

import com.yukoon.showpages.entities.Results;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ResultController extends BasicController {

	@PostMapping("/result")
	public String addResult(Results results) {
		System.out.println(results);
		return null;
	}
}
