package com.yukoon.showpages.services;

import com.yukoon.showpages.entities.CustomField;
import com.yukoon.showpages.repos.CustomFieldRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Comparator;
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

	public List<CustomField> findAllandSortByBusinessId(Integer id) {
		List<CustomField> customFields = findAllByBusinessId(id);
		Collections.sort(customFields, new Comparator<CustomField>() {
			@Override
			public int compare(CustomField o1, CustomField o2) {
				if (null == o1.getOrder() || null == o2.getOrder()) return -1;
				return o1.getOrder().compareTo(o2.getOrder());
			}
		});
		return customFields;
	}

	public Integer getLastOrderByBusinessId(Integer id) {
		List<CustomField> customFields = findAllandSortByBusinessId(id);
		return customFields.get(customFields.size()-1).getOrder() + 1;
	}

	public CustomField findById(Integer id) {
		return customFieldRepo.findOne(id);
	}

}
