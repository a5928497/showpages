package com.yukoon.showpages;

import com.yukoon.showpages.repos.UserRepo;
import com.yukoon.showpages.services.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShowpagesApplicationTests {

	@Autowired
	private UserService userService;
	@Autowired
	private UserRepo userRepo;
	@Test
	public void contextLoads() {
		System.out.println(userRepo.findPasswordByUsername("admin"));
	}

}
