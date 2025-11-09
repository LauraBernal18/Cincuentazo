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
    // Repartir 4 cartas a cada jugador
        for (int i = 0; i < jugadores.size(); i++) {
            Jugador jugador = jugadores.get(i); // Obtener el jugador actual

            // Dar 4 cartas al jugador
            for (int j = 0; j < 4; j++) {
                Carta carta = mazo.tomarCarta(); // Tomar una carta del mazo
                jugador.recibirCarta(carta);     // Añadir la carta a la mano del jugador
            }
        }
    }

    // Controla el turno de cada jugador
    public void siguienteTurno() {

        if (terminado) {
            return; // El juego terminó, no hacer nada
        }

        Jugador jugadorActual = jugadores.get(turnoActual);

        // Si el jugador está eliminado, pasar al siguiente
        if (jugadorActual.esEliminado()) {
            pasarAlSiguienteJugador();
            cambiarTurnoConHilo();
            return;
        }

        int sumaMesa = mesa.getSumaActual();

        // El jugador intenta seleccionar carta según la regla del juego
        Carta cartaSeleccionada = jugadorActual.seleccionarCarta(sumaMesa);

        if (cartaSeleccionada != null) {
            // Juega la carta y la coloca en la mesa
            jugadorActual.jugarCarta(cartaSeleccionada);
            mesa.colocarCarta(cartaSeleccionada);

            // Si la suma pasó de 50, jugador eliminado y sus cartas pasan al mazo
            if (mesa.getSumaActual() > 50) {
                jugadorActual.setEliminado(true);

                // PASAR LAS CARTAS DEL JUGADOR ELIMINADO AL FINAL DEL MAZO
                // utilizando el metodo para agregar varias cartas a la vez
                mazo.agregarCartasAlFinal(jugadorActual.getMano());

                jugadorActual.limpiarMano(); // limpia las cartas y marca eliminado
            }
        } else {
            // Si no hay carta válida para jugar, eliminar jugador y pasar cartas al mazo
            jugadorActual.setEliminado(true);
            mazo.agregarCartasAlFinal(jugadorActual.getMano());
            jugadorActual.limpiarMano();
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

    public Mazo getMazo(){
        return mazo;
    }

}
