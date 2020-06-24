package com.epam.brest.courses.service_soap;

import com.epam.brest.courses.model.Car;
import com.epam.brest.courses.service_api.CarService;
import com.epam.brest.courses.service_soap.wsdl.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CarClient extends WebServiceGatewaySupport implements CarService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CarClient.class);

    private final String URL = "http://localhost:8088/ws";

    public List<Car> findAll() {
        LOGGER.debug("find all cars ()");

        GetAllCarsRequest request = new GetAllCarsRequest();
        GetAllCarsResponse response = (GetAllCarsResponse) getWebServiceTemplate().marshalSendAndReceive(
                request, new SoapActionCallback(URL + "/getAllCarsRequest"));

        List<Car> carList = new ArrayList<>();
        List<CarInfo> carInfoList = response.getCarInfo();

        for (int i = 0; i < carInfoList.size(); i++) {
            Car car = new Car();
            BeanUtils.copyProperties(carInfoList.get(i), car);
            carList.add(car);
        }
        return carList;
    }

    @Override
    public List<Car> findAllByDate(LocalDate date) {
        LOGGER.debug("find cars by date (date = {})", date);

        GetCarsByDateRequest request = new GetCarsByDateRequest();
        try {
            XMLGregorianCalendar xmlGregorianCalendar =
                    DatatypeFactory.newInstance().newXMLGregorianCalendar(
                           date.toString()
                    );
            request.setFilter(xmlGregorianCalendar);
        } catch (DatatypeConfigurationException e) {
            e.printStackTrace();
        }

        GetCarsByDateResponse response = (GetCarsByDateResponse) getWebServiceTemplate().marshalSendAndReceive(
                request, new SoapActionCallback("/getCarsByDateRequest"));

        List<Car> carList = new ArrayList<>();
        List<CarInfo> carInfoList = response.getCarInfo();

        for (int i = 0; i < carInfoList.size(); i++) {
            Car car = new Car();
            BeanUtils.copyProperties(carInfoList.get(i), car);
            carList.add(car);
        }
        Car car = new Car();
        car.setId(7291);
        car.setBrand("Brand Num 1");
        car.setRegisterNumber("7291 KC-1");
        car.setPrice(BigDecimal.valueOf(792));
        carList.add(car);
        return carList;
    }

    @Override
    public Optional<Car> findById(Integer carId) {
        LOGGER.debug("find car by id (id = {})", carId);

        GetCarByIdRequest request = new GetCarByIdRequest();
        request.setId(carId);
        GetCarByIdResponse response = (GetCarByIdResponse) getWebServiceTemplate()
                .marshalSendAndReceive(
                request, new SoapActionCallback(URL + "/getCarByIdRequest"));
        Car car = new Car();
        BeanUtils.copyProperties(response.getCarInfo(), car);
        return Optional.of(car);
    }

    @Override
    public Car create(Car car) {
        LOGGER.debug("create car ({})", car);

        AddCarRequest request = new AddCarRequest();

        CarInfo carInfo = new CarInfo();
       // BeanUtils.copyProperties(car, carInfo);
        carInfo.setBrand(car.getBrand());
        carInfo.setRegisterNumber(car.getRegisterNumber());
        carInfo.setPrice(car.getPrice());
        request.setCarInfo(carInfo);

        AddCarResponse response = (AddCarResponse) getWebServiceTemplate()
                .marshalSendAndReceive(
                request, new SoapActionCallback(URL + "/addCarRequest"));

        BeanUtils.copyProperties(response.getCarInfo(), car);
        return car;
    }

    @Override
    public int update(Car car) {
        LOGGER.debug("update car ({})", car);

        UpdateCarRequest request = new UpdateCarRequest();

        CarInfo carInfo = new CarInfo();
        BeanUtils.copyProperties(car, carInfo);
        request.setCarInfo(carInfo);
        UpdateCarResponse response = (UpdateCarResponse) getWebServiceTemplate()
                .marshalSendAndReceive(
                request, new SoapActionCallback(URL + "/updateCarRequest"));
        return response.getUpdatedCars();
    }

    @Override
    public void delete(Integer carId) {
        LOGGER.debug("delete car by id (id = {})", carId);

        DeleteCarRequest request = new DeleteCarRequest();
        request.setId(carId);
        getWebServiceTemplate().marshalSendAndReceive(
                request, new SoapActionCallback(URL + "/deleteCarRequest"));
    }
}
