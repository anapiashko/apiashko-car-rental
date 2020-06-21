package com.epam.brest.courses.soap_app;

import com.epam.brest.courses.model.Car;
import com.epam.brest.courses.service_api.CarService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Endpoint
public class CarEndpoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(CarEndpoint.class);

    private static final String NAMESPACE_URI = "http://epam.com/brest/courses/soap_app";

    private final CarService carService;

    @Autowired
    public CarEndpoint(CarService carService) {
        this.carService = carService;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAllCarsRequest")
    @ResponsePayload
    public GetAllCarsResponse getAll() {
        LOGGER.debug("find all cars ()");

        GetAllCarsResponse response = new GetAllCarsResponse();
        List<CarInfo> carInfoList = new ArrayList<>();
        List<Car> carList = carService.findAll();
        for (int i = 0; i < carList.size(); i++) {
            CarInfo carInfo = new CarInfo();
            BeanUtils.copyProperties(carList.get(i), carInfo);
            carInfoList.add(carInfo);
        }
        response.getCarInfo().addAll(carInfoList);

        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getCarByIdRequest")
    @ResponsePayload
    public GetCarByIdResponse getById(@RequestPayload GetCarByIdRequest request) {
        GetCarByIdResponse response = new GetCarByIdResponse();
        CarInfo carInfo = new CarInfo();
        Optional<Car> car = carService.findById(request.getId());
        BeanUtils.copyProperties(car.get(), carInfo);
        response.setCarInfo(carInfo);
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "addCarRequest")
    @ResponsePayload
    public AddCarResponse create(@RequestPayload AddCarRequest request) {
        AddCarResponse response = new AddCarResponse();
        ServiceStatus serviceStatus = new ServiceStatus();
        CarInfo carInfo = request.getCarInfo();

        Car car = new Car();
        car.setBrand(carInfo.getBrand());
        car.setRegisterNumber(carInfo.getRegisterNumber());
        car.setPrice(carInfo.getPrice());
        Car savedCar = carService.create(car);

        if (savedCar == null) {
            serviceStatus.setStatusCode("CONFLICT");
            serviceStatus.setMessage("Conflict during saving");
            response.setServiceStatus(serviceStatus);
        } else {
            Integer id = savedCar.getId();
            response.setId(id);
            serviceStatus.setStatusCode("SUCCESS");
            serviceStatus.setMessage("Content Added Successfully");
            response.setServiceStatus(serviceStatus);
        }
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "updateCarRequest")
    @ResponsePayload
    public UpdateCarResponse update(@RequestPayload UpdateCarRequest request) {
        Car article = new Car();
        BeanUtils.copyProperties(request.getCarInfo(), article);
        int updatedCars = carService.update(article);

        ServiceStatus serviceStatus = new ServiceStatus();
        serviceStatus.setStatusCode("SUCCESS");
        serviceStatus.setMessage("Content Updated Successfully");
        UpdateCarResponse response = new UpdateCarResponse();

        response.setServiceStatus(serviceStatus);
        response.setUpdatedCars(updatedCars);
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteCarRequest")
    @ResponsePayload
    public DeleteCarResponse delete(@RequestPayload DeleteCarRequest request) {
        Optional<Car> article = carService.findById(request.getId());
        ServiceStatus serviceStatus = new ServiceStatus();
        if (article == null ) {
            serviceStatus.setStatusCode("FAIL");
            serviceStatus.setMessage("Content Not Available");
        } else {
            carService.delete(article.get().getId());
            serviceStatus.setStatusCode("SUCCESS");
            serviceStatus.setMessage("Content Deleted Successfully");
        }
        DeleteCarResponse response = new DeleteCarResponse();
        response.setServiceStatus(serviceStatus);
        return response;
    }
}
