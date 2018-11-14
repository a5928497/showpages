package com.yukoon.showpages.repos;


import com.yukoon.showpages.entities.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepo extends JpaRepository<Permission,Integer>{
}
