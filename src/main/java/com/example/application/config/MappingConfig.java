package com.example.application.config;

import org.dozer.DozerBeanMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MappingConfig {
    @Bean
    public DozerBeanMapper beanMapper() {
        return new DozerBeanMapper();
    }
}
