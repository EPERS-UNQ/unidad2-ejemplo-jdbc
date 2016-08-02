package ar.edu.unq.unidad1.wop.modelo;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("item")
@JsonAutoDetect(creatorVisibility = Visibility.ANY)
public class Item implements Serializable {
	private static final long serialVersionUID = 1L;

	private String nombre;
	private int peso;
	
	private Item() {
	}
	
	public Item(String nombre, int peso) {
		this();
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
