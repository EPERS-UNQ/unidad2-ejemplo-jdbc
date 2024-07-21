package ar.edu.unq.unidad1.wop.modelo;

import ar.edu.unq.unidad1.wop.modelo.exception.MuchoPesoException;

import java.util.Set;

public record Personaje(String nombre, Integer pesoMaximo, Integer xp, Integer vida, Set<Item> inventario)  {

    public void recoger(Item item) {
        if (getPesoActual() + item.peso() > pesoMaximo) {
            throw new MuchoPesoException(this, item);
        }
        inventario.add(item);
    }

    public Integer getPesoActual() {
        return inventario.stream().mapToInt(Item::peso).sum();
    }

}