package cincuentazo.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Stack;

import static org.junit.jupiter.api.Assertions.*;

class MesaTest {
    private Mesa mesa;

    @BeforeEach
    void setUp() {
        mesa = new Mesa();  // Se crea una mesa nueva antes de cada prueba
    }

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

    @Test
    void getUltimaCarta_deberiaRetornarLaUltimaColocada() {
        Carta c1 = new Carta("3", "D");
        Carta c2 = new Carta("7", "C");

        mesa.colocarCartaEnMesa(c1);
        mesa.colocarCartaEnMesa(c2);

        assertEquals(c2, mesa.getUltimaCarta());
        assertEquals("7", mesa.getUltimaCarta().getValor());
    }

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

    @Test
    void getUltimaCarta_deberiaRetornarNullSiMesaVacia() {
        assertNull(mesa.getUltimaCarta());
    }

    @Test
    void colocarCarta_conCartaEnMesaNull_noDebeAfectarMesa() {
        mesa.colocarCartaEnMesa(null);
        assertEquals(0, mesa.getSumaActual());
        assertTrue(mesa.getCartasEnMesa().isEmpty());
    }

}