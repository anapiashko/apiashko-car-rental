package com.epam.brest.courses.model;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "car")
public class Car {
    /**
     * Car id.
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

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getRegisterNumber() {
        return registerNumber;
    }

    public void setRegisterNumber(String registerNumber) {
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
