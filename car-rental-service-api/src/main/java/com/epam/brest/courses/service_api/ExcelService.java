package com.epam.brest.courses.service_api;

import com.epam.brest.courses.model.Car;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

public interface ExcelService {

    ByteArrayInputStream carsToExcel(List<Car> cars) throws IOException;

    ByteArrayInputStream excelToCars(MultipartFile file) throws IOException;
}
