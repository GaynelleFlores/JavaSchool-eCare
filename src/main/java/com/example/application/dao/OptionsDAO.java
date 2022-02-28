package com.example.application.dao;

import com.example.application.models.OptionsEntity;
import org.springframework.stereotype.Component;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Component
public class OptionsDAO {
    @PersistenceContext
    private EntityManager entityManager;

    public List<OptionsEntity> index() {
        Query query = entityManager.createQuery("SELECT c FROM OptionsEntity c", OptionsEntity.class);
        return query.getResultList();
    }

    public OptionsEntity show(int id) {
        return entityManager.find(OptionsEntity.class, id);
    }

    public void add(OptionsEntity option) {
        entityManager.persist(entityManager.merge(option));
    }

    public void delete(OptionsEntity option) {
        entityManager.remove(entityManager.merge(option));
    }

    public void edit(OptionsEntity option) {
        entityManager.merge(option);
    }
}
