package com.example.application.converters;

import com.example.application.models.PlansEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class jsonToPlanConverter implements Converter<String, PlansEntity> {

    @Override
    public PlansEntity convert(String s) {
        ObjectMapper objectMapper;
        objectMapper = new ObjectMapper();
        try {
            return  objectMapper.readValue(s, PlansEntity.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return new PlansEntity();
    }
}

