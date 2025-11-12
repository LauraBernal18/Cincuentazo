package cincuentazo.models;

/**
 * Represents a human player in the Cincuentazo card game.
 * <p>
 * This class extends {@link Jugador} and allows manual interaction with the player,
 * such as selecting cards and choosing the value of an Ace (A).
 * </p>
 *
 * @author Hilary Herrera, Dana Gómez, Laura Bernal
 * @version 1.2
 * @since 2025
 * @see Jugador
 * @see Carta
 */

public class JugadorHumano extends Jugador {
    private Carta cartaSeleccionada;

    /**
     * Constructs a human player with the given name.
     *
     * @param nombre the name of the human player.
     */
    public JugadorHumano(String nombre) {
        super(nombre);
    }


    /**
     * Manually selects a card from the player's hand.
     * <p>
     * This method is typically called when the user clicks a card in the GUI.
     * </p>
     *
     * @param cartaSeleccionada the {@link Carta} chosen by the player.
     */
    public void seleccionarCartaManual(Carta cartaSeleccionada) {
        this.cartaSeleccionada = cartaSeleccionada;
    }


    /**
     * Allows the player to choose the value of an Ace (A) card.
     * <p>
     * If the selected card is an Ace, its value can be set to 1 or 10 depending on
     * the player's decision. This choice is stored inside the card itself.
     * </p>
     *
     * @param valorElegido the value chosen for the Ace (must be {@code 1} or {@code 10}).
     */
    //si la carta seleccionada es un AS y no es nula llama al metodo de la carta para guardar la elección
    public void elegirValorAs(int valorElegido) {
        if (cartaSeleccionada != null && cartaSeleccionada.identificarAS()) {
            cartaSeleccionada.setValorAsElegido(valorElegido);
        }
    }


    /**
     * Returns the card currently selected by the player to play during their turn.
     * <p>
     * This method overrides {@link Jugador#seleccionarCarta(int)} and simply
     * returns the card that was manually chosen via the interface.
     * </p>
     *
     * @param sumaMesa the current total value of the cards on the table (not used for human selection).
     * @return the {@link Carta} selected by the human player, or {@code null} if no card is selected.
     */
    @Override
    public Carta seleccionarCarta(int sumaMesa) {
        return cartaSeleccionada;
    }
}
