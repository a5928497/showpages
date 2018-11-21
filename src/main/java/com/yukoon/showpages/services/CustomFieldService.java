package com.yukoon.showpages.services;

import com.yukoon.showpages.entities.CustomField;
import com.yukoon.showpages.repos.CustomFieldRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CustomFieldService {
	@Autowired
	private CustomFieldRepo customFieldRepo;

	@Transactional
	public CustomField saveField(CustomField customField) {
		return customFieldRepo.saveAndFlush(customField);
	}

	public List<CustomField> findAllByBusinessId(Integer businessId) {
		return customFieldRepo.findAllByBusinessId(businessId);
	}

	public CustomField findById(Integer id) {
		return customFieldRepo.findOne(id);
	}

}
