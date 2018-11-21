package com.yukoon.showpages.repos;

import com.yukoon.showpages.entities.CustomField;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CustomFieldRepo extends JpaRepository<CustomField,Integer> {

	@Query("select c from CustomField c where c.business.id = :id and c.status = 1")
	public List<CustomField> findAllByBusinessId(@Param("id")Integer id);
}
