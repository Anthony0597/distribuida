package com.programacion.distribuida.config;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

@ApplicationScoped
public class JpaConfig {

    EntityManagerFactory emf;

    @PostConstruct
    public void init() {
        emf = Persistence.createEntityManagerFactory("pu-distribuida");
    }

    @Produces
    public EntityManager em() {
        return emf.createEntityManager();
    }
}
