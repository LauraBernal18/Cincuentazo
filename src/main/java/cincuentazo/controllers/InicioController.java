package cincuentazo.controllers;

import cincuentazo.views.JuegoView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

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

    @FXML
    void onActionJugar(ActionEvent event) {
        try {
            JuegoView juegoView = JuegoView.getInstance();

            juegoView.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onActionMostrarReglas(ActionEvent event) {

    }

    @FXML
    void onActionSeleccionarDosJugadores(ActionEvent event) {

    }

    @FXML
    void onActionSeleccionarTresJugadores(ActionEvent event) {

    }

    @FXML
    void onActionSeleccionarUnJugador(ActionEvent event) {

    }

}

