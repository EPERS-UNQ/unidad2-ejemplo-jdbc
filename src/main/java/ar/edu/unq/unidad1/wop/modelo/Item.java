package ar.edu.unq.unidad1.wop.modelo;

public class Item {

    private String nombre;
    private Integer peso;

    public Item(String nombre, Integer peso) {
        this.nombre = nombre;
        this.peso = peso;
    }

    public String getNombre() {
        return nombre;
    }

    public Integer getPeso() {
        return peso;
    }
}
