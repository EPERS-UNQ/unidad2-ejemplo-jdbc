package ar.edu.unq.unidad1.persistence;

import ar.edu.unq.unidad1.modelo.Personaje;

/**
 * Tiene la responsabilidad de guardar y recuperar personajes desde
 * el medio persistente
 */
public interface PersonajeDAO {
    void guardar(Personaje personaje);
    Personaje recuperar(String nombre);
    void eliminar(Personaje personaje);
}