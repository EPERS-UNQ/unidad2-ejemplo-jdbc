package ar.edu.unq.unidad1.service;

import ar.edu.unq.unidad1.modelo.Personaje;

/**
 * Tiene la responsabilidad de orquestar (coordinar)
 * a la capa de modelo y persistencia a la hora de
 * realizarse una operacion de negocio
 */
public interface PersonajeService {
    void guardar(Personaje personaje);
    Personaje recuperar(String nombre);
    void eliminar(Personaje personaje);
}
