package com.example.application.services;

import com.example.application.dao.implementations.PlansDAO;
import com.example.application.dto.OptionDTO;
import com.example.application.dto.PlanDTO;
import com.example.application.exceptions.BusinessLogicException;
import com.example.application.mapping.SetMapping;
import com.example.application.models.PlansEntity;
import com.example.application.validation.PlanValidation;
import lombok.RequiredArgsConstructor;
import org.dozer.DozerBeanMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlansService {

    private final DozerBeanMapper mapper;

    private final PlansDAO plansDAO;

    private final PlanValidation planValidation;

    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    public List<PlanDTO> getPlansList() {
        return plansDAO.findAll().stream()
                .map(entity -> mapper.map(entity, PlanDTO.class))
                .collect(Collectors.toList());
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    public  PlanDTO getPlan(int id) {
        PlansEntity plan = plansDAO.show(id);
        if (plan == null) {
            throw new BusinessLogicException("Plan not found");
        }
        return mapper.map(plan, PlanDTO.class);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void createPlan(PlanDTO plan) {
        plansDAO.add(mapper.map(plan, PlansEntity.class));
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void createPlan(PlanDTO planDTO, Set<OptionDTO> allowedOptions) {
        PlansEntity plan = new PlansEntity();
        plan.setAllowedOptions(SetMapping.optionsMapping(allowedOptions));
        plan.setTitle(planDTO.getTitle());
        plan.setPrice(planDTO.getPrice());
        if (!planValidation.validatePlan(plan)) {
            throw new BusinessLogicException("Failed to create plan, plan is invalid");
        }
        plansDAO.add(plan);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void deletePlan(int id) {
        plansDAO.delete(plansDAO.show(id));
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updatePlan(PlanDTO planDTO) {
        PlansEntity plan = mapper.map(planDTO, PlansEntity.class);
        if (!planValidation.validatePlan(plan)) {
            throw new BusinessLogicException("Failed to update plan, plan is invalid");
        }
        plansDAO.edit(plan);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updatePlan(PlanDTO planDTO, Set<OptionDTO> allowedOptions) {
        PlansEntity plan = plansDAO.show(planDTO.getId());
        plan.setAllowedOptions(SetMapping.optionsMapping(allowedOptions));
        plan.setTitle(planDTO.getTitle());
        plan.setPrice(planDTO.getPrice());
        if (!planValidation.validatePlan(plan)) {
            throw new BusinessLogicException("Failed to update plan, plan is invalid");
        }
        plansDAO.edit(plan);
    }
}
