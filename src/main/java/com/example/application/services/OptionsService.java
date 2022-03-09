package com.example.application.services;

import com.example.application.dao.implementations.OptionsDAO;
import com.example.application.dto.OptionDTO;
import com.example.application.models.OptionsEntity;
import lombok.RequiredArgsConstructor;
import org.dozer.DozerBeanMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OptionsService {

    private final DozerBeanMapper mapper;

    private final OptionsDAO optionsDAO;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<OptionDTO> getOptionsList() {
        return optionsDAO.findAll().stream()
                .map(entity -> mapper.map(entity, OptionDTO.class))
                .collect(Collectors.toList());
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public  OptionDTO getOption(int id) {
        OptionsEntity option = optionsDAO.show(id);
        return mapper.map(option, OptionDTO.class);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void createOption(OptionDTO option) {
        optionsDAO.add(mapper.map(option, OptionsEntity.class));
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void deleteOption(int id) {
        optionsDAO.delete(optionsDAO.show(id));
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updateOption(OptionDTO option) {
        optionsDAO.edit(mapper.map(option, OptionsEntity.class));
    }
}
