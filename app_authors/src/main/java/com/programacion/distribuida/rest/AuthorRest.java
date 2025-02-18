package com.programacion.distribuida.rest;

import com.programacion.distribuida.db.Author;
import com.programacion.distribuida.repo.AuthorRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Path("/authors")
@Tag(name = "Autores", description = "Operaciones relacionadas con autores")
@Produces("application/json")
@Consumes("application/json")
@ApplicationScoped
//@Transactional
public class AuthorRest {

    @Inject
    private AuthorRepository repository;

    @Inject
    @ConfigProperty(name = "quarkus.http.port")
    Integer port;

    AtomicInteger counter = new AtomicInteger(1);



    @GET
    @Operation(summary = "autor por id", description = "Retorna, si existe, el autor que tenga el id proporcionado")
    @Path("/{id}")
    public Response findById(
            @PathParam("id")
            @Parameter(description = "El ID del autor que solo puede ser numeros enteros mayores a cero", required = true, example = "1")
            Integer id) throws UnknownHostException {

        int value = counter.getAndIncrement();

        if(value%5!=0){
            String msg = String.format("Intento %d ==> error",value);
            System.out.println("*********"+msg);
            throw new RuntimeException(msg);
        }

        System.out.printf("%s: Server %d\n", LocalDateTime.now(), port);

        var obj = repository.findById(id);

        String ipAddress = InetAddress.getLocalHost().getHostAddress();
        String txt = String.format("[%s:%d]-%s", ipAddress,port, obj.getFirstName());

        var ret = new Author();
        ret.setId(obj.getId());
        ret.setFirstName(txt);
        ret.setLastName(obj.getLastName());

        return Response.ok(ret).build();
    }

    @GET
    @Operation(summary = "Lista todos los autores", description = "Retorna un listado de todos los autores registrados")
    public List<Author> findAll(){
        return repository.findAll()
                .list();
    }

    @POST
    @Operation(summary = "Registra un autor", description = "Guarda la informacion de una nuevo autor que se proporcione")
    public Response create(Author author){
        repository.persist(author);
        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    @Operation(summary = "Actualiza un autor", description = "Actualiza, si existe, los datos del autor con el id proporcionado")
    @Path("/{id}")
    public Response update(
            @PathParam("id")
            @Parameter(description = "El ID del autor que solo puede ser numeros enteros mayores a cero", required = true, example = "1")
            Integer id, Author author){
        var obj = repository.update(id, author);

        if(obj.isEmpty()){
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(obj).build();
    }

    @DELETE
        @Operation(summary = "Borrar un autor", description = "Elima del registro el autor que cuyo id coincida con el proporcionado")
    @Path("/{id}")
    public Response delete(
            @PathParam("id")
            @Parameter(description = "El ID del autor que solo puede ser numeros enteros mayores a cero", required = true, example = "1")
            Integer id){
        var obj = repository.deleteById(id);
        if(!obj){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.status(Response.Status.OK).build();
    }


}
