package com.programacion.distribuida;

import com.programacion.distribuida.servicios.StringService;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;

public class PrincipalCdi {
    public static void main(String[] args) {
        try(SeContainer container = SeContainerInitializer.newInstance()
                .initialize() ){
            StringService servicio = container.select(StringService.class).get();
            var ret = servicio.convert("hola mundo");

            System.out.println(ret);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
