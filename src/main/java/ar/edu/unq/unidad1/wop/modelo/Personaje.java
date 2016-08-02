package ar.edu.unq.unidad1.wop.modelo;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import ar.edu.unq.unidad1.wop.modelo.exception.MuchoPesoException;

public class Personaje implements Serializable {
	private static final long serialVersionUID = 1L;

	private String nombre;
	private int pesoMaximo;
	private int xp;
	private int vida;
	
	private Set<Item> inventario = new HashSet<>();
	
	public Personaje(String nombre) {
		this.nombre = nombre;
	}
	
	public String getNombre() {
		return this.nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getPesoMaximo() {
		return this.pesoMaximo;
	}
	public void setPesoMaximo(int pesoMaximo) {
		this.pesoMaximo = pesoMaximo;
	}
	
	public int getXp() {
		return this.xp;
	}
	public void setXp(int xp) {
		this.xp = xp;
	}
	
	public int getVida() {
		return this.vida;
	}
	public void setVida(int vida) {
		this.vida = vida;
	}

	public void recoger(Item item) {
		int pesoActual = this.getPesoActual();
		if (pesoActual + item.getPeso() > this.pesoMaximo) {
			throw new MuchoPesoException(this, item);
		}
		
		this.inventario.add(item);
	}

	private int getPesoActual() {
		int pesoActual = 0;
		for (Item item : this.inventario) {
			pesoActual += item.getPeso();
		}
		return pesoActual;
	}
	
	@Override
	public String toString() {
		return this.nombre;
	}

}
