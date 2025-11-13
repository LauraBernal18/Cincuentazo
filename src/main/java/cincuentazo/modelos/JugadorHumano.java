package cincuentazo.modelos;

/**
 * Representa a un jugador humano en el juego de cartas Cincuentazo.
 * <p>
 * Esta clase extiende {@link Jugador} y permite la interacción manual con el jugador,
 * como seleccionar cartas y elegir el valor de un As (A).
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
     * Crea un jugador humano con el nombre dado.
     *
     * @param nombre el nombre del jugador humano.
     */
    public JugadorHumano(String nombre) {
        super(nombre);
    }


    /**
     * Selecciona manualmente una carta de la mano del jugador.
     * <p>
     * Este método se llama normalmente cuando el usuario hace clic en una carta en la interfaz gráfica.
     * </p>
     *
     * @param cartaSeleccionada la {@link Carta} elegida por el jugador.
     */
    public void seleccionarCartaManual(Carta cartaSeleccionada) {
        this.cartaSeleccionada = cartaSeleccionada;
    }


    /**
     * Permite al jugador elegir el valor de un As (A).
     * <p>
     * Si la carta seleccionada es un As, su valor puede establecerse en 1 o 10 según
     la decisión del jugador. Esta elección se almacena en la propia carta.
     * </p>
     *
     * @param valorElegido el valor elegido para el As (debe ser {@code 1} o {@code 10}).
     */
    //si la carta seleccionada es un AS y no es nula llama al metodo de la carta para guardar la elección
    public void elegirValorAs(int valorElegido) {
        if (cartaSeleccionada != null && cartaSeleccionada.identificarAS()) {
            cartaSeleccionada.setValorAsElegido(valorElegido);
        }
    }


    /**
     * Devuelve la carta que el jugador ha seleccionado para jugar durante su turno.
     * <p>
     * Este metodo redefine {@link Jugador#seleccionarCartaAJugar(int)} y simplemente
     * devuelve la carta que se seleccionó manualmente a través de la interfaz.
     * </p>
     *
     * @param sumaMesa el valor total actual de las cartas sobre la mesa (no se utiliza para la selección humana).
     * @return la {@link Carta} seleccionada por el jugador, o {@code null} si no se ha seleccionado ninguna carta.
     */
    @Override
    public Carta seleccionarCartaAJugar(int sumaMesa) {
        return cartaSeleccionada;
    }
}
