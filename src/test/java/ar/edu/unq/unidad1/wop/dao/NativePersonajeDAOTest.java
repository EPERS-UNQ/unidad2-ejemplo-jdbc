package ar.edu.unq.unidad1.wop.dao;

import org.junit.Test;

import ar.edu.unq.unidad1.wop.modelo.Item;
import ar.edu.unq.unidad1.wop.modelo.Personaje;

public class NativePersonajeDAOTest {
	
	private PersonajeDAO dao = new NativePersonajeDAO();
	
	@Test
	public void al_guardar_y_luego_recuperar_se_obtiene_objetos_similares() {
		Personaje personaje = new Personaje("Maguito");
		personaje.setPesoMaximo(15);
		personaje.setVida(198);
		personaje.setXp(2500);
		
		personaje.recoger(new Item("Tunica gris", 1));
		personaje.recoger(new Item("Baculo gris", 5));
		
		this.dao.guardar(personaje);
	}

}
