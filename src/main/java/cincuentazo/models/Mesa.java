package cincuentazo.models;

import java.util.Stack;

public class Mesa {

    private Stack<Carta> cartasEnMesa;  // Pila de cartas jugadas

    // Constructor: crea la pila vacía
    public Mesa() {
        cartasEnMesa = new Stack<>();
    }

    //Coloca una carta en la mesa (la pone encima de la pila)
    public void colocarCarta(Carta carta) {
        if (carta != null) {
            cartasEnMesa.push(carta);
        }
    }

    // Devuelve la carta que está visible (la última jugada)
    public Carta getUltimaCarta() { // MAS QUE TODO PARA MOSTRARLA EN EL FXML
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
    
    // Reinicia la mesa dejando solo la última carta (usada cuando se reinicia el mazo)
    public void reiniciarMesaDejandoUltima() {
        if (cartasEnMesa.size() <= 1) {
            return; // si no hay suficientes cartas, no hace nada
        }

        Carta ultima = cartasEnMesa.pop(); // guarda la carta visible
        cartasEnMesa.clear();              // limpia la mesa
        cartasEnMesa.push(ultima);         // deja solo la última
    }

    // Verifica si la mesa está vacía -> un poco innecesario
    public boolean estaVacia() {
        return cartasEnMesa.isEmpty();
    }
}
