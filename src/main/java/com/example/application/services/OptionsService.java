package com.example.application.services;

import com.example.application.dao.implementations.OptionsDAO;
import com.example.application.dto.OptionDTO;
import com.example.application.exceptions.BusinessLogicException;
import com.example.application.mapping.SetMapping;
import com.example.application.models.OptionsEntity;
import com.example.application.validation.OptionValidation;
import lombok.RequiredArgsConstructor;
import org.dozer.DozerBeanMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OptionsService {

    private final DozerBeanMapper mapper;

    private final OptionsDAO optionsDAO;

    private final OptionValidation optionValidation;

    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    public List<OptionDTO> getOptionsList() {
        return optionsDAO.findAll().stream()
                .map(entity -> mapper.map(entity, OptionDTO.class))
                .collect(Collectors.toList());
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    public  OptionDTO getOption(int id) {
        OptionsEntity option = optionsDAO.show(id);
        if (option == null) {
            throw new BusinessLogicException("Option not found");
        }
        return mapper.map(option, OptionDTO.class);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    public  Set<OptionDTO> getRequiredOptions(int id) {
        OptionDTO opt = this.getOption(id);
        Set<OptionsEntity> result = new HashSet<OptionsEntity>();
        result.addAll(opt.getRequiredOptions());
        result.addAll(opt.getRequiredOptionsMirror());
        return SetMapping.optionsDTOMapping(result);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    public  Set<OptionDTO> getIncompatibleOptions(int id) {
        OptionDTO opt = this.getOption(id);
        Set<OptionsEntity> result = new HashSet<OptionsEntity>();
        result.addAll(opt.getIncompatibleOptions());
        result.addAll(opt.getIncompatibleOptionsMirror());
        return SetMapping.optionsDTOMapping(result);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void createOption(OptionDTO option) {
        optionsDAO.add(mapper.map(option, OptionsEntity.class));
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void createOption(OptionDTO optionDTO, Set<OptionDTO> reqOptions, Set<OptionDTO> incOptions) {
        OptionsEntity option = new OptionsEntity();
        option.setRequiredOptions(SetMapping.optionsMapping(reqOptions));
        option.setIncompatibleOptions(SetMapping.optionsMapping(incOptions));
        option.setTitle(optionDTO.getTitle());
        option.setPrice(optionDTO.getPrice());
        option.setConnectionCost(optionDTO.getConnectionCost());
        optionValidation.validateOption(option);
        optionsDAO.add(option);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void deleteOption(int id) {
        optionsDAO.delete(optionsDAO.show(id));
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updateOption(OptionDTO option) {
        optionsDAO.edit(mapper.map(option, OptionsEntity.class));
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updateOption(OptionDTO optionDTO, Set<OptionDTO> reqOptions, Set<OptionDTO> incOptions) {
        OptionsEntity option = optionsDAO.show(optionDTO.getId());
        option.setRequiredOptions(SetMapping.optionsMapping(reqOptions));
        option.setIncompatibleOptions(SetMapping.optionsMapping(incOptions));
        option.setIncompatibleOptionsMirror(new HashSet<OptionsEntity>());
        option.setRequiredOptionsMirror(new HashSet<OptionsEntity>());
        option.setTitle(optionDTO.getTitle());
        option.setPrice(optionDTO.getPrice());
        option.setConnectionCost(optionDTO.getConnectionCost());
        optionsDAO.edit(option);
    }
}
