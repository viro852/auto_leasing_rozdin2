package com.autoleasing.controllers;

import com.autoleasing.components.UserComponent;
import com.autoleasing.dto.CarDto;
import com.autoleasing.entity.Car;
import com.autoleasing.exception.ValidationException;
import com.autoleasing.services.CarService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class CarController {

    private final Logger logger = LoggerFactory.getLogger(CarController.class);
    private final CarService carService;
    private final UserComponent userComponent;

    @Autowired
    public CarController(CarService carService, UserComponent userComponent) {
        this.carService = carService;
        this.userComponent = userComponent;
    }

    @GetMapping("/carList")
    public String carList(Model model) {
        List<Car> carList = carService.getAllCarsList();
        model.addAttribute("admin", userComponent.getUser().getEmail().equalsIgnoreCase("juliahome@list.ru"));
        model.addAttribute("carList", carList);
        model.addAttribute("carForSave", new CarDto());
        return "carList";
    }

    @PostMapping("/createCar")
    public String createCar(@ModelAttribute(name = "carForSave") CarDto carDto) {
        logger.info("attempt to create new car: " + carDto);
        carDto.setAvailable(true);
        logger.info("added status to new car: " + carDto.isAvailable());
        try {
            carService.saveNewCar(carDto);
        } catch (ValidationException e) {
            logger.error("user entered incorrect data and transferred to errorPage");
            return "orderErrorWhenSaveNewCar";
        }
        return "redirect:/carList";
    }
}
