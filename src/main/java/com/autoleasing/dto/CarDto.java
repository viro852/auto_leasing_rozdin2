package com.autoleasing.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class CarDto {

    private Integer id;
    @NotBlank
    @Size(message = "min 3 character")
    private String brand;
    @NotBlank
    @Size(message = "min 3 character")
    private String model;
    @NotBlank
    private String color;

    private Integer rentalPrice;
    private Boolean available;

    public CarDto() {
    }

    public CarDto(Integer id, String brand, String model, String color, Integer rentalPrice, Boolean available) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.color = color;
        this.rentalPrice = rentalPrice;
        this.available = available;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Integer getRentalPrice() {
        return rentalPrice;
    }

    public void setRentalPrice(Integer rentalPrice) {
        this.rentalPrice = rentalPrice;
    }

    public Boolean isAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    @Override
    public String toString() {
        return "CarDto{" +
                "id=" + id +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", color='" + color + '\'' +
                ", rentalPrice=" + rentalPrice +
                ", available=" + available +
                '}';
    }
}
