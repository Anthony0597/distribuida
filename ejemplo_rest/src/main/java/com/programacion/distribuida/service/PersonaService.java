package com.programacion.distribuida.service;

import com.programacion.distribuida.db.Persona;

import java.util.List;


public interface PersonaService {
    public Persona getById(int id);
    public List<Persona> getAll();
    public String insertPersona(Persona persona);
    public Persona putPersona(Persona persona);
    public String deletePersona(int id);

}
