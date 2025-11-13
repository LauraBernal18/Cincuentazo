package cincuentazo.modelos;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JugadorMaquinaTest {
    private JugadorMaquina maquina;
    private Carta cartaValida;
    private Carta cartaInvalida;

    @BeforeEach
    void setUp() {
        maquina = new JugadorMaquina("Maquina test");

        // el valor de las cartas se obtiene con getValorSegunReglas() y devuelve un número según el tipo de carta
        cartaValida = new Carta("5", "Corazones");  // esta suma no pasará de 50
        cartaInvalida = new Carta("K", "Tréboles"); // resta 10 según las reglas

        maquina.recibirCarta(cartaValida);
        maquina.recibirCarta(cartaInvalida);
    }

    @Test
    void testNombreSeGeneraAutomaticamente() {
        assertTrue(maquina.getNombre().startsWith("Máquina "));
    }

    @Test
    void testEsperarJugadorNoLanzaExcepcion() {
        //se verifica que no lance error al ejecutar el pensamiento de la maquina
        assertDoesNotThrow(() -> maquina.esperarJugador());
    }

    @Test
    void testSeleccionarCartaAJugarDevuelveUnaValida() {
        int sumaMesa = 20; // suma actual de la mesa

        Carta seleccionada = maquina.seleccionarCartaAJugar(sumaMesa);

        assertNotNull(seleccionada, "La maquina debe seleccionar una carta válida si hay disponibles");
        assertTrue(seleccionada.getValorSegunReglas(sumaMesa) + sumaMesa <= 50, "La carta seleccionada no debe hacer que la suma pase de 50");
    }

    @Test
    void testSeleccionarCartaAJugarDevuelveNullSiNoHayValidas() {
        // creamos una máquina y le damos que harían pasar de 50
        JugadorMaquina maquinaSinOpciones = new JugadorMaquina("Máquina sin cartas");
        Carta cartaGrande = new Carta("10", "Picas");
        maquinaSinOpciones.recibirCarta(cartaGrande);

        // Supongamos que la suma de la mesa ya está en 49, entonces ninguna carta es válida
        Carta seleccionada = maquinaSinOpciones.seleccionarCartaAJugar(49);

        assertNull(seleccionada, "Debe devolver null si ninguna carta puede jugarse.");
        assertTrue(maquinaSinOpciones.esEliminado(), "La máquina debe quedar eliminada si no puede jugar.");
    }

    @Test
    void testSeleccionarCartaAJugarConVariasOpcionesEligeUna() {
        // Agregamos más cartas válidas para probar selección aleatoria
        maquina.recibirCarta(new Carta("2", "Diamantes"));
        maquina.recibirCarta(new Carta("3", "Picas"));

        Carta seleccionada = maquina.seleccionarCartaAJugar(10);

        assertNotNull(seleccionada);
        assertTrue(maquina.getMano().contains(seleccionada),
                "La carta seleccionada debe ser una de las cartas en la mano.");
    }
}