package ar.edu.unq.unidad1.wop.dao.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * TODO: Esta es una implementacion sencilla que tiene como objeto
 * mostrar algunas formas de persistir en archivos (usando distintas
 * herramientas para guardar en distintos formatos)
 * 
 * Entre todas las implementaciones de BaseFileDAO existe mucho codigo
 * repetido (relacionado a verificar si el archivo existe o no, abrir
 * y cerrar el archivo, etc). Ese codigo repetido NO deberia existir
 * ya que deberia ser responsabilidad de estar super clase.
 * 
 * @author Claudio Fernandez
 */
public class BaseFileDAO {
	
	private final String extension;
	
	public BaseFileDAO(String extension) {
		this.extension = extension;
	}

	protected File getStorage(String nombre) {
		return new File("data/" + nombre + "." + this.extension);
	}
	
	/**
	 * Remueve el archivo si es que existe
	 */
	protected void deleteIfExists(File dataFile) {
		try {
			Files.deleteIfExists(dataFile.toPath());
		} catch (IOException e) {
			throw new RuntimeException("No se puede eliminar el archivo " + dataFile, e);
		}
	}
	
	
	
}
