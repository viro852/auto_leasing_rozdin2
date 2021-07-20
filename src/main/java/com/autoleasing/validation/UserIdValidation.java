package com.autoleasing.validation;

import com.autoleasing.exception.ValidationException;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;

@Component
public class UserIdValidation {

    public void userIdValidateMethod(Integer userId) throws ValidationException {
        if (isNull(userId) || userId <= 0) {
            throw new ValidationException("Non valid id");
        }
    }
}
