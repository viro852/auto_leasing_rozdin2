package com.autoleasing.utils;

import com.autoleasing.dto.UserDto;
import com.autoleasing.entity.Role;
import com.autoleasing.entity.User;
import com.autoleasing.enums.RoleEnum;

import java.util.HashSet;
import java.util.Set;

public class UserPrototype {

    public static User aUser() {
        User user = new User();
        user.setName("test_name");
        user.setEmail("test_email");
        user.setPassword("test_password");
        user.setLastName("test_lastName");
        user.setId(1);
        Set<Role> setOfRole = new HashSet<>();
        Role role = new Role();
        role.setRoleEnum(RoleEnum.ADMIN);
        setOfRole.add(role);
        user.setSetOfRoles((HashSet<Role>) setOfRole);

        return user;
    }

    public static UserDto aUserDto() {
        UserDto userDto = new UserDto();
        userDto.setName("test_name");
        userDto.setEmail("test_email");
        userDto.setPassword("test_password");
        userDto.setLastName("test_lastName");
        userDto.setId(1);
        Set<Role> setOfRole = new HashSet<>();
        Role role = new Role();
        role.setRoleEnum(RoleEnum.ADMIN);
        setOfRole.add(role);
        userDto.setSetOfRoles(setOfRole);
        return userDto;
    }
}
