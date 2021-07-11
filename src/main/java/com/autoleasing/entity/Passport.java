package com.autoleasing.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "passports")
public class Passport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "passport_id")
    private Integer id;

    @Column(name = "seria_number")
    private String seriaNumber;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "registration")
    private String registration;


    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Passport() {
    }

    public Passport(String seriaNumber, LocalDate dateOfBirth, String registration, User userId) {
        this.seriaNumber = seriaNumber;
        this.dateOfBirth = dateOfBirth;
        this.registration = registration;
        this.user = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSeriaNumber() {
        return seriaNumber;
    }

    public void setSeriaNumber(String seriaNumber) {
        this.seriaNumber = seriaNumber;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getRegistration() {
        return registration;
    }

    public void setRegistration(String registration) {
        this.registration = registration;
    }

    public User getUserId() {
        return user;
    }

    public void setUserId(User userId) {
        this.user = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Passport passport = (Passport) o;
        return id == passport.id && Objects.equals(seriaNumber, passport.seriaNumber) && Objects.equals(dateOfBirth, passport.dateOfBirth) && Objects.equals(registration, passport.registration) && Objects.equals(user, passport.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, seriaNumber, dateOfBirth, registration, user);
    }

    @Override
    public String toString() {
        return "Passport{" +
                "id=" + id +
                ", userId=" + user +
                '}';
    }
}
