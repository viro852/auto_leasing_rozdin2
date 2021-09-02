package com.autoleasing.validation;

import com.autoleasing.dto.UserDto;
import com.autoleasing.exception.ValidationException;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;

@Component
public class UserValidation {

    public void validateUserDto(UserDto userDto) throws ValidationException {
        if (isNull(userDto)) {
            throw new ValidationException("Object User is null");
        }
        if (isNull(userDto.getName()) || userDto.getName().isEmpty()) {
            throw new ValidationException("Name is empty");
        }
        if (isNull(userDto.getLastName()) || userDto.getLastName().isEmpty()) {
            throw new ValidationException("LastName is empty");
        }
        if (isNull(userDto.getEmail()) || userDto.getEmail().isEmpty()) {
            throw new ValidationException("Email is empty");
        }
        if (isNull(userDto.getSetOfRoles()) || userDto.getSetOfRoles().isEmpty()) {
            throw new ValidationException("Add roles");
        }
        if (isNull(userDto.getPassword()) || userDto.getPassword().isEmpty()) {
            throw new ValidationException("Password is Empty");
        }
    }
}
