package cincuentazo.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

public class Mazo {
    private ArrayList<Carta> cartasMazo;
    private Stack<Carta> cartasMesa;

    public Mazo(Stack<Carta> cartasMesa) {
        this.cartasMesa = cartasMesa;
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
        if (!cartasMazo.isEmpty()) {
            return cartasMazo.remove(0);
        }
        return null;
    }

    /*
    public boolean estaVacio() {
        return cartas.isEmpty();
    }
     */

    private void reiniciarConCartasDeMesa() {
        if (cartasMesa == null || cartasMesa.size() <= 1) {
            return;
            //Si en la mesa no hay cartas para tomar paila
        }

        // Guardar la carta superior de la pila (la que se sigue mostrando en la mesa)
        Carta ultimaCarta = cartasMesa.pop();

        // Mover todas las demás cartas al mazo
        while (!cartasMesa.isEmpty()) {
            cartasMazo.add(cartasMesa.pop());
        }

        // Mezclar el mazo recién recargado
        barajar();

        // Volver a dejar la última carta en la mesa
        cartasMesa.push(ultimaCarta);
    }
}
