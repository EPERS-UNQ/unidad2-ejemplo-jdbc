package ar.edu.unq.unidad1.wop.dao.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;

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
public class XMLPersonajeDAO extends BaseFileDAO implements PersonajeDAO {

	private XStream xstream;
	
	public XMLPersonajeDAO() {
		super("xml");
		this.xstream = new XStream(new StaxDriver());
		this.xstream.processAnnotations(Personaje.class);
		this.xstream.processAnnotations(Item.class);
	}
	
	@Override
	public void guardar(Personaje personaje) {
		File dataFile = this.getStorage(personaje.getNombre());
		this.deleteIfExists(dataFile);
		
		try (FileOutputStream stream = new FileOutputStream(dataFile)) {
			this.xstream.toXML(personaje, stream);
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
			return (Personaje) this.xstream.fromXML(dataFile);
		} catch (Exception e) {
			throw new RuntimeException("No se puede recuperar " + nombre, e);
		}
	}

}
