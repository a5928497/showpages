package com.yukoon.showpages.repos;

import com.yukoon.showpages.entities.Extension;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ExtensionRepo extends JpaRepository<Extension,Integer> {

    @Query("select e from Extension e where e.business.id = :id")
    public List<Extension> findAllByBusinessId(@Param("id")Integer id);
}
