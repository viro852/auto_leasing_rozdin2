package com.autoleasing.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "cars")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "car_id")
    private Integer id;

    @Column(name = "brand")
    private String brand;

    @Column(name = "model")
    private String model;

    @Column(name = "color")
    private String color;

    @Column(name = "rental_price")
    private Integer rentalPrice;

    @Column(name = "available")
    private Boolean available;

    @Column(name = "photos")
    private String photos;

    public Car() {
    }

    public Car(String brand, String model, String color, int rentalPrice, boolean available, String photos) {

        this.brand = brand;
        this.model = model;
        this.color = color;
        this.rentalPrice = rentalPrice;
        this.available = available;
        this.photos = photos;
    }

    @Transient
    public String getPhotosImagePath() {
        if (photos == null || id == null) return null;

        return "/user-photos/" + id + "/" + photos;
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

    public int getRentalPrice() {
        return rentalPrice;
    }

    public void setRentalPrice(int rentalPrice) {
        this.rentalPrice = rentalPrice;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public String getPhotos() {
        return photos;
    }

    public void setPhotos(String photos) {
        this.photos = photos;
    }

    public String getPhotoPath(){
        if(photos==null||id==null){
            return null;
        }
        return "/car-photo/"+ id + "/" + photos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return Objects.equals(id, car.id) && Objects.equals(brand, car.brand) && Objects.equals(model, car.model) && Objects.equals(color, car.color) && Objects.equals(rentalPrice, car.rentalPrice) && Objects.equals(available, car.available) && Objects.equals(photos, car.photos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, brand, model, color, rentalPrice, available, photos);
    }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", color='" + color + '\'' +
                ", rentalPrice=" + rentalPrice +
                ", available=" + available +
                ", photos='" + photos + '\'' +
                '}';
    }
}
