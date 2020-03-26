--auto

CREATE TABLE car (
                      car_id   INTEGER NOT NULL AUTO_INCREMENT,
                      car_brand VARCHAR(45) NOT NULL ,
                      car_register_number VARCHAR(15) NOT NULL ,
                      car_price DECIMAL(10,2) NOT NULL DEFAULT 0,
                      PRIMARY KEY (car_id)
);



--rent_record

CREATE TABLE order_record (
                     order_record_id    INTEGER NOT NULL AUTO_INCREMENT,
                     order_date TIMESTAMP(6),
                     car_id INTEGER NOT NULL,
                     PRIMARY KEY (order_record_id),
                     CONSTRAINT record_to_car_fk
                         FOREIGN KEY (car_id)
                             REFERENCES car (car_id)
                             ON DELETE NO ACTION
                             ON UPDATE NO ACTION
);

--DEFAULT CURRENT_TIMESTAMP