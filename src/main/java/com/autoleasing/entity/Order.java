package com.autoleasing.entity;

import com.autoleasing.enums.OrderStatus;


import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne
    @JoinColumn(name = "car_id")
    private Car car;

    @Column(name = "commentary")
    private String commentary;

    @Column(name = "admin_commentary")
    private String adminCommentary;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private OrderStatus orderStatus;

    @Column(name = "date_of_rent")
    private LocalDate dateOfRent;

    @Column(name = "date_of_rent_finish")
    private LocalDate dateOfRentFinish;

    public Order() {
    }

    public Order(User user, String commentary, OrderStatus orderStatus, LocalDate dateOfRent, LocalDate dateOfRentFinish, String adminCommentary) {
        this.user = user;
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

    public void setUser(User userId) {
        this.user = userId;
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

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public String getAdminCommentary() {
        return adminCommentary;
    }

    public void setAdminCommentary(String adminCommentary) {
        this.adminCommentary = adminCommentary;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id) && Objects.equals(user, order.user) && Objects.equals(car, order.car) && Objects.equals(commentary, order.commentary) && Objects.equals(adminCommentary, order.adminCommentary) && orderStatus == order.orderStatus && Objects.equals(dateOfRent, order.dateOfRent) && Objects.equals(dateOfRentFinish, order.dateOfRentFinish);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, car, commentary, adminCommentary, orderStatus, dateOfRent, dateOfRentFinish);
    }

    @Override
    public String toString() {
        return "Order{" +
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
