package com.epam.brest.courses.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class OrderDto {
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
     * Order date.
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(name = "order_date")
    private LocalDate date;
}
