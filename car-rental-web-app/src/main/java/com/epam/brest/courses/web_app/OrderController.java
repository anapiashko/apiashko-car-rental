package com.epam.brest.courses.web_app;

import com.epam.brest.courses.model.Order;
import com.epam.brest.courses.service_api.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Order controller
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
     * @param order new order with filled data

     * @return redirect to view name
     */
    @PostMapping(value = "/cars")
    public final String createOrder(@ModelAttribute final Order order) {
        LOGGER.debug("create order: {}", order);

        orderService.create(order);
        return "redirect:/cars";
    }
}
