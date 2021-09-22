package com.autoleasing.services;

import com.autoleasing.dto.CarDto;
import com.autoleasing.entity.Car;
import com.autoleasing.exception.EntityNotFoundException;
import com.autoleasing.exception.FileIsNotUploadedException;
import com.autoleasing.exception.ValidationException;
import com.autoleasing.repository.CarRepo;
import com.autoleasing.validation.CarIdValidation;
import com.autoleasing.validation.CarValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarServiceImpl implements CarService {

    private CarRepo carRepo;
    private CarConverter carConverter;
    private CarValidation carValidation;
    private CarIdValidation carIdValidation;


    @Autowired
    public CarServiceImpl(CarRepo carRepo, CarConverter carConverter, CarValidation carValidation, CarIdValidation carIdValidation) {
        this.carRepo = carRepo;
        this.carConverter = carConverter;
        this.carValidation = carValidation;
        this.carIdValidation = carIdValidation;

    }

    @Override
    public CarDto saveNewCar(CarDto carDto) throws ValidationException{
        carDto.setAvailable(true);
        carValidation.validateCarDto(carDto);
        Car carForSave = carRepo.save(carConverter.fromCarDtoToCar(carDto));
        return carConverter.fromCarToCarDto(carForSave);
    }

    @Override
    public List<Car> getAllCarsList() {
        return carRepo.findAll();
    }

    @Override
    public void deleteCar(Integer carId) {
        carRepo.deleteById(carId);
    }

    @Override
    public CarDto updateCar(CarDto carDto) throws EntityNotFoundException, ValidationException {

        carValidation.validateCarDto(carDto);
        Car carFromDB = carRepo.findById(carDto.getId()).orElseThrow(() -> new EntityNotFoundException("Car not found"));

        carFromDB.setBrand(carDto.getBrand());
        carFromDB.setModel(carDto.getModel());
        carFromDB.setColor(carDto.getColor());
        carFromDB.setRentalPrice(carDto.getRentalPrice());
        carFromDB.setAvailable(carDto.getAvailable());

        return carConverter.fromCarToCarDto(carRepo.save(carFromDB));
    }

    @Override
    public CarDto getCarById(Integer carId) throws EntityNotFoundException, ValidationException {

        carIdValidation.carIdValidateMethod(carId);

        CarDto carDto;
        Car car = carRepo.findById(carId).orElseThrow(() -> new EntityNotFoundException("Car not found"));
        carDto = carConverter.fromCarToCarDto(car);
        return carDto;
    }
}
