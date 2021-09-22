package com.autoleasing.services;

import com.autoleasing.dto.CarDto;
import com.autoleasing.entity.Car;
import org.springframework.stereotype.Component;

@Component
public class CarConverter {

    public CarConverter() {
    }

    public Car fromCarDtoToCar(CarDto carDto) {
        Car car = new Car();
        car.setBrand(carDto.getBrand());
        car.setModel(carDto.getModel());
        car.setId(carDto.getId());
        car.setColor(carDto.getColor());
        car.setRentalPrice(carDto.getRentalPrice());
        car.setAvailable(carDto.getAvailable());
        car.setPhotos(carDto.getPhotos());
        return car;
    }

    public CarDto fromCarToCarDto(Car car) {
        CarDto carDto = new CarDto();
        carDto.setBrand(car.getBrand());
        carDto.setModel(car.getModel());
        carDto.setId(car.getId());
        carDto.setColor(car.getColor());
        carDto.setRentalPrice(car.getRentalPrice());
        carDto.setAvailable(car.getAvailable());
        carDto.setPhotos(car.getPhotos());
        return carDto;
    }
}

