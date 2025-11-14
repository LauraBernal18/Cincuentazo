package cincuentazo.modelos;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Clase de pruebas unitarias para {@link JugadorMaquina}.
 * <p>
 * Esta clase verifica el comportamiento del jugador controlado por la máquina,
 * evaluando la selección automática de cartas, el manejo de errores, la lógica
 * de eliminación y la correcta generación del nombre.
 * </p>
 * @author Hilary Herrera, Dana Gómez, Laura Bernal
 * @version 1.4
 * @since 2025
 * @see JugadorMaquina
 * @see Carta
 */
class JugadorMaquinaTest {
    private JugadorMaquina maquina;
    private Carta cartaValida;
    private Carta cartaInvalida;


    /**
     * Configura el entorno inicial antes de cada prueba.
     * <p>
     * Se crean dos cartas (una válida y una inválida) y se agregan a la mano
     * del jugador máquina. Además, se inicializa la instancia de
     * {@link JugadorMaquina}.
     * </p>
     */
    @BeforeEach
    void setUp() {
        maquina = new JugadorMaquina("Maquina test");

        // el valor de las cartas se obtiene con getValorSegunReglas() y devuelve un número según el tipo de carta
        cartaValida = new Carta("5", "Corazones");  // esta suma no pasará de 50
        cartaInvalida = new Carta("K", "Tréboles"); // resta 10 según las reglas

        maquina.recibirCarta(cartaValida);
        maquina.recibirCarta(cartaInvalida);
    }

    /**
     * Verifica que el nombre del jugador máquina sea generado automáticamente
     * siguiendo el formato esperado, que comienza con la palabra “Máquina ”.
     */
    @Test
    void testNombreSeGeneraAutomaticamente() {
        assertTrue(maquina.getNombre().startsWith("Máquina "));
    }


    /**
     * Verifica que el método {@link JugadorMaquina#esperarJugador()} no lance
     * ninguna excepción durante su ejecución.
     * <p>
     * Este método simula el tiempo de decisión de la inteligencia artificial,
     * por lo que debe ejecutarse sin errores.
     * </p>
     */
    @Test
    void testEsperarJugadorNoLanzaExcepcion() {
        //se verifica que no lance error al ejecutar el pensamiento de la maquina
        assertDoesNotThrow(() -> maquina.esperarJugador());
    }


    /**
     * Comprueba que la máquina seleccione una carta válida según la suma actual
     * de la mesa.
     * <p>
     * La carta seleccionada nunca debe hacer que la suma total del juego
     * sobrepase el límite de 50.
     * </p>
     */
    @Test
    void testSeleccionarCartaAJugarDevuelveUnaValida() {
        int sumaMesa = 20; // suma actual de la mesa

        Carta seleccionada = maquina.seleccionarCartaAJugar(sumaMesa);

        assertNotNull(seleccionada, "La maquina debe seleccionar una carta válida si hay disponibles");
        assertTrue(seleccionada.getValorSegunReglas(sumaMesa) + sumaMesa <= 50, "La carta seleccionada no debe hacer que la suma pase de 50");
    }


    /**
     * Verifica el comportamiento de la máquina cuando no tiene ninguna carta
     * válida para jugar.
     * <p>
     * En este caso:
     * <ul>
     *     <li>{@code seleccionarCartaAJugar()} debe devolver {@code null}.</li>
     *     <li>La máquina debe quedar eliminada del juego.</li>
     * </ul>
     * </p>
     */
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


    /**
     * Verifica que, cuando la máquina tiene múltiples cartas válidas, seleccione
     * una de ellas correctamente.
     * <p>
     * Se agregan varias cartas adicionales y se confirma que:
     * <ul>
     *     <li>La carta seleccionada no sea {@code null}.</li>
     *     <li>La carta seleccionada pertenezca realmente a la mano de la máquina.</li>
     * </ul>
     * </p>
     */
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