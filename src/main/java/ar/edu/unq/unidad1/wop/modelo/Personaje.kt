package ar.edu.unq.unidad1.wop.modelo

import ar.edu.unq.unidad1.wop.modelo.exception.MuchoPesoException
import java.util.*

class Personaje(val nombre: String)  {
    var pesoMaximo = 0
    var xp = 0
    var vida = 0
    var inventario: MutableSet<Item> = HashSet()


    fun recoger(item: Item) {
        val pesoActual = pesoActual
        if (pesoActual + item.peso > pesoMaximo) {
            throw MuchoPesoException(this, item)
        }
        inventario.add(item)
    }

    val pesoActual: Int
        get() {
            var pesoActual = 0
            for (item in inventario) {
                pesoActual += item.peso
            }
            return pesoActual
        }

    override fun toString(): String {
        return nombre
    }

}