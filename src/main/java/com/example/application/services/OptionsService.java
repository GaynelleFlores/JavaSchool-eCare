package com.example.application.services;

import com.example.application.dao.OptionsDAO;
import com.example.application.dto.OptionDTO;
import com.example.application.models.OptionsEntity;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLOutput;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OptionsService {

    private final DozerBeanMapper mapper;

    private final OptionsDAO optionsDAO;

    @Autowired
    public OptionsService(OptionsDAO optionsDAO, DozerBeanMapper mapper) {
        this.optionsDAO = optionsDAO;
        this.mapper = mapper;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<OptionDTO> getOptionsList() {
        return optionsDAO.index().stream()
                .map(entity -> mapper.map(entity, OptionDTO.class))
                .collect(Collectors.toList());
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public  OptionDTO getOption(int id) {
        OptionsEntity option = optionsDAO.show(id);
        System.out.println(option.toString());
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
