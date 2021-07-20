package com.autoleasing.services;

import com.autoleasing.dto.CarDto;
import com.autoleasing.entity.Car;
import com.autoleasing.exception.EntityNotFoundException;
import com.autoleasing.exception.ValidationException;

import java.util.List;

public interface CarService {

    public CarDto saveNewCar(CarDto carDto) throws ValidationException;

    public List<Car> getAllCarsList();

    public void deleteCar(Integer id);

    public CarDto updateCar(CarDto carDto) throws EntityNotFoundException, ValidationException;

    public CarDto getCarById(Integer id) throws EntityNotFoundException, ValidationException;
}
