package com.autoleasing.controllers;

import com.autoleasing.components.UserComponent;
import com.autoleasing.dto.UserDto;
import com.autoleasing.entity.Passport;
import com.autoleasing.entity.Role;
import com.autoleasing.entity.User;
import com.autoleasing.enums.RoleEnum;
import com.autoleasing.exception.EntityNotFoundException;
import com.autoleasing.exception.FileIsNotUploadedException;
import com.autoleasing.exception.ValidationException;
import com.autoleasing.services.UserService;
import com.autoleasing.services.UsersConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.naming.Binding;
import javax.validation.Valid;
import java.util.HashSet;

@Controller
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserComponent userComponent;
    private final UserService userService;
    private final UsersConverter usersConverter;


    @Autowired
    public UserController(UserComponent userComponent, UserService userService, UsersConverter usersConverter) {
        this.userComponent = userComponent;
        this.userService = userService;
        this.usersConverter = usersConverter;
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("user", new UserDto());
        return "registration";
    }

    @GetMapping("/personalInfo")
    public String personalInfoPage(Model model) {
        UserDto currentUser = usersConverter.fromUserToUserDto(userComponent.getUser());
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("newUserPassport", new Passport());
        model.addAttribute("currentUserPassport", currentUser.getPassportId());
        model.addAttribute("passportIsNotNull", currentUser.getPassportId() != null);
        return "personalInfo";
    }

    @PostMapping("/addPassport")
    public String addPassport(@ModelAttribute("currentUserPassport") Passport passport) throws ValidationException {
        userService.saveUsersPassport(passport);
        return "redirect:/personalInfo";
    }

    @PostMapping("/registration")
    public String register(@Valid @ModelAttribute("user") UserDto userDto, Errors errors) {
        if (errors.hasErrors()) {
            return "registration";
        }

        logger.info("attempt to registration " + userDto + " from registration form");
        User registeredUser = userService.checkUserByEmailAndPhoneNumber(userDto.getEmail(), userDto.getPhoneNumber());

        if (registeredUser == null) {
            try {
                Role roleForNewUser = new Role();
                roleForNewUser.setRoleEnum(RoleEnum.CLIENT);
                HashSet<Role> setOfRolesForNewUser = new HashSet<>();
                setOfRolesForNewUser.add(roleForNewUser);
                userDto.setSetOfRoles(setOfRolesForNewUser);
                userService.saveNewUser(userDto);

            } catch (ValidationException e) {
                logger.error("We have error: " + e.getMessage() + ".User redirected to error page");
                return "errorPage";
            }
            return "/authentification";
        } else {
            logger.error("attempt to repeat registration, user transferred to errorPage");
            return "/errorWhenRepeatRegistration";
        }
    }

    @GetMapping("/authentification")
    public String authorization(Model model) {
        model.addAttribute("user", new UserDto());
        return "authentification";
    }

    @PostMapping("/personalCabinet")
    public String login(@ModelAttribute("user") UserDto userDto, Model model) throws EntityNotFoundException {
        logger.info("attempt to authentification " + userDto + " from authentification form");
        User checkedUser = userService.checkUserByEmailAndPass(userDto.getEmail(), userDto.getPassword());
        if (checkedUser != null) {
            userComponent.setUser(checkedUser);
            model.addAttribute("userEmail", checkedUser.getEmail());
            Role admin = checkedUser.getSetOfRoles().stream().filter(elem -> elem.getRoleEnum().equals(RoleEnum.ADMIN)).findAny().orElseGet(() -> {
                return new Role();
            });
            model.addAttribute("admin", admin.getRoleEnum() == RoleEnum.ADMIN);
            return "/personalCabinet";
        } else {
            logger.error("user entered incorrect data and transferred to errorPage");
            return "/errorPage";
        }
    }
}