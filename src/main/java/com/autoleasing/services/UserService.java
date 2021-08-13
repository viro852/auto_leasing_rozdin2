package com.autoleasing.services;

import com.autoleasing.dto.UserDto;
import com.autoleasing.entity.User;
import com.autoleasing.exception.EntityNotFoundException;
import com.autoleasing.exception.ValidationException;

public interface UserService {

     UserDto saveNewUser(UserDto userDto) throws ValidationException;

     UserDto getUserById(Integer id) throws EntityNotFoundException, ValidationException;

     User checkUserByEmailAndPass(String email, String password);

     User checkUserByEmailAndPhoneNumber(String email,String phoneNumber);

}
