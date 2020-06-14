package com.epam.brest.courses.rest_app;

import com.epam.brest.courses.model.Order;
import com.epam.brest.courses.service_api.ExcelService;
import com.epam.brest.courses.service_api.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

@RestController
public class OrderRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderRestController.class);

    private final OrderService orderService;

    private final ExcelService excelService;

    @Autowired
    public OrderRestController(OrderService orderService, ExcelService excelService) {
        this.orderService = orderService;
        this.excelService = excelService;
    }

    @GetMapping(value = "orders/download/orders.xlsx")
    public ResponseEntity<InputStreamResource> excelCarsReport() throws IOException {
        LOGGER.debug("export order table to excel sheet ()");

        List<Order> orders = orderService.findAll();

        ByteArrayInputStream in = excelService.ordersToExcel(orders);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=orders.xlsx");

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(new InputStreamResource(in));
    }

    @GetMapping(value = "orders/download/template.xlsx")
    public ResponseEntity<InputStreamResource> excelTemplateReport() throws IOException {
        LOGGER.debug("export template order table to excel sheet ()");

        List<Order> orders = new LinkedList<>();
        orders.add(new Order(1, LocalDate.parse("2020-01-01"), 1));
        orders.add(new Order(2, LocalDate.parse("2020-01-02"), 2));

        ByteArrayInputStream in = excelService.ordersToExcel(orders);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=template-orders.xlsx");

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(new InputStreamResource(in));
    }

    @PostMapping(value = "/orders/import_xlsx")
    public ResponseEntity<InputStreamResource> uploadFromExcel(@RequestParam(value = "file", required = false) MultipartFile file) throws IOException {
        LOGGER.debug("import excel sheet to order table)");

        ByteArrayInputStream cars = excelService.excelToOrders(file);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=result-orders.xlsx");

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(new InputStreamResource(cars));
    }

    /**
     * Create order.
     *
     * @param order order
     * @return id created record
     */
    @PostMapping(value = "/orders")
    public ResponseEntity<Order> create(@RequestBody Order order){
        LOGGER.debug("create order({})",order);

        return new ResponseEntity<>(orderService.create(order), HttpStatus.CREATED);
    }
}
