package ar.edu.unq.unidad1.modelo.exception;

import ar.edu.unq.unidad1.modelo.Item;
import ar.edu.unq.unidad1.modelo.Personaje;

public class MuchoPesoException extends RuntimeException {
    public MuchoPesoException(Personaje personaje, Item item) {
        super("El personaje [" + personaje + "] no puede recoger [" + item + "] porque carga mucho peso ya");
    }
}