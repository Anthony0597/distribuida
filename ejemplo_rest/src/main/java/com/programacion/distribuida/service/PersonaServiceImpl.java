package com.programacion.distribuida.service;

import com.programacion.distribuida.db.Persona;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import java.util.List;

@ApplicationScoped
public class PersonaServiceImpl implements PersonaService {

    @Inject
    EntityManager em;

    @Override
    public Persona getById(int id) {
        return em.find(Persona.class, id);
    }

    @Override
    public List<Persona> getAll() {
        return em.createQuery("select p from Persona p order by p.nombre", Persona.class).getResultList();
    }

    @Override
    public String insertPersona(Persona persona) {
        try {
            em.getTransaction().begin();
            em.persist(persona);
            em.getTransaction().commit();
            return "¡Persona "+persona.getId()+" insertada con exito!";
        }catch(Exception e) {
            return "Error al insertar: "+e.getMessage();
        }
    }

    @Override
    public Persona putPersona(Persona persona) {
        try {
            em.getTransaction().begin();
            var refreshPer = em.merge(persona);
            em.getTransaction().commit();
            return refreshPer;
        }catch (Exception e) {
            return null;
        }

    }

    @Override
    public String deletePersona(int id) {
        try{
            em.getTransaction().begin();
            em.remove(em.find(Persona.class, id));
            em.getTransaction().commit();
            return "¡Persona "+id+" eliminada con exito!";
        }catch(Exception e) {
            return "Error al eliminar: "+e.getMessage();
        }

    }


}
