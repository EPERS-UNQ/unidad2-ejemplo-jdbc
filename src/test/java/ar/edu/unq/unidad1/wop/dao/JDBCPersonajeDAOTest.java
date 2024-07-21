package ar.edu.unq.unidad1.wop.dao;

import ar.edu.unq.unidad1.wop.dao.impl.JDBCPersonajeDAO;
import ar.edu.unq.unidad1.wop.modelo.Item;
import ar.edu.unq.unidad1.wop.modelo.Personaje;
import org.junit.jupiter.api.*;

import java.util.HashSet;

import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

@TestInstance(PER_CLASS)
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
        Assertions.assertEquals(maguito.nombre(), otroMaguito.nombre());
        Assertions.assertEquals(maguito.pesoMaximo(), otroMaguito.pesoMaximo());
        Assertions.assertEquals(maguito.vida(), otroMaguito.vida());
        Assertions.assertEquals(maguito.xp(), otroMaguito.xp());
        //		assertEquals(this.maguito.getInventario().size(), otroMaguito.getInventario().size());

        //Pero no son el mismo objeto =(
        //A esto nos referimos con "perdida de identidad"
        Assertions.assertNotEquals(maguito, otroMaguito);
    }


    @AfterEach
    void emilinarModelo() {
        dao.eliminar(maguito);
    }
}