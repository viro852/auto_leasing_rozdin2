package com.autoleasing.services;

import com.autoleasing.dto.UserDto;
import com.autoleasing.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UsersConverter {

    public User fromUserDtoToUser(UserDto userDto) {
        User user = new User();
        user.setName(userDto.getName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setId(userDto.getId());
        user.setPassword(userDto.getPassword());
        user.setSetOfRoles(userDto.getSetOfRoles());
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setPassportId(userDto.getPassportId());
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
        userDto.setPhoneNumber(user.getPhoneNumber());
        userDto.setPassport(user.getPassportId());
        return userDto;
    }
}
