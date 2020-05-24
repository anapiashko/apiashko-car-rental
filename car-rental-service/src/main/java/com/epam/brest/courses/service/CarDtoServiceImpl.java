package com.epam.brest.courses.service;

import com.epam.brest.courses.dao.CarDtoRepository;
import com.epam.brest.courses.model.dto.CarDto;
import com.epam.brest.courses.service_api.CarDtoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class CarDtoServiceImpl implements CarDtoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CarDtoServiceImpl.class);

    private final CarDtoRepository carDtoRepository;

    @Autowired
    public CarDtoServiceImpl(CarDtoRepository carDtoRepository) {
        this.carDtoRepository = carDtoRepository;
    }

    @Override
    public List<CarDto> findAllWithNumberOfOrders(LocalDate dateFrom, LocalDate dateTo) {
        LOGGER.debug("find all with number of orders (dateFrom = {}, dateTo = {})",dateFrom,dateTo);

        return carDtoRepository.findAllWithNumberOfOrders(dateFrom,dateTo);
    }
}
