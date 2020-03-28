package com.epam.brest.courses.model.dto;

public class CarDto {

    /**
     * Car id.
     */
    private Integer id;

    private String brand;

    private String registerNumber;

    private Integer numberOrders;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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
    public String toString() {
        return "CarDto{" +
                "id=" + id +
                ", brand='" + brand + '\'' +
                ", registerNumber='" + registerNumber + '\'' +
                ", numberOrders=" + numberOrders +
                '}';
    }
}
