package com.autoleasing.services;

import com.autoleasing.dto.UserDto;
import com.autoleasing.entity.Role;
import com.autoleasing.entity.User;
import com.autoleasing.exception.ValidationException;
import com.autoleasing.validation.UserValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;

@Component
public class UsersConverter {

    private UserValidation userValidation;

    @Autowired
    public UsersConverter(UserValidation userValidation) {
        this.userValidation = userValidation;
    }

    public User fromUserDtoToUser(UserDto userDto) throws ValidationException {
        userValidation.validateUserDto(userDto);
        User user = new User();
        user.setName(userDto.getName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setId(userDto.getId());
        user.setPassword(userDto.getPassword());
        user.setSetOfRoles((HashSet<Role>) userDto.getSetOfRoles());
        return user;
    }

    public UserDto fromUserToUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setLastName(user.getLastName());
        userDto.setEmail(user.getEmail());
        userDto.setPassword(user.getPassword());
        userDto.setSetOfRoles(user.getSetOfRoles());
        return userDto;
    }
}
