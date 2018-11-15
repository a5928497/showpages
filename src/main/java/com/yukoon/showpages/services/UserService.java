package com.yukoon.showpages.services;

import com.yukoon.showpages.entities.User;
import com.yukoon.showpages.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {
	@Autowired
	private UserRepo userRepo;

	@Transactional
	public User addUser(User user) {
		return userRepo.saveAndFlush(user);
	}

	public List<User> findAllAdmin() {
		return  userRepo.findAllAdmin();
	}

	public boolean isAdmin(User user) {
		return user.getRole().getRoleName().equals("admin");
	}
	public boolean isBussiness(User user) {
		return user.getRole().getRoleName().equals("business");
	}
	public boolean isCustom(User user) {
		return user.getRole().getRoleName().equals("custom");
	}
}
