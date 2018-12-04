package com.yukoon.showpages.repos;

import com.yukoon.showpages.entities.Colors;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ColorsRepo extends JpaRepository<Colors,Integer> {

    @Query("select c from Colors c where c.business.id = :id")
    public Colors findByBusinessId(@Param("id")Integer id);
}
