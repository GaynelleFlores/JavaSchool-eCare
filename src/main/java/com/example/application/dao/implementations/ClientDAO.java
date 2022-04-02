package com.example.application.dao.implementations;

import com.example.application.dao.GenericDAO;
import com.example.application.models.ClientsEntity;
import com.example.application.models.Role;
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

    public ClientsEntity getClientByLogin(String login) {
        String query = "SELECT c FROM ClientsEntity c WHERE login = \'" + login + "\'";
        List users = entityManager.createQuery(query).getResultList();
        if (users.size() != 1) {
            return null;
        }
        return (ClientsEntity) users.get(0);
    }

    public void addRole(ClientsEntity client, int roleId) {
        Role role = entityManager.find(Role.class, roleId);
        role.getClients().add(client);
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
