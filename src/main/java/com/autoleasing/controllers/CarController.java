package com.autoleasing.controllers;

import com.autoleasing.entity.Car;
import com.autoleasing.services.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class CarController {

    private final CarService carService;

    @Autowired
    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping("/carList")
    public String carList(Model model) {
        List<Car> carList = carService.getAllCarsList();
        model.addAttribute("carList", carList);
        return "carList";
    }
}
