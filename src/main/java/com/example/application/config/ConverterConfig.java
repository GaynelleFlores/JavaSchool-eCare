package com.example.application.config;

import com.example.application.converters.jsonToOptionConverter;
import com.example.application.converters.jsonToPlanConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ConverterConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new jsonToOptionConverter());
        registry.addConverter(new jsonToPlanConverter());
    }
}