package com.example.application.converters;

import com.example.application.dto.OptionDTO;
import com.example.application.models.OptionsEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class jsonToOptionDTOConverter implements Converter<String, OptionDTO> {

    @Override
    public OptionDTO convert(String s) {
        ObjectMapper objectMapper = new ObjectMapper();
        if (s.equals("empty"))
            return null;
        try {
            return  objectMapper.readValue(s, OptionDTO.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return new OptionDTO();
    }
}