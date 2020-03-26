package com.epam.brest.courses.web_app.validators;

import com.epam.brest.courses.model.Car;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import static com.epam.brest.courses.model.constants.CarConstants.CAR_BRAND_SIZE;

@Component
public class CarValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return Car.class.equals(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {

        ValidationUtils.rejectIfEmpty(errors, "brand", "brand.empty");
        ValidationUtils.rejectIfEmpty(errors,"registerNumber", "registerNumber.empty");
        ValidationUtils.rejectIfEmpty(errors,"price", "price.empty");
        Car car = (Car) target;

        if (StringUtils.hasLength(car.getBrand())
                && car.getBrand().length() > CAR_BRAND_SIZE) {
            errors.rejectValue("brand", "brand.maxSize");
        }
    }
}
