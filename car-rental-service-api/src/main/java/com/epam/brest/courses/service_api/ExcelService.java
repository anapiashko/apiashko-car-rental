package com.epam.brest.courses.service_api;

import com.epam.brest.courses.model.Car;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

public interface ExcelService {

    ByteArrayInputStream carsToExcel(List<Car> cars) throws IOException;

    List<Car> excelToCars(String fileName);
}
