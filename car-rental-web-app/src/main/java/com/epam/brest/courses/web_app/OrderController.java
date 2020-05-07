package com.epam.brest.courses.web_app;

import com.epam.brest.courses.model.Order;
import com.epam.brest.courses.service_api.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

/**
 * Order web controller
 */
@Controller
public class OrderController {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);

    private final OrderService orderService;

    public OrderController(final OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * Create new order.
     *
     * @param carId car id
     * @param date date of the order
     * @return redirect to view name
     */
    @PostMapping(value = "/orders")
    public final String createOrder(@RequestParam(value = "carId", required = false) Integer carId,
                                    @DateTimeFormat(pattern = "yyyy-MM-dd")
                                    @RequestParam(value = "date", required = false) LocalDate date) {
        LOGGER.debug("create order: ({carId = {}, date = {})", carId, date);

        Order order = new Order();
        order.setCarId(carId);
        order.setDate(date);

        orderService.create(order);
        return "redirect:/cars";
    }
}