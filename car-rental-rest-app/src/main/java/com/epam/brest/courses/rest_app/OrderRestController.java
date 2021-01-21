package com.epam.brest.courses.rest_app;

import com.epam.brest.courses.model.Order;
import com.epam.brest.courses.service_api.OrderService;
import com.epam.brest.courses.service_api.XmlService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import org.springframework.web.bind.annotation.*;

@RestController
public class OrderRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderRestController.class);

    private final OrderService orderService;

    private final XmlService<Order> xmlService;

    @Autowired
    public OrderRestController(OrderService orderService, XmlService<Order> xmlService) {
        this.orderService = orderService;
        this.xmlService = xmlService;
    }

    @GetMapping(value = "/orders/download/orders.xml", produces = "application/zip")
    public ResponseEntity<InputStreamResource> xmlOrdersReport() throws IOException {
        LOGGER.debug("export order table xml archive ()");

        List<Order> orders = orderService.findAll();

        ByteArrayInputStream in = xmlService.entitiesToXml(orders);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=orders.zip");
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(new InputStreamResource(in));

    }

    @PostMapping(value = "/orders/import_xml")
    public ResponseEntity<Void> uploadFromXml(@RequestParam(value = "file", required = false) MultipartFile file) throws IOException {
        LOGGER.debug("import xml archive to order table)");

        xmlService.xmlToEntities(file);

        return new ResponseEntity<>(HttpStatus.OK);
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

    /**
     * Delete order.
     *
     * @param id order id
     */
    @DeleteMapping(value = "/orders/{id}", produces = {"application/json"})
    public final ResponseEntity<Void> delete(@PathVariable(name = "id") Integer id) {
        LOGGER.debug("delete order({})", id);

        orderService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
