package cincuentazo.models;

import java.util.Stack;


/**
 * Represents the game table ("Mesa") where cards are played and the current sum is tracked.
 * <p>
 * This class manages the stack of played cards, calculates the accumulated sum
 * according to the game rules, and provides methods to reset or inspect the table's state.
 * </p>
 *
 * @author Hilary Herrera, Dana Gómez, Laura Bernal
 * @version 1.4
 * @since 2025
 * @see Carta
 * @see Mazo
 */
public class Mesa {

    /** Stack that stores all cards played on the table in order. */
    private Stack<Carta> cartasEnMesa;  // Pila de cartas jugadas

    /** The current total sum of all cards played on the table. */
    private int sumaActual;             // Suma acumulada total

    /**
     * Constructs a new, empty table with no cards and a total sum of 0.
     */
    public Mesa() {
        cartasEnMesa = new Stack<>();
        sumaActual = 0;
    }


    /**
     * Places a new card on the table and updates the accumulated total according to game rules.
     * <p>
     * The card’s value is obtained through {@link Carta#getValorSegunReglas(int)},
     * and then added to the current total sum.
     * </p>
     *
     * @param carta the {@link Carta} to be placed on the table. If {@code null}, no action is taken.
     * @see Carta#getValorSegunReglas(int)
     */
    // Coloca una carta sobre la mesa y actualiza la suma acumulada correctamente
    public void colocarCartaEnMesa(Carta carta) {
        if (carta == null)
            return;

        cartasEnMesa.push(carta); // agrega al tope
        int valorCarta = carta.getValorSegunReglas(sumaActual);
        sumaActual += valorCarta; // suma acumulada correcta
    }


    /**
     * Returns the current total sum of all cards played on the table.
     *
     * @return the current accumulated sum as an integer.
     */
    // Devuelve la suma actual de la mesa
    public int getSumaActual() {
        return sumaActual;
    }


    /**
     * Retrieves the most recently played card (top of the stack) without removing it.
     *
     * @return the top {@link Carta} on the table, or {@code null} if the table is empty.
     */
    // Devuelve la carta visible (última jugada)
    public Carta getUltimaCarta() {
        if (cartasEnMesa.isEmpty())
            return null;

        return cartasEnMesa.peek();
    }

    /**
     * Provides access to the stack of all cards currently on the table.
     * <p>
     * This is typically used by {@link Mazo} when recycling cards
     * after a player exceeds the limit or during deck refresh.
     * </p>
     *
     * @return the {@link Stack} containing all cards played on the table.
     */
    // Devuelve todas las cartas (para reciclarlas si alguien se pasa de 50)
    public Stack<Carta> getCartasEnMesa() {
        return cartasEnMesa;
    }


    /**
     * Resets the table by keeping only the last played card visible
     * and maintaining the current accumulated total.
     * <p>
     * Useful when the deck is replenished from the table
     * and one card must remain visible for the next round.
     * </p>
     */
    // Deja solo la última carta sobre la mesa, manteniendo la suma acumulada
    public void reiniciarMesaDejandoUltima() {
        if (cartasEnMesa.size() <= 1)
            return;

        Carta ultima = cartasEnMesa.peek();
        cartasEnMesa.clear();
        cartasEnMesa.push(ultima);
        //  No reiniciamos sumaActual: se mantiene igual
    }


}
