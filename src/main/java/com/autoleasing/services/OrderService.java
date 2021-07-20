package com.autoleasing.services;

import com.autoleasing.dto.OrderDto;
import com.autoleasing.exception.EntityNotFoundException;
import com.autoleasing.exception.ValidationException;

public interface OrderService {

    public void bookACar(Integer carId, Integer userId, String commentFromUser) throws EntityNotFoundException, ValidationException;

    public void updateOrder(OrderDto orderDto) throws EntityNotFoundException, ValidationException;
}
