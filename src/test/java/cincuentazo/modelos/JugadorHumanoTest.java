package cincuentazo.modelos;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JugadorHumanoTest {
    private JugadorHumano jugador;
    private Carta cartaNormal;
    private Carta cartaAs;


     //Configura el entorno antes de cada prueba, crear un jugador humano con dos cartas: una normal 5-C y un As
    @BeforeEach
    void setUp() {
        jugador = new JugadorHumano("Test");
        cartaNormal = new Carta("5", "C");
        cartaAs = new Carta("A", "D");
        jugador.recibirCarta(cartaNormal);
        jugador.recibirCarta(cartaAs);
    }


    //Verifica que el jugador pueda seleccionar una carta correctamente.

    //Después de que el jugador selecciona manualmente una carta de su mano,
    //el método seleccionarCartaAJugar debe devolver esa misma carta.
    @Test
    void testSeleccionarCarta_DisponibleParaJugar() {
        jugador.seleccionarCartaManual(cartaNormal);
        assertEquals(cartaNormal, jugador.seleccionarCartaAJugar(10));
    }

    //comportamiento cuando el jugador no ha seleccionado ninguna carta.
    //En este caso, el método seleccionarCartaAJugar debe devolver null,
    @Test
    void testNoSeleccion_NoHayCartaParaJugar() {
        assertNull(jugador.seleccionarCartaAJugar(10));
    }


    //Verifica que el valor del As se asigne correctamente
    //El jugador selecciona un As y elige jugarlo con valor 10 y se comprueba que getValorSegunReglas devuelva 10,
    @Test
    void testElegirValorAs_AsignarValorCorrectamente() {
        jugador.seleccionarCartaManual(cartaAs);
        jugador.elegirValorAs(10);
        assertEquals(10, cartaAs.getValorSegunReglas(0));
    }


    //Verifica que al jugar una carta, esta se elimine correctamente de la mano del jugador.
    //jugarCarta debe devolver la carta jugada y removerla de la lista de cartas en mano.
    @Test
    void testJugarCarta_RemoverDeLaMano() {
        jugador.seleccionarCartaManual(cartaNormal);
        Carta jugada = jugador.jugarCarta(cartaNormal);

        assertEquals(cartaNormal, jugada);
        assertFalse(jugador.getMano().contains(cartaNormal));
    }

    //Verifica el comportamiento al intentar jugar una carta que no está en la mano.
    //Si el jugador intenta jugar una carta inexistente, método jugarCarta debe devolver null e indica
    //que no se realizaron acciones
    @Test
    void testCartaNoEstaEnMano_RetornaNull() {
        Carta cartaNoEnMano = new Carta("8", "T");
        assertNull(jugador.jugarCarta(cartaNoEnMano));
    }


    //Verifica que el jugador sea marcado como eliminado al quedarse sin cartas.
    //Después de jugar todas sus cartas, métpdp esEliminado devuelve true, el jugador ya no puede seguir participando
    @Test
    void testSinCartas_JugadorEsEliminado() {
        jugador.jugarCarta(cartaNormal);
        jugador.jugarCarta(cartaAs);
        assertTrue(jugador.esEliminado());
    }
}