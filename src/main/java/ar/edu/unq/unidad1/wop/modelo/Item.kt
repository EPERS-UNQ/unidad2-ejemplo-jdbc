package ar.edu.unq.unidad1.wop.modelo

import java.io.Serializable

class Item(var nombre: String, var peso: Int) : Serializable {

    override fun toString(): String {
        return nombre
    }

}