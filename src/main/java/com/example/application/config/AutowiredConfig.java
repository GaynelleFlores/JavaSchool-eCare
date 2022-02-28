package com.example.application.config;

import com.example.application.dao.ClientDAO;
import com.example.application.dao.ContractDAO;
import com.example.application.dao.OptionsDAO;
import com.example.application.util.EntityManagerUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

@Configuration


public class AutowiredConfig {

    @Bean
    public static EntityManagerFactory getEntityManagerFactory() {
        return EntityManagerUtil.getEntityManagerFactory();
    }

    @Bean
    public static EntityManager getEntityManager() {
        return EntityManagerUtil.getEntityManagerFactory().createEntityManager();
    }

    @Bean
    public static ClientDAO getClientDAO() {
        return new ClientDAO();
    }

    @Bean
    public static ContractDAO getContractDAO() {
        return new ContractDAO();
    }

    @Bean
    public static OptionsDAO getOptionsDAO() {return new OptionsDAO();}
}
