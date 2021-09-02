package com.autoleasing.dto;

import com.autoleasing.entity.Car;
import com.autoleasing.entity.User;
import com.autoleasing.enums.OrderStatus;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

public class OrderDto {

    private Integer id;
    @NotBlank
    private User user;
    @NotBlank
    private Car car;
    private String commentary;
    private String adminCommentary;
    private OrderStatus orderStatus;
    @NotBlank
    private LocalDate dateOfRent;
    @NotBlank
    private LocalDate dateOfRentFinish;

    public OrderDto() {
    }

    public OrderDto(User user, Car car, String commentary, OrderStatus orderStatus, LocalDate dateOfRent, LocalDate dateOfRentFinish, String adminCommentary) {
        this.user = user;
        this.car = car;
        this.commentary = commentary;
        this.orderStatus = orderStatus;
        this.dateOfRent = dateOfRent;
        this.dateOfRentFinish = dateOfRentFinish;
        this.adminCommentary = adminCommentary;
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

    public String getAdminCommentary() {
        return adminCommentary;
    }

    public void setAdminCommentary(String adminCommentary) {
        this.adminCommentary = adminCommentary;
    }

    @Override
    public String toString() {
        return "OrderDto{" +
                "id=" + id +
                ", user=" + user +
                ", car=" + car +
                ", commentary='" + commentary + '\'' +
                ", adminCommentary='" + adminCommentary + '\'' +
                ", orderStatus=" + orderStatus +
                ", dateOfRent=" + dateOfRent +
                ", dateOfRentFinish=" + dateOfRentFinish +
                '}';
    }
}
