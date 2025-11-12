package cincuentazo.models;

import java.util.ArrayList;

/**
 * Represents an automated machine player in the Cincuentazo card game.
 * <p>
 * This class extends {@link Jugador} and implements simple decision-making logic
 * to choose a valid card to play based on the current total on the table.
 * </p>
 *
 * @author Hilary Herrera, Dana Gómez, Laura Bernal
 * @version 1.3
 * @since 2025
 * @see Jugador
 * @see Carta
 */
public class JugadorMaquina extends Jugador {

    private static int contador = 1;

    /**
     * Constructs a machine player with an automatically assigned name.
     * <p>
     * Example: "Máquina 1", "Máquina 2", etc.
     * </p>
     *
     * @param nombre the name provided (ignored, as the machine name is auto-generated).
     */
    public JugadorMaquina(String nombre) {
        super("Máquina " + contador);
        contador++;
    }


    /**
     * Simulates the machine's thinking delay before playing a card.
     * <p>
     * The delay is randomly chosen between 2 and 4 seconds to make the
     * machine's behavior appear more human-like.
     * </p>
     */
    public void esperarJugador() {
        try {
            int tiempo = (int) (Math.random() * 2000) + 2000; // 2000 - 4000 ms
            Thread.sleep(tiempo);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }


    /**
     * Selects a playable card based on the current total sum of the table.
     * <p>
     * The machine evaluates all cards in its hand and adds to a list those that,
     * when played, will not cause the table's sum to exceed 50.
     * If multiple valid cards exist, one is chosen randomly.
     * </p>
     *
     * @param sumaMesa the current total value of the cards on the table.
     * @return a valid {@link Carta} that does not exceed 50 when played,
     *         or {@code null} if no valid card is found (the machine is eliminated).
     */
    @Override
    public Carta seleccionarCartaAJugar(int sumaMesa) {

        ArrayList<Carta> cartasJugables = new ArrayList<>();
        // Recorremos las cartas de la mano con for de índice
        for (int i = 0; i < mano.size(); i++) {
            Carta carta = mano.get(i);
            // Calculamos la nueva suma si se jugara esta carta
            int nuevaSuma = sumaMesa + carta.getValorSegunReglas(sumaMesa);
            // Si la nueva suma no pasa de 50, se puede jugar esa carta
            if (nuevaSuma <= 50) {
                cartasJugables.add(carta); //añadir a cartas validas
            }
        }

        if(!cartasJugables.isEmpty()){
            int indice = (int)(Math.random()*cartasJugables.size());
            return cartasJugables.get(indice);
        }

        // Si no se encontró ninguna carta válida, se elimina el jugador
        this.setEliminado(true);
        return null; // No hay carta para jugar
    }
}