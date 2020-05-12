package com.epam.brest.courses.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "order_records")
public class Order {
    /**
     * Order id.
     */
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    /**
     * Car id.
     */
    private Integer carId;

    /**
     * Date of order.
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate date;

    public final Integer getId() {
        return id;
    }

    public final void setId(final Integer id) {
        this.id = id;
    }

    public Integer getCarId() {
        return carId;
    }

    public void setCarId(final Integer carId) {
        this.carId = carId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public final String toString() {
        return "Order{"
                + "id=" + id
                + ", car_id=" + carId
                + ", date='" + date + '\''
                + '}';
    }
}
