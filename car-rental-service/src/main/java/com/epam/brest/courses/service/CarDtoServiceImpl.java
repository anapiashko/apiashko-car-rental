package com.epam.brest.courses.service;

import com.epam.brest.courses.dao.CarDtoDao;
import com.epam.brest.courses.model.dto.CarDto;
import com.epam.brest.courses.service_api.CarDtoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CarDtoServiceImpl implements CarDtoService {

    private final CarDtoDao carDtoDao;

    @Autowired
    public CarDtoServiceImpl(CarDtoDao carDtoDao) {
        this.carDtoDao = carDtoDao;
    }

    @Override
    public List<CarDto> findAllWithNumberOfOrders(String dateFrom, String dateTo) {
        return carDtoDao.findAllWithNumberOfOrders(dateFrom,dateTo);
    }
}
