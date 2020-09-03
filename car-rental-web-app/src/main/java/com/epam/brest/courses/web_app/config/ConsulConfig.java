package com.epam.brest.courses.web_app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConsulConfig {

    @Value("${rest.service.name}")
    private String restAppName;

    private final DiscoveryClient discoveryClient;

    @Autowired
    public ConsulConfig(DiscoveryClient discoveryClient) {
        this.discoveryClient = discoveryClient;
    }

    @Bean
    public String restServiceURI(){
        return discoveryClient.getInstances(restAppName)
                .stream().map(si -> si.getUri()).findFirst().get().toString();
    }
}
