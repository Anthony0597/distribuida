package com.programacion.distribuida.rest;

import com.programacion.distribuida.db.Persona;
import com.programacion.distribuida.services.PersonaRepository;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/personas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class PersonaRest {

    @Inject
    PersonaRepository repository;

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Integer id) {

        var obj = repository.findByIdOptional(id);

        if(obj.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(obj.get()).build();
    }

    @GET
    public List<Persona> findAll() {
        return repository.findAll(Sort.ascending("id"))
                .stream().toList();
    }

    @POST
    public Response create(Persona persona) {
        repository.persist(persona);
        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Integer id, Persona persona) {
        var obj = repository.update(id, persona);

        if(obj.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.status(Response.Status.OK).build();
    }


    @DELETE
    @Path("/{id}")
    public void delete(@PathParam("id") Integer id) {
        repository.deleteById(id);
    }




}
