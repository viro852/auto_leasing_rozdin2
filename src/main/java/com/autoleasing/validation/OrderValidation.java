package com.autoleasing.validation;

import com.autoleasing.dto.OrderDto;
import com.autoleasing.exception.ValidationException;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;

@Component
public class OrderValidation {

    public void validateOrderDto(OrderDto orderDto) throws ValidationException {
        if (isNull(orderDto)) {
            throw new ValidationException("Order is null");
        }
        if (isNull(orderDto.getCar())) {
            throw new ValidationException("Car is empty");
        }
        if (isNull(orderDto.getUser())) {
            throw new ValidationException("User is empty");
        }
        if (isNull(orderDto.getDateOfRent())) {
            throw new ValidationException("Start Date of order is empty");
        }

        if (isNull(orderDto.getDateOfRentFinish())) {
            throw new ValidationException("Finish Date of order is empty");
        }

        if (isNull(orderDto.getOrderStatus())) {
            throw new ValidationException("OrderStatus is empty");
        }
    }
}
