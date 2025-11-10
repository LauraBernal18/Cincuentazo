package cincuentazo.controllers;
import cincuentazo.models.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class JuegoController {

    /*label de notificación sobre los procesos dentro del juego "maquina pensando, maquina colocando carta, jugador
    //colocó una carta"... etc*/
    @FXML
    private Label labelEstadoJuego;

    //labels de actualización en turnos
    @FXML
    private Label labelTurnomaquina1, labelTurnoMaquina2,labelTurnoMaquina3;


    //Image view del mazo de comer cartas
    @FXML
    private ImageView MazoBocaAbajo;

    //image view del mazo que tiene las cartas boca arriba
    @FXML
    private ImageView MazoConteoMesa;

    //cartas bot1
    @FXML
    private ImageView carta01, carta02, carta03,carta04;

    //cartas bot2
    @FXML
    private ImageView carta11,carta12,carta13,carta14;

    //cartas bot3
    @FXML
    private ImageView carta21,carta22,carta23,carta24;

    //cartas Jugador
    @FXML
    private ImageView cartaJugador1,cartaJugador2,cartaJugador3,cartaJugador4;

    //label que lleva la cuenta de la mesa
    @FXML
    private Label labelConteoActualMesa;

    //label para reflejar el nombre del jugador
    @FXML
    private Label LabelNombreJugador;

    private Juego juego;
    private List<Jugador> jugadores;
    private JugadorHumano jugadorHumano;
    private Image imagenReverso;


    @FXML
    public void initialize(String nombreJugador, int cantidadMaquinas) {
        // Crear el modelo
        juego = new Juego(cantidadMaquinas);
        jugadores = juego.getJugadores();

        // Configurar jugador humano
        jugadorHumano = (JugadorHumano) jugadores.get(0);
        jugadorHumano.setNombre(nombreJugador);

        // Mostrar nombre en etiqueta
        LabelNombreJugador.setText(nombreJugador);
        labelEstadoJuego.setText("Tú empiezas :)");

        imagenReverso = new Image(getClass().getResourceAsStream("/cincuentazo/images/cartas/0-C.png"));

        // Poner una carta inicial en la mesa
        Carta cartaInicial = juego.getMesa().getUltimaCarta();
        if (cartaInicial == null) {
            Carta carta = juego.getMazo().tomarCarta();
            juego.getMesa().colocarCarta(carta);
        }

        // Cargar interfaz inicial
        actualizarVistaInicial();
        configurarEventosCartasJugador();
        MazoBocaAbajo.setOnMouseClicked(e -> onClickTomarCarta());

    }

    //informar sobre el proceso de ejecución de turnos
    private void actualizarEstadoJuego(String mensaje){
        Platform.runLater(()->{
          labelEstadoJuego.setText(mensaje);
          System.out.println("Estado " + mensaje); //Solo para verificar en terminal
        });
    }

    private void actualizarVistaInicial() {
        // Mostrar mazo boca abajo
        //Image imagenMazo = new Image(getClass().getResourceAsStream("/cincuentazo/images/cartas/0-C.png"));
        MazoBocaAbajo.setImage(imagenReverso);

        // Mostrar carta visible de la mesa
        Carta cartaMesa = juego.getMesa().getUltimaCarta();
        if (cartaMesa != null) {
            String ruta = "/cincuentazo/images/cartas/" + cartaMesa.getValor() + "-" + cartaMesa.getPalo() + ".png";
            MazoConteoMesa.setImage(new Image(getClass().getResourceAsStream(ruta)));
            labelConteoActualMesa.setText(String.valueOf(juego.getMesa().getSumaActual()));
        }

        // Mostrar cartas del jugador humano (sus 4 cartas)
        List<Carta> manoJugador = jugadorHumano.getMano();
        ImageView[] vistas = {cartaJugador1, cartaJugador2, cartaJugador3, cartaJugador4};

        for (int i = 0; i < vistas.length; i++) {
            if (i < manoJugador.size()) {
                Carta carta = manoJugador.get(i);
                String ruta = "/cincuentazo/images/cartas/" + carta.getValor() + "-" + carta.getPalo() + ".png";
                vistas[i].setImage(new Image(getClass().getResourceAsStream(ruta)));
                vistas[i].setVisible(true); //asegurar que sea visible
            } else {
                vistas[i].setImage(null);
                vistas[i].setVisible(false);
            }
        }

        // Mostrar cartas de las máquinas boca abajo
        ImageView[][] bots = {
                {carta01, carta02, carta03, carta04},
                {carta11, carta12, carta13, carta14},
                {carta21, carta22, carta23, carta24}
        };

        for (int i = 0; i < bots.length; i++) {
            for (int j = 0; j < bots[i].length; j++) {
                bots[i][j].setImage(imagenReverso);
            }
        }
    }

    // ==== Asigna clics a las cartas del jugador humano ====
    private void configurarEventosCartasJugador() {
        ImageView[] cartasJugador = {cartaJugador1, cartaJugador2, cartaJugador3, cartaJugador4};
        JugadorHumano jugadorHumano = (JugadorHumano) juego.getJugadores().get(0);

        for (int i = 0; i < cartasJugador.length; i++) {
            int posicion = i; // solo para usar dentro del clic
            cartasJugador[i].setOnMouseClicked(event -> {
                if (jugadorHumano.getMano().size() > posicion) {
                    Carta carta = jugadorHumano.getMano().get(posicion);
                    jugadorHumano.seleccionarCartaManual(carta);
                    jugarCartaHumano(carta);
                }
            });
        }
    }

    //los bots no van a jugar si el humano no termina sus movimientos
    private boolean esperarMovimientoJugador = false;

    private void jugarCartaHumano (Carta carta){

        carta = jugadorHumano.seleccionarCarta(juego.getMesa().getSumaActual());

        actualizarEstadoJuego("turno de: " + jugadorHumano.getNombre());

        if (carta != null) {
            jugadorHumano.jugarCarta(carta);
            juego.getMesa().colocarCarta(carta);
            actualizarEstadoJuego((jugadorHumano.getNombre()) + "colocó "
                    + carta.getValor() + "de " + carta.getPalo());
            actualizarVistaInicial();

            // Verificar si hay ganador
            if (juego.hayGanador()) {
                mostrarVentanaGanador(juego.getGanador().getNombre());
                return;
            }

            esperarMovimientoJugador = true;
            actualizarEstadoJuego("Ahora toma una carta del mazo");

            //hilo para vigilar cada medio segundo si el humano ya realizó su movimiento
            new Thread(()->{
                while(esperarMovimientoJugador){
                    try{
                        Thread.sleep(500);
                    } catch (InterruptedException e){
                        Thread.currentThread().interrupt();
                    }
                }
                Platform.runLater(()-> continuarLuegoDeTomarCarta());

            }) .start();

            // Llamar directamente al método del modelo (ya maneja su propio hilo)
            /*actualizarEstadoJuego("Pasando turno a maquinas...");
            juego.siguienteTurno();

            // Esperar a que el hilo del modelo actualice el estado y luego refrescar la vista
            Platform.runLater(() -> {
                actualizarVistaInicial();
                if (juego.hayGanador()) {
                    mostrarVentanaGanador(juego.getGanador().getNombre());
                }
            });*/
        }
    }

    //actualizar label para indicar al jugador que está pasando luego de que él realizó su movimiento por completo
    private void continuarLuegoDeTomarCarta() {
        actualizarEstadoJuego("Pasando turno a las máquinas...");
        juego.siguienteTurno();

        Platform.runLater(() -> {
            actualizarVistaInicial();
            if (juego.hayGanador()) {
                mostrarVentanaGanador(juego.getGanador().getNombre());
            }
        });
    }

    private void mostrarVentanaGanador(String nombreGanador) {
        try {
            // Crear y mostrar la ventana del ganador
            new cincuentazo.views.VentanaGanadorView(nombreGanador).show();

            // Cerrar la ventana del juego actual
            Stage ventanaActual = (Stage) labelConteoActualMesa.getScene().getWindow();
            ventanaActual.close();

        } catch (IOException e) {
            System.out.println("Error al mostrar la ventana del ganador.");
        }
    }


    // ==== Evento: jugador toma carta (click en el mazo) ====
    @FXML
    private void onClickTomarCarta() {
        if (juego.esTerminado()) {
            return;
        }
        Carta carta = juego.getMazo().tomarCarta();

        if (carta != null) {
            jugadorHumano.recibirCarta(carta);
            //System.out.println("El jugador tomó una carta del mazo.");
            actualizarVistaInicial();
            actualizarEstadoJuego("Tomaste una carta");
            //vuelve a su valor inicial
            esperarMovimientoJugador = false;
        } else {
            //System.out.println("No hay más cartas en el mazo.");
            actualizarEstadoJuego("mazo vacío");
        }
    }
}
