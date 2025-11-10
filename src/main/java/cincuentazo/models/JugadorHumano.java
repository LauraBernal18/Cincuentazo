package cincuentazo.models;

public class JugadorHumano extends Jugador {
    private Carta cartaSeleccionada;

    public JugadorHumano(String nombre) {
        super(nombre);
    }

    public void seleccionarCartaManual(Carta cartaSeleccionada) {
        this.cartaSeleccionada = cartaSeleccionada;
    }

    //si la carta seleccionada es un AS y no es nula llama al metodo de la carta para guardar la elección
    public void elegirValorAs(int valorElegido) {
        if (cartaSeleccionada != null && cartaSeleccionada.identificarAS()) {
            cartaSeleccionada.setValorAsElegido(valorElegido);
        }
    }

    @Override
    public Carta seleccionarCarta(int sumaMesa) {
        return cartaSeleccionada;
    }
}
