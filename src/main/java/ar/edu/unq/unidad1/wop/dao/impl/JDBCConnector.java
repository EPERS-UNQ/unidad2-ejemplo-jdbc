package ar.edu.unq.unidad1.wop.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.function.Function;

public class JDBCConnector {

    private static JDBCConnector INSTANCE = null;
    private JDBCConnector() {}

    public static JDBCConnector getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new JDBCConnector();
            return INSTANCE;
        }
        return INSTANCE;
    }
    /**
     * Ejecuta un bloque de codigo contra una conexion.
     */
    public <T> T execute(Function<Connection, T> bloque) {
        final var connection = openConnection();
        try {
            return bloque.apply(connection);
        } catch(Exception e) {
            throw new RuntimeException(e);
        } finally {
            closeConnection(connection);
        }
    }


    /**
     * Establece una conexion a la url especificada
     *
     * @return la conexion establecida
     */
    private Connection openConnection() {
        final var env = System.getenv();
        final var user = "root";
        final var password = "root";
        final var host = "localhost";
        final var dataBase = "epers_ejemplo_jdbc";
        final var url = env.getOrDefault(
                "SQL_URL", String.format(
                        "jdbc:mysql://%s:3306/%s?user=%s&password=%s&useSSL=false&serverTimezone=UTC&createDatabaseIfNotExist=true",
                        host, dataBase, user, password)
        );

        try {
            return DriverManager.getConnection(url);
        } catch (SQLException e) {
            throw new RuntimeException("No se puede establecer una conexion. Revisar si el servidor SQL esta corriendo.", e);
        }
    }

    /**
     * Cierra una conexion con la base de datos (libera los recursos utilizados por la misma)
     *
     * @param connection - la conexion a cerrar.
     */
    private void closeConnection(Connection connection) {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException("Error al cerrar la conexion", e);
        }
    }
}