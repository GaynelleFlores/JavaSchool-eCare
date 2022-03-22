package com.example.application.mapping;

import com.example.application.dto.OptionDTO;
import com.example.application.models.OptionsEntity;
import lombok.RequiredArgsConstructor;
import org.dozer.DozerBeanMapper;
import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
public class SetMapping {

    private static final DozerBeanMapper mapper = new DozerBeanMapper();

    public static Set<OptionsEntity> optionsMapping(Set<OptionDTO> source) {
        Set<OptionsEntity> result = new HashSet<OptionsEntity>();
        for (OptionDTO option : source) {
            if (option == null)
                break;
            result.add(mapper.map(option, OptionsEntity.class));
        }
        return result;
    }

    public static Set<OptionDTO> optionsDTOMapping(Set<OptionsEntity> source) {
        Set<OptionDTO> result = new HashSet<OptionDTO>();
        for (OptionsEntity option : source) {
            if (option == null)
                break;
            result.add(mapper.map(option, OptionDTO.class));
        }
        return result;
    }
}
