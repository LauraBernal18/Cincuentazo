package cincuentazo.modelos;

import java.util.Stack;


/**
 * Representa la mesa de juego ("Mesa") donde se juegan las cartas y se lleva el registro de la suma actual.
 * <p>
 * Esta clase administra la pila de cartas jugadas, calcula la suma acumulada
 * según las reglas del juego y proporciona métodos para restablecer o inspeccionar el estado de la mesa.
 * </p>
 *
 * @author Hilary Herrera, Dana Gómez, Laura Bernal
 * @version 1.4
 * @since 2025
 * @see Carta
 * @see Mazo
 */
public class Mesa {

    /** Pila que almacena todas las cartas jugadas sobre la mesa en orden. */
    private Stack<Carta> cartasEnMesa;  // Pila de cartas jugadas

    /** La suma total actual de todas las cartas jugadas sobre la mesa. */
    private int sumaActual;             // Suma acumulada total

    /**
     * Crea una nueva tabla vacía, sin cartas y con una suma total de 0.
     */
    public Mesa() {
        cartasEnMesa = new Stack<>();
        sumaActual = 0;
    }


    /**
     * Coloca una nueva carta sobre la mesa y actualiza el total acumulado según las reglas del juego.
     * <p>
     * El valor de la carta se obtiene mediante {@link Carta#getValorSegunReglas(int)},
     * y luego se suma al total actual.
     * </p>
     *
     * @param carta la {@link Carta} que se colocará sobre la mesa. Si es {@code null}, no se realiza ninguna acción.
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
     * Devuelve la suma total actual de todas las cartas jugadas en la mesa.
     *
     * @return la suma acumulada actual como un número entero.
     */
    // Devuelve la suma actual de la mesa
    public int getSumaActual() {
        return sumaActual;
    }


    /**
     * Recupera la última carta jugada (la que está encima de la pila) sin eliminarla.
     *
     * @return la {@link Carta} superior de la mesa, o {@code null} si la mesa está vacía.
     */
    // Devuelve la carta visible (última jugada)
    public Carta getUltimaCarta() {
        if (cartasEnMesa.isEmpty())
            return null;

        return cartasEnMesa.peek();
    }

    /**
     * Proporciona acceso a la pila de todas las cartas que se encuentran actualmente sobre la mesa.
     * <p>
     * Esto lo suele usar {@link Mazo} al reciclar cartas
     * después de que un jugador exceda el límite o durante la actualización del mazo.
     * </p>
     *
     * @return la {@link Stack} que contiene todas las cartas jugadas sobre la mesa.
     */
    // Devuelve todas las cartas (para reciclarlas si alguien se pasa de 50)
    public Stack<Carta> getCartasEnMesa() {
        return cartasEnMesa;
    }


    /**
     * Reinicia la mesa manteniendo visible solo la última carta jugada
     * y conservando el total acumulado actual.
     * <p>
     * Útil cuando se repone el mazo desde la mesa
     * y se debe mantener visible una carta para la siguiente ronda.
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
