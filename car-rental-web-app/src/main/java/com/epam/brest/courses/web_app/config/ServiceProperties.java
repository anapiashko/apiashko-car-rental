package com.epam.brest.courses.web_app.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties("app.service")
public class ServiceProperties {

    private String baseUrl;
    private String orderUrl;
    private String carUrl;
    private String carDtoUrl;
    private String orderDtoUrl;
    private String xmlUrl;
}
