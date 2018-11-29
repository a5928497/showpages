package com.yukoon.showpages.controllers;

import com.yukoon.showpages.entities.Results;
import com.yukoon.showpages.entities.User;
import com.yukoon.showpages.services.ResultsService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

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

    @RequiresRoles(value = {"admin","business"},logical = Logical.OR)
    @GetMapping("/allresults/{id}")
    public String toAllResults(@PathVariable("id")Integer id, Map<String,Object> map) {
        User me = whoAmI();
        if (null != me && (me.getId() == id || "admin".equals(me.getRole().getRoleName()))) {
            List<Results> results = resultsService.findAllByBusinessId(id);
            map.put("title","所有回复列表");
            map.put("results",results);
            map.put("id",id);
            return "/backend/results_list";
        }
        return "redirect:/bus_dashboard/" + me.getId();
    }

    @RequiresRoles(value = {"admin","business"},logical = Logical.OR)
    @GetMapping("/newresults/{id}")
    public String toNewResults(@PathVariable("id")Integer id, Map<String,Object> map) {
        User me = whoAmI();
        if (null != me && (me.getId() == id || "admin".equals(me.getRole().getRoleName()))) {
            List<Results> results = resultsService.findNewsByBussinessId(id);
            map.put("title","所有新增回复列表");
            map.put("results",results);
            map.put("id",id);
            resultsService.setOld(results);
            return "/backend/results_list";
        }
        return "redirect:/bus_dashboard/" + me.getId();
    }
}
