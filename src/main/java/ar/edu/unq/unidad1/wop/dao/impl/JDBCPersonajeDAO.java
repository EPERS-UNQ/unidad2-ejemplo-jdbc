package ar.edu.unq.unidad1.wop.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import ar.edu.unq.unidad1.wop.dao.PersonajeDAO;
import ar.edu.unq.unidad1.wop.modelo.Personaje;

/**
 * Una implementacion de {@link PersonajeDAO} que persiste
 * en una base de datos relacional utilizando JDBC
 *
 */
public class JDBCPersonajeDAO implements PersonajeDAO {

	public JDBCPersonajeDAO() {
	}

	@Override
	public void guardar(Personaje personaje) {
		this.executeWithConnection(conn -> {
			PreparedStatement ps = conn.prepareStatement("INSERT INTO personaje (nombre, pesoMaximo, xp, vida) VALUES (?,?,?,?)");
			ps.setString(1, personaje.getNombre());
			ps.setInt(2, personaje.getPesoMaximo());
			ps.setInt(3, personaje.getXp());
			ps.setInt(4, personaje.getVida());
			//ojo, no estamos guardando el inventario!
			ps.execute();

			if (ps.getUpdateCount() != 1) {
				throw new RuntimeException("No se inserto el personaje " + personaje);
			}
			ps.close();

			return null;
		});
	}

	@Override
	public Personaje recuperar(String nombre) {
		return this.executeWithConnection(conn -> {
			PreparedStatement ps = conn.prepareStatement("SELECT pesoMaximo, xp, vida FROM personaje WHERE nombre = ?");
			ps.setString(1, nombre);
			
			ResultSet resultSet = ps.executeQuery();

			Personaje personaje = null;
			while (resultSet.next()) {
				//si personaje no es null aca significa que el while dio mas de una vuelta, eso
				//suele pasar cuando el resultado (resultset) tiene mas de un elemento.
				if (personaje != null) {
					throw new RuntimeException("Existe mas de un personaje con el nombre " + nombre);
				}
				
				personaje = new Personaje(nombre);
				personaje.setPesoMaximo(resultSet.getInt("pesoMaximo"));
				personaje.setXp(resultSet.getInt("xp"));
				personaje.setVida(resultSet.getInt("vida"));
			}

			ps.close();
			return personaje;
		});
	}

	/**
	 * Ejecuta un bloque de codigo contra una conexion.
	 */
	private <T> T executeWithConnection(ConnectionBlock<T> bloque) {
		Connection connection = this.openConnection();
		try {
			return bloque.executeWith(connection);
		} catch (SQLException e) {
			throw new RuntimeException("Error no esperado", e);
		} finally {
			this.closeConnection(connection);
		}
	}

	/**
	 * Establece una conexion a la url especificada
	 * @return la conexion establecida
	 */
	private Connection openConnection() {
		try {
			//La url de conexion no deberia estar harcodeada aca
			return DriverManager.getConnection("jdbc:mysql://localhost:3306/epers_ejemplo_jdbc?useSSL=false&user=root&password=root");
		} catch (SQLException e) {
			throw new RuntimeException("No se puede establecer una conexion", e);
		}
	}

	/**
	 * Cierra una conexion con la base de datos (libera los recursos utilizados por la misma)
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
