package cincuentazo.modelos;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Stack;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Clase de pruebas unitarias para {@link Mesa}.
 * <p>
 * Esta clase verifica el comportamiento de la mesa de juego, asegurando que
 * las cartas se coloquen correctamente, que la suma total de la mesa se
 * actualice según las reglas, y que la manipulación de la pila de cartas
 * funcione adecuadamente.
 * </p>
 * @author Hilary Herrera, Dana Gómez, Laura Bernal
 * @version 1.3
 * @since 2025
 * @see Mesa
 */
class MesaTest {
    private Mesa mesa;

    /**
     * Configura el entorno inicial antes de cada prueba.
     * <p>
     * Se crea una nueva instancia de {@link Mesa} para garantizar que cada
     * prueba se ejecute en un estado limpio e independiente.
     * </p>
     */
    @BeforeEach
    void setUp() {
        mesa = new Mesa();  // Se crea una mesa nueva antes de cada prueba
    }


    /**
     * Verifica que el método {@link Mesa#colocarCartaEnMesa(Carta)} actualice
     * correctamente la suma total de la mesa según las reglas del juego.
     * <p>
     * Se colocan tres cartas con distintos valores (normal, carta que resta,
     * As con valor ajustable) y se comprueba que la suma refleje la
     * operación correcta.
     * </p>
     */
    @Test
    void colocarCarta_EnMesa_deberiaActualizarSumaCorrectamente() {
        Carta c1 = new Carta("5", "C");
        Carta c2 = new Carta("K", "P"); // vale -10
        Carta c3 = new Carta("A", "T"); // vale 10 si no pasa de 50

        mesa.colocarCartaEnMesa(c1);
        mesa.colocarCartaEnMesa(c2);
        mesa.colocarCartaEnMesa(c3);

        assertEquals(5 - 10 + 10, mesa.getSumaActual());
    }

    /**
     * Comprueba que {@link Mesa#getUltimaCarta()} retorne correctamente la
     * última carta colocada en la mesa.
     * <p>
     * Se colocan dos cartas y se verifica que el método devuelva la última
     * agregada y que sus atributos sean correctos.
     * </p>
     */
    @Test
    void getUltimaCarta_deberiaRetornarLaUltimaColocada() {
        Carta c1 = new Carta("3", "D");
        Carta c2 = new Carta("7", "C");

        mesa.colocarCartaEnMesa(c1);
        mesa.colocarCartaEnMesa(c2);

        assertEquals(c2, mesa.getUltimaCarta());
        assertEquals("7", mesa.getUltimaCarta().getValor());
    }


    /**
     * Verifica que {@link Mesa#reiniciarMesaDejandoUltima()} mantenga únicamente
     * la última carta de la mesa.
     * <p>
     * Se colocan varias cartas, se llama a reiniciarMesaDejandoUltima y se
     * comprueba que:
     * <ul>
     *     <li>Solo quede una carta en la pila.</li>
     *     <li>La última carta sea la misma que antes.</li>
     *     <li>La suma total de la mesa no se vea alterada.</li>
     * </ul>
     * </p>
     */
    @Test
    void reiniciarMesaDejandoUltima_deberiaMantenerSoloUnaCarta() {
        Carta c1 = new Carta("4", "T");
        Carta c2 = new Carta("8", "C");
        Carta c3 = new Carta("K", "P");

        mesa.colocarCartaEnMesa(c1);
        mesa.colocarCartaEnMesa(c2);
        mesa.colocarCartaEnMesa(c3);

        int sumaAntes = mesa.getSumaActual();

        mesa.reiniciarMesaDejandoUltima();

        Stack<Carta> cartas = mesa.getCartasEnMesa();
        assertEquals(1, cartas.size());
        assertEquals(c3, cartas.peek());
        assertEquals(sumaAntes, mesa.getSumaActual()); // la suma no cambia
    }


    /**
     * Verifica que {@link Mesa#getUltimaCarta()} retorne {@code null} si la
     * mesa está vacía.
     */
    @Test
    void getUltimaCarta_deberiaRetornarNullSiMesaVacia() {
        assertNull(mesa.getUltimaCarta());
    }


    /**
     * Comprueba que intentar colocar {@code null} en la mesa mediante
     * {@link Mesa#colocarCartaEnMesa(Carta)} no afecte la pila ni la suma total.
     * <p>
     * Esto asegura que la mesa maneje correctamente entradas nulas sin
     * generar errores.
     * </p>
     */
    @Test
    void colocarCarta_conCartaEnMesaNull_noDebeAfectarMesa() {
        mesa.colocarCartaEnMesa(null);
        assertEquals(0, mesa.getSumaActual());
        assertTrue(mesa.getCartasEnMesa().isEmpty());
    }

}