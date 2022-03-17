package com.example.application.converters;

import com.example.application.models.OptionsEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class jsonToOptionConverter implements Converter<String, OptionsEntity> {

    @Override
    public OptionsEntity convert(String s) {
        ObjectMapper objectMapper;
        objectMapper = new ObjectMapper();
        if (s.equals("empty"))
            return null;
        try {
            return  objectMapper.readValue(s, OptionsEntity.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return new OptionsEntity();
    }
}
