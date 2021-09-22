package com.autoleasing.controllers;

import com.autoleasing.components.UserComponent;
import com.autoleasing.exception.FileIsNotUploadedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class FileIsNotUploadedExceptionAdvice {

    private final Logger logger = LoggerFactory.getLogger(CarController.class);

    @ExceptionHandler(FileIsNotUploadedException.class)
    public String handleNotUploadedException(Model model){
        return "errorWhenFileIsNotUploaded";
    }
}
