package ar.edu.unq.unidad1.wop.dao;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Files;

import ar.edu.unq.unidad1.wop.modelo.Personaje;

public class NativePersonajeDAO implements PersonajeDAO {

	@Override
	public void guardar(Personaje personaje) {
		File dataFile = new File("data/" + personaje.getNombre() + ".bin");
		this.deleteIfExists(dataFile);
		
		//Resource block - asegura que stream.close() se llama en todos los casos
		try (ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(dataFile))) {
			stream.writeObject(personaje);
		} catch (IOException e) {
			throw new RuntimeException("No se puede guardar " + personaje.getNombre(), e);
		}
	}
	
	/**
	 * Remueve el archivo si es que existe
	 */
	private void deleteIfExists(File dataFile) {
		try {
			Files.deleteIfExists(dataFile.toPath());
		} catch (IOException e) {
			throw new RuntimeException("No se puede eliminar el archivo " + dataFile, e);
		}
	}

	@Override
	public Personaje recuperar(String nombre) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
