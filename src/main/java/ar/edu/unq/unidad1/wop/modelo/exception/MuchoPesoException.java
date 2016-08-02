package ar.edu.unq.unidad1.wop.modelo.exception;

import ar.edu.unq.unidad1.wop.modelo.Item;
import ar.edu.unq.unidad1.wop.modelo.Personaje;

public class MuchoPesoException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private final Personaje personaje;
	private final Item item;

	public MuchoPesoException(Personaje personaje, Item item) {
		this.personaje = personaje;
		this.item = item;
	}

	@Override
	public String getMessage() {
		return "El personaje [" + this.personaje + "] no puede recoger [" + this.item + "] porque cagar mucho peso ya";
	}
	
}
