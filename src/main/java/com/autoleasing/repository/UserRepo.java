package com.autoleasing.repository;

import com.autoleasing.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepo extends JpaRepository<User,Integer> {
}
