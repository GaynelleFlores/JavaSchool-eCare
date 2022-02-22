package com.example.application;

import com.example.application.models.ClientsEntity;
import com.example.application.util.HibernateUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.hibernate.Session;
import org.hibernate.Transaction;

@SpringBootApplication
public class eCareApplication {

    public static void main(String[] args) {
        System.setProperty("spring.devtools.restart.enabled", "false");
//        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
//        EntityManager entityManager = entityManagerFactory.createEntityManager();
//        EntityTransaction transaction = entityManager.getTransaction();

        SpringApplication.run(eCareApplication.class, args);
    }
}
