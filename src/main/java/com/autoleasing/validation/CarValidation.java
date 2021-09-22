package com.autoleasing.validation;

import com.autoleasing.dto.CarDto;
import com.autoleasing.exception.FileIsNotUploadedException;
import com.autoleasing.exception.ValidationException;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;

@Component
public class CarValidation {

    public void validateCarDto(CarDto carDto) throws ValidationException {
        if (isNull(carDto)) {
            throw new ValidationException("Object Car is null");
        }

        if (isNull(carDto.getBrand()) || carDto.getBrand().isEmpty()) {
            throw new ValidationException("Brand is empty");
        }
        if (isNull(carDto.getModel()) || carDto.getModel().isEmpty()) {
            throw new ValidationException("Model is empty");
        }
        if (isNull(carDto.getRentalPrice()) || carDto.getRentalPrice() <= 0) { // можно ли так проверить на пустоту числовое поле?
            throw new ValidationException("Price is empty");
        }
        if (isNull(carDto.getColor()) || carDto.getColor().isEmpty()) {
            throw new ValidationException("Color is empty");
        }
    }
}
