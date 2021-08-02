package com.autoleasing.validation;

import com.autoleasing.exception.ValidationException;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;

@Component
public class CarIdValidation {

    public void carIdValidateMethod(Integer carId) throws ValidationException {
        if (isNull(carId)) {
            throw new ValidationException("Non valid carId");
        }
        if (carId <= 0) {
            throw new ValidationException("Non valid carId");
        }

    }
}
