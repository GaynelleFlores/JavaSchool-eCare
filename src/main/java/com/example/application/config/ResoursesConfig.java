package com.example.application.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
@EnableWebMvc
public class ResoursesConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/css/**")
                .addResourceLocations("classpath:/static/css/");
        registry
                .addResourceHandler("/images/**")
                .addResourceLocations("classpath:/static/images/");
        registry
                .addResourceHandler("/scripts/**")
                .addResourceLocations("classpath:/static/scripts/");
    }
}
