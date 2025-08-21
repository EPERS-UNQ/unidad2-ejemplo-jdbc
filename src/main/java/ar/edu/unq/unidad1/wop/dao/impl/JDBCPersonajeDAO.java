package ar.edu.unq.unidad1.wop.dao.impl;

import ar.edu.unq.unidad1.wop.dao.PersonajeDAO;
import ar.edu.unq.unidad1.wop.modelo.Personaje;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;

/**
 * Una implementacion de [PersonajeDAO] que persiste
 * en una base de datos relacional utilizando JDBC
 */
public record JDBCPersonajeDAO() implements PersonajeDAO {

    public void guardar(Personaje personaje) {
        JDBCConnector.getInstance().execute(conn  -> {
            try {
                var ps = prepareInsertQueryStatement(personaje, conn);
                return ps.execute();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private static PreparedStatement prepareInsertQueryStatement(Personaje personaje, Connection conn) throws SQLException {
        var ps = conn.prepareStatement("INSERT INTO personaje (nombre, pesoMaximo, xp, vida) VALUES (?,?,?,?)");
        ps.setString(1, personaje.getNombre());
        ps.setInt(2, personaje.getPesoMaximo());
        ps.setInt(3, personaje.getXp());
        ps.setInt(4, personaje.getVida());
        //ojo, no estamos guardando el inventario!
        return ps;
    }

    public Personaje recuperar(String nombre) {
        return JDBCConnector.getInstance().execute( conn -> {
            try {
                var ps = prepareSelectQueryStatement(nombre, conn);
                var resultSet = ps.executeQuery();
                return buildPersonaje(nombre, resultSet);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private static PreparedStatement prepareSelectQueryStatement(String nombre, Connection conn) throws SQLException {
        var ps = conn.prepareStatement("SELECT pesoMaximo, xp, vida FROM personaje WHERE nombre = ?");
        ps.setString(1, nombre);
        return ps;
    }

    private static Personaje buildPersonaje(String nombre, ResultSet resultSet) throws SQLException {
        Personaje personaje = null;
        while (resultSet.next()) {
            //si personaje no es null aca significa que el while dio mas de una vuelta, eso
            //suele pasar cuando el resultado (resultset) tiene mas de un elemento.
            if (personaje != null) {
                throw new RuntimeException(String.format("Existe mas de un personaje con el nombre %s", nombre));
            }
            personaje = new Personaje(nombre,
                    resultSet.getInt("pesoMaximo"),
                    resultSet.getInt("xp"),
                    resultSet.getInt("vida"),
                    new HashSet<>()
            );
        }
        return personaje;
    }

    public void eliminar(Personaje personaje) {
        JDBCConnector.getInstance().execute (conn -> {
            try {
                var ps = prepareDeleteQueryStatement(personaje, conn);
                return ps.execute();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private static PreparedStatement prepareDeleteQueryStatement(Personaje personaje, Connection conn) throws SQLException {
        var ps = conn.prepareStatement("DELETE FROM personaje WHERE nombre =  ? ");
        ps.setString(1, personaje.getNombre());
        return ps;
    }

    public JDBCPersonajeDAO() {
        try {
            var uri = getClass().getClassLoader().getResource("createAll.sql").toURI();
            var initializeScript = Files.readString(Paths.get(uri));
            JDBCConnector.getInstance().execute(conn -> {
                try {
                    var ps = conn.prepareStatement(initializeScript);
                    return ps.execute();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}