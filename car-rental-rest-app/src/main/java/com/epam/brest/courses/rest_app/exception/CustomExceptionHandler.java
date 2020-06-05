package com.epam.brest.courses.rest_app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Handler for custom exceptions.
 */
@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Message car nor found.
     */
    public static final String CAR_NOT_FOUND = "car.not_found";

    /**
     * Error message.
     */
    public static final String VALIDATION_ERROR = "validation_error";

    @ExceptionHandler(CarNotFoundException.class)
    public final ResponseEntity<ErrorResponse> handleCarNotFoundException(final CarNotFoundException ex, final WebRequest request) {
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        ErrorResponse error = new ErrorResponse(CAR_NOT_FOUND, details);
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {IllegalArgumentException.class})
    public final ResponseEntity<ErrorResponse> handleIllegalArgumentException(final Exception ex, final WebRequest request) {

        return new ResponseEntity<>(
                new ErrorResponse(VALIDATION_ERROR,  Arrays.asList(ex.getMessage())),
                HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
