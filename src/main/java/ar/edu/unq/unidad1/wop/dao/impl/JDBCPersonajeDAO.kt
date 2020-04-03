package ar.edu.unq.unidad1.wop.dao.impl

import ar.edu.unq.unidad1.wop.dao.PersonajeDAO
import ar.edu.unq.unidad1.wop.modelo.Personaje
import java.sql.Connection
import ar.edu.unq.unidad1.wop.dao.impl.JDBCConnector.execute

/**
 * Una implementacion de [PersonajeDAO] que persiste
 * en una base de datos relacional utilizando JDBC
 */
class JDBCPersonajeDAO : PersonajeDAO {

    override fun guardar(personaje: Personaje) {
        execute { conn: Connection ->
            val ps =
                conn.prepareStatement("INSERT INTO personaje (nombre, pesoMaximo, xp, vida) VALUES (?,?,?,?)")
            ps.setString(1, personaje!!.nombre)
            ps.setInt(2, personaje.pesoMaximo)
            ps.setInt(3, personaje.xp)
            ps.setInt(4, personaje.vida)
            //ojo, no estamos guardando el inventario!
            ps.execute()
            if (ps.updateCount != 1) {
                throw RuntimeException("No se inserto el personaje $personaje")
            }
            ps.close()
            null
        }
    }

    override fun recuperar(nombre: String): Personaje {
        return execute{ conn: Connection ->
            val ps = conn.prepareStatement("SELECT pesoMaximo, xp, vida FROM personaje WHERE nombre = ?")
            ps.setString(1, nombre)
            val resultSet = ps.executeQuery()
            var personaje: Personaje? = null
            while (resultSet.next()) {
                //si personaje no es null aca significa que el while dio mas de una vuelta, eso
                //suele pasar cuando el resultado (resultset) tiene mas de un elemento.
                if (personaje != null) {
                    throw RuntimeException("Existe mas de un personaje con el nombre $nombre")
                }
                personaje = Personaje(nombre)
                personaje.pesoMaximo = resultSet.getInt("pesoMaximo")
                personaje.xp = resultSet.getInt("xp")
                personaje.vida = resultSet.getInt("vida")
            }
            ps.close()
            personaje!!
        }
    }

    override fun eliminar(personaje: Personaje) {
        execute { conn: Connection ->
            val ps =  conn.prepareStatement("DELETE FROM personaje WHERE nombre =  ? ")
            ps.setString(1, personaje.nombre)
            ps.execute()
            if (ps.updateCount != 1) {
                throw RuntimeException("No se eliminó el personaje $personaje")
            }
            ps.close()
            null
        }
    }


    init {
        val initializeScript = javaClass.classLoader.getResource("createAll.sql").readText()
        execute {
            val ps = it.prepareStatement(initializeScript)
            ps.execute()
            ps.close()
            null
        }
    }
}