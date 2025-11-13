package cincuentazo.models;

import java.util.ArrayList;

/**
 * Representa un jugador automático en el juego de cartas Cincuentazo.
 * <p>
 * Esta clase extiende {@link Jugador} e implementa una lógica de toma de decisiones simple
 * para elegir una carta válida para jugar según el total actual en la mesa.
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
     * Crea un jugador automático con un nombre asignado automáticamente.
     * <p>
     * Ejemplo: "Máquina 1", "Máquina 2", etc.
     * </p>
     *
     * @param nombre el nombre proporcionado (se ignora, ya que el nombre de la máquina se genera automáticamente).
     */
    public JugadorMaquina(String nombre) {
        super("Máquina " + contador);
        contador++;
    }


    /**
     * Simula el tiempo de espera de la máquina antes de jugar una carta.
     * <p>
     * El tiempo de espera se elige aleatoriamente entre 2 y 4 segundos para que
     el comportamiento de la máquina parezca más humano.
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
     * Selecciona una carta jugable según la suma total actual de la mesa.
     * <p>
     * La máquina evalúa todas las cartas en su mano y añade a una lista aquellas que,
     * al jugarse, no harán que la suma de la mesa supere 50.
     * Si existen varias cartas válidas, se elige una al azar.
     * </p>
     *
     * @param sumaMesa el valor total actual de las cartas en la mesa.
     * @return una {@link Carta} válida que no supere 50 al jugarse,
     *         o {@code null} si no se encuentra ninguna carta válida (la máquina se elimina).
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