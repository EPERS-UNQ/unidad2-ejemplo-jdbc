package ar.edu.unq.unidad1.wop.modelo;

import java.io.Serializable;

/*
 * Los records ya implementan:
 *  equals, hashcode,
 *  private final para todos los fields
 *  toString con className + atributos
 *  getters para todos los fields
 *
 *  Todos los records son inmutables por definición.
 */
public record Item(String nombre, Integer peso) implements Serializable { }