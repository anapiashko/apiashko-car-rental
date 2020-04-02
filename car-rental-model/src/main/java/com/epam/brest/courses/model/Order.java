package com.epam.brest.courses.model;

public class Order {

    /**
     * Order id.
     */
    private Integer id;

    /**
     * Car id.
     */
    private Integer carId;

    /**
     * Date of order.
     */
    private String date;

    public final Integer getId() {
        return id;
    }

    public final void setId(final Integer id) {
        this.id = id;
    }

    public final Integer getCarId() {
        return carId;
    }

    public final void setCarId(final Integer carId) {
        this.carId = carId;
    }

    public final String getDate() {
        return date;
    }

    public final void setDate(final String date) {
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
