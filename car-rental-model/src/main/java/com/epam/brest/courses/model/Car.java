package com.epam.brest.courses.model;

import java.math.BigDecimal;

public class Car {
    /**
     * Car id.
     */
    private Integer id;

    /**
     * Car brand.
     */
    private String brand;

    /**
     * Car registration number.
     */
    private String registerNumber;

    /**
     * Price for car.
     */
    private BigDecimal price;

    public final Integer getId() {
        return id;
    }

    public final void setId(final Integer id) {
        this.id = id;
    }

    public final String getBrand() {
        return brand;
    }

    public final void setBrand(final String brand) {
        this.brand = brand;
    }

    public final BigDecimal getPrice() {
        return price;
    }

    public final void setPrice(final BigDecimal price) {
        this.price = price;
    }

    public final String getRegisterNumber() {
        return registerNumber;
    }

    public final void setRegisterNumber(final String registerNumber) {
        this.registerNumber = registerNumber;
    }


    @Override
    public final String toString() {
        return "Car{"
                + "id=" + id
                + ", brand='" + brand + '\''
                + ", price=" + price
                + ", register_number='" + registerNumber + '\''
                + '}';
    }
}