package com.example.application.config;

import com.example.application.dto.ClientDTO;
import com.example.application.models.ClientsEntity;
import org.dozer.DozerBeanMapper;
import org.dozer.loader.api.BeanMappingBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MappingConfig {
//    @Bean
//    public BeanMappingBuilder beanMappingBuilder() {
//        return new BeanMappingBuilder() {
//            @Override
//            protected void configure() {
//                mapping(ClientsEntity.class, ClientDTO.class);
//            }
//        };
//    }

    @Bean
    public DozerBeanMapper beanMapper() {
        DozerBeanMapper dozerBeanMapper = new DozerBeanMapper();
        return dozerBeanMapper;
    }
}
