package ar.edu.unq.unidad1.wop.modelo;

import java.io.Serializable;

public class Item implements Serializable {
	private static final long serialVersionUID = 1L;

	private String nombre;
	private int peso;
	
	public Item(String nombre, int peso) {
		this.nombre = nombre;
		this.peso = peso;
	}

	public String getNombre() {
		return this.nombre;
	}
	
	public int getPeso() {
		return this.peso;
	}
	
	@Override
	public String toString() {
		return this.nombre;
	}
	
}
