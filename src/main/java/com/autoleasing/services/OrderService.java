package com.autoleasing.services;

import com.autoleasing.dto.OrderDto;
import com.autoleasing.exception.EntityNotFoundException;
import com.autoleasing.exception.ValidationException;

import java.time.LocalDate;

public interface OrderService {

     void bookACar(Integer carId, Integer userId, String commentFromUser, LocalDate dateOfRent, LocalDate dateOfRentFinish) throws EntityNotFoundException, ValidationException;

     void updateOrder(OrderDto orderDto) throws EntityNotFoundException, ValidationException;
}
