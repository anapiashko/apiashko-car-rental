package com.epam.brest.courses.service_rest;

import com.epam.brest.courses.service_api.XmlService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

public class XmlCarServiceRest implements XmlService {

    private static final Logger LOGGER = LoggerFactory.getLogger(XmlCarServiceRest.class);

    private String url;

    private RestTemplate restTemplate;

    public XmlCarServiceRest(String url, RestTemplate restTemplate) {
        this.url = url;
        this.restTemplate = restTemplate;
    }

    @Override
    public ByteArrayInputStream entitiesToXml(List objects) throws IOException {
        return null;
    }

    @Override
    public void xmlToEntities(MultipartFile file) {
        LOGGER.debug("proxy multipartFile car()");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", file.getResource());

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        restTemplate.postForEntity(url,requestEntity, MultipartFile.class);
    }

}
