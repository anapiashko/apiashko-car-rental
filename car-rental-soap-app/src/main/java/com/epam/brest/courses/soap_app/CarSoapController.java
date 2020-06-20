package com.epam.brest.courses.soap_app;

import com.epam.brest.courses.model.Car;
import com.epam.brest.courses.service_api.CarService;
import com.epam.brest.courses.soap_app.generated.GetCarsResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.List;

@Profile("soap")
@Endpoint
public class CarSoapController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CarSoapController.class);

    private final CarService carService;

    @Autowired
    public CarSoapController(CarService carService) {
        this.carService = carService;
    }

    @PayloadRoot(namespace = "/cars", localPart = "CarsRequest")
    @ResponsePayload
    public List<Car> getLoanStatus() {
        GetCarsResponse getCarsResponse = new GetCarsResponse();
        //getCarsResponse.setCars(carService.findAll());
        return carService.findAll();
    }
}
