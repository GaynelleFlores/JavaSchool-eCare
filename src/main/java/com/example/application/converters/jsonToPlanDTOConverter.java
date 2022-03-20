package com.example.application.converters;

import com.example.application.dto.PlanDTO;
import com.example.application.models.PlansEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class jsonToPlanDTOConverter implements Converter<String, PlanDTO> {

    @Override
    public PlanDTO convert(String s) {
        ObjectMapper objectMapper;
        objectMapper = new ObjectMapper();
        try {
            return  objectMapper.readValue(s, PlanDTO.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return new PlanDTO();
    }
}
