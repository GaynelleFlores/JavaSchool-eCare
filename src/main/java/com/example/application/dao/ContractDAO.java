package com.example.application.dao;

import com.example.application.models.ContractsEntity;
import org.springframework.stereotype.Component;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Component
public class ContractDAO {
    @PersistenceContext
    private EntityManager entityManager;

    public List<ContractsEntity> index() {
        Query query = entityManager.createQuery("SELECT c FROM ContractsEntity c", ContractsEntity.class);
        return query.getResultList();
    }

    public void delete(ContractsEntity contract) {
        entityManager.remove(entityManager.merge(contract));
    }

    public ContractsEntity show(int id) {
        return entityManager.find(ContractsEntity.class, id);
    }

    public void edit(ContractsEntity contract) {
        entityManager.merge(contract);
    }

    public void add(ContractsEntity contract) {
        entityManager.persist(entityManager.merge(contract));
    }

}
