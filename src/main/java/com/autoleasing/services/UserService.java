package com.autoleasing.services;

import com.autoleasing.dto.UserDto;
import com.autoleasing.exception.EntityNotFoundException;
import com.autoleasing.exception.ValidationException;

public interface UserService {

     UserDto saveNewUser(UserDto userDto) throws ValidationException;

     UserDto getUserById(Integer id) throws EntityNotFoundException, ValidationException;
}
