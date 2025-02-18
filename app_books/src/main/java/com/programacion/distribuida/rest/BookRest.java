package com.programacion.distribuida.rest;

import com.programacion.distribuida.clients.AuthorRestClient;
import com.programacion.distribuida.db.Book;
import com.programacion.distribuida.dto.BookDto;
import com.programacion.distribuida.repo.BookRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.List;

@Path("/books")
@Tag(name = "Libros", description = "Operaciones relacionadas con libros")
@Produces("application/json")
@Consumes("application/json")
@ApplicationScoped
//@Transactional
public class BookRest {

    @Inject
    BookRepository repository;

    @Inject
    @RestClient
    AuthorRestClient authorRest;

    @GET
    @Operation(summary = "Lista todos los libros", description = "Retorna un listado de todos los libros registrados")
    public List<BookDto> findAll(){
        // version 1
//        return repository.findAll()
//                .list();

        // version 2 ----------
//        Client client = ClientBuilder.newClient();
//
//        return  repository.streamAll()
//                .map( book -> {
//                    System.out.println("Buscando author con id: "+book.getAuthorId());
//                    var author = client.target(authorsServer)
//                            .path("/authors/{id}")
//                            .resolveTemplate("id", 1)
//                            .request(MediaType.APPLICATION_JSON)
//                            .get(AuthorDto.class);
//                    var bookDto = new BookDto();
//                    bookDto.setId(book.getId());
//                    bookDto.setTitle(book.getTitle());
//                    bookDto.setIsbn(book.getIsbn());
//                    bookDto.setPrice(book.getPrice());
//                    bookDto.setAuthorName(author.getFirstName() + " " + author.getLastName());
//                    return bookDto;
//                })
//                .toList();
        //version 3 --------------


        // version 4 ---------------------------

        return  repository.streamAll()
                .map( book -> {
                    System.out.println("Buscando author con id: "+book.getAuthorId());
                    var author = authorRest.findById(book.getAuthorId());

                    var bookDto = new BookDto();

                    bookDto.setId(book.getId());
                    bookDto.setTitle(book.getTitle());
                    bookDto.setIsbn(book.getIsbn());
                    bookDto.setPrice(book.getPrice());
                    bookDto.setAuthorName(author.getFirstName()+ " " + author.getLastName());
                    return bookDto;
                })
                .toList();



    }

    @GET
    @Operation(summary = "Libro por id", description = "Retorna, si existe, el libro que tenga el id proporcionado")
    @Path("/{id}")
    public Response findById(
            @PathParam("id")
            @Parameter(description = "El ID del libro que solo puede ser numeros enteros mayores a cero", required = true, example = "1")
            Integer id){
        var obj = repository.findByIdOptional(id);
        if(obj.isEmpty()){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        var auth= authorRest.findById(id);

        var bookDto = new BookDto();

        bookDto.setId(obj.get().getId());
        bookDto.setTitle(obj.get().getTitle());
        bookDto.setIsbn(obj.get().getIsbn());
        bookDto.setPrice(obj.get().getPrice());
        bookDto.setAuthorName(auth.getFirstName()+ " " + auth.getLastName());


        return Response.ok(bookDto).build();
    }

    @POST
    @Operation(summary = "Registra un libro", description = "Guarda la informacion de una nuevo libro que se proporcione")
    public Response create(Book book){
        repository.persist(book);
        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    @Path("/{id}")
    @Operation(summary = "Actualiza un libro", description = "Actualiza, si existe, los datos del libro con el id proporcionado")
    public Response update(
            @PathParam("id")
            @Parameter(description = "El ID del libro que solo puede ser numeros enteros mayores a cero", required = true, example = "1")
            Integer id, Book book){
        var obj = repository.update(id, book);

        if(obj.isEmpty()){
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(obj).build();
    }

    @DELETE
    @Operation(summary = "Borrar un libro", description = "Elima del registro el libro que cuyo id coincida con el proporcionado")
    @Path("/{id}")
    public Response delete(
            @PathParam("id")
            @Parameter(description = "El ID del libro que solo puede ser numeros enteros mayores a cero", required = true, example = "1")
            Integer id){
        var obj = repository.deleteById(id);
        if(!obj){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.status(Response.Status.OK).build();
    }
}
