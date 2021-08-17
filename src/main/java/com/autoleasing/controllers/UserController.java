package com.autoleasing.controllers;

import com.autoleasing.components.UserComponent;
import com.autoleasing.dto.UserDto;
import com.autoleasing.entity.Role;
import com.autoleasing.entity.User;
import com.autoleasing.enums.RoleEnum;
import com.autoleasing.exception.ValidationException;
import com.autoleasing.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.HashSet;

@Controller
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserComponent userComponent;

    private final UserService userService;

    @Autowired
    public UserController(UserComponent userComponent, UserService userService) {
        this.userComponent = userComponent;
        this.userService = userService;
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("user", new UserDto());

        return "registration";
    }

    @PostMapping("/registration")
    public String register(@ModelAttribute("user") UserDto userDto, Model model) {
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
                e.getMessage();
            }

            return "/authentification";
        } else {
            logger.error("user entered incorrect data and transferred to errorPage");
            return "/errorPage";
        }
    }

    @GetMapping("/authentification")
    public String authorization(Model model) {
        model.addAttribute("user", new UserDto());
        return "authentification";
    }

    @PostMapping("/authentification")
    public String login(@ModelAttribute("user") UserDto userDto, Model model) {
        logger.info("attempt to authentification " + userDto + " from authentification form");
        User checkedUser = userService.checkUserByEmailAndPass(userDto.getEmail(), userDto.getPassword());
        if (checkedUser != null) {
            userComponent.setUser(checkedUser);
            System.out.println(checkedUser);
            model.addAttribute("userEmail", checkedUser.getEmail());
            return "/personalCabinet";
        } else {
            logger.error("user entered incorrect data and transferred to errorPage");
            return "/errorPage";
        }
    }
}