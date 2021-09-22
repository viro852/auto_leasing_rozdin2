package com.autoleasing.controllers;

import antlr.StringUtils;
import com.autoleasing.components.UserComponent;
import com.autoleasing.dto.CarDto;
import com.autoleasing.entity.Car;
import com.autoleasing.exception.EntityNotFoundException;
import com.autoleasing.exception.FileIsNotUploadedException;
import com.autoleasing.exception.ValidationException;
import com.autoleasing.services.CarService;
import com.autoleasing.utils.FileUploadUtil;
import org.apache.tomcat.util.http.fileupload.FileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
public class CarController {

    private final Logger logger = LoggerFactory.getLogger(CarController.class);
    private final CarService carService;
    private final UserComponent userComponent;
    @Value("${upload.path}")
    private String uploadPath;


    @Autowired
    public CarController(CarService carService, UserComponent userComponent) {
        this.carService = carService;
        this.userComponent = userComponent;
    }

    @GetMapping("/carList")
    public String carList(Model model) {
        List<Car> carList = carService.getAllCarsList();
        model.addAttribute("admin", userComponent.getUser().getEmail().equalsIgnoreCase("juliahome@list.ru"));
        model.addAttribute("carList", carList);
        model.addAttribute("carForSave", new CarDto());
        return "carList";
    }

    @PostMapping("/createCar")
    public String createCar(@ModelAttribute(name = "carForSave") CarDto carDto, @RequestParam(name = "image") MultipartFile multipartFile) throws IOException, FileIsNotUploadedException {
        String fileName = multipartFile.getOriginalFilename();
        carDto.setPhotos(fileName);
        logger.info("attempt to create new car: " + carDto);
        carDto.setAvailable(true);
        CarDto savedCar = new CarDto();
        logger.info("added status to new car: " + carDto.getAvailable());
        try {
            savedCar = carService.saveNewCar(carDto);
        } catch (ValidationException e) {
            logger.error("user entered incorrect data and transferred to errorPage");
            return "orderErrorWhenSaveNewCar";
        }
        String uploadDir = uploadPath + savedCar.getId();

        try {
            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
        } catch (FileIsNotUploadedException e) {
            logger.error("We have error: " + e.getMessage() + ".User redirected to errorWhenFileIsNotUploaded page");
            return "errorWhenFileIsNotUploaded";
        }
        return "redirect:/carList";
    }

    @GetMapping("/updateCarForm")
    public String getupdateCarForm(@RequestParam(name = "carId") Integer carId, Model model) {
        logger.info("attempt to update car with id: " + carId);
        CarDto carForUpdate = null;
        try {
            carForUpdate = carService.getCarById(carId);
        } catch (EntityNotFoundException e) {
            logger.error("We have error: " + e.getMessage() + ".User redirected to errorWhenCarIsUnavailable page");
            return "errorWhenCarIsUnavailable";
        } catch (ValidationException e) {
            logger.error("user entered incorrect data and transferred to errorPage");
            return "errorPage";
        }
        model.addAttribute("car", carForUpdate);
        return "carUpdateForm";
    }

    @PostMapping("/updateCar")
    public String updateCar(@ModelAttribute CarDto car) {
        try {
            carService.updateCar(car);
        } catch (EntityNotFoundException e) {
            logger.error("We have error: " + e.getMessage() + ".User redirected to carList page");
            return "redirect:/carList";
        } catch (ValidationException e) {
            logger.error("user entered incorrect data and transferred to errorPage");
            return "errorPage";
        }
        return "redirect:/carList";
    }
}
