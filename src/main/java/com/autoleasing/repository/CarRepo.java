package com.autoleasing.repository;

import com.autoleasing.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CarRepo extends JpaRepository<Car, Integer> {



    
}
