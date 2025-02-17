package com.programacion.distribuida.servicios;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class StringServiceImpl implements StringService{

    @Inject
    LogService log;

    @Override
    public String convert(String txt) {
        log.log("Converting <<"+ txt +">>");
        return txt.toUpperCase();
    }
}
