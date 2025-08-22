package ar.edu.unq.unidad1.wop.dao;

import ar.edu.unq.unidad1.wop.dao.impl.JDBCPersonajeDAO;
import ar.edu.unq.unidad1.wop.modelo.Item;
import ar.edu.unq.unidad1.wop.modelo.Personaje;
import ar.edu.unq.unidad1.wop.service.PersonajeService;
import ar.edu.unq.unidad1.wop.service.impl.PersonajeServiceImpl;
import org.junit.jupiter.api.*;

import java.util.HashSet;

import static java.util.logging.LogManager.getLogManager;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class JDBCPersonajeServiceTest {
    private PersonajeDAO dao = new JDBCPersonajeDAO();
    private PersonajeService service = new PersonajeServiceImpl(dao);
    private Personaje maguito;

//    Descomentar para ver logs.

@BeforeAll
static void setupLogging() {
    try {
        // Cargamos la configuración de logging
        getLogManager().readConfiguration(
            JDBCPersonajeServiceTest.class.getClassLoader().getResourceAsStream("logging.properties")
        );
    } catch (Exception e) {
        System.err.println("No se pudo cargar la configuración de logging: " + e.getMessage());
    }
}

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
        service.guardar(maguito);

        //Los personajes son iguales
        var otroMaguito = service.recuperar("Maguito");
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
        service.eliminar(maguito);
    }
}