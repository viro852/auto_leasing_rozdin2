package com.autoleasing.services;

import com.autoleasing.dto.OrderDto;
import com.autoleasing.exception.EntityNotFoundException;
import com.autoleasing.exception.ValidationException;

public interface OrderService {

     void bookACar(Integer carId, Integer userId, String commentFromUser) throws EntityNotFoundException, ValidationException;

     void updateOrder(OrderDto orderDto) throws EntityNotFoundException, ValidationException;
}
