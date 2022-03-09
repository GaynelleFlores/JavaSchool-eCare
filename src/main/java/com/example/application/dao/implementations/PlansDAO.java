package com.example.application.dao.implementations;

import com.example.application.dao.GenericDAO;
import com.example.application.models.PlansEntity;
import org.springframework.stereotype.Component;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Component
public class PlansDAO implements GenericDAO<PlansEntity> {
    @PersistenceContext
    private EntityManager entityManager;

    public List<PlansEntity> findAll() {
        return entityManager.createQuery("SELECT p FROM PlansEntity p", PlansEntity.class).getResultList();
    }

    public PlansEntity show(int id) {
        return entityManager.find(PlansEntity.class, id);
    }

    public void add(PlansEntity plan) {
        entityManager.persist(plan);
    }

    public void edit(PlansEntity plan) {
        entityManager.merge(plan);
    }

    public void delete(PlansEntity plan) {
        entityManager.remove(entityManager.merge(plan));
    }
}
