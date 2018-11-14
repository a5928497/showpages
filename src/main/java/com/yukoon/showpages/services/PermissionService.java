package com.yukoon.showpages.services;

import com.yukoon.showpages.entities.Permission;
import com.yukoon.showpages.repos.PermissionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PermissionService {
	@Autowired
	private PermissionRepo permissionRepo;

	@Transactional
	public Permission addPermission(Permission permission) {
		return  permissionRepo.saveAndFlush(permission);
	}
}
