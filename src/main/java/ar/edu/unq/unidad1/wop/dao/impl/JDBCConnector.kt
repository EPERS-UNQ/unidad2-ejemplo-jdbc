package ar.edu.unq.unidad1.wop.dao.impl

import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

object JDBCConnector {

    /**
     * Ejecuta un bloque de codigo contra una conexion.
     */
    fun <T> execute(bloque: (Connection)->T): T {
        val connection = openConnection()
        return connection.use(bloque)
    }

    /**
     * Establece una conexion a la url especificada
     *
     * @return la conexion establecida
     */
    private fun openConnection(): Connection {
        val env = System.getenv()
        val user = env.getOrDefault("USER", "root")
        val password = env.getOrDefault("PASSWORD", "root")
        val host = env.getOrDefault("HOST", "localhost")
        val dataBase = env.getOrDefault("DATA_BASE", "epers_ejemplo_jdbc")

        return try {
            val url = "jdbc:mysql://$host:3306/$dataBase?user=$user&password=$password&useSSL=false&serverTimezone=UTC&createDatabaseIfNotExist=true"
            DriverManager.getConnection(url)
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
}