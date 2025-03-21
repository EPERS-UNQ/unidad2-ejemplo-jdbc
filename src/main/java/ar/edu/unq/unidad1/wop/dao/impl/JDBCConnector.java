package ar.edu.unq.unidad1.wop.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.function.Function;

import static java.util.logging.Level.*;
import static java.util.logging.Logger.getLogger;

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

        createDatabaseIfNotExists(dataBase, user, password, host, port);

        final var url = String.format(
                "jdbc:postgresql://%s:%s/%s?loggerLevel=DEBUG&loggerFile=postgresql.log",
                host, port, dataBase
        );

        getLogger("org.postgresql").setLevel(FINEST);

        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new RuntimeException("No se puede establecer una conexión. Revisar si el servidor PostgreSQL está corriendo.", e);
        }
    }

    private void createDatabaseIfNotExists(String databaseName, String user, String password, String host, String port) {
        String url = String.format("jdbc:postgresql://%s:%s/postgres", host, port);
        try (var connection = DriverManager.getConnection(url, user, password);
             var statement = connection.createStatement()) {

            String checkDbQuery = "SELECT 1 FROM pg_database WHERE datname = '" + databaseName + "'";
            var resultSet = statement.executeQuery(checkDbQuery);
            if (!resultSet.next()) {
                statement.executeUpdate("CREATE DATABASE " + databaseName);
                getLogger("JDBCConnector").info("Base de datos creada: " + databaseName);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al verificar/crear la base de datos", e);
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