package com.example.application.dao;

import com.example.application.models.ClientsEntity;
import com.example.application.models.ContractsEntity;
import org.springframework.stereotype.Component;
import javax.persistence.*;
import java.util.List;

@Component
public class ClientDAO {
    @PersistenceContext
    private EntityManager entityManager;

    public List<ClientsEntity> index() {
        Query query = entityManager.createQuery("SELECT c FROM ClientsEntity c", ClientsEntity.class);
        return query.getResultList();
    }

    public ClientsEntity show(int id) {
        return entityManager.find(ClientsEntity.class, id);
    }

    public void add(ClientsEntity client) {
        entityManager.persist(entityManager.merge(client));
    }

    public void delete(ClientsEntity client) {
        for (int i = 0; i < client.getContracts().size(); i++) {
            ContractsEntity contractDB = entityManager.find(ContractsEntity.class, client.getContracts().get(i).getId());
            entityManager.remove(entityManager.merge(contractDB));
        }
        entityManager.remove(entityManager.merge(client));
    }

    public void edit(ClientsEntity client) {
        entityManager.merge(client);
    }
}
