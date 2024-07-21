package ar.edu.unq.unidad1.wop.modelo.exception;

import ar.edu.unq.unidad1.wop.modelo.Item;
import ar.edu.unq.unidad1.wop.modelo.Personaje;

public class MuchoPesoException extends RuntimeException {
    public MuchoPesoException(Personaje personaje, Item item) {
        super("El personaje [" + personaje + "] no puede recoger [" + item + "] porque cagar mucho peso ya");
    }
}