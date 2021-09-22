package com.autoleasing.services;

import com.autoleasing.components.UserComponent;
import com.autoleasing.dto.UserDto;
import com.autoleasing.entity.Passport;
import com.autoleasing.entity.User;
import com.autoleasing.exception.EntityNotFoundException;
import com.autoleasing.exception.ValidationException;
import com.autoleasing.repository.UserRepo;
import com.autoleasing.validation.UserIdValidation;
import com.autoleasing.validation.UserValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.FileNotFoundException;

@Service
public class UserServiceImpl implements UserService {

    private UserRepo userRepo;
    private UsersConverter usersConverter;
    private UserValidation userValidation;
    private UserIdValidation userIdValidation;
    private UserComponent userComponent;

    @Autowired
    public UserServiceImpl(UserRepo userRepo, UsersConverter usersConverter, UserValidation userValidation, UserIdValidation userIdValidation, UserComponent userComponent) {
        this.userRepo = userRepo;
        this.usersConverter = usersConverter;
        this.userValidation = userValidation;
        this.userIdValidation = userIdValidation;
        this.userComponent = userComponent;
    }

    @Override
    public UserDto saveNewUser(UserDto userDto) throws ValidationException {
        userValidation.validateUserDto(userDto);
        User userForSave = userRepo.save(usersConverter.fromUserDtoToUser(userDto));
        return usersConverter.fromUserToUserDto(userForSave);
    }

    @Override
    public UserDto getUserById(Integer userId) throws EntityNotFoundException, ValidationException {
        userIdValidation.userIdValidateMethod(userId);

        UserDto userDto = null;
        User user = userRepo.findById(userId).orElseThrow(() -> {
            return new EntityNotFoundException("User not found");
        });
        userDto = usersConverter.fromUserToUserDto(user);
        return userDto;
    }

    @Override
    public User checkUserByEmailAndPass(String email, String password) {
        return userRepo.findByEmailAndPassword(email, password).orElse(null);
    }

    @Override
    public User checkUserByEmailAndPhoneNumber(String email, String phoneNumber) {
        return userRepo.findByEmailOrPhoneNumber(email, phoneNumber).orElse(null);
    }

    @Override
    public void saveUsersPassport(Passport passport) {
        User currentUser = userComponent.getUser();
        currentUser.setPassportId(passport);
        userRepo.save(currentUser);
    }
}
