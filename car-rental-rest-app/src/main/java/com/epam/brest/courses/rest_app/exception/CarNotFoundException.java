package com.epam.brest.courses.rest_app.exception;

public class CarNotFoundException extends RuntimeException {

    public CarNotFoundException(Integer id){
        super("Car id not found : " + id);
    }
}
