package ar.edu.unq.unidad1.wop.modelo

import java.io.Serializable

class Item(val nombre: String, val peso: Int) : Serializable {

    override fun toString(): String {
        return nombre
    }

}