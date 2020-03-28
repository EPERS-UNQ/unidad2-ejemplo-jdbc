package ar.edu.unq.unidad1.wop.dao.impl

import ar.edu.unq.unidad1.wop.dao.PersonajeDAO
import ar.edu.unq.unidad1.wop.modelo.Personaje
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

/**
 * Una implementacion de [PersonajeDAO] que persiste
 * en una base de datos relacional utilizando JDBC
 */
class JDBCPersonajeDAO : PersonajeDAO {

    override fun guardar(personaje: Personaje) {
        executeWithConnection { conn: Connection ->
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
        return executeWithConnection{ conn: Connection ->
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

    /**
     * Ejecuta un bloque de codigo contra una conexion.
     */
    private fun <T> executeWithConnection(bloque: (Connection)->T): T {
        val connection = openConnection()
        return try {
            bloque(connection)
        } catch (e: SQLException) {
            throw RuntimeException("Error no esperado", e)
        } finally {
            closeConnection(connection)
        }
    }

    /**
     * Establece una conexion a la url especificada
     *
     * @return la conexion establecida
     */
    private fun openConnection(): Connection {
        val env = System.getenv()
        val user = env.getOrDefault("user", "root")
        val password = env.getOrDefault("password", "root")
        val host = env.getOrDefault("host", "localhost")
        val dataBase = env.getOrDefault("dataBase", "epers_ejemplo_jdbc")
        return try {
            DriverManager.getConnection("jdbc:mysql://$host:3306/$dataBase?user=$user&password=$password&useSSL=false&createDatabaseIfNotExist=true")
        } catch (e: SQLException) {
            throw RuntimeException("No se puede establecer una conexion", e)
        }
    }

    /**
     * Cierra una conexion con la base de datos (libera los recursos utilizados por la misma)
     *
     * @param connection - la conexion a cerrar.
     */
    private fun closeConnection(connection: Connection) {
        try {
            connection.close()
        } catch (e: SQLException) {
            throw RuntimeException("Error al cerrar la conexion", e)
        }
    }

    init {
        val initializeScript = javaClass.classLoader.getResource("createAll.sql").readText()
        executeWithConnection{
            val ps = it.prepareStatement(initializeScript)
            ps.execute()
            ps.close()
            null
        }
    }
}