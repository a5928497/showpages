package com.yukoon.showpages.services;

import com.yukoon.showpages.entities.WelcomeInfo;
import com.yukoon.showpages.repos.WelcomeInfoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WelcomeInfoService {
	@Autowired
	private WelcomeInfoRepo welcomeInfoRepo;

	public WelcomeInfo findByBusinessId(Integer businessId) {
		return welcomeInfoRepo.findByBusinessId(businessId);
	}

	@Transactional
	public WelcomeInfo saveWelcomeInfo(WelcomeInfo welcomeInfo) {
		return welcomeInfoRepo.saveAndFlush(welcomeInfo);
	}

	public WelcomeInfo findById(Integer id) {
		return welcomeInfoRepo.findOne(id);
	}
}
