package cincuentazo.models;

import javafx.application.Platform;
import java.util.ArrayList;
import java.util.List;

public class Juego {
    private Mazo mazo;
    private Mesa mesa;
    private List<Jugador> jugadores;
    private int turnoActual;
    private boolean terminado;
    private Runnable onCambioDeTurno;

    // Constructor principal
    public Juego(int cantidadMaquinas) {
        this.mesa = new Mesa();
        this.mazo = new Mazo(mesa);
        this.jugadores = new ArrayList<>();
        this.turnoActual = 0;
        this.terminado = false;

        inicializarJugadores(cantidadMaquinas);
        repartirCartasIniciales();
    }

    // Crea jugadores (1 humano + máquinas)
    private void inicializarJugadores(int cantidadMaquinas) {
        jugadores.add(new JugadorHumano("")); // humano
        for (int i = 1; i <= cantidadMaquinas; i++) {
            jugadores.add(new JugadorMaquina("Máquina " + i));
        }
    }

    // Reparte 4 cartas a cada jugador
    private void repartirCartasIniciales() {
        for (Jugador jugador : jugadores) {
            for (int j = 0; j < 4; j++) {
                Carta carta = mazo.tomarCarta();
                jugador.recibirCarta(carta);
            }
        }
    }

    // ======================= NUEVA LÓGICA DE TURNOS =========================

    public void jugarTurnosMaquinas() {
        Thread hilo = new Thread(() -> {
            while (!terminado) {
                Jugador jugador = jugadores.get(turnoActual);

                // Si llega el humano → detener el hilo (turno humano)
                if (!(jugador instanceof JugadorMaquina)) {
                    break;
                }

                JugadorMaquina maquina = (JugadorMaquina) jugador;
                System.out.println("Turno de: " + maquina.getNombre());
                maquina.esperarJugador(); // simula pensar

                Carta carta = maquina.seleccionarCarta(mesa.getSumaActual());
                if (carta != null) {
                    maquina.jugarCarta(carta);
                    mesa.colocarCarta(carta);
                    System.out.println(maquina.getNombre() + " jugó: " + carta.getValor() + "-" + carta.getPalo());

                    // NUEVO: después de jugar, la máquina toma una carta del mazo
                    Carta nueva = mazo.tomarCarta();
                    if (nueva != null) {
                        maquina.recibirCarta(nueva);
                        System.out.println(maquina.getNombre() + " tomó una nueva carta del mazo.");
                    }

                } else {
                    // si no tiene carta válida, se elimina
                    maquina.setEliminado(true);
                    mazo.agregarCartasAlFinal(maquina.getMano());
                    maquina.limpiarMano();
                    System.out.println(maquina.getNombre() + " fue eliminado (sin jugadas válidas).");
                }

                // Refrescar GUI
                if (onCambioDeTurno != null) {
                    Platform.runLater(onCambioDeTurno);
                }

                // Si se pasa de 50 → eliminar jugador
                if (mesa.getSumaActual() > 50) {
                    System.out.println(maquina.getNombre() + " se pasó de 50 y queda eliminado.");
                    maquina.setEliminado(true);
                    mazo.agregarCartasAlFinal(maquina.getMano());
                    maquina.limpiarMano();
                }

                // Verificar si el juego terminó
                if (hayGanador()) {
                    break;
                }

                pasarAlSiguienteJugador();

                // Pausa entre máquinas
                try {
                    Thread.sleep(1200);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }

            // Cuando termine el hilo, actualizar una vez más
            if (onCambioDeTurno != null) {
                Platform.runLater(onCambioDeTurno);
            }

        });

        hilo.setDaemon(true);
        hilo.start();
    }


    // Pasa al siguiente jugador
    public void pasarAlSiguienteJugador() {
        turnoActual++;
        if (turnoActual >= jugadores.size()) turnoActual = 0;

        // Verificar si solo queda un jugador
        int activos = 0;
        Jugador posibleGanador = null;
        for (Jugador j : jugadores) {
            if (!j.esEliminado()) {
                activos++;
                posibleGanador = j;
            }
        }

        if (activos <= 1) {
            terminado = true;
            System.out.println("¡Ganó " + posibleGanador.getNombre() + " por eliminación!");
        }
    }

    // ======================= MÉTODOS DE APOYO =========================

    public void setOnCambioDeTurno(Runnable onCambioDeTurno) {
        this.onCambioDeTurno = onCambioDeTurno;
    }

    public Mesa getMesa() { return mesa; }

    public List<Jugador> getJugadores() { return jugadores; }

    public Jugador getJugadorActual() { return jugadores.get(turnoActual); }

    public boolean esTerminado() { return terminado; }

    public Mazo getMazo() { return mazo; }

    public boolean hayGanador() { return terminado; }

    public Jugador getGanador() {
        for (Jugador j : jugadores) if (!j.esEliminado()) return j;
        return null;
    }

    public void reiniciarJuego(int cantidadMaquinas) {
        this.mesa.reiniciarMesaCompleta();
        this.mazo = new Mazo(mesa);
        this.jugadores.clear();
        this.turnoActual = 0;
        this.terminado = false;
        inicializarJugadores(cantidadMaquinas);
        repartirCartasIniciales();
    }
}
