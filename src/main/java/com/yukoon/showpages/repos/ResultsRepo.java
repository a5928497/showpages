package com.yukoon.showpages.repos;

import com.yukoon.showpages.entities.Results;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ResultsRepo extends JpaRepository<Results,Integer> {

    @Query("select r from Results r where r.business.id= :id")
    public List<Results> findAllByBusinessId(@Param("id")Integer id);

    @Query("select r from Results r where r.business.id = :id and r.isNew = 1")
    public List<Results> findNewByBusinessId(@Param("id")Integer id);
}
