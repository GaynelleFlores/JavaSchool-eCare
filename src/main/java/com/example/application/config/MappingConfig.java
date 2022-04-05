package com.example.application.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.dozer.DozerBeanMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MappingConfig {
    @Bean
    public DozerBeanMapper getBeanMapper() {
        return new DozerBeanMapper();
    }

    @Bean
    public ObjectMapper getObjectMapper() {return new ObjectMapper();}
}
