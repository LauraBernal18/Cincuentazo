package cincuentazo.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a generic player in the Cincuentazo card game.
 * <p>
 * This abstract class provides the shared attributes and behaviors
 * for both human and computer-controlled players.
 * Each player has a name, a hand of cards, and an elimination status.
 * </p>
 *
 * @author Hilary Herrera, Dana Gómez, Laura Bernal
 * @version 1.3
 * @since 2025
 * @see Carta
 * @see JugadorHumano
 * @see JugadorMaquina
 */
public abstract class Jugador {

    protected String nombre;
    protected ArrayList<Carta> mano;

    /** Indicates whether the player has been eliminated from the game. */
    protected boolean eliminado;


    /**
     * Constructs a new player with the given name.
     * The hand starts empty, and the player is active (not eliminated).
     *
     * @param nombre the name assigned to the player.
     */
    public Jugador(String nombre) {
        this.nombre = nombre;
        this.mano = new ArrayList<>();
        this.eliminado = false;
    }

    /**
     * Returns the list of cards currently in the player's hand.
     *
     * @return a {@link List} containing the cards in hand.
     */
    public ArrayList<Carta> getMano() {
        return mano;
    }

    /**
     * Gets the player's name.
     *
     * @return the name of the player as a {@link String}.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Checks whether the player has been eliminated.
     *
     * @return {@code true} if the player is eliminated, {@code false} otherwise.
     */
    public boolean esEliminado() {
        return eliminado;
    }


    /**
     * Sets the elimination status of the player.
     *
     * @param eliminado {@code true} if the player should be marked as eliminated,
     *                  {@code false} otherwise.
     */
    public void setEliminado(boolean eliminado) {
        this.eliminado = eliminado;
    }

    /**
     * Updates the player's name.
     *
     * @param nombre the new name to assign to the player.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    /**
     * Adds a new card to the player's hand.
     *
     * @param carta the {@link Carta} to be added to the hand.
     */
    public void recibirCarta(Carta carta) {
        mano.add(carta);
    }

    /**
     * Removes all cards from the player's hand and marks them as eliminated.
     * <p>
     * This method is typically called when the player can no longer continue in the game.
     * </p>
     */
    public void limpiarMano() {
        mano.clear();    // Elimina todas las cartas de la mano
        eliminado = true; // Marca al jugador como eliminado
    }

    /**
     * Defines the logic for selecting a card to play during a turn.
     * <p>
     * Each subclass (human or AI) must implement its own selection logic
     * based on the current table sum.
     * </p>
     *
     * @param sumaMesa the current total value of the cards on the table.
     * @return the {@link Carta} selected to be played.
     */
    public abstract Carta seleccionarCartaAJugar(int sumaMesa);


    /**
     * Plays the specified card if it is in the player's hand.
     * <p>
     * The card is removed from the hand. If the player runs out of cards,
     * they are automatically marked as eliminated.
     * </p>
     *
     * @param carta the {@link Carta} the player wishes to play.
     * @return the same card if successfully played, or {@code null} if it was invalid.
     */
    public Carta jugarCarta(Carta carta) {
        if (carta != null && mano.remove(carta)) {
            if (mano.isEmpty()) { // actualiza el estado si se queda sin cartas
                setEliminado(true);
            }
            return carta;
        }
        return null; // no pudo jugar (mano vacía)
    }
}
