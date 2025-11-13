package cincuentazo.controladores;
import cincuentazo.modelos.*;
import cincuentazo.vistas.VentanaGanadorVista;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import cincuentazo.modelos.CuadroAlerta;

import java.io.IOException;
import java.util.List;

public class JuegoControlador {

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

    @FXML
    private Label labelTurnoActual;

    private Juego juego;
    private List<Jugador> jugadores;
    private JugadorHumano jugadorHumano;
    private Image imagenReverso;
    //cantidad de maquinas seleccionadas
    private int cantidadMaquinas;

    private CuadroAlerta alertaCuadro = new CuadroAlerta();

    //los bots no van a jugar si el humano no termina sus movimientos
    private boolean esperarMovimientoJugador = false;

    @FXML
    public void initialize(String nombreJugador, int cantidadMaquinas) {
        // Crear el modelo
        //guardar el parametro para que se pueda usar sin problema dentro de esta clase
        this.cantidadMaquinas = cantidadMaquinas;
        juego = new Juego(nombreJugador, cantidadMaquinas, labelTurnoActual);
        juego.jugarTurnosMaquinas();
        jugadores = juego.getJugadores();

        // Configurar jugador humano
        jugadorHumano = (JugadorHumano) jugadores.get(0);
        jugadorHumano.setNombre(nombreJugador);

        // Mostrar nombre en etiqueta
        LabelNombreJugador.setText(nombreJugador);
        labelEstadoJuego.setText("Tú empiezas :)");
        labelTurnoActual.setText(" ");

        //imagen de reverso nueva en cada juego iniciado
        String[] palos = Mazo.getPalos(); // Acceder a los palos del Mazo
        String paloAleatorio = palos[(int)(Math.random() * palos.length)];
        imagenReverso = new Image(getClass().getResourceAsStream("/cincuentazo/imagenes/cartas/0-" + paloAleatorio
                + ".png"));

        // Poner una carta inicial en la mesa
        Carta cartaInicial = juego.getMesa().getUltimaCarta();
        if (cartaInicial == null) {
            Carta carta = juego.getMazo().tomarCarta();
            juego.getMesa().colocarCartaEnMesa(carta);
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
    private void actualizarLabelEstadoJuego(String mensaje) {
        // Usamos Platform.runLater para asegurarnos que la actualización del label se haga en el hilo de interfaz gráfica
        // Esto es obligatorio porque solo este hilo puede modificar elementos gráficos.
        Platform.runLater(() -> labelEstadoJuego.setText(mensaje));

        // Creamos un nuevo hilo independiente para no bloquear el hilo principal.
        // Este hilo tendrá como función esperar un tiempo y luego borrar el mensaje del label.
        Thread hilo = new Thread(() -> {
            try {
                // Aquí hacemos que el hilo "duerma" 10 segs
                // Esto es para que el mensaje se muestre al usuario durante ese periodo antes de desaparecer.
                Thread.sleep(30000);
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

    private void actualizarLabelTurnos(String texto) {
        Platform.runLater(() -> labelTurnoActual.setText(texto));
    }

    private void actualizarVistaInicial() {
        // Mostrar mazo boca abajo
        //Image imagenMazo = new Image(getClass().getResourceAsStream("/cincuentazo/images/cartas/0-C.png"));
        MazoBocaAbajo.setImage(imagenReverso);

        // Mostrar carta visible de la mesa
        Carta cartaMesa = juego.getMesa().getUltimaCarta();
        if (cartaMesa != null) {
            String ruta = "/cincuentazo/imagenes/cartas/" + cartaMesa.getValor() + "-" + cartaMesa.getPalo() + ".png";
            MazoConteoMesa.setImage(new Image(getClass().getResourceAsStream(ruta)));
            labelConteoActualMesa.setText(String.valueOf(juego.getMesa().getSumaActual()));
        }

        try {
            // Mostrar cartas del jugador humano (sus 4 cartas)
            List<Carta> manoJugador = jugadorHumano.getMano();
            ImageView[] vistas = {cartaJugador1, cartaJugador2, cartaJugador3, cartaJugador4};

            for (int i = 0; i < vistas.length; i++) {
                if (i < manoJugador.size()) {
                    Carta carta = manoJugador.get(i);
                    String ruta = "/cincuentazo/imagenes/cartas/" + carta.getValor() + "-" + carta.getPalo() + ".png";
                    vistas[i].setImage(new Image(getClass().getResourceAsStream(ruta)));
                    vistas[i].setVisible(true);
                } else {
                    vistas[i].setImage(null);
                    vistas[i].setVisible(false);
                }
            }

            /*se usa si el programa intenta acceder a una posición no existente dentro de una lista,
            * evitando que el juego se detenga*/
        } catch (IndexOutOfBoundsException e) {
            System.err.println("problema al mostrar cartas del jugador");
            /*se usa para evitar que el programa se detenga si por ejemplo la ruta de una imagen está mal
            * o no existe*/
        } catch (NullPointerException e) {
            System.err.println("recurso de imagen no encontrado");
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

    private void jugarCartaHumano (Carta carta){
        // Si ya jugó una carta en este turno
        if (esperarMovimientoJugador) {
            actualizarLabelEstadoJuego("Ya jugaste. Toma carta del mazo");
            return;
        }

        //verificar si hay ganador antes de ejecutar su turno
        if (juego.hayGanador()) {
            mostrarVentanaGanador(juego.getGanador().getNombre());
            return;
        }

        if (carta == null) {
            actualizarLabelEstadoJuego("Selecciona una carta válida.");
            return;
        }

        int sumaActual = juego.getMesa().getSumaActual();

        // Si es un AS, pedir valor antes de validar
        if (carta.identificarAS()) {
            int valorElegido = alertaCuadro.mostrarEleccionAS();

            if (valorElegido == 0) {
                actualizarLabelEstadoJuego("Cancelaste jugar el AS.");
                return;
            }

            if (valorElegido == 10 && (sumaActual + 10 > 50)) {
                valorElegido = 1;
                actualizarLabelEstadoJuego("No puedes usar el AS como 10. Se jugará como 1.");
            }

            jugadorHumano.elegirValorAs(valorElegido);
        }

        // Calcular nueva suma
        int nuevaSuma = sumaActual + carta.getValorSegunReglas(sumaActual);
        boolean cartaValida = nuevaSuma <= 50;

        // Verificar si hay otras cartas válidas
        boolean hayCartasValidas = false;
        for (Carta c : jugadorHumano.getMano()) {
            int posibleSuma = sumaActual + c.getValorSegunReglas(sumaActual);
            if (posibleSuma <= 50) {
                hayCartasValidas = true;
                break;
            }
        }

        if (!cartaValida && hayCartasValidas) {
            try {
                throw new MovimientoExcepcion(
                        "La carta seleccionada haría que la suma pase de " + sumaActual + " a " + nuevaSuma +
                                ".\n\nTienes otras cartas en tu mano que puedes jugar sin pasarte de 50."
                );
            } catch (MovimientoExcepcion e) {
                CuadroAlerta.mostrarError("Movimiento Inválido", "No puedes jugar esa carta", e.getMessage());
                return;
            }
        } else if (!cartaValida && !hayCartasValidas) {
            juego.eliminarJugadorHumano(jugadorHumano, "No tienes cartas válidas para jugar, has sido eliminado.");
            juego.pasarAlSiguienteJugador();
            actualizarVistaInicial();

            if (juego.hayGanador()) {
                mostrarVentanaGanador(juego.getGanador().getNombre());
                return;
            }

            if (juego.getJugadorActual() instanceof JugadorMaquina && !juego.hayGanador()) {
                juego.jugarTurnosMaquinas();
            }
            return;
        }

        carta = jugadorHumano.seleccionarCartaAJugar(juego.getMesa().getSumaActual());

        actualizarLabelTurnos("turno de: " + jugadorHumano.getNombre());

        if (carta != null) {
            jugadorHumano.jugarCarta(carta);
            juego.getMesa().colocarCartaEnMesa(carta);
            actualizarVistaInicial();

            // Verificar si hay ganador
            if (juego.hayGanador()) {
                mostrarVentanaGanador(juego.getGanador().getNombre());
                return;
            }

            esperarMovimientoJugador = true;
            actualizarLabelEstadoJuego("Ahora toma una carta del mazo");

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

        if (juego.hayGanador()) {
            mostrarVentanaGanador(juego.getGanador().getNombre());
            return;
        }

        actualizarLabelEstadoJuego("Pasando turno a las máquinas...");
        juego.pasarAlSiguienteJugador(); //  MUY IMPORTANTE: pasar al primer bot

        if (juego.hayGanador()) {
            mostrarVentanaGanador(juego.getGanador().getNombre());
            return;
        }

        deshabilitarInteraccionHumano();

        // Registrar callback para refrescar GUI cada jugada de máquina
        juego.setOnCambioDeTurno(() -> {
            actualizarVistaInicial();
            labelConteoActualMesa.setText(String.valueOf(juego.getMesa().getSumaActual()));

            if (juego.hayGanador()) {
                mostrarVentanaGanador(juego.getGanador().getNombre());
                return;
            }

            if (juego.getJugadorActual() instanceof JugadorHumano) {
                habilitarInteraccionHumano();
                actualizarLabelEstadoJuego("Tu turno nuevamente :)");
                actualizarLabelTurnos("turno de: " + jugadorHumano.getNombre());
            }

        });

        // Iniciar jugadas automáticas
        juego.jugarTurnosMaquinas();
    }

    private void mostrarVentanaGanador(String nombreGanador) {
        Platform.runLater(() -> {
        try {
            // Crear y mostrar la ventana del ganador
            new VentanaGanadorVista(nombreGanador).show();

            // Cerrar la ventana del juego actual
            Stage ventanaActual = (Stage) labelConteoActualMesa.getScene().getWindow();
            ventanaActual.close();

        } catch (IOException e) {
            System.out.println("Error al mostrar la ventana del ganador.");
        }
        });
    }

    private boolean esTurnoJugadorHumano() {
        return juego.getJugadores().get(juego.getTurnoActual()) == jugadorHumano;
    }

    // ==== Evento: jugador toma carta (click en el mazo) ====
    @FXML
    private void onClickTomarCarta() {
        if (juego.esTerminado()) return;


        if (!esTurnoJugadorHumano()) {
            actualizarLabelEstadoJuego("No es tu turno para tomar carta.");
            return;
        }


        if (jugadorHumano.getMano().size() >= 4) {
            actualizarLabelEstadoJuego("No puedes tomar más cartas.");
            return;
        }

        if (!esperarMovimientoJugador) {
            actualizarLabelEstadoJuego("Primero juega una carta!!.");
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
