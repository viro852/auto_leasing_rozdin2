package com.autoleasing.validation;

import com.autoleasing.exception.ValidationException;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;

@Component
public class OrderIdValidation {

    public void orderIdValidateMethod(Integer orderId) throws ValidationException {
        if (isNull(orderId) || orderId <= 0) {
            throw new ValidationException("Non valid id");
        }
    }
}
