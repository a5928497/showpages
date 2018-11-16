package com.yukoon.showpages.services;

import com.yukoon.showpages.entities.Permission;
import com.yukoon.showpages.repos.PermissionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PermissionService {
	@Autowired
	private PermissionRepo permissionRepo;

	@Transactional
	public Permission addPermission(Permission permission) {
		return  permissionRepo.saveAndFlush(permission);
	}

	@Transactional
	public List<String> getPermissions(Integer role_id) {
		return permissionRepo.findPermNameByRoleid(role_id);
	}
}
