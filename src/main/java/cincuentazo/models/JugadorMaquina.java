package cincuentazo.models;

public class JugadorMaquina extends Jugador {

    private static int contador = 1;

    public JugadorMaquina(String nombre) {
        super("Máquina " + contador);
        contador++;
    }

    public void esperarJugador() {
        try {
            int tiempo = (int) (Math.random() * 2000) + 2000; // 2000 - 4000 ms
            Thread.sleep(tiempo);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }


    @Override
    public Carta seleccionarCarta(int sumaMesa) {
        // Recorremos las cartas de la mano con for de índice para principiantes
        for (int i = 0; i < mano.size(); i++) {
            Carta carta = mano.get(i);
            // Calculamos la nueva suma si se jugara esta carta
            int nuevaSuma = sumaMesa + carta.getValorSegunReglas(sumaMesa);
            // Si la nueva suma no pasa de 50, se puede jugar esa carta
            if (nuevaSuma <= 50) {
                return carta; // Retornamos la carta válida para jugar
            }
        }
        // Si no se encontró ninguna carta válida, se elimina el jugador
        this.setEliminado(true);
        return null; // No hay carta para jugar
    }
}