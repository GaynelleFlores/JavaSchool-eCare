package com.example.application.converters;

import com.example.application.dto.ClientDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class jsonToClientDTOConverter implements Converter<String, ClientDTO> {

    @Override
    public ClientDTO convert(String s) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return  objectMapper.readValue(s, ClientDTO.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return new ClientDTO();
    }
}