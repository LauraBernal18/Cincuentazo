package cincuentazo.modelos;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Clase de pruebas unitarias para la clase {@link JugadorHumano}.
 * <p>
 * Esta clase verifica el correcto funcionamiento de los métodos de {@link JugadorHumano},
 * incluyendo la selección de cartas, el manejo del As, la eliminación del jugador
 * y la interacción con la mano de cartas.
 * </p>
 *
 * <p>Se utilizan anotaciones de JUnit 5:</p>
 * <ul>
 *     <li>{@link BeforeEach} para inicializar el entorno de prueba antes de cada test.</li>
 *     <li>{@link Test} para marcar los métodos de prueba individuales.</li>
 * </ul>
 *
 * <p>Se verifica tanto comportamiento esperado como situaciones límite.</p>
 *
 * @author Hilary Herrera, Dana Gómez, Laura Bernal
 * @version 1.3
 * @since 2025
 * @see JugadorHumano
 * @see Carta
 */
class JugadorHumanoTest {
    private JugadorHumano jugador;
    private Carta cartaNormal;
    private Carta cartaAs;


    /**
     * Inicializa el entorno de pruebas antes de cada test.
     * <p>
     * Se crea un {@link JugadorHumano} llamado "Test" y se le reparten
     * dos cartas: una normal "5-C" y un As "A-D".
     * </p>
     */
    //Configura el entorno antes de cada prueba, crear un jugador humano con dos cartas: una normal 5-C y un As
    @BeforeEach
    void setUp() {
        jugador = new JugadorHumano("Test");
        cartaNormal = new Carta("5", "C");
        cartaAs = new Carta("A", "D");
        jugador.recibirCarta(cartaNormal);
        jugador.recibirCarta(cartaAs);
    }


    // ======================= PRUEBAS =========================

    /**
     * Verifica que el jugador pueda seleccionar una carta disponible para jugar.
     * <p>
     * Se selecciona manualmente una carta de la mano y se comprueba que
     * {@link JugadorHumano#seleccionarCartaAJugar(int)} devuelva la misma carta.
     * </p>
     */
    //el método seleccionarCartaAJugar debe devolver esa misma carta.
    @Test
    void testSeleccionarCarta_DisponibleParaJugar() {
        jugador.seleccionarCartaManual(cartaNormal);
        assertEquals(cartaNormal, jugador.seleccionarCartaAJugar(10));
    }


    /**
     * Verifica el comportamiento cuando el jugador no ha seleccionado ninguna carta.
     * <p>
     * En este caso, {@link JugadorHumano#seleccionarCartaAJugar(int)} debe devolver {@code null}.
     * Esto simula que el jugador aún no ha tomado ninguna decisión.
     * </p>
     */
    //En este caso, el método seleccionarCartaAJugar debe devolver null,
    @Test
    void testNoSeleccion_NoHayCartaParaJugar() {
        assertNull(jugador.seleccionarCartaAJugar(10));
    }


    /**
     * Verifica que el valor del As se asigne correctamente.
     * <p>
     * Se selecciona un As y se elige jugarlo con valor 10.
     * Luego se comprueba que {@link Carta#getValorSegunReglas(int)} devuelva 10.
     * </p>
     */
    @Test
    void testElegirValorAs_AsignarValorCorrectamente() {
        jugador.seleccionarCartaManual(cartaAs);
        jugador.elegirValorAs(10);
        assertEquals(10, cartaAs.getValorSegunReglas(0));
    }

    /**
     * Verifica que al jugar una carta, esta se elimine correctamente de la mano.
     * <p>
     * {@link JugadorHumano#jugarCarta(Carta)} debe devolver la carta jugada
     * y removerla de la lista de cartas en mano.
     * </p>
     */
    @Test
    void testJugarCarta_RemoverDeLaMano() {
        jugador.seleccionarCartaManual(cartaNormal);
        Carta jugada = jugador.jugarCarta(cartaNormal);

        assertEquals(cartaNormal, jugada);
        assertFalse(jugador.getMano().contains(cartaNormal));
    }


    /**
     * Verifica el comportamiento al intentar jugar una carta que no está en la mano.
     * <p>
     * Si el jugador intenta jugar una carta inexistente,
     * {@link JugadorHumano#jugarCarta(Carta)} debe devolver {@code null}.
     * </p>
     */
    //Si el jugador intenta jugar una carta inexistente, método jugarCarta debe devolver null e indica
    //que no se realizaron acciones
    @Test
    void testCartaNoEstaEnMano_RetornaNull() {
        Carta cartaNoEnMano = new Carta("8", "T");
        assertNull(jugador.jugarCarta(cartaNoEnMano));
    }



    /**
     * Verifica que el jugador sea marcado como eliminado al quedarse sin cartas.
     * <p>
     * Después de jugar todas sus cartas, {@link JugadorHumano#esEliminado()} debe devolver {@code true},
     * indicando que el jugador ya no puede seguir participando.
     * </p>
     */
    @Test
    void testSinCartas_JugadorEsEliminado() {
        jugador.jugarCarta(cartaNormal);
        jugador.jugarCarta(cartaAs);
        assertTrue(jugador.esEliminado());
    }
}