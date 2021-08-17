package com.autoleasing.controllers;

import com.autoleasing.components.UserComponent;
import com.autoleasing.dto.CarDto;
import com.autoleasing.dto.CreateOrderRequest;
import com.autoleasing.entity.Car;
import com.autoleasing.exception.EntityNotFoundException;
import com.autoleasing.exception.ValidationException;
import com.autoleasing.services.CarConverter;
import com.autoleasing.services.CarService;
import com.autoleasing.services.OrderService;
import com.autoleasing.services.SendEmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class OrderController {


    private final SendEmailService sendEmailService;
    private final Logger logger = LoggerFactory.getLogger(OrderController.class);
    private final UserComponent userComponent;
    private final CarService carService;
    private final CarConverter carConverter;
    private final OrderService orderService;

    @Autowired
    public OrderController(UserComponent userComponent, CarService carService, CarConverter carConverter, OrderService orderService,SendEmailService sendEmailService) {
        this.userComponent = userComponent;
        this.carService = carService;
        this.carConverter = carConverter;
        this.orderService = orderService;
        this.sendEmailService = sendEmailService;
    }

    @GetMapping("/newOrderForm")
    public String newOrderForm(@RequestParam("carId") String carId, Model model) {

        logger.info("attempt to book car with id:" + carId);

        Integer carIdFromView = Integer.parseInt(carId);

        Car car = null;
        try {
            car = carConverter.fromCarDtoToCar(carService.getCarById(carIdFromView));
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
        } catch (ValidationException e) {
            e.printStackTrace();
        }

        CreateOrderRequest createOrderRequest = new CreateOrderRequest();

        model.addAttribute("car", car.getId());
        model.addAttribute("carModel", car.getModel());
        model.addAttribute("order", createOrderRequest);
        model.addAttribute("user", userComponent.getUser().getId());
        return "newOrderForm";
    }

    @PostMapping("/createOrder")
    public String createOrder(@ModelAttribute("order") CreateOrderRequest orderRequest, Model model) {
        logger.info("attemt to make an order from user: " + orderRequest.getUserId());

        try {
            orderService.bookACar(orderRequest.getCarId(), orderRequest.getUserId(), orderRequest.getComment(), orderRequest.getStart(), orderRequest.getFinish());
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
        } catch (ValidationException e) {
            e.printStackTrace();
        }

        CarDto carDto = null;
        try {
            carDto = carService.getCarById(orderRequest.getCarId());
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
        } catch (ValidationException e) {
            e.printStackTrace();
        }
        sendEmailService.sendEmail("rozdin92@gmail.com","the customer made an order","New Order");
        model.addAttribute("bookedCarBrand", carDto.getBrand());
        model.addAttribute("bookedCarModel", carDto.getModel());

        model.addAttribute("dateOfStart",orderRequest.getStart());
        model.addAttribute("dateOfFinish",orderRequest.getFinish());

        return "successOrder";
    }
}
