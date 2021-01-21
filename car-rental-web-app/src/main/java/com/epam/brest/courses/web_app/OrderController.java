package com.epam.brest.courses.web_app;

import com.epam.brest.courses.model.Order;
import com.epam.brest.courses.model.dto.OrderDto;
import com.epam.brest.courses.service_api.OrderDtoService;
import com.epam.brest.courses.service_api.OrderService;
import com.epam.brest.courses.service_api.XmlService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

/**
 * Order web controller
 */
@Controller
public class OrderController {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);

    private final OrderService orderService;

    private final OrderDtoService orderDtoService;

    private final XmlService<Order> xmlService;

    public OrderController(final OrderService orderService, OrderDtoService orderDtoService, XmlService<Order> xmlService) {
        this.orderService = orderService;
        this.orderDtoService = orderDtoService;
        this.xmlService = xmlService;
    }

    /**
     * Find all orders.
     *
     * @param model model
     * @return view name
     */
    @GetMapping(value = "/orders")
    public final String getAll(Model model){
        LOGGER.debug("find all orders ()");

        List<OrderDto> orders = orderDtoService.findAllOrdersWithCar();
        model.addAttribute("orders", orders);
        return "orders";
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

    /**
     * Delete order.
     *
     * @param id order
     * @return redirect to view name
     */
    @GetMapping(value = "orders/{id}/delete")
    public final String deleteOrder(@PathVariable Integer id, Model model) {
        LOGGER.debug("delete order({})", id);
        try {
            orderService.delete(id);
            return "redirect:/orders";
        } catch (HttpServerErrorException e) {
            model.addAttribute("error", "Can not delete this order");
            return getAll(model);
        }
    }

    @PostMapping(value = "/orders/import_xml")
    public String uploadFromXml(@RequestParam(value = "file", required = false) MultipartFile file) {
        LOGGER.debug("import xml archive to order table)");

        xmlService.xmlToEntities(file);

        return "redirect:/orders";
    }
}