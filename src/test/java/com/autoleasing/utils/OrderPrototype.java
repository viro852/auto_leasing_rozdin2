package com.autoleasing.utils;

import com.autoleasing.dto.OrderDto;
import com.autoleasing.entity.Order;
import com.autoleasing.enums.OrderStatus;

import java.time.LocalDate;

import static com.autoleasing.utils.CarPrototype.uCar;
import static com.autoleasing.utils.UserPrototype.aUser;

public class OrderPrototype {

    public static Order uOrder (){
        Order order = new Order();
        order.setUser(aUser());
        order.setCar(uCar());
        order.setId(1);
        order.setCommentary("commentary for order");
        order.setDateOfRent(LocalDate.now());
        order.setOrderStatus(OrderStatus.NEW);

        return order;
    }


    public static OrderDto uOrderDTO (){
        OrderDto orderDto = new OrderDto();
        orderDto.setUser(aUser());
        orderDto.setCar(uCar());
        orderDto.setId(1);
        orderDto.setCommentary("commentary for order");
        orderDto.setDateOfRent(LocalDate.now());
        orderDto.setOrderStatus(OrderStatus.NEW);

        return orderDto;
    }
}
