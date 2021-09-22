package com.autoleasing.rest;

import com.autoleasing.components.UserComponent;
import com.autoleasing.controllers.CarController;
import com.autoleasing.dto.CarDto;
import com.autoleasing.entity.Car;
import com.autoleasing.exception.EntityNotFoundException;
import com.autoleasing.exception.IncorrectCarData;
import com.autoleasing.exception.ValidationException;
import com.autoleasing.services.CarService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CarsRestController {

    private final Logger logger = LoggerFactory.getLogger(CarController.class);
    private final CarService carService;
    private final UserComponent userComponent;

    @Autowired
    public CarsRestController(CarService carService, UserComponent userComponent) {
        this.carService = carService;
        this.userComponent = userComponent;
    }

    @GetMapping("/cars")
    public List<Car> showAllCars() {
        logger.info("attempt to get all cars list");
        List<Car> carList = carService.getAllCarsList();
        return carList;
    }

    @GetMapping("/cars/{id}")
    public CarDto showCarById(@PathVariable Integer id) throws EntityNotFoundException {
        logger.info("attempt to search car with id: " + id);
        CarDto carById = null;
        try {
            carById = carService.getCarById(id);
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException("There is no car with id: "+id+" in DB");

        } catch (ValidationException e) {
            logger.error("user entered incorrect data and transferred to errorPage");
        }
        return carById;
    }

    @PostMapping(value = "/cars")
    public CarDto addNewCar(@RequestBody CarDto carDto) throws IOException {
        logger.info("attempt to save new Car: " + carDto);

        carDto.setAvailable(true);
        CarDto savedCar = new CarDto();
        logger.info("added status to new car: " + carDto.getAvailable());
        try {
            savedCar = carService.saveNewCar(carDto);
        } catch (ValidationException e) {
            logger.error("user entered incorrect data and transferred to errorPage");
        }
        return savedCar;
    }

    @PutMapping("/cars")
    public CarDto updateCar(@RequestBody CarDto carDto) {
        logger.info("attempt to update car: " + carDto);
        try {
            carService.updateCar(carDto);
        } catch (EntityNotFoundException e) {
            logger.error("There is no car with id: "+carDto.getId()+" in DB");
        } catch (ValidationException e) {
            logger.error("user entered incorrect data and transferred to errorPage");
        }
        return carDto;
    }

    @ExceptionHandler
    public ResponseEntity<IncorrectCarData>handleException(EntityNotFoundException exception){
        IncorrectCarData incorrectCarData = new IncorrectCarData();
        incorrectCarData.setInfo(exception.getMessage());

        return new ResponseEntity<>(incorrectCarData, HttpStatus.NOT_FOUND);
    }
}
