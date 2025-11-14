package cincuentazo.modelos;

import javafx.application.Platform;

import javafx.scene.control.Label;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase principal que gestiona la lógica interna del juego "Cincuentazo".
 * <p>
 * Esta clase se encarga de manejar los jugadores (humano y máquinas), el mazo de cartas,
 * la mesa de juego, el turno actual, las eliminaciones, y la verificación de ganador.
 * Controla también la secuencia de turnos automáticos de las máquinas y permite la integración
 * con la interfaz gráfica mediante {@link Label} y callbacks.
 * </p>
 *
 * <p>Responsabilidades principales:</p>
 * <ul>
 *     <li>Inicializar el mazo y la mesa.</li>
 *     <li>Crear jugadores (humano y máquinas) y repartir cartas iniciales.</li>
 *     <li>Controlar el turno actual y la transición entre jugadores.</li>
 *     <li>Gestionar jugadas de máquinas de forma automática en hilos separados.</li>
 *     <li>Eliminar jugadores cuando se pasan de 50 o no tienen jugadas válidas.</li>
 *     <li>Verificar el fin del juego y determinar el ganador.</li>
 *     <li>Actualizar la interfaz mediante {@link Label} y el callback {@link #onCambioDeTurno}.</li>
 * </ul>
 *
 * @author Hilary Herrera, Dana Gómez, Laura Bernal
 * @version 3.5
 * @since 2025
 * @see Mesa
 * @see Mazo
 * @see Jugador
 * @see JugadorHumano
 * @see JugadorMaquina
 */
public class Juego {
    private Mazo mazo;
    private Mesa mesa;
    private ArrayList<Jugador> jugadores;
    private int turnoActual;
    private boolean terminado;
    private Runnable onCambioDeTurno;
    private Label labelTurnoActual;


    /**
     * Constructor principal del juego.
     * <p>
     * Inicializa la mesa, el mazo, los jugadores y reparte 4 cartas iniciales a cada uno.
     * También guarda el label de turno para actualizar la interfaz.
     * </p>
     *
     * @param nombreJugador Nombre del jugador humano.
     * @param cantidadMaquinas Número de jugadores máquina en la partida.
     * @param labelTurnoActual Label que mostrará el turno actual en la interfaz gráfica.
     */
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

    /**
     * Inicializa la lista de jugadores.
     * <p>
     * Crea un {@link JugadorHumano} y la cantidad de {@link JugadorMaquina} indicada.
     * </p>
     *
     * @param cantidadMaquinas Número de jugadores máquina a crear.
     */
    // Crea jugadores (1 humano + máquinas)
    private void inicializarJugadores(int cantidadMaquinas) {
        jugadores.add(new JugadorHumano("")); // humano
        for (int i = 1; i <= cantidadMaquinas; i++) {
            jugadores.add(new JugadorMaquina("Máquina " + i));
        }
    }

    /**
     * Reparte 4 cartas a cada jugador desde el mazo inicial.
     */
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

    /**
     * Procesa la jugada de una máquina: coloca carta en la mesa y toma nueva carta.
     * <p>
     * Actualiza la GUI y simula el "pensar" de la máquina.
     * </p>
     *
     * @param maquina Máquina que realiza la jugada.
     * @param carta Carta que la máquina decide jugar.
     */
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


    /**
     * Elimina una máquina del juego, devuelve sus cartas al mazo y ajusta el turno.
     *
     * @param maquina Máquina a eliminar.
     * @param motivo Texto explicativo de la eliminación (por ejemplo, se pasó de 50).
     */
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

    /**
     * Ejecuta los turnos automáticos de las máquinas en un hilo separado.
     * <p>
     * Simula el pensamiento de cada máquina, juega cartas válidas, elimina
     * máquinas que no puedan jugar o se pasen de 50 y verifica ganador.
     * </p>
     */
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
                    eliminarMaquina(maquina, "fue eliminado (sin jugadas válidas)."); // usa el submétodo
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


    /**
     * Elimina al jugador humano del juego.
     * <p>
     * Devuelve sus cartas al mazo y actualiza la GUI.
     * </p>
     *
     * @param humano Jugador humano a eliminar.
     * @param motivo Texto explicativo.
     */
    public void eliminarJugadorHumano(JugadorHumano humano, String motivo) {
        humano.setEliminado(true);
        mazo.agregarCartasAlFinal(humano.getMano());
        humano.limpiarMano();
        actualizarLabelTurnos( motivo);
    }


    /**
     * Pasa al siguiente jugador activo, ajustando el índice y verificando si hay ganador.
     */
    // Pasa al siguiente jugador
    public void pasarAlSiguienteJugador() {
        int totalJugadores = jugadores.size();
        int intentos = 0; // Para no caer en un bucle infinito

        //pasar de turno cuando no hay jugadores eliminados
        turnoActual++;
        if (turnoActual >= totalJugadores) {
            turnoActual = 0; // Vuelve al primer jugador
        }

        //pasar de turno cuando hay eliminados
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

    /** Establece el callback que se ejecutará al cambiar el turno.
     * @param onCambioDeTurno Runnable que se ejecutará al cambiar el turno.
     */
    public void setOnCambioDeTurno(Runnable onCambioDeTurno) {
        this.onCambioDeTurno = onCambioDeTurno;
    }

    /**
     * Devuelve la mesa de juego.
     *
     * @return la instancia de {@link Mesa} asociada a la partida.
     */
    public Mesa getMesa() { return mesa; }

    /**
     * Devuelve la lista de jugadores participantes en la partida.
     *
     * @return una lista de {@link Jugador} con todos los jugadores activos y eliminados.
     */
    public List<Jugador> getJugadores() { return jugadores; }

    /**
     * Devuelve el jugador cuyo turno está actualmente activo.
     *
     * @return el {@link Jugador} que tiene el turno en este momento.
     */
    public Jugador getJugadorActual() { return jugadores.get(turnoActual); }

    /**
     * Indica si la partida ha terminado.
     *
     * @return {@code true} si la partida terminó, {@code false} en caso contrario.
     */
    public boolean esTerminado() { return terminado; }

    /**
     * Devuelve el mazo de cartas de la partida.
     *
     * @return la instancia de {@link Mazo} utilizada en la partida.
     */
    public Mazo getMazo() { return mazo; }

    /**
     * Indica si hay un ganador de la partida.
     *
     * @return {@code true} si la partida ha terminado y hay un ganador, {@code false} de lo contrario.
     */
    public boolean hayGanador() { return terminado; }

    /**
     * Devuelve el jugador ganador de la partida, si existe.
     *
     * @return el {@link Jugador} ganador, o {@code null} si aún no hay ganador.
     */
    public Jugador getGanador() {
        for (Jugador j : jugadores) if (!j.esEliminado()) return j;
        return null;
    }

    /**
     * Devuelve el índice del turno actual dentro de la lista de jugadores.
     *
     * @return un entero que representa la posición del jugador que tiene el turno activo.
     */
    public int getTurnoActual() {
        return turnoActual;
    }


    /**
     * Actualiza el {@link Label} de la interfaz de usuario con el turno o mensaje actual.
     *
     * @param texto el mensaje o nombre del jugador que se mostrará en la interfaz.
     */
    private void actualizarLabelTurnos(String texto) {
        Platform.runLater(() -> labelTurnoActual.setText(texto));
    }
}
