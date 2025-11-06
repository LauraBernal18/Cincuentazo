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

        //esperarJugador();

        // Recorremos todas las cartas
        for (int i = 0; i < mano.size(); i++) {

            Carta carta = mano.get(i);

            // Calculamos la suma si se jugara esta carta
            int nuevaSuma = sumaMesa + carta.getValorSegunReglas(sumaMesa);

            // Si no se pasa de 50 la carta sirve
            if (nuevaSuma <= 50) {
                return carta; // devolvemos directamente la carta
            }
        }

            // Si el ciclo termina y no se encontró ninguna carta válida
            this.eliminado = true;
            return null;

        }

}