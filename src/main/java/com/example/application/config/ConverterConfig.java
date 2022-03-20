package com.example.application.config;

import com.example.application.converters.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ConverterConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        //registry.addConverter(new jsonToOptionConverter());
        //registry.addConverter(new jsonToPlanConverter());
        registry.addConverter(new jsonToOptionDTOConverter());
        registry.addConverter(new jsonToPlanDTOConverter());
        registry.addConverter(new jsonToClientDTOConverter());
    }
}