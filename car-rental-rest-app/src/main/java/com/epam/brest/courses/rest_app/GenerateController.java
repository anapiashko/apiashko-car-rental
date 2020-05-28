package com.epam.brest.courses.rest_app;

import com.epam.brest.courses.service_api.FakeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GenerateController {

    private static final Logger LOGGER = LoggerFactory.getLogger(GenerateController.class);

    private final FakeService fakeService;

    public GenerateController(FakeService fakeService) {
        this.fakeService = fakeService;
    }

    @GetMapping("/genCars")
    public String generateCars(@RequestParam(value = "number", defaultValue = "1") Integer number){
        LOGGER.debug("generate {} car(s)", number);

        fakeService.createCarDataSample(number);
        return "generate\t" + number;
    }

    @GetMapping("/genOrders")
    public String generateOrders(@RequestParam(value = "number", defaultValue = "1") Integer number){
        LOGGER.debug("generate {} order(s)", number);

        fakeService.createOrderDataSample(number);
        return "generate\t" + number;
    }
}
