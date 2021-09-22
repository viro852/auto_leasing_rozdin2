package com.autoleasing.services;

import com.autoleasing.dto.CarDto;
import com.autoleasing.entity.Car;
import com.autoleasing.exception.EntityNotFoundException;
import com.autoleasing.exception.FileIsNotUploadedException;
import com.autoleasing.exception.ValidationException;

import java.util.List;

public interface CarService {

    CarDto saveNewCar(CarDto carDto) throws ValidationException;

    List<Car> getAllCarsList();

    void deleteCar(Integer id);

    CarDto updateCar(CarDto carDto) throws EntityNotFoundException, ValidationException;

    CarDto getCarById(Integer id) throws EntityNotFoundException, ValidationException;
}
