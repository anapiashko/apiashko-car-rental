package com.epam.brest.courses.service_api;

import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

public interface XmlService <T> {
    ByteArrayInputStream entitiesToXml(List<T> objects) throws IOException;
    void xmlToEntities(MultipartFile file);
}
