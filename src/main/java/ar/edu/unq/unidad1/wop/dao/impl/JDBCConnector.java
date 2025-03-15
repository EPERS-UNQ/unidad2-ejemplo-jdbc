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
        T result = null;
        try {
            // Para que no haga commit despues de cada sentencia
            // el driver por defecto hace BEGIN cuando abrimos sesion
            connection.setAutoCommit(false);
            
            // Ejecutamos el bloque
            result = bloque.apply(connection);
            
            // Si llegamos aquí sin excepciones, hacemos commit
            connection.commit();
            return result;
        } catch(Exception e) {
            try {
                // Si hubo algún error, hacemos rollback
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException("Error al hacer rollback de la transacción", ex);
            }
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
        final var user = env.getOrDefault("DB_USER", "postgres");
        final var password = env.getOrDefault("DB_PASSWORD", "root");
        final var host = env.getOrDefault("DB_HOST", "localhost");
        final var dataBase = env.getOrDefault("DB_NAME", "epers_ejemplo_jdbc");
        final var port = env.getOrDefault("DB_PORT", "5432");
        final var url = env.getOrDefault(
                "SQL_URL", String.format(
                        "jdbc:postgresql://%s:%s/%s",
                        host, port, dataBase)
        );

        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new RuntimeException("No se puede establecer una conexion. Revisar si el servidor PostgreSQL esta corriendo.", e);
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