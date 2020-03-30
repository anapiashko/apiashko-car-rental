package com.epam.brest.courses.web_app;

import com.epam.brest.courses.model.Order;
import com.epam.brest.courses.service_api.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * Create new order.
     *
     * @param order  new order with filled data

     * @return redirect to view name
     */
    @PostMapping(value = "/cars")
    public final String createOrder (@ModelAttribute Order order) {
        orderService.create(order);
        return "redirect:/cars";
    }
}
