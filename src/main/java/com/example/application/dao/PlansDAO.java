package com.example.application.dao;

import com.example.application.models.ContractsEntity;
import com.example.application.models.PlansEntity;
import org.springframework.stereotype.Component;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Component
public class PlansDAO {
    @PersistenceContext
    private EntityManager entityManager;

    public List<PlansEntity> index() {
        Query query = entityManager.createQuery("SELECT p FROM PlansEntity p", PlansEntity.class);
        return query.getResultList();
    }

    public PlansEntity show(int id) {
        return entityManager.find(PlansEntity.class, id);
    }

    public void add(PlansEntity plan) {
        entityManager.persist(entityManager.merge(plan));
    }

    public void delete(PlansEntity plan) {
        for (int i = 0; i < plan.getContracts().size(); i++) {
            ContractsEntity contractDB = entityManager.find(ContractsEntity.class, plan.getContracts().get(i).getId());
            entityManager.remove(entityManager.merge(contractDB));
        }
        entityManager.remove(entityManager.merge(plan));
    }

    public void edit(PlansEntity plan) {
        entityManager.merge(plan);
    }
}
