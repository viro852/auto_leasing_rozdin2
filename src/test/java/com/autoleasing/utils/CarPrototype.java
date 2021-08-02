package com.autoleasing.utils;

import com.autoleasing.dto.CarDto;
import com.autoleasing.entity.Car;

import java.util.ArrayList;
import java.util.List;

public class CarPrototype {

    public static Car uCar(){
        Car car =new Car();
        car.setId(1);
        car.setModel("i8");
        car.setColor("blue");
        car.setBrand("bmw");
        car.setAvailable(false);
        car.setRentalPrice(1500);

        return car;
    }

    public static CarDto uCarDto(){
        CarDto carDto =new CarDto();
        carDto.setId(1);
        carDto.setModel("i8");
        carDto.setColor("blue");
        carDto.setBrand("bmw");
        carDto.setAvailable(false);
        carDto.setRentalPrice(1500);

        return carDto;
    }

    public static List<Car> uCarList(){
        ArrayList<Car> carList = new ArrayList<>();
        Car car =new Car();
        car.setId(2);
        car.setModel("x6");
        car.setColor("red");
        car.setBrand("bmw");
        car.setAvailable(true);
        car.setRentalPrice(500);

        carList.add(uCar());
        carList.add(car);

        return carList;
    }
}
