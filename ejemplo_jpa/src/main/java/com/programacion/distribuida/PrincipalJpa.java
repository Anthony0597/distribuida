package com.programacion.distribuida;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.programacion.distribuida.db.Persona;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;

public class PrincipalJpa {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu-distribuida");
        EntityManager em = emf.createEntityManager();

        // Create
//        Persona p = new Persona();
//        p.setNombre("per1");
//        p.setDireccion("dir1");
//        em.getTransaction().begin();
//        em.persist(p);
//        em.getTransaction().commit();

        // Select All
        Query query = em.createQuery("select p from Persona p order by p.nombre asc", Persona.class);
        var lista = query.getResultList();
        lista.forEach(System.out::println);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        System.out.println(gson.toJson(lista));

        // Cerrando Conexion
        em.close();
        emf.close();


    }
}
