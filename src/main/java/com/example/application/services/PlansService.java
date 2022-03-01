package com.example.application.services;

import com.example.application.dao.PlansDAO;
import com.example.application.dto.PlanDTO;
import com.example.application.models.PlansEntity;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlansService {
    private final DozerBeanMapper mapper;

    private final PlansDAO plansDAO;

    @Autowired
    public PlansService(PlansDAO plansDAO, DozerBeanMapper mapper) {
        this.plansDAO = plansDAO;
        this.mapper = mapper;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<PlanDTO> getPlansList() {
        return plansDAO.index().stream()
                .map(entity -> mapper.map(entity, PlanDTO.class))
                .collect(Collectors.toList());
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public  PlanDTO getPlan(int id) {
        PlansEntity plan = plansDAO.show(id);
        System.out.println(plan.toString());
        return mapper.map(plan, PlanDTO.class);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void createPlan(PlanDTO plan) {
        plansDAO.add(mapper.map(plan, PlansEntity.class));
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void deletePlan(int id) {
        plansDAO.delete(plansDAO.show(id));
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updatePlan(PlanDTO plan) {
        plansDAO.edit(mapper.map(plan, PlansEntity.class));
    }
}
