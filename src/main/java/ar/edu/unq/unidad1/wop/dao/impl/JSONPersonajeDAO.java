package ar.edu.unq.unidad1.wop.dao.impl;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import ar.edu.unq.unidad1.wop.dao.PersonajeDAO;
import ar.edu.unq.unidad1.wop.modelo.Item;
import ar.edu.unq.unidad1.wop.modelo.Personaje;

/**
 * Esta implementacion de {@link PersonajeDAO} persistirá toda la agregación
 * del {@link Personaje} (es decir, el {@link Personaje} y sus {@link Item})
 * en un archivo binario
 * 
 * @author cf
 */
public class JSONPersonajeDAO extends BaseFileDAO implements PersonajeDAO {

	private ObjectMapper mapper;
	
	public JSONPersonajeDAO() {
		super("json");
		this.mapper = new ObjectMapper();
		this.mapper.enable(SerializationFeature.INDENT_OUTPUT);
	}
	
	@Override
	public void guardar(Personaje personaje) {
		File dataFile = this.getStorage(personaje.getNombre());
		this.deleteIfExists(dataFile);
		
		try {
			this.mapper.writeValue(dataFile, personaje);
		} catch (IOException e) {
			throw new RuntimeException("No se puede guardar " + personaje.getNombre(), e);
		}
	}
	
	@Override
	public Personaje recuperar(String nombre) {
		File dataFile = this.getStorage(nombre);
		
		if (! dataFile.exists()) {
			// No existe el personaje
			return null;
		}
		
		try {
			return this.mapper.readValue(dataFile, Personaje.class);
		} catch (IOException e) {
			throw new RuntimeException("No se puede recuperar " + nombre, e);
		}
	}
	
}
