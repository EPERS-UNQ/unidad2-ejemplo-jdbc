package ar.edu.unq.unidad1.wop.dao

import ar.edu.unq.unidad1.wop.dao.impl.JDBCPersonajeDAO
import ar.edu.unq.unidad1.wop.modelo.Item
import ar.edu.unq.unidad1.wop.modelo.Personaje
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class JDBCPersonajeDAOTest {
    private val dao: PersonajeDAO = JDBCPersonajeDAO()
    lateinit var maguito: Personaje

    @Before
    fun crearModelo() {
        maguito = Personaje("Maguito")
        maguito.pesoMaximo = 15
        maguito.vida = 198
        maguito.xp = 2500
        maguito.recoger(Item("Tunica gris", 1))
        maguito.recoger(Item("Baculo gris", 5))
    }

    @Test
    fun alGuardarYLuegoRecuperarSeObtieneObjetosSimilares() {
        dao.guardar(maguito)

        //Los personajes son iguales
        val otroMaguito = dao.recuperar("Maguito")
        Assert.assertEquals(maguito.nombre, otroMaguito.nombre)
        Assert.assertEquals(maguito.pesoMaximo.toLong(), otroMaguito.pesoMaximo.toLong())
        Assert.assertEquals(maguito.vida.toLong(), otroMaguito.vida.toLong())
        Assert.assertEquals(maguito.xp.toLong(), otroMaguito.xp.toLong())
        //		assertEquals(this.maguito.getInventario().size(), otroMaguito.getInventario().size());

        //Pero no son el mismo objeto =(
        //A esto nos referimos con "perdida de identidad"
        Assert.assertTrue(maguito !== otroMaguito)
    }


    @After
    fun emilinarModelo() {
        dao.eliminar(maguito)
    }
}