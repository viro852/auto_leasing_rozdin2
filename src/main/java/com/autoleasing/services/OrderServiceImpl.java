package com.autoleasing.services;

import com.autoleasing.dto.OrderDto;
import com.autoleasing.entity.Car;
import com.autoleasing.entity.Order;
import com.autoleasing.entity.User;
import com.autoleasing.enums.OrderStatus;
import com.autoleasing.exception.EntityNotFoundException;
import com.autoleasing.exception.ValidationException;
import com.autoleasing.repository.OrderRepo;
import com.autoleasing.validation.CarIdValidation;
import com.autoleasing.validation.OrderValidation;
import com.autoleasing.validation.UserIdValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class OrderServiceImpl implements OrderService {

    private OrderRepo orderRepo;
    private CarService carService;
    private UserService userService;
    private OrderValidation orderValidation;
    private UsersConverter usersConverter;
    private CarConverter carConverter;
    private CarIdValidation carIdValidation;
    private UserIdValidation userIdValidation;

    @Autowired
    public OrderServiceImpl(OrderRepo orderRepo, CarService carService, UserService userService, OrderValidation orderValidation,UsersConverter usersConverter,CarConverter carConverter,CarIdValidation carIdValidation,UserIdValidation userIdValidation) {
        this.orderRepo = orderRepo;
        this.carService = carService;
        this.userService = userService;
        this.orderValidation = orderValidation;
        this.usersConverter = usersConverter;
        this.carConverter = carConverter;
        this.carIdValidation = carIdValidation;
        this.userIdValidation = userIdValidation;
    }

    public void bookACar(Integer carId, Integer userId, String commentaryFromUser) throws EntityNotFoundException, ValidationException {
       carIdValidation.carIdValidateMethod(carId);
       userIdValidation.userIdValidateMethod(userId);

        User user = usersConverter.fromUserDtoToUser(userService.getUserById(userId));

        Car car = carConverter.fromCarDtoToCar(carService.getCarById(carId));

        Order order = new Order();

        if (car.isAvailable() == false) {
            car.setAvailable(true);
        }
        order.setUser(user);
        order.setCar(car);
        order.setOrderStatus(OrderStatus.NEW);
        order.setDateOfRent(LocalDate.now());
        order.setCommentary(commentaryFromUser);
        orderRepo.save(order);
    }

    @Override
    public void updateOrder(OrderDto orderDto) throws EntityNotFoundException, ValidationException {

        orderValidation.validateOrderDto(orderDto);
        Order orderFromDB = orderRepo.findById(orderDto.getId()).orElseThrow(() -> {
            return new EntityNotFoundException("Order not found");
        });
        orderFromDB.setOrderStatus(orderDto.getOrderStatus());
        orderRepo.save(orderFromDB);
    }
}


