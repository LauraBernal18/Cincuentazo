package cincuentazo.modelos;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa a un jugador genérico en el juego de cartas Cincuentazo.
 * <p>
 * Esta clase abstracta proporciona los atributos y comportamientos compartidos
 * tanto para jugadores humanos como para jugadores controlados por computadora.
 * Cada jugador tiene un nombre, una mano de cartas y un estado de eliminación.
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
    protected boolean eliminado;


    /**
     * Crea un nuevo jugador con el nombre dado.
     * La mano comienza vacía y el jugador está activo (no eliminado).
     *
     * @param nombre el nombre asignado al jugador.
     */
    public Jugador(String nombre) {
        this.nombre = nombre;
        this.mano = new ArrayList<>();
        this.eliminado = false;
    }

    /**
     * Devuelve la lista de cartas que el jugador tiene actualmente en la mano.
     *
     * @return una {@link List} que contiene las cartas en la mano.
     */
    public ArrayList<Carta> getMano() {
        return mano;
    }

    /**
     * Obtiene el nombre del jugador.
     *
     * @return el nombre del jugador como un {@link String}.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Comprueba si el jugador ha sido eliminado.
     *
     * @return {@code true} si el jugador ha sido eliminado, {@code false} en caso contrario.
     */
    public boolean esEliminado() {
        return eliminado;
    }


    /**
     * Establece el estado de eliminación del jugador.
     *
     * @param eliminado {@code true} si el jugador debe ser marcado como eliminado,
     * {@code false} en caso contrario.
     */
    public void setEliminado(boolean eliminado) {
        this.eliminado = eliminado;
    }

    /**
     * Actualiza el nombre del jugador.
     *
     * @param nombre el nuevo nombre que se asignará al jugador.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    /**
     * Agrega una nueva carta a la mano del jugador.
     *
     * @param carta la {@link Carta} que se agregará a la mano.
     */
    public void recibirCarta(Carta carta) {
        mano.add(carta);
    }

    /**
     * Retira todas las cartas de la mano del jugador y las marca como eliminadas.
     * <p>
     * Este método se suele llamar cuando el jugador ya no puede continuar en la partida.
     * </p>
     */
    public void limpiarMano() {
        mano.clear();    // Elimina todas las cartas de la mano
        eliminado = true; // Marca al jugador como eliminado
    }

    /**
     * Define la lógica para seleccionar una carta para jugar durante un turno.
     * <p>
     * Cada subclase (humana o IA) debe implementar su propia lógica de selección
     * basada en la suma actual de la mesa.
     * </p>
     *
     * @param sumaMesa el valor total actual de las cartas en la mesa.
     * @return la {@link Carta} seleccionada para jugar.
     */
    public abstract Carta seleccionarCartaAJugar(int sumaMesa);


    /**
     * Juega la carta especificada si está en la mano del jugador.
     * <p>
     * La carta se retira de la mano. Si el jugador se queda sin cartas,
     * se marcan automáticamente como eliminadas.
     * </p>
     *
     * @param carta la {@link Carta} que el jugador desea jugar.
     * @return la misma carta si se jugó correctamente, o {@code null} si no fue válida.
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
