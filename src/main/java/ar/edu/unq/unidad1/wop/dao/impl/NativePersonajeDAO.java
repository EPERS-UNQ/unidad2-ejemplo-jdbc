package ar.edu.unq.unidad1.wop.dao.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

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
public class NativePersonajeDAO extends BaseFileDAO implements PersonajeDAO {

	public NativePersonajeDAO() {
		super("bin");
	}

	@Override
	public void guardar(Personaje personaje) {
		File dataFile = this.getStorage(personaje.getNombre());
		this.deleteIfExists(dataFile);
		
		//Resource block - asegura que stream.close() se llama en todos los casos
		try (ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(dataFile))) {
			stream.writeObject(personaje);
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
		
		try (ObjectInputStream stream = new ObjectInputStream(new FileInputStream(dataFile))) {
			return (Personaje) stream.readObject();
		} catch (IOException | ClassNotFoundException e) {
			throw new RuntimeException("No se puede recuperar " + nombre, e);
		}
	}
	
}
