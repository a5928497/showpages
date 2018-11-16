package com.yukoon.showpages.repos;


import com.yukoon.showpages.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepo extends JpaRepository<User,Integer>{

	@Query("select u from User u where u.role.roleName = 'admin'")
	public List<User> findAllAdmin();

	public User findByUsername(String username);
}
