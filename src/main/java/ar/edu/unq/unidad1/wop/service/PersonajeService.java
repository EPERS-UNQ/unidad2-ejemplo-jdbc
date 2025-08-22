package ar.edu.unq.unidad1.wop.service;

import ar.edu.unq.unidad1.wop.modelo.Personaje;

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
