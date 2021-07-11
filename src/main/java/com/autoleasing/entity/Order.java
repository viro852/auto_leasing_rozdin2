package com.autoleasing.entity;

import com.autoleasing.enums.StatusEnum;

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

    @Column(name = "commentary")
    private String commentary;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusEnum statusEnum;

    @Column(name = "date_of_rent")
    private LocalDate dateOfRent;

    public Order() {
    }

    public Order(User userId, String commentary, StatusEnum statusEnum, LocalDate dateOfRent) {
        this.user = userId;
        this.commentary = commentary;
        this.statusEnum = statusEnum;
        this.dateOfRent = dateOfRent;
    }

    public int getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUserId() {
        return user;
    }

    public void setUserId(User userId) {
        this.user = userId;
    }

    public String getCommentary() {
        return commentary;
    }

    public void setCommentary(String commentary) {
        this.commentary = commentary;
    }

    public StatusEnum getStatusEnum() {
        return statusEnum;
    }

    public void setStatusEnum(StatusEnum statusEnum) {
        this.statusEnum = statusEnum;
    }

    public LocalDate getDateOfRent() {
        return dateOfRent;
    }

    public void setDateOfRent(LocalDate dateOfRent) {
        this.dateOfRent = dateOfRent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return id == order.id && Objects.equals(user, order.user) && Objects.equals(commentary, order.commentary) && statusEnum == order.statusEnum && Objects.equals(dateOfRent, order.dateOfRent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, commentary, statusEnum, dateOfRent);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", userId=" + user +
                ", commentary='" + commentary + '\'' +
                ", statusEnum=" + statusEnum +
                ", dateOfRent=" + dateOfRent +
                '}';
    }
}
