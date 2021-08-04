package com.autoleasing.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    @GetMapping("/registration")
    public String registration(){
        return "registration";
    }

    @PostMapping("/personalCabinet")
    public String personalCabinet(@RequestParam("login") String login, Model model){
        model.addAttribute("login",login);
        return "personalCabinet";
    }


    @GetMapping("/authorization")
    public String authorization(){
        return "authorization";
    }

}
