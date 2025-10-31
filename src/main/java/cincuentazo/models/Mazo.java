package cincuentazo.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

public class Mazo {
    private ArrayList<Carta> cartasMazo;
    private Mesa mesa;

    public Mazo(Mesa mesa) {
        this.mesa = mesa;
        cartasMazo = new ArrayList<>();
        crearMazo();
        barajar();
    }

    private void crearMazo() {
        String[] palos = {"bombones", "burbujas", "bellotas", "el otro"};
        String[] valores = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};

        for (String palo : palos) {
            for (String valor : valores) {
                cartasMazo.add(new Carta(valor, palo));
            }
        }
    }

    public void barajar() {
        Collections.shuffle(cartasMazo); // se puede hacer manualmente como el sudoku
    }

    public Carta tomarCarta() {
            if (cartasMazo.isEmpty()) {
                reiniciarConCartasDeMesa();
            }

            if (!cartasMazo.isEmpty()) {
                return cartasMazo.remove(0); // toma la primera carta
            }

            return null; // si sigue vacío, no hay cartas
    }

    /*
    public boolean estaVacio() {
        return cartas.isEmpty();
    }
     */

    private void reiniciarConCartasDeMesa() {
        Stack<Carta> cartasMesa = mesa.getCartasEnMesa();

        if (cartasMesa == null || cartasMesa.size() <= 1) {
            return;
            //Si en la mesa no hay cartas para tomar paila
        }

        // Guardar la carta superior de la pila (la que se sigue mostrando en la mesa)
        Carta ultimaCarta = cartasMesa.peek();

        // Pasar todas las demás al mazo
        while (cartasMesa.size() > 1) {
            cartasMazo.add(cartasMesa.pop());
        }

        // Barajar el mazo y dejar la mesa limpia con su última carta
        barajar();
        mesa.reiniciarMesaDejandoUltima();
    }
}
