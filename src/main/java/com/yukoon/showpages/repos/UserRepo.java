package com.yukoon.showpages.repos;


import com.yukoon.showpages.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepo extends JpaRepository<User,Integer>{

	@Query("select u from User u where u.role.roleName = 'admin'")
	public List<User> findAllAdmin();

	@Query("select u from User u where u.role.roleName = 'business' and u.status = 1")
	public List<User> findAllAvailableBussiness();

	public User findByUsername(String username);

	//验证用户名是否存在
	@Query("select u.username from User u where u.username = :username")
	public String vaildateUsername(@Param("username") String username);

	@Query("select u.password from User u where u.username = :username")
	public String findPasswordByUsername(@Param("username") String username);
}
