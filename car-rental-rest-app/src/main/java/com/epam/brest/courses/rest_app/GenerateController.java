package com.epam.brest.courses.rest_app;

import com.epam.brest.courses.service_api.FakeCarService;
import com.epam.brest.courses.service_api.FakeOrderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GenerateController {

    private final FakeCarService fakeCarService;

    private final FakeOrderService fakeOrderService;

    public GenerateController(FakeCarService fakeCarService, FakeOrderService fakeOrderService) {
        this.fakeCarService = fakeCarService;
        this.fakeOrderService = fakeOrderService;
    }

    @GetMapping("/genCars")
    public String generateCars(@RequestParam(value = "number", defaultValue = "1") Integer number){
        fakeCarService.createSampleData(number);
        return "generate\t" + number;
    }

    @GetMapping("/genOrders")
    public String generateOrders(@RequestParam(value = "number", defaultValue = "1") Integer number){
        fakeOrderService.createSampleData(number);
        return "generate\t" + number;
    }
}
