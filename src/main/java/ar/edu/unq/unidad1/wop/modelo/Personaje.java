package ar.edu.unq.unidad1.wop.modelo;

import ar.edu.unq.unidad1.wop.modelo.exception.MuchoPesoException;

import java.util.Set;

public class Personaje  {

    private String nombre;
    private Integer pesoMaximo;
    private Integer xp;
    private Integer vida;
    private final Set<Item> inventario;

    public Personaje(String nombre, Integer pesoMaximo, Integer xp, Integer vida, Set<Item> inventario) {
        this.nombre = nombre;
        this.pesoMaximo = pesoMaximo;
        this.xp = xp;
        this.vida = vida;
        this.inventario = inventario;
    }

    public void recoger(Item item) {
        if (getPesoActual() + item.getPeso() > pesoMaximo) {
            throw new MuchoPesoException(this, item);
        }
        inventario.add(item);
    }

    public Integer getPesoActual() {
        return inventario.stream().mapToInt(Item::getPeso).sum();
    }

    public String getNombre() {
        return nombre;
    }

    public Integer getPesoMaximo() {
        return pesoMaximo;
    }

    public Integer getXp() {
        return xp;
    }

    public Integer getVida() {
        return vida;
    }

    public Set<Item> getInventario() {
        return inventario;
    }
}