package cincuentazo.models;

import javafx.application.Platform;

import javafx.scene.control.Label;
import java.util.ArrayList;
import java.util.List;

public class Juego {
    private Mazo mazo;
    private Mesa mesa;
    private List<Jugador> jugadores;
    private int turnoActual;
    private boolean terminado;
    private Runnable onCambioDeTurno;
    private Label labelTurnoActual;

    // Constructor principal
    public Juego(String nombreJugador, int cantidadMaquinas, Label labelTurnoActual) {
        this.mesa = new Mesa();
        this.mazo = new Mazo(mesa);
        this.jugadores = new ArrayList<>();
        this.turnoActual = 0;
        this.terminado = false;
        this.labelTurnoActual = labelTurnoActual;


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


    private void procesarCartaJugadaMaquina(JugadorMaquina maquina, Carta carta) {
        maquina.jugarCarta(carta);
        mesa.colocarCartaEnMesa(carta);

        // Refrescar GUI
        if (onCambioDeTurno != null) {
            Platform.runLater(onCambioDeTurno);
        }
        actualizarLabelTurnos(maquina.getNombre() + " colocó carta en la mesa.");
        maquina.esperarJugador();

        Carta nueva = mazo.tomarCarta();
        if (nueva != null) {
            maquina.recibirCarta(nueva);
            actualizarLabelTurnos(maquina.getNombre() + " tomó una nueva carta del mazo.");
            maquina.esperarJugador();
        }
    }

    public void eliminarMaquina(JugadorMaquina maquina, String motivo) {
        maquina.setEliminado(true);
        mazo.agregarCartasAlFinal(maquina.getMano());
        maquina.limpiarMano();
        actualizarLabelTurnos(maquina.getNombre() + " " + motivo);

        // Remover de la lista
        int indiceEliminado = jugadores.indexOf(maquina);
        if (indiceEliminado != -1) {
            jugadores.remove(indiceEliminado);

            // ajustar turnoActual
            if (indiceEliminado <= turnoActual && turnoActual > 0) {
                turnoActual--; // para no saltar jugadores
            }
        }
    }

    public void jugarTurnosMaquinas() {
        Thread hilo = new Thread(() -> {
            while (!terminado) {
                Jugador jugador = jugadores.get(turnoActual);

                if (!(jugador instanceof JugadorMaquina)) {
                    // Si el humano está eliminado, avanzar automáticamente
                    if (jugador.esEliminado()) {
                        pasarAlSiguienteJugador();
                        continue; // continuar ciclo para siguiente jugador
                    }
                    // Si humano no eliminado, esperar a que él juegue (detener hilo)
                    break;
                }

                JugadorMaquina maquina = (JugadorMaquina) jugador;
                actualizarLabelTurnos("Turno de: " + maquina.getNombre());
                maquina.esperarJugador(); // simula pensar

                Carta carta = maquina.seleccionarCartaAJugar(mesa.getSumaActual());
                if (carta != null) {
                    procesarCartaJugadaMaquina(maquina, carta);
                } else {
                    eliminarMaquina(maquina, "fue eliminado (sin jugadas válidas)."); // 🔹 usa el submétodo
                }

                // Si se pasa de 50 → eliminar jugador maquina
                if (mesa.getSumaActual() > 50) {
                    eliminarMaquina(maquina, "se pasó de 50 y queda eliminado.");
                }

                // Verificar si el juego terminó
                if (hayGanador()) {
                    break;
                }

                pasarAlSiguienteJugador();

                // Pausa entre máquinas
                try {
                    Thread.sleep(2000);
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
    public void eliminarJugadorHumano(JugadorHumano humano, String motivo) {
        humano.setEliminado(true);
        mazo.agregarCartasAlFinal(humano.getMano());
        humano.limpiarMano();
        actualizarLabelTurnos("El jugador humano " + motivo);
    }
/*
    public void jugarTurnoHumano(Carta cartaSeleccionada) {
        JugadorHumano humano = (JugadorHumano) jugadores.get(turnoActual);

        // Guardar la carta seleccionada
        humano.seleccionarCartaManual(cartaSeleccionada);

        // Obtener la carta seleccionada real
        Carta carta = humano.seleccionarCarta(mesa.getSumaActual());

        if (carta == null) {
            eliminarJugadorHumano(humano, "no tiene jugadas válidas.");
            pasarAlSiguienteJugador();
            return;
        }

        // Jugar la carta
        humano.jugarCarta(carta);
        mesa.colocarCarta(carta);

        // Verificar si se pasó de 50
        if (mesa.getSumaActual() > 50) {
            eliminarJugadorHumano(humano, "se pasó de 50 y queda eliminado.");
            pasarAlSiguienteJugador();
            return;
        }

        // nueva carta si hay mazo
        Carta nueva = mazo.tomarCarta();
        if (nueva != null) {
            humano.recibirCarta(nueva);
        }

        // Pasar turno al siguiente
        pasarAlSiguienteJugador();

        // Actualizar interfaz
        if (onCambioDeTurno != null) {
            onCambioDeTurno.run();
        }
    } */


    // Pasa al siguiente jugador
    public void pasarAlSiguienteJugador() {
        int totalJugadores = jugadores.size();
        int intentos = 0; // Para no caer en un bucle infinito

        turnoActual++;
        if (turnoActual >= totalJugadores) {
            turnoActual = 0; // Vuelve al primer jugador
        }

        while (jugadores.get(turnoActual).esEliminado()) {
            turnoActual++;
            if (turnoActual >= totalJugadores) {
                turnoActual = 0;
            }
            intentos++;

            if (intentos > totalJugadores) {
                terminado = true;
                return;
            }
        }

        int activos = 0;
        Jugador posibleGanador = null;
        for (int i = 0; i < jugadores.size(); i++) {
            Jugador j = jugadores.get(i);
            if (!j.esEliminado()) {
                activos++;
                posibleGanador = j;
            }
        }

        if (activos <= 1) {
            terminado = true;
            actualizarLabelTurnos("¡Ganó " + posibleGanador.getNombre() + " por eliminación!");
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

    public int getTurnoActual() {
        return turnoActual;
    }

    private void actualizarLabelTurnos(String texto) {
        Platform.runLater(() -> labelTurnoActual.setText(texto));
    }
}
