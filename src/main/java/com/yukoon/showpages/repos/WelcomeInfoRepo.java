package com.yukoon.showpages.repos;

import com.yukoon.showpages.entities.WelcomeInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface WelcomeInfoRepo extends JpaRepository<WelcomeInfo,Integer>{

	@Query("select w from WelcomeInfo w where w.business.id = :id")
	public WelcomeInfo findByBusinessId(@Param("id")Integer id);
}
