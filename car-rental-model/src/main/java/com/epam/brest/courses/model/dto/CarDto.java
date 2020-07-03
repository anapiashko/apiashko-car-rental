package com.epam.brest.courses.model.dto;

import javax.persistence.*;

/**
 * Car Dto.
 */
@Entity
public class CarDto {

    /**
     * CarDto id.
     */
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    /**
     * Car brand.
     */
    private String brand;

    /**
     * Car registration number.
     */
    @Column(name = "register_number")
    private String registerNumber;

    /**
     * Number of orders.
     */
    @Column(name = "number_orders")
    private Integer numberOrders;

    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getRegisterNumber() {
        return registerNumber;
    }

    public void setRegisterNumber(String registerNumber) {
        this.registerNumber = registerNumber;
    }

    public Integer getNumberOrders() {
        return numberOrders;
    }

    public void setNumberOrders(Integer numberOrders) {
        this.numberOrders = numberOrders;
    }

    @Override
    public final String toString() {
        return "CarDto{"
                + "id=" + id
                + ", brand='" + brand + '\''
                + ", registerNumber='" + registerNumber + '\''
                + ", numberOrders=" + numberOrders
                + '}';
    }
}
