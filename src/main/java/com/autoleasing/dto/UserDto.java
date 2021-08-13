package com.autoleasing.dto;


import com.autoleasing.entity.Role;

import java.util.Set;
public class UserDto {

    private Integer id;
    private String name;
    private String lastName;
    private String email;
    private String password;
    private Set<Role> setOfRoles;
    private String phoneNumber;

    public UserDto() {
    }

    public UserDto(Integer id, String name, String lastName, String email, String password, Set<Role> setOfRoles,String phoneNumber) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.setOfRoles = setOfRoles;
        this.phoneNumber = phoneNumber;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getSetOfRoles() {
        return setOfRoles;
    }

    public void setSetOfRoles(Set<Role> setOfRoles) {
        this.setOfRoles = setOfRoles;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", setOfRoles=" + setOfRoles +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
