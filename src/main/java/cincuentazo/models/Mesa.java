package cincuentazo.models;

import java.util.Stack;

public class Mesa {

    private Stack<Carta> cartasEnMesa;  // Pila de cartas jugadas
    private int sumaActual;  //guarda la suma de las cartas jugadas hasta el momento

    // Constructor: crea la pila vacía
    public Mesa() {
        cartasEnMesa = new Stack<>();
        sumaActual = 0;
    }

    public void colocarCarta(Carta carta) {
        cartasEnMesa.add(carta);
        // La suma actual debe ser el total acumulado de TODAS las cartas en mesa
        int nuevaSuma = 0;
        for (Carta c : cartasEnMesa) {
            nuevaSuma = c.getValorSegunReglas(nuevaSuma);
        }
        sumaActual = nuevaSuma;
    }


    public int getSumaActual() {
        return sumaActual;
    }

    // Devuelve la carta que está visible (la última jugada)
    public Carta getUltimaCarta() { // PARA MOSTRARLA EN LA INTERFAZ
        if (cartasEnMesa.isEmpty()) {
            return null;
        }
        return cartasEnMesa.peek(); // peek() devuelve la de arriba sin quitarla
    }

    // Devuelve toda la pila (para que el mazo pueda reciclar las cartas)
    public Stack<Carta> getCartasEnMesa() {
        return cartasEnMesa;
    }

    //Si más adelante en el juego algún bug deja 2 o 3 cartas en la mesa.
    //O si más de un jugador juega cartas seguidas sin limpiar correctamente.
    //O si la mesa se reinicia al empezar una nueva ronda.

    // Deja solo la última carta visible, pero conserva la suma total
    public void reiniciarMesaDejandoUltima() {
        if (cartasEnMesa.size() <= 1) {
            return; // Si no hay suficientes cartas, no hace nada
        }

        // Guardamos la última carta (visible)
        Carta ultima = cartasEnMesa.peek();

        // Quitamos todas las cartas menos la última, sin tocar la suma
        while (cartasEnMesa.size() > 1) {
            cartasEnMesa.remove(0); // elimina desde el fondo
        }

        // No tocamos sumaActual, se mantiene igual
    }


    // Verifica si la mesa está vacía -> un poco innecesario
    public boolean estaVacia() {
        return cartasEnMesa.isEmpty();
    }

    // Reinicia completamente la mesa (para comenzar un nuevo juego)
    public void reiniciarMesaCompleta() {
        cartasEnMesa.clear(); // limpia toda la pila
        sumaActual = 0;
    }

}
