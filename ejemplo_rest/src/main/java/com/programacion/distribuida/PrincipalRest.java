package com.programacion.distribuida;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.programacion.distribuida.db.Persona;
import com.programacion.distribuida.service.PersonaService;
import io.helidon.webserver.WebServer;
import io.helidon.webserver.http.HttpRouting;
import io.helidon.webserver.http.ServerRequest;
import io.helidon.webserver.http.ServerResponse;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import jakarta.enterprise.inject.spi.CDI;

public class PrincipalRest {

    static void handleHello(ServerRequest req, ServerResponse res) {
        var servicio = CDI.current().select(PersonaService.class).get();
        var gson = new GsonBuilder().setPrettyPrinting().create();
        var persona = servicio.getById(3);
        res.send(gson.toJson(persona));
    }

    static void handleAlgo(ServerRequest req, ServerResponse res) {
        res.send("algo!");
    }

    static void handleInsert(ServerRequest req, ServerResponse res) {
        var servicio = CDI.current().select(PersonaService.class).get();
        var gson = new GsonBuilder().setPrettyPrinting().create();
        String json = req.content().as(String.class);
        Persona persona = gson.fromJson(json, Persona.class);
        var msg = servicio.insertPersona(persona);
        res.send(gson.toJson(msg));
    }

    static void handleUpdate(ServerRequest req, ServerResponse res) {
        var servicio = CDI.current().select(PersonaService.class).get();
        var gson = new GsonBuilder().setPrettyPrinting().create();
        String json = req.content().as(String.class);
        Persona persona = gson.fromJson(json, Persona.class);
        var msg = servicio.putPersona(persona);
        res.send(gson.toJson(msg));
    }

    static void handleFindById(ServerRequest req, ServerResponse res) {
        var servicio = CDI.current().select(PersonaService.class).get();
        var gson = new GsonBuilder().setPrettyPrinting().create();
        Integer id = Integer.parseInt( req.path().pathParameters().get("id"));
        var msg = servicio.getById(id);
        res.send(gson.toJson(msg));
    }

    static void handleFindAll(ServerRequest req, ServerResponse res) {
        var servicio = CDI.current().select(PersonaService.class).get();
        var gson = new GsonBuilder().setPrettyPrinting().create();
        var msg = servicio.getAll();
        res.send(gson.toJson(msg));
    }

    static void handleDelete(ServerRequest req, ServerResponse res) {
        var servicio = CDI.current().select(PersonaService.class).get();
        var gson = new GsonBuilder().setPrettyPrinting().create();
        String json = req.content().as(String.class);
        Persona persona = gson.fromJson(json, Persona.class);
        var msg = servicio.putPersona(persona);
        res.send(gson.toJson(msg));
    }

    public static void main(String[] args) {

        SeContainer container = SeContainerInitializer.newInstance().initialize();

        WebServer.builder()
                .routing( it -> it
                        .get("/hello", PrincipalRest::handleHello)
                        .get("/algo", PrincipalRest::handleAlgo)

                        .get("/persona", PrincipalRest::handleFindAll)
                        .get("/persona/{id}", PrincipalRest::handleFindById)
                        .post("/persona", PrincipalRest::handleInsert)
                        .put("/persona", PrincipalRest::handleUpdate)
                        .delete("/persona", PrincipalRest::handleDelete)
                )
                .port(8080)
                .build()
                .start();
    }
}
