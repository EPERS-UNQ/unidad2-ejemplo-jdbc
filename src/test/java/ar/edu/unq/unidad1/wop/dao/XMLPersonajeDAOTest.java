package ar.edu.unq.unidad1.wop.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import ar.edu.unq.unidad1.wop.dao.impl.XMLPersonajeDAO;
import ar.edu.unq.unidad1.wop.modelo.Item;
import ar.edu.unq.unidad1.wop.modelo.Personaje;

/**
 * TODO: Esta es una implementacion sencilla que tiene como objeto
 * mostrar algunas formas de persistir en archivos (usando distintas
 * herramientas para guardar en distintos formatos)
 * 
 * Entre todas las distintas implementaciones de test el codigo es
 * exactamente el mismo. Se mantiene asi solo para mostrar el funcionamiento
 * sin demasiada complicacion, pero el codigo repetido NO deberia existir.
 * 
 * @author Claudio Fernandez
 */
public class XMLPersonajeDAOTest {
	
	private PersonajeDAO dao = new XMLPersonajeDAO();
	private Personaje maguito;
	
	@Before
	public void crearModelo() {
		this.maguito = new Personaje("Maguito");
		this.maguito.setPesoMaximo(15);
		this.maguito.setVida(198);
		this.maguito.setXp(2500);
		this.maguito.recoger(new Item("Tunica gris", 1));
		this.maguito.recoger(new Item("Baculo gris", 5));
	}
	
	@Test
	public void al_guardar_y_luego_recuperar_se_obtiene_objetos_similares() {
		this.dao.guardar(this.maguito);
		
		//Los personajes son iguales
		Personaje otroMaguito = this.dao.recuperar("Maguito");
		assertEquals(this.maguito.getNombre(), otroMaguito.getNombre());
		assertEquals(this.maguito.getPesoMaximo(), otroMaguito.getPesoMaximo());
		assertEquals(this.maguito.getVida(), otroMaguito.getVida());
		assertEquals(this.maguito.getXp(), otroMaguito.getXp());
		assertEquals(this.maguito.getInventario().size(), otroMaguito.getInventario().size());
		
		//Pero no son el mismo objeto =(
		//A esto nos referimos con "perdida de identidad"
		assertTrue(this.maguito != otroMaguito);
	}

}
