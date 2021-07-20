package com.autoleasing.services;

import com.autoleasing.dto.CarDto;
import com.autoleasing.entity.Car;
import com.autoleasing.exception.ValidationException;
import com.autoleasing.validation.CarValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarConverter {

    private CarValidation carValidation;

    @Autowired
    public CarConverter(CarValidation carValidation) {
        this.carValidation = carValidation;
    }

    public Car fromCarDtoToCar(CarDto carDto) throws ValidationException {
        carValidation.validateCarDto(carDto);
        Car car = new Car();
        car.setBrand(carDto.getBrand());
        car.setModel(carDto.getModel());
        car.setId(carDto.getId());
        car.setColor(carDto.getColor());
        car.setRentalPrice(carDto.getRentalPrice());
        car.setAvailable(carDto.isAvailable());
        return car;
    }

    public CarDto fromCarToCarDto(Car car) {
        CarDto carDto = new CarDto();
        carDto.setBrand(car.getBrand());
        carDto.setModel(car.getModel());
        carDto.setId(car.getId());
        carDto.setColor(car.getColor());
        carDto.setRentalPrice(car.getRentalPrice());
        carDto.setAvailable(car.isAvailable());
        return carDto;
    }
}

