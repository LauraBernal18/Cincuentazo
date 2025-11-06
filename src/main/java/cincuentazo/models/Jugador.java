package cincuentazo.models;

import java.util.ArrayList;
import java.util.List;

public class Jugador {

    protected String nombre;
    protected List<Carta> mano;
    protected boolean eliminado;

    public Jugador(String nombre) {
        this.nombre = nombre;
        this.mano = new ArrayList<>();
        this.eliminado = false;
    }

    public List<Carta> getMano() {
        return mano;
    }
    public String getNombre() {
        return nombre;
    }
    public boolean esEliminado() {
        return eliminado;
    }

    public void setEliminado(boolean eliminado) {
        this.eliminado = eliminado;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void recibirCarta(Carta carta) {
        mano.add(carta);
    }

    public boolean puedeJugar(int sumaMesa) {
       for (int i = 0; i < mano.size(); i++) {
            Carta carta = mano.get(i);
            if (sumaMesa + carta.getValorSegunReglas(sumaMesa) <= 50) {
                return true;
            }
        }
        return false;
    }

    public void limpiarMano() {
        mano.clear();
        eliminado = true; // sin cartas → eliminado del juego
    }

 /* selecciona carta or defecto*/
    public Carta seleccionarCarta(int sumaMesa) {
        if (!mano.isEmpty()) {
            return mano.get(0);
        }
        return null;
    }

    public Carta jugarCarta(Carta carta) {

        if (carta != null && mano.remove(carta)) {
            if (mano.isEmpty()) { // actualiza el estado si se queda sin cartas
                setEliminado(true);
            }
            return carta;
        }
        return null; // no pudo jugar (mano vacía)
    }
}
