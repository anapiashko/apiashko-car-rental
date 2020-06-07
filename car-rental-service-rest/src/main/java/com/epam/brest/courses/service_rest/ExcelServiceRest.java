package com.epam.brest.courses.service_rest;

import com.epam.brest.courses.model.Car;
import com.epam.brest.courses.service_api.ExcelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class ExcelServiceRest implements ExcelService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CarServiceRest.class);

    private String url;

    private RestTemplate restTemplate;

    public ExcelServiceRest(String url, RestTemplate restTemplate) {
        this.url = url;
        this.restTemplate = restTemplate;
    }

    @Override
    public ByteArrayInputStream carsToExcel(List<Car> cars) throws IOException {
        return null;
    }

//
//    public List<Car> excelToCars(String fileName){
//        LOGGER.debug("import in db()");
//
//        ResponseEntity<List> responseEntity = restTemplate.getForEntity(url+"?filename="+fileName, List.class);
//        return (List<Car>) responseEntity.getBody();
//    }

//    @Override
//    public ByteArrayInputStream excelToCars(MultipartFile file) throws IOException {
//        LOGGER.debug("import in db()");
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
//
////        restTemplate.getMessageConverters().add(new ByteArrayHttpMessageConverter());
////        headers.setAccept(Arrays.asList(MediaType.APPLICATION_OCTET_STREAM, MediaType.MULTIPART_FORM_DATA));
//
//        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
//        body.add("file", new MultipartFileResource(file.getInputStream()));
//
//        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
//
//
//        restTemplate.postForEntity(url,requestEntity, InputStreamResource.class);
//
//        MultiValueMap<String, Object> n = requestEntity.getBody();
//        byte[] bytes = {1,2,3};
//        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
//        return byteArrayInputStream;
//    }

    @Override
    public ByteArrayInputStream excelToCars(MultipartFile file) throws IOException {
        LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        ResponseEntity<Resource> response = null;

        map.add("file", file.getResource());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity = new HttpEntity<>(map, headers);
        response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, Resource.class);

        InputStream responseInputStream = response.getBody().getInputStream();
        byte[] bytes = {1, 2, 3};
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        return byteArrayInputStream;
    }

    class MultipartInputStreamFileResource extends InputStreamResource {

        public MultipartInputStreamFileResource(InputStream inputStream) {
            super(inputStream);
        }

        @Override
        public long contentLength() throws IOException {
            return -1; // we do not want to generally read the whole stream into memory ...
        }
    }
//    private class MultipartFileResource extends InputStreamResource {
//
//        private String filename;
//
//        public MultipartFileResource(InputStream inputStream, String filename) {
//            super(inputStream);
//            this.filename = filename;
//        }
//
//        public MultipartFileResource(InputStream inputStream) {
//            super(inputStream);
//        }
//
//        @Override
//        public String getFilename() {
//            return this.filename;
//        }
//    }

}
