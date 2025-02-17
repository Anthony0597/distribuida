package com.programacion.distribuida.services;

import com.programacion.distribuida.db.Persona;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.Optional;

@ApplicationScoped
@Transactional
public class PersonaRepository implements PanacheRepositoryBase<Persona, Integer> {

    public Optional<Persona> update(Integer id, Persona persona) {
        var obj = this.findByIdOptional(id);

        if(obj.isEmpty()){
            return Optional.empty();
        }

        var perObj = obj.get();
        perObj.setNombre(persona.getNombre());
        perObj.setDireccion(persona.getDireccion());

        return Optional.of(obj.get());
    }
}
