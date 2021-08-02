package com.autoleasing.services;

import com.autoleasing.dto.UserDto;
import com.autoleasing.entity.User;
import com.autoleasing.exception.EntityNotFoundException;
import com.autoleasing.exception.ValidationException;
import com.autoleasing.repository.UserRepo;
import com.autoleasing.validation.UserIdValidation;
import com.autoleasing.validation.UserValidation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static com.autoleasing.utils.UserPrototype.aUser;
import static com.autoleasing.utils.UserPrototype.aUserDto;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


public class UserServiceImplTest {
    private UserService userService;
    private UserRepo userRepo;
    private UsersConverter usersConverter;
    private UserValidation userValidation;
    private UserIdValidation userIdValidation;

    @BeforeEach
    void setUp() {
        userRepo = mock(UserRepo.class);
        usersConverter = new UsersConverter();
        userValidation = new UserValidation();
        userIdValidation = new UserIdValidation();

        userService = new UserServiceImpl(userRepo, usersConverter, userValidation, userIdValidation);

    }

    @Test
    void saveNewUser() throws ValidationException {
        when(userRepo.save(aUser())).thenReturn(aUser());
        UserDto userDto = userService.saveNewUser(aUserDto());
        assertThat(userDto).isNotNull();
        assertThat(userDto.getName()).isEqualTo(aUser().getName());
        verify(userRepo).save(aUser());
    }


    @Test
    void saveNewUserThrowsValidationExceptionWhenNameIsNull() {
        UserDto userDtoWhichCallException = aUserDto();
        userDtoWhichCallException.setName("");
        assertThrows(ValidationException.class, () -> {
            userService.saveNewUser(userDtoWhichCallException);
        }, "Name is null");
    }

    @Test
    void getUserById() throws ValidationException, EntityNotFoundException {
        User expectedUser = aUser();
        when(userRepo.findById(1)).thenReturn(Optional.of(aUser()));
        UserDto actualUser = userService.getUserById(1);
        assertThat(actualUser).isNotNull();
        assertThat(actualUser.getName()).isEqualTo(aUser().getName());
        verify(userRepo).findById(1);
    }

    @Test
    void saveNewUserShouldThrowValidateExceptionWhenUserIsNull() {
        UserDto invalidUserDto = null;
        assertThrows(ValidationException.class, () -> {
            userService.saveNewUser(invalidUserDto);
        }, "Car is null");
    }

    @Test
    void saveNewUserShouldThrowValidateExceptionWhenUserNameIsEmpty() {
        UserDto invalidUserDto = aUserDto();
        invalidUserDto.setName("");
        assertThrows(ValidationException.class, () -> {
            userService.saveNewUser(invalidUserDto);
        }, "Name is null or empty");
    }

    @Test
    void saveNewUserShouldThrowValidateExceptionWhenUserLastNameIsEmpty() {
        UserDto invalidUserDto = aUserDto();
        invalidUserDto.setLastName("");
        assertThrows(ValidationException.class, () -> {
            userService.saveNewUser(invalidUserDto);
        }, "LastName is null or empty");
    }

    @Test
    void saveNewUserShouldThrowValidateExceptionWhenUserEmailIsEmpty() {
        UserDto invalidUserDto = aUserDto();
        invalidUserDto.setEmail("");
        assertThrows(ValidationException.class, () -> {
            userService.saveNewUser(invalidUserDto);
        }, "Email is null or empty");
    }

    @Test
    void saveNewUserShouldThrowValidateExceptionWhenPasswordIsEmpty() {
        UserDto invalidUserDto = aUserDto();
        invalidUserDto.setPassword("");
        assertThrows(ValidationException.class, () -> {
            userService.saveNewUser(invalidUserDto);
        }, "Password is null or empty");
    }
}