package com.autoleasing.dto;

import com.autoleasing.entity.Car;
import com.autoleasing.entity.User;
import com.autoleasing.enums.OrderStatus;

import java.time.LocalDate;

public class OrderDto {

    private Integer id;
    private User user;
    private Car car;
    private String commentary;
    private OrderStatus orderStatus;
    private LocalDate dateOfRent;
    private LocalDate dateOfRentFinish;

    public OrderDto() {
    }

    public OrderDto(User user, Car car, String commentary, OrderStatus orderStatus, LocalDate dateOfRent,LocalDate dateOfRentFinish) {
        this.user = user;
        this.car = car;
        this.commentary = commentary;
        this.orderStatus = orderStatus;
        this.dateOfRent = dateOfRent;
        this.dateOfRentFinish = dateOfRentFinish;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public String getCommentary() {
        return commentary;
    }

    public void setCommentary(String commentary) {
        this.commentary = commentary;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public LocalDate getDateOfRent() {
        return dateOfRent;
    }

    public void setDateOfRent(LocalDate dateOfRent) {
        this.dateOfRent = dateOfRent;
    }

    public LocalDate getDateOfRentFinish() {
        return dateOfRentFinish;
    }

    public void setDateOfRentFinish(LocalDate dateOfRentFinish) {
        this.dateOfRentFinish = dateOfRentFinish;
    }

    @Override
    public String toString() {
        return "OrderDto{" +
                "id=" + id +
                ", user=" + user +
                ", car=" + car +
                ", commentary='" + commentary + '\'' +
                ", orderStatus=" + orderStatus +
                ", dateOfRent=" + dateOfRent +
                ", dateOfRentFinish=" + dateOfRentFinish +
                '}';
    }
}
