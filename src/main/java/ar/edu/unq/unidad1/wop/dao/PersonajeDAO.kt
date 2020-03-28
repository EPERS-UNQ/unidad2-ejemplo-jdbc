package ar.edu.unq.unidad1.wop.dao

import ar.edu.unq.unidad1.wop.modelo.Personaje

/**
 * Tiene la responsabilidad de guardar y recuperar personajes desde
 * el medio persistente
 */
interface PersonajeDAO {
    fun guardar(personaje: Personaje)
    fun recuperar(nombre: String): Personaje
    fun eliminar(personaje: Personaje)
}