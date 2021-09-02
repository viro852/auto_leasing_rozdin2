package com.autoleasing.services;

import com.autoleasing.dto.OrderDto;
import com.autoleasing.entity.Order;
import org.springframework.stereotype.Component;

@Component
public class OrderConverter {

    public OrderDto fromOrderToOrderDto(Order order) {
        OrderDto orderDto = new OrderDto();
        orderDto.setId(order.getId());
        orderDto.setOrderStatus(order.getOrderStatus());
        orderDto.setDateOfRentFinish(order.getDateOfRentFinish());
        orderDto.setDateOfRent(order.getDateOfRent());
        orderDto.setCar(order.getCar());
        orderDto.setUser(order.getUser());
        orderDto.setAdminCommentary(order.getAdminCommentary());
        return orderDto;
    }

    public Order fromOrderDtoToOrder(OrderDto orderDto) {
        Order order = new Order();
        order.setId(orderDto.getId());
        order.setOrderStatus(orderDto.getOrderStatus());
        order.setDateOfRentFinish(orderDto.getDateOfRentFinish());
        order.setDateOfRent(orderDto.getDateOfRent());
        order.setCar(orderDto.getCar());
        order.setUser(orderDto.getUser());
        order.setAdminCommentary(orderDto.getAdminCommentary());
        return order;
    }
}
