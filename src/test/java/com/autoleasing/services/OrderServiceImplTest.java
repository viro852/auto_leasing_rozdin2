package com.autoleasing.services;

import com.autoleasing.entity.Order;
import com.autoleasing.exception.EntityNotFoundException;
import com.autoleasing.exception.ValidationException;
import com.autoleasing.repository.OrderRepo;
import com.autoleasing.validation.CarIdValidation;
import com.autoleasing.validation.OrderValidation;
import com.autoleasing.validation.UserIdValidation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static com.autoleasing.utils.CarPrototype.uCar;
import static com.autoleasing.utils.CarPrototype.uCarDto;
import static com.autoleasing.utils.OrderPrototype.uOrder;
import static com.autoleasing.utils.OrderPrototype.uOrderDTO;
import static com.autoleasing.utils.UserPrototype.aUser;
import static com.autoleasing.utils.UserPrototype.aUserDto;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

class OrderServiceImplTest {
    private OrderService orderService;
    private OrderRepo orderRepo;
    private CarService carService;
    private UserService userService;
    private OrderValidation orderValidation;
    private UsersConverter usersConverter;
    private CarConverter carConverter;
    private CarIdValidation carIdValidation;
    private UserIdValidation userIdValidation;

    @BeforeEach
    void setUp() {
        orderRepo = mock(OrderRepo.class);
        carService = mock(CarService.class);
        userService = mock(UserService.class);
        usersConverter = new UsersConverter();
        carConverter = new CarConverter();
        carIdValidation = new CarIdValidation();
        userIdValidation = new UserIdValidation();
        orderValidation = mock(OrderValidation.class);
        orderService = new OrderServiceImpl(orderRepo, carService, userService, orderValidation, usersConverter, carConverter, carIdValidation, userIdValidation);
    }

    @Test
    void bookACar() throws ValidationException, EntityNotFoundException {
        when(carService.getCarById(uCar().getId())).thenReturn(uCarDto());
        when(userService.getUserById(aUser().getId())).thenReturn(aUserDto());
        orderService.bookACar(uCar().getId(), aUser().getId(), uOrder().getCommentary(),uOrder().getDateOfRent(),uOrder().getDateOfRentFinish());

        Order expectedOrder=uOrder();
        expectedOrder.setId(null);

        verify(carService).getCarById(uCar().getId());
        verify(userService).getUserById(aUser().getId());
        verify(orderRepo).save(expectedOrder);
    }

    @Test
    void updateOrder() throws ValidationException, EntityNotFoundException {
        when(orderRepo.save(any())).thenReturn(uOrder());
        when(orderRepo.findById(any())).thenReturn(Optional.of(uOrder()));
        orderService.updateOrder(uOrderDTO());
        verify(orderRepo).save(uOrder());
    }
}