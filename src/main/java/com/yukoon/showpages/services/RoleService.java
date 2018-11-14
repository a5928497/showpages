package com.yukoon.showpages.services;

import com.yukoon.showpages.entities.Role;
import com.yukoon.showpages.repos.RoleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RoleService {
	@Autowired
	private RoleRepo roleRepo;

	@Transactional
	public Role addRole(Role role) {
		return roleRepo.saveAndFlush(role);
	}
}
