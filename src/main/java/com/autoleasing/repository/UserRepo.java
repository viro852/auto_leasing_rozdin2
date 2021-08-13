package com.autoleasing.repository;

import com.autoleasing.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepo extends JpaRepository<User,Integer> {

    Optional<User> findByEmailAndPassword(String email, String password);

    Optional<User> findByEmailOrPhoneNumber(String email,String phoneNumber);
}
