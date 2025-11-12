package cincuentazo.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

/**
 * Represents the deck of cards used in the "Cincuentazo" game.
 * <p>
 * This class manages all card operations, including creating the deck,
 * shuffling, drawing cards, recycling cards from the table (mesa),
 * and adding cards back into the deck.
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

    /** Reference to the game table, used when recycling cards. */
    private Mesa mesa;


    /**
     * Constructs a new {@code Mazo} instance linked to a specific {@link Mesa}.
     * It automatically creates a standard deck of 52 cards and shuffles it.
     *
     * @param mesa the {@link Mesa} object associated with this deck.
     */
    public Mazo(Mesa mesa) {
        this.mesa = mesa;
        cartasMazo = new ArrayList<>();
        crearMazo();
        barajar();
    }


    /**
     * Creates the standard deck of 52 cards used in the game.
     * <p>Includes all 13 values ("A" to "K") for each of the 4 suits:
     * Trebol ("T"), Pica ("P"), Diamante ("D"), and Corazón ("C").</p>
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
     * Randomly shuffles the deck using {@link Collections#shuffle(List)}.
     */
    public void barajar() {
        Collections.shuffle(cartasMazo); // se puede hacer manualmente como el sudoku
    }


    /**
     * Draws (removes and returns) the top card from the deck.
     * <p>
     * If the deck is empty, it will attempt to recycle cards from the table (mesa).
     * </p>
     *
     * @return the drawn {@link Carta}, or {@code null} if there are no cards available.
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
     * Refills the deck by taking cards from the table (mesa),
     * leaving only the last played card visible.
     * <p>Once cards are moved, the deck is reshuffled.</p>
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

