package com.autoleasing.dto;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class CreateOrderRequest {

    private Integer userId;
    private Integer carId;
    private String comment;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate start;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate finish;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getCarId() {
        return carId;
    }

    public void setCarId(Integer carId) {
        this.carId = carId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDate getStart() {
        return start;
    }

    public void setStart(LocalDate start) {
        this.start = start;
    }

    public LocalDate getFinish() {
        return finish;
    }

    public void setFinish(LocalDate finish) {
        this.finish = finish;
    }

    @Override
    public String toString() {
        return "CreateOrderRequest{" +
                "userId=" + userId +
                ", carId=" + carId +
                ", comment='" + comment + '\'' +
                ", start=" + start +
                ", finish=" + finish +
                '}';
    }
}
