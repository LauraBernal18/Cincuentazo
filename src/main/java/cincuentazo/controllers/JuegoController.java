package cincuentazo.controllers;
import cincuentazo.models.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import cincuentazo.models.AlertBox;

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
    //cantidad de maquinas seleccionadas
    private int cantidadMaquinas;

    private AlertBox alertBox = new AlertBox();


    @FXML
    public void initialize(String nombreJugador, int cantidadMaquinas) {
        // Crear el modelo
        //guardar el parametro para que se pueda usar sin problema dentro de esta clase
        this.cantidadMaquinas = cantidadMaquinas;
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

        //nuevo metodo
        visibilidadDeMaquinas();

        // Cargar interfaz inicial
        actualizarVistaInicial();



        configurarEventosCartasJugador();
        MazoBocaAbajo.setOnMouseClicked(e -> onClickTomarCarta());

    }

    private void visibilidadDeMaquinas(){
        //array de elementos visuales
        Label[] labelsTurno = {labelTurnomaquina1,labelTurnoMaquina2,labelTurnoMaquina3};
        ImageView[][] cartasMaquina = {
                {carta01, carta02, carta03, carta04}, //maquina 1
                {carta11, carta12, carta13, carta14}, //maquina 2
                {carta21, carta22, carta23, carta24} //maquina 4
        };

        //ocultar todo al iniciar
        for (int i = 0; i<3; i++){
            if (labelsTurno[i] != null){
                labelsTurno[i].setVisible(false);  //ocultar el label
                labelsTurno[i].setText(""); //borrar texto por defecto
            }

            for (ImageView carta : cartasMaquina[i]){
                if(carta != null){
                    carta.setVisible(false); //ocultar las cartas
                }
            }
        }

        for (int i = 0; i<this.cantidadMaquinas; i++){
            if(i + 1 < jugadores.size()){ //i +1 salta la posición 0 es decir al jugador Humano
                JugadorMaquina maquina = (JugadorMaquina) jugadores.get(i+1); //obtiene la maquina desde el modelo

                if(labelsTurno[i] != null){
                    labelsTurno[i].setText(maquina.getNombre()); //nombre: maquina 1, maquina 2...
                    labelsTurno[i].setVisible(true); //ahora el label se muestra
                }

                for (ImageView carta : cartasMaquina[i]){
                    if(carta != null){
                        carta.setVisible(true); //muestra las cartas de la maquina
                    }
                }
            }
        }
    }


    // Metodo para actualizar el texto del label estado juego con un mensaje temporal que desaparece luego de un tiempo
    private void actualizarEstadoJuego(String mensaje) {
        // Usamos Platform.runLater para asegurarnos que la actualización del label se haga en el hilo de interfaz gráfica
        // Esto es obligatorio porque solo este hilo puede modificar elementos gráficos.
        Platform.runLater(() -> labelEstadoJuego.setText(mensaje));

        // Creamos un nuevo hilo independiente para no bloquear el hilo principal.
        // Este hilo tendrá como función esperar un tiempo y luego borrar el mensaje del label.
        Thread hilo = new Thread(() -> {
            try {
                // Aquí hacemos que el hilo "duerma" 2 segs
                // Esto es para que el mensaje se muestre al usuario durante ese periodo antes de desaparecer.
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                // Si el hilo es interrumpido, marcaremos el estado de la interrupción para respetar la cancelación.
                Thread.currentThread().interrupt();
            }
            // Una vez pasado el tiempo, volvemos a usar Platform.runLater porque vamos a borrar texto del label
            Platform.runLater(() -> labelEstadoJuego.setText(""));
        });

        // Marcamos el hilo como daemon para que no impida que la aplicación JavaFX cierre si todavía está esperando.
        hilo.setDaemon(true);

        // Iniciamos la ejecución del hilo, que correrá paralelamente al hilo UI.
        hilo.start();
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

        for (int i = 0; i < bots.length; i++){
            boolean maquinaExiste = i < juego.getJugadores().size() - 1; //porque el jugador 0 es humano

            for (int j = 0; j< bots[i].length; j++){
                //verificación, solo se pone la imagen si la maquina existe y la carta está visible en pantalla
                //para evitar el acceso a elementos que están ocultos
                if(maquinaExiste && bots[i][j].isVisible()){
                    bots[i][j].setImage(imagenReverso);
                }
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
        //verificar que la carta a jugar sea un AS
        if (carta.identificarAS()) {
            //mostar alerta para que el jugador haga la elección de valor
            int valorElegido = alertBox.mostrarEleccionAS();

            //devuelve 0 cuando el usuario cancela
            if (valorElegido == 0) {
                // El usuario canceló, no jugar la carta
                actualizarEstadoJuego("Cancelaste jugar el AS");
                return;
            }

            // Establecer el valor elegido para el AS
            jugadorHumano.elegirValorAs(valorElegido);
            //informar al usuario como jugó su AS
            actualizarEstadoJuego("Jugando AS como " + valorElegido);
        }

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

        }
    }

    private void continuarLuegoDeTomarCarta() {
        actualizarEstadoJuego("Pasando turno a las máquinas...");
        juego.pasarAlSiguienteJugador(); //  MUY IMPORTANTE: pasar al primer bot
        deshabilitarInteraccionHumano();

        // Registrar callback para refrescar GUI cada jugada de máquina
        juego.setOnCambioDeTurno(() -> {
            actualizarVistaInicial();
            labelConteoActualMesa.setText(String.valueOf(juego.getMesa().getSumaActual()));

            if (juego.getJugadorActual() instanceof JugadorHumano) {
                habilitarInteraccionHumano();
                actualizarEstadoJuego("Tu turno nuevamente :)");
            }

            if (juego.hayGanador()) {
                mostrarVentanaGanador(juego.getGanador().getNombre());
            }
        });

        // Iniciar jugadas automáticas
        juego.jugarTurnosMaquinas();
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
    private boolean esTurnoJugadorHumano() {
        return juego.getJugadores().get(juego.getTurnoActual()) == jugadorHumano;
    }

    // ==== Evento: jugador toma carta (click en el mazo) ====
    @FXML
    private void onClickTomarCarta() {
        if (juego.esTerminado()) return;


        if (!esTurnoJugadorHumano()) {
            actualizarEstadoJuego("No es tu turno para tomar carta.");
            return;
        }


        if (jugadorHumano.getMano().size() >= 4) {
            actualizarEstadoJuego("No puedes tomar más cartas.");
            return;
        }
        if (juego.esTerminado()) return;

        JugadorHumano jugador = (JugadorHumano) juego.getJugadores().get(0);
        Carta carta = juego.getMazo().tomarCarta();

        if (carta != null) {
            jugador.recibirCarta(carta);
            System.out.println("Estado Tomaste una carta");
            actualizarVistaInicial();
            esperarMovimientoJugador = false;

        } else {
            System.out.println("No hay más cartas en el mazo.");
        }
    }


    private void deshabilitarInteraccionHumano() {
        cartaJugador1.setDisable(true);
        cartaJugador2.setDisable(true);
        cartaJugador3.setDisable(true);
        cartaJugador4.setDisable(true);
        MazoBocaAbajo.setDisable(true);
    }

    private void habilitarInteraccionHumano() {
        cartaJugador1.setDisable(false);
        cartaJugador2.setDisable(false);
        cartaJugador3.setDisable(false);
        cartaJugador4.setDisable(false);
        MazoBocaAbajo.setDisable(false);
    }


}
