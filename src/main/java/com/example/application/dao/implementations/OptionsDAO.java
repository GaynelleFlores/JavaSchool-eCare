package com.example.application.dao.implementations;

import com.example.application.dao.GenericDAO;
import com.example.application.models.OptionsEntity;
import org.springframework.stereotype.Component;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Component
public class OptionsDAO implements GenericDAO<OptionsEntity> {
    @PersistenceContext
    private EntityManager entityManager;

    public List<OptionsEntity> findAll() {
        return entityManager.createQuery("SELECT c FROM OptionsEntity c", OptionsEntity.class).getResultList();
    }

    public OptionsEntity show(int id) {
        return entityManager.find(OptionsEntity.class, id);
    }

    public void add(OptionsEntity option) {
        entityManager.persist(option);
    }

    public void edit(OptionsEntity option) {
        entityManager.merge(option);
    }

    public void delete(OptionsEntity option) {
        entityManager.remove(entityManager.merge(option));
    }
}
