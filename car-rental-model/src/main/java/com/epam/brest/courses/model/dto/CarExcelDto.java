package com.epam.brest.courses.model.dto;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class CarExcelDto {

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
    private String registerNumber;

    /**
     * Successful save status.
     */
    private boolean successSave;

    public CarExcelDto() {
    }

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

    public boolean isSuccessSave() {
        return successSave;
    }

    public void setSuccessSave(boolean successSave) {
        this.successSave = successSave;
    }
}
