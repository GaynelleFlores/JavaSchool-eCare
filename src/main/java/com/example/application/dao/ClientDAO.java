package com.example.application.dao;

import com.example.application.models.ClientsEntity;
import com.example.application.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

public class ClientDAO {
    public List<ClientsEntity> index() throws Exception {
        List<ClientsEntity> list;
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        list = session.createCriteria(ClientsEntity.class).list();
        tx.commit();
        session.close();
        return list;
    }
    public ClientsEntity show(int id) throws Exception {
        ClientsEntity client;
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        client = session.get(ClientsEntity.class, id);
        tx.commit();
        session.close();
        return client;
    }

    public void add(ClientsEntity client)  throws Exception {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        Integer id = (Integer) session.save(client);
        tx.commit();
        session.close();
    }

    public void delete(int id)  throws Exception {
        Session session = HibernateUtil.getSessionFactory().openSession();
        ClientsEntity client = new ClientsEntity();
        Transaction tx = session.beginTransaction();
        client.setId(id);
        session.delete(client);
        tx.commit();
        session.close();
    }

    public void edit(ClientsEntity client)  throws Exception {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.update(client);
        tx.commit();
        session.close();
    }

}
