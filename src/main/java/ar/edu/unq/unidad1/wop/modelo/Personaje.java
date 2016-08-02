package ar.edu.unq.unidad1.wop.modelo;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.thoughtworks.xstream.annotations.XStreamAlias;

import ar.edu.unq.unidad1.wop.modelo.exception.MuchoPesoException;

@XStreamAlias("personaje")
@JsonAutoDetect(creatorVisibility = Visibility.ANY, fieldVisibility = Visibility.ANY)
public class Personaje implements Serializable {
	private static final long serialVersionUID = 1L;

	private String nombre;
	private int pesoMaximo;
	private int xp;
	private int vida;
	
	private Set<Item> inventario = new HashSet<>();
	
	private Personaje() {
	}
	
	public Personaje(String nombre) {
		this();
		this.nombre = nombre;
	}
	
	public String getNombre() {
		return this.nombre;
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
	
	public Set<Item> getInventario() {
		return this.inventario;
	}
	public void setInventario(Set<Item> inventario) {
		this.inventario = inventario;
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
