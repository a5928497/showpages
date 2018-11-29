package com.yukoon.showpages.controllers;

import com.yukoon.showpages.entities.Results;
import com.yukoon.showpages.services.ResultsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ResultController extends BasicController {
	@Autowired
	private ResultsService resultsService;

	@ResponseBody
	@PostMapping("/result")
	public boolean addResult(Results results) {
		results = resultsService.saveResults(results);
		if (null != results.getId()) {
			return true;
		}
		return false;
	}
}
