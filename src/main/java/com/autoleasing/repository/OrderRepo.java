package com.autoleasing.repository;


import com.autoleasing.entity.Order;
import com.autoleasing.entity.User;
import com.autoleasing.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface OrderRepo extends JpaRepository<Order, Integer> {

    List<Order> findAllByUser(User user);

    List<Order> findAllByOrderStatus(OrderStatus orderStatus);
}
