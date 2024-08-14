package ar.edu.unq.unidad1.wop.dao;

import ar.edu.unq.unidad1.wop.dao.impl.JDBCPersonajeDAO;
import ar.edu.unq.unidad1.wop.modelo.Item;
import ar.edu.unq.unidad1.wop.modelo.Personaje;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JDBCPersonajeDAOTest {
    private PersonajeDAO dao = new JDBCPersonajeDAO();
    private Personaje maguito;

    @BeforeEach
    void crearModelo() {
        maguito = new Personaje("Maguito",
                15,
                2500,
                198,
                new HashSet<>()
                );
        maguito.recoger(new Item("Tunica gris", 1));
        maguito.recoger(new Item("Baculo gris", 5));
    }

    @Test
    void alGuardarYLuegoRecuperarSeObtieneObjetosSimilares() {
        dao.guardar(maguito);

        //Los personajes son iguales
        var otroMaguito = dao.recuperar("Maguito");
        assertEquals(maguito.getNombre(), otroMaguito.getNombre());
        assertEquals(maguito.getPesoMaximo(), otroMaguito.getPesoMaximo());
        assertEquals(maguito.getVida(), otroMaguito.getVida());
        assertEquals(maguito.getXp(), otroMaguito.getXp());
//        assertEquals(this.maguito.getInventario().size(), otroMaguito.getInventario().size());

        //Pero no son el mismo objeto =(
        //A esto nos referimos con "perdida de identidad"
        Assertions.assertNotEquals(maguito, otroMaguito);
    }


    @AfterEach
    void emilinarModelo() {
        dao.eliminar(maguito);
    }
}