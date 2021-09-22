package com.autoleasing.entity;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
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

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "registration")
    private String registration;

    public Passport() {
    }

    public Passport(String seriaNumber, LocalDate dateOfBirth, String registration) {
        this.seriaNumber = seriaNumber;
        this.dateOfBirth = dateOfBirth;
        this.registration = registration;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Passport passport = (Passport) o;
        return id == passport.id && Objects.equals(seriaNumber, passport.seriaNumber) && Objects.equals(dateOfBirth, passport.dateOfBirth) && Objects.equals(registration, passport.registration);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, seriaNumber, dateOfBirth, registration);
    }

    @Override
    public String toString() {
        return "Passport{" +
                "id=" + id +
                ", seriaNumber='" + seriaNumber + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", registration='" + registration + '\'' +
                '}';
    }
}
