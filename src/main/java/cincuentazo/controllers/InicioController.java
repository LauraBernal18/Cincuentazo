package cincuentazo.controllers;

import cincuentazo.models.AlertBox;
import cincuentazo.models.Juego;
import cincuentazo.views.JuegoView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class InicioController {

    @FXML
    private Button btnDosJugadores;

    @FXML
    private Button btnJugar;

    @FXML
    private Button btnReglas;

    @FXML
    private Button btnTresJugadores;

    @FXML
    private Button btnUnJugador;

    @FXML
    private TextField textFieldNombre;

    private String nombreJugador = " ";
    private int cantidadMaquinas = 0;
    AlertBox alertBox = new AlertBox();


    @FXML
    void onActionJugar(ActionEvent event) {
        nombreJugador = textFieldNombre.getText();

        if (cantidadMaquinas == 0 && nombreJugador.isEmpty()){
            alertBox.mostrarAdvertencia("ESPERA!!", "No completaste ninguno de los campos necesarios \n" +
                    "el juego no empieza hasta que lo hagas :(");
            return;
        }

        if (nombreJugador.isEmpty()){
            alertBox.mostrarAdvertencia("ESPERA!!" ,"Debes rellenar el campo de nombre!!");
            return;
        }

        if (cantidadMaquinas == 0){
            alertBox.mostrarAdvertencia("ESPERA!!", "Debes seleccionar la cantidad de jugadores maquina");
            return;
        }

        //creación de modelo de juego y asignar nombre ingresado al jugador humano
        //dentro del arreglo el jugador humano ocuparía la posición 0
        Juego juego = new Juego(cantidadMaquinas);
        juego.getJugadores().get(0).setNombre(nombreJugador);


        //cargar la ventana principal usando excepciones en caso de errores para encontrar archivo
        try{
            JuegoView vistaJuego = new JuegoView(nombreJugador, cantidadMaquinas);
            vistaJuego.show();

            Stage ventanaInicio = (Stage) btnJugar.getScene().getWindow();
            ventanaInicio.close();
        } catch (IOException e){
            alertBox.mostrarAdvertencia("ERROR","ocurrio un problema al iniciar el juego");
        }
    }

    @FXML
    void onActionMostrarReglas(ActionEvent event) {
        alertBox.mostrarReglas();
    }

    @FXML
    void onActionSeleccionarDosJugadores(ActionEvent event) {
        cantidadMaquinas = 2;
    }

    @FXML
    void onActionSeleccionarTresJugadores(ActionEvent event) {
        cantidadMaquinas = 3;
    }

    @FXML
    void onActionSeleccionarUnJugador(ActionEvent event) {
        cantidadMaquinas = 1;

    }

}

