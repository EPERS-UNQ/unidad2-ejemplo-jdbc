package ar.edu.unq.unidad1.wop.modelo.exception

import ar.edu.unq.unidad1.wop.modelo.Item
import ar.edu.unq.unidad1.wop.modelo.Personaje

class MuchoPesoException(private val personaje: Personaje, private val item: Item) :
    RuntimeException() {
    override val message: String
        get() = "El personaje [" + personaje + "] no puede recoger [" + item + "] porque cagar mucho peso ya"

    companion object {
        private const val serialVersionUID = 1L
    }

}