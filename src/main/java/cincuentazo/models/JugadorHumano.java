package cincuentazo.models;

public class JugadorHumano extends Jugador {
    private Carta cartaSeleccionada;

    public JugadorHumano(String nombre) {
        super(nombre);
    }

    public void seleccionarCartaManual(Carta cartaSeleccionada) {
        this.cartaSeleccionada = cartaSeleccionada;
    }

    @Override
    public Carta seleccionarCarta(int sumaMesa) {
        return cartaSeleccionada;
    }
}
