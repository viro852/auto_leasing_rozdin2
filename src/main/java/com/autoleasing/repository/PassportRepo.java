package com.autoleasing.repository;

import com.autoleasing.entity.Passport;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PassportRepo extends JpaRepository<Passport,Integer> {
}
