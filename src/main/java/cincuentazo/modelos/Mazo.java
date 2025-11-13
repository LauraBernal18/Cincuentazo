package cincuentazo.modelos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

/**
 * Representa la baraja de cartas utilizada en el juego del "Cincuentazo".
 * <p>
 * Esta clase gestiona todas las operaciones con las cartas, incluyendo la creación de la baraja,
 * barajar, robar cartas, reciclar cartas de la mesa,
 * y añadir cartas de nuevo a la baraja.
 * </p>
 *
 * @author Hilary Herrera, Dana Gómez, Laura Bernal
 * @version 1.3
 * @since 2025
 * @see Carta
 * @see Mesa
 */
public class Mazo {
    private ArrayList<Carta> cartasMazo;

    /** Referencia a la tabla de juego, utilizada al reciclar cartas. */
    private Mesa mesa;


    /**
     * Crea una nueva instancia de {@code Mazo} vinculada a un {@link Mesa} específico.
     * Crea automáticamente una baraja estándar de 52 cartas y la baraja.
     *
     * @param mesa el objeto {@link Mesa} asociado a esta baraja.
     */
    public Mazo(Mesa mesa) {
        this.mesa = mesa;
        cartasMazo = new ArrayList<>();
        crearMazo();
        barajar();
    }


    /**
     * Crea la baraja estándar de 52 cartas utilizada en el juego.
     * <p>Incluye los 13 valores ("A" a "K") de cada uno de los 4 palos:
     * Trébol ("T"), Pica ("P"), Diamante ("D") y Corazón ("C").</p>
     */
    private void crearMazo() {
        String[] palos = {"T", "P", "D", "C"};
        String[] valores = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};

        // Recorremos cada palo usando un ciclo for con índice
        for (int i = 0; i < palos.length; i++) {
            String palo = palos[i];  // Obtenemos el palo actual

            // Para cada palo, recorremos todos los valores con otro for con índice
            for (int j = 0; j < valores.length; j++) {
                String valor = valores[j];  // Obtenemos el valor actual

                // Creamos una nueva carta con valor y palo y la añadimos al mazo
                cartasMazo.add(new Carta(valor, palo));
            }
        }
    }

    /**
     * Baraja aleatoriamente la baraja usando {@link Collections#shuffle(List)}.
     */
    public void barajar() {
        Collections.shuffle(cartasMazo); // se puede hacer manualmente como el sudoku
    }


    /**
     * Extrae (retira y devuelve) la primera carta del mazo.
     * <p>
     * Si el mazo está vacío, intentará reciclar cartas de la mesa.
     * </p>
     *
     * @return la {@link Carta} extraída, o {@code null} si no hay cartas disponibles.
     */
    public Carta tomarCarta() {
            if (cartasMazo.isEmpty()) {
                reiniciarConCartasDeMesa();
            }

            if (!cartasMazo.isEmpty()) {
                return cartasMazo.remove(0); // toma la primera carta
            }

            return null; // si sigue vacío, no hay cartas
    }


    /**
     * Repone la baraja tomando cartas de la mesa,
     * dejando visible solo la última carta jugada.
     * <p>Una vez movidas las cartas, la baraja se vuelve a barajar.</p>
     */
    private void reiniciarConCartasDeMesa() {
        Stack<Carta> cartasMesa = mesa.getCartasEnMesa();

        if (cartasMesa == null || cartasMesa.size() <= 1) {
            return;
            //Si en la mesa no hay cartas para tomar paila
        }

        // Pasar todas las demás al mazo
        while (cartasMesa.size() > 1) {
            cartasMazo.add(cartasMesa.pop());
        }

        // Barajar el mazo y dejar la mesa limpia con su última carta
        barajar();
        mesa.reiniciarMesaDejandoUltima();
    }


    /**
     * Returns an array containing all valid suits (palos) used in the deck.
     *
     * @return an array of strings representing the suits: {"T", "P", "D", "C"}.
     */
    //acceder a los palos de las cartas
    public static String[] getPalos() {
        return new String[]{"T", "P", "D", "C"};
    }


    /**
     * Adds a list of cards to the bottom of the deck.
     * <p>Typically used when a player is eliminated, and their cards
     * must be returned to the deck.</p>
     *
     * @param cartas the list of {@link Carta} objects to add to the deck.
     */
    public void agregarCartasAlFinal(List<Carta> cartas) {
        cartasMazo.addAll(cartas);
    }

}

