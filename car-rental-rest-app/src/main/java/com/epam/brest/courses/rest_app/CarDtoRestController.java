package com.epam.brest.courses.rest_app;

import com.epam.brest.courses.model.dto.CarDto;
import com.epam.brest.courses.service_api.CarDtoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

@RestController
@EnableSwagger2
public class CarDtoRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CarDtoRestController.class);

    private final CarDtoService carDtoService;

    @Autowired
    public CarDtoRestController(CarDtoService carDtoService) {
        this.carDtoService = carDtoService;
    }

    /**
     * Find list of cars with number of orders in period
     *
     * @param dateFrom date from
     * @param dateTo date to
     * @return carDto list
     */
    @GetMapping(value = "/car_dtos")
    public List<CarDto> carStatistics(@RequestParam(value="dateFrom",required = false) String dateFrom
            , @RequestParam(value = "dateTo",required = false) String dateTo){
        LOGGER.debug("find all carDtos between (dateFrom = {}, dateTo = {})", dateFrom, dateTo);

        return carDtoService.findAllWithNumberOfOrders(dateFrom, dateTo);
    }
}
