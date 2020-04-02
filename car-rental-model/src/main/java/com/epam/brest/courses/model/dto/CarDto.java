package com.epam.brest.courses.model.dto;

/**
 * Car Dto.
 */
public class CarDto {

    /**
     * CarDto id.
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
     * Number of orders.
     */
    private Integer numberOrders;

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

    public final String getRegisterNumber() {
        return registerNumber;
    }

    public final void setRegisterNumber(final String registerNumber) {
        this.registerNumber = registerNumber;
    }

    public final Integer getNumberOrders() {
        return numberOrders;
    }

    public final void setNumberOrders(final Integer numberOrders) {
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
