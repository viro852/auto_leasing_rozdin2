package com.autoleasing.repository;


import com.autoleasing.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RoleRepo extends JpaRepository<Role,Integer> {
}
