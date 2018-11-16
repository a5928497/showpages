package com.yukoon.showpages.services;

import com.yukoon.showpages.entities.User;
import com.yukoon.showpages.repos.UserRepo;
import com.yukoon.showpages.utils.EncodeUtil;
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
		//判断用户是否已经注册
		if (userRepo.vaildateUsername(user.getUsername()) == null) {
			user = userRepo.saveAndFlush(user);
		}
		return user;
	}

	@Transactional
	public User editUser(User user) {
		user = userRepo.saveAndFlush(user);
		return user;
	}

	public List<User> findAllAdmin() {
		return  userRepo.findAllAdmin();
	}

	public List<User> findAllBusiness() {
		return userRepo.findAllAvailableBussiness();
	}

	public User findByUsername(String username) {
		return userRepo.findByUsername(username);
	}

	public User findById(Integer id) {
		return userRepo.findOne(id);
	}

	@Transactional
	public User login(User user) {
		User user_temp = userRepo.findByUsername(user.getUsername());
		//验证密码
		if (user_temp != null && user_temp.getPassword().equals(user.getPassword())) {
			return user_temp;
		}
		return null;
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
