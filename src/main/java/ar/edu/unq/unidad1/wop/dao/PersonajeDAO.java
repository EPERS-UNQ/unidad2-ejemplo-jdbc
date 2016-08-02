package ar.edu.unq.unidad1.wop.dao;

import ar.edu.unq.unidad1.wop.modelo.Personaje;

/**
 * Tiene la responsabilidad de guardar y recuperar personajes desde
 * el medio persistente
 */
public interface PersonajeDAO {

	void guardar(Personaje personaje);
	
	Personaje recuperar(String nombre);
	
}
