package com.autoleasing.services;

import com.autoleasing.dto.CarDto;
import com.autoleasing.entity.Car;
import com.autoleasing.exception.EntityNotFoundException;
import com.autoleasing.exception.ValidationException;
import com.autoleasing.repository.CarRepo;
import com.autoleasing.validation.CarIdValidation;
import com.autoleasing.validation.CarValidation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static com.autoleasing.utils.CarPrototype.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

class CarServiceImplTest {

    private CarService carService;
    private CarRepo carRepo;
    private CarConverter carConverter;
    private CarValidation carValidation;
    private CarIdValidation carIdValidation;

    @BeforeEach
    void setUp() {
        carRepo = mock(CarRepo.class);
        carConverter = new CarConverter();
        carValidation = new CarValidation();
        carIdValidation = new CarIdValidation();
        carService = new CarServiceImpl(carRepo, carConverter, carValidation, carIdValidation);
    }

    @Test
    void saveNewCar() throws ValidationException {
        when(carRepo.save(uCar()))
                .thenReturn(uCar());
        CarDto carDto = carService.saveNewCar(uCarDto());
        assertThat(carDto).isNotNull();
        assertThat(carDto.getId()).isEqualTo(uCar().getId());
        verify(carRepo).save(uCar());
    }

    @Test
    void getAllCarsList() throws ValidationException {
        when(carRepo.findAll()).thenReturn(uCarList());
        List<Car> carlistFromDB = carService.getAllCarsList();
        assertThat(carlistFromDB).isNotNull();
        assertThat(carlistFromDB.get(1).getModel()).isEqualTo(uCarList().get(1).getModel());
        verify(carRepo).findAll();
    }

    @Test
    void deleteCar() {
        Car car = uCar();
        carService.deleteCar(car.getId());
        verify(carRepo).deleteById(car.getId());
    }

    @Test
    void getCarById() throws ValidationException, EntityNotFoundException {
        when(carRepo.findById(any())).thenReturn(Optional.of(uCar()));
        CarDto carDto = carService.getCarById(uCarDto().getId());
        assertThat(carDto).isNotNull();
        assertThat(carDto.getModel()).isEqualTo(uCar().getModel());

    }

    @Test
    void getCarByIdShouldThrowValeidationExceptionWhenIdIsZeroOrUnderZero() {
        CarDto carDto = uCarDto();
        carDto.setId(-1);
        assertThrows(ValidationException.class, () -> {
            carService.getCarById(carDto.getId());
        }, "id Is zero or under zero");
    }

    @Test
    void getCarByIdShouldThrowValeidationExceptionWhenIdIsNull() {
        CarDto carDto = uCarDto();
        carDto.setId(null);
        assertThrows(ValidationException.class, () -> {
            carService.getCarById(carDto.getId());
        }, "id is null");
    }

    @Test
    void updateCar() throws ValidationException, EntityNotFoundException {
        when(carRepo.save(any())).thenReturn(uCar());
        when(carRepo.findById(any())).thenReturn(Optional.of(uCar()));
        carService.updateCar(uCarDto());
        verify(carRepo).findById(uCarDto().getId());
        verify(carRepo).save(uCar());

    }

    @Test
    void saveNewCarShouldThrowValidateExceptionWhenCarPriceIsNullOrUnderZero() {
        CarDto invalidCarDto = uCarDto();
        invalidCarDto.setRentalPrice(0);
        assertThrows(ValidationException.class, () -> {
            carService.saveNewCar(invalidCarDto);
        }, "Car price is Null or under zero");
    }

    @Test
    void saveNewCarShouldThrowValidateExceptionWhenColorIsNullorEmpty() {
        CarDto invalidCarDto = uCarDto();
        invalidCarDto.setColor("");
        assertThrows(ValidationException.class, () -> {
            carService.saveNewCar(invalidCarDto);
        }, "Color is empty");
    }

    @Test
    void saveNewCarShouldThrowValidateExceptionWhenBrandIsNullorEmpty() {
        CarDto invalidCarDto = uCarDto();
        invalidCarDto.setBrand("");
        assertThrows(ValidationException.class, () -> {
            carService.saveNewCar(invalidCarDto);
        }, "Brand is empty");
    }

    @Test
    void saveNewCarShouldThrowValidateExceptionWhenModelIsNullorEmpty() {
        CarDto invalidCarDto = uCarDto();
        invalidCarDto.setColor("");
        assertThrows(ValidationException.class, () -> {
            carService.saveNewCar(invalidCarDto);
        }, "Model is empty");
    }

    @Test
    void saveNewCarShouldThrowValidateExceptionWhenCarIsNull() {
        CarDto invalidCarDto = null;
        assertThrows(ValidationException.class, () -> {
            carService.saveNewCar(invalidCarDto);
        }, "Car is null");
    }
}