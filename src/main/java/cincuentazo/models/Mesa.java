package cincuentazo.models;

import java.util.Stack;

public class Mesa {

    private Stack<Carta> cartasEnMesa;  // Pila de cartas jugadas
    private int sumaActual;             // Suma acumulada total

    public Mesa() {
        cartasEnMesa = new Stack<>();
        sumaActual = 0;
    }

    // Coloca una carta sobre la mesa y actualiza la suma acumulada correctamente
    public void colocarCarta(Carta carta) {
        if (carta == null) return;

        cartasEnMesa.push(carta); // agrega al tope
        int valorCarta = carta.getValorSegunReglas(sumaActual);
        sumaActual += valorCarta; // suma acumulada correcta
    }

    // Devuelve la suma actual de la mesa
    public int getSumaActual() {
        return sumaActual;
    }

    // Devuelve la carta visible (última jugada)
    public Carta getUltimaCarta() {
        if (cartasEnMesa.isEmpty()) return null;
        return cartasEnMesa.peek();
    }

    // Devuelve todas las cartas (para reciclarlas si alguien se pasa de 50)
    public Stack<Carta> getCartasEnMesa() {
        return cartasEnMesa;
    }

    // Deja solo la última carta sobre la mesa, manteniendo la suma acumulada
    public void reiniciarMesaDejandoUltima() {
        if (cartasEnMesa.size() <= 1) return;

        Carta ultima = cartasEnMesa.peek();
        cartasEnMesa.clear();
        cartasEnMesa.push(ultima);
        //  No reiniciamos sumaActual: se mantiene igual
    }

    // Indica si la mesa está vacía
    public boolean estaVacia() {
        return cartasEnMesa.isEmpty();
    }

    // Reinicia completamente la mesa (para un nuevo juego)
    public void reiniciarMesaCompleta() {
        cartasEnMesa.clear();
        sumaActual = 0;
    }
}
