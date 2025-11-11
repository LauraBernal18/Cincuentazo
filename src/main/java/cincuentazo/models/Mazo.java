package cincuentazo.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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
        String[] palos = {"T", "P", "D", "C"};
        String[] valores = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};

        // Recorremos cada palo usando un ciclo for con índice
        for (int i = 0; i < palos.length; i++) {
            String palo = palos[i];  // Obtenemos el palo actual

            // Para cada palo, recorremos todos los valores con otro for con índice
            for (int j = 0; j < valores.length; j++) {
                String valor = valores[j];  // Obtenemos el valor actual

                // Creamos una nueva carta con valor y palo y la añadimos al mazo
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

    private void reiniciarConCartasDeMesa() {
        Stack<Carta> cartasMesa = mesa.getCartasEnMesa();

        if (cartasMesa == null || cartasMesa.size() <= 1) {
            return;
            //Si en la mesa no hay cartas para tomar paila
        }


        // Pasar todas las demás al mazo
        while (cartasMesa.size() > 1) {
            cartasMazo.add(cartasMesa.pop());
        }

        // Barajar el mazo y dejar la mesa limpia con su última carta
        barajar();
        mesa.reiniciarMesaDejandoUltima();
    }

    //acceder a los palos de las cartas
    public static String[] getPalos() {
        return new String[]{"T", "P", "D", "C"};
    }

    public void agregarCartasAlFinal(List<Carta> cartas) {
        cartasMazo.addAll(cartas);
    }

}
