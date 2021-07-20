package com.autoleasing.entity;

import com.autoleasing.enums.OrderStatus;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//генерируем это значение автоматически
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

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private OrderStatus orderStatus;

    @Column(name = "date_of_rent")
    private LocalDate dateOfRent;

    public Order() {
    }

    public Order(User user, String commentary, OrderStatus orderStatus, LocalDate dateOfRent) {
        this.user = user;
        this.commentary = commentary;
        this.orderStatus = orderStatus;
        this.dateOfRent = dateOfRent;
    }

    public int getId() {
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

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return id == order.id && Objects.equals(user, order.user) && Objects.equals(commentary, order.commentary) && orderStatus == order.orderStatus && Objects.equals(dateOfRent, order.dateOfRent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, commentary, orderStatus, dateOfRent);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", userId=" + user +
                ", commentary='" + commentary + '\'' +
                ", orderStatus=" + orderStatus +
                ", dateOfRent=" + dateOfRent +
                '}';
    }
}
