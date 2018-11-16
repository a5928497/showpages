package com.yukoon.showpages.repos;


import com.yukoon.showpages.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepo extends JpaRepository<User,Integer>{

	@Query("select u from User u where u.role.roleName = 'admin'")
	public List<User> findAllAdmin();

	@Query("select u from User u where u.role.roleName = 'business'")
	public List<User> findAllBussiness();

	public User findByUsername(String username);

	//验证用户名是否存在
	@Query("select u.username from User u where u.username = :username")
	public String vaildateUsername(@Param("username") String username);
}
