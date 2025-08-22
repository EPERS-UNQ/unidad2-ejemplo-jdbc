package ar.edu.unq.unidad1.wop.service.impl;

import ar.edu.unq.unidad1.wop.dao.PersonajeDAO;
import ar.edu.unq.unidad1.wop.modelo.Personaje;
import ar.edu.unq.unidad1.wop.service.PersonajeService;

public class PersonajeServiceImpl implements PersonajeService {
    private PersonajeDAO personajeDAO;
    public PersonajeServiceImpl(PersonajeDAO personajeDAO) {
        this.personajeDAO = personajeDAO;
    }

    @Override
    public void guardar(Personaje personaje) {
        personajeDAO.guardar(personaje);
    }

    @Override
    public Personaje recuperar(String nombre) {
        return personajeDAO.recuperar(nombre);
    }

    @Override
    public void eliminar(Personaje personaje) {
        personajeDAO.eliminar(personaje);
    }
    
}
