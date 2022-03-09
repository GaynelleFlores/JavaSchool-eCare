package com.example.application.dao.implementations;

import com.example.application.dao.GenericDAO;
import com.example.application.models.ContractsEntity;
import org.springframework.stereotype.Component;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Component
public class ContractDAO implements GenericDAO<ContractsEntity> {
    @PersistenceContext
    private EntityManager entityManager;

    public List<ContractsEntity> findAll() {
        return entityManager.createQuery("SELECT c FROM ContractsEntity c", ContractsEntity.class).getResultList();
    }

    public ContractsEntity show(int id) {
        return entityManager.find(ContractsEntity.class, id);
    }

    public void edit(ContractsEntity contract) {
        entityManager.merge(contract);
    }

    public void add(ContractsEntity contract) {
        entityManager.persist(contract);
    }

    public void delete(ContractsEntity contract) {
        entityManager.remove(entityManager.merge(contract));
    }
}
