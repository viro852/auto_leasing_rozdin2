package com.autoleasing.services;

import com.autoleasing.dto.UserDto;
import com.autoleasing.exception.EntityNotFoundException;
import com.autoleasing.exception.ValidationException;

public interface UserService {

    public UserDto saveNewUser(UserDto userDto) throws ValidationException;

    public UserDto getUserById(Integer id) throws EntityNotFoundException, ValidationException;
}
