package com.epam.brest.courses.service_api;

import com.epam.brest.courses.model.Car;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

public interface XmlService {
    ByteArrayInputStream carsToXml(List<Car> cars) throws IOException;
    void xmlToCars(MultipartFile file);
}
