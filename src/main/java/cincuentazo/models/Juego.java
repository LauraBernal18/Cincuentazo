package cincuentazo.models;

import java.util.ArrayList;
import java.util.List;


public class Juego {
    private Mazo mazo;
    private Mesa mesa;
    private List<Jugador> jugadores;
    private int turnoActual;
    private boolean terminado;

    // Constructor principal
    public Juego(int cantidadMaquinas) {
        this.mesa = new Mesa();
        this.mazo = new Mazo(mesa); // el mazo necesita conocer la mesa
        this.jugadores = new ArrayList<>();
        this.turnoActual = 0;
        this.terminado = false;

        inicializarJugadores(cantidadMaquinas);
        repartirCartasIniciales();
    }

    // Crea los jugadores (1 humano + n máquinas)
    private void inicializarJugadores(int cantidadMaquinas) {
        jugadores.add(new JugadorHumano(""));

        for (int i = 1; i <= cantidadMaquinas; i++) {
            jugadores.add(new JugadorMaquina("Máquina " + i));
        }
    }

    // Reparte 4 cartas iniciales a cada jugador
    private void repartirCartasIniciales() {
        for (Jugador jugador : jugadores) {
            for (int i = 0; i < 4; i++) {
                Carta carta = mazo.tomarCarta();
                jugador.recibirCarta(carta);
            }
        }
    }

    // Controla el turno de cada jugador
    public void siguienteTurno() {
        if (terminado) {
            return; //si ya hay ganador no hace nada
        }

        Jugador jugadorActual = jugadores.get(turnoActual);

        if (jugadorActual.esEliminado()) {
            pasarAlSiguienteJugador();
            cambiarTurnoConHilo(); // cambia el turno con retardo visual
            return;
        }

        int sumaMesa = mesa.getSumaActual();
        Carta cartaSeleccionada = jugadorActual.seleccionarCarta(sumaMesa); // esta por defecto en lo de Dana

        if (cartaSeleccionada != null) {
            jugadorActual.jugarCarta(cartaSeleccionada);
            mesa.colocarCarta(cartaSeleccionada);

            // Si pasa de 50 → se elimina
            if (mesa.getSumaActual() > 50) {
                jugadorActual.setEliminado(true);
                System.out.println(jugadorActual.getNombre() + " se pasó de 50 y fue eliminado.");
            }

        }
        pasarAlSiguienteJugador();
        cambiarTurnoConHilo();
    }

    // Cambia de turno usando un hilo para que la transición sea más natural
    public void cambiarTurnoConHilo() {
        Thread hiloCambioTurno = new Thread(() -> {
            try {
                // Espera 1.5 segundos (puedes ajustar el tiempo si quieres)
                Thread.sleep(1500);

                javafx.application.Platform.runLater(() -> {
                    // Avanzar al siguiente jugador
                    turnoActual++;

                    // Si ya pasó el último jugador, volver al primero
                    if (turnoActual >= jugadores.size()) {
                        turnoActual = 0;
                    }

                    Jugador jugadorActual = jugadores.get(turnoActual);
                    System.out.println("Turno de: " + jugadorActual.getNombre());

                    // Si es una máquina, esperar y jugar automáticamente
                    if (jugadorActual instanceof JugadorMaquina maquina) {
                        maquina.esperarJugador(); // hilo interno que simula pensar
                        // después puedes hacer que elija y juegue su carta:
                        siguienteTurno();
                    }
                });

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        hiloCambioTurno.start(); // Inicia el hilo de cambio de turno
    }



    // Cambia el turno al siguiente jugador
    private void pasarAlSiguienteJugador() {
        turnoActual++;

        // Si pasa del último jugador, volver al primero
        if (turnoActual >= jugadores.size()) {
            turnoActual = 0;
        }

            // Si todos están eliminados menos uno, termina el juego
        int jugadoresActivos = 0;
        Jugador posibleGanador = null;
        for (Jugador jugadorX : jugadores) {
            if (!jugadorX.esEliminado()) {
                jugadoresActivos++;
                posibleGanador = jugadorX;
            }
        }

        if (jugadoresActivos <= 1) {
            terminado = true;
            System.out.println("¡Ganó " + posibleGanador.getNombre() + " por eliminación!");
        }
    }


    // Verifica si el juego terminó
    public boolean hayGanador() {
        return terminado;
    }

    // Obtiene el jugador ganador
    public Jugador getGanador() {
        for (Jugador jugadorX : jugadores) {
            if (!jugadorX.esEliminado()) {
                return jugadorX;
            }
        }
        return null;
    }

    // Reinicia completamente el juego (para nuevo intento)
    public void reiniciarJuego(int cantidadMaquinas) {
        this.mesa.reiniciarMesaCompleta();
        this.mazo = new Mazo(mesa);
        this.jugadores.clear();
        this.turnoActual = 0;
        this.terminado = false;
        inicializarJugadores(cantidadMaquinas);
        repartirCartasIniciales();
    }

    // Métodos de apoyo (para la interfaz)
    public Mesa getMesa() {
        return mesa;
    }

    public List<Jugador> getJugadores() {
        return jugadores;
    }

    public Jugador getJugadorActual() {
        return jugadores.get(turnoActual);
    }

    public boolean esTerminado() {
        return terminado;
    }

    public Mazo getMazo() {
        return mazo;
    }

}
