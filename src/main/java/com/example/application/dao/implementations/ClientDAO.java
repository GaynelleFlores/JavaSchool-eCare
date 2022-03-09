package com.example.application.dao.implementations;

import com.example.application.dao.GenericDAO;
import com.example.application.models.ClientsEntity;
import org.springframework.stereotype.Component;
import javax.persistence.*;
import java.util.List;

@Component
public class ClientDAO implements GenericDAO<ClientsEntity> {
    @PersistenceContext
    private EntityManager entityManager;

    public List<ClientsEntity> findAll() {
        return entityManager.createQuery("SELECT c FROM ClientsEntity c", ClientsEntity.class).getResultList();
    }

    public ClientsEntity show(int id) {
        return entityManager.find(ClientsEntity.class, id);
    }

    public void add(ClientsEntity client) {
        entityManager.persist(client);
    }

    public void edit(ClientsEntity client) {
        entityManager.merge(client);
    }

    public void delete(ClientsEntity client) {
        entityManager.remove(entityManager.merge(client));
    }
}
