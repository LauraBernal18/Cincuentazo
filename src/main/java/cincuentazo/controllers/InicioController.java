package cincuentazo.controllers;

import cincuentazo.models.AlertBox;
import cincuentazo.models.MovimientoException;
import cincuentazo.views.JuegoView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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

    private String nombreJugador = "";
    private int cantidadMaquinas = 0;
    AlertBox alertBox = new AlertBox();

    @FXML
    void onActionJugar(ActionEvent event) {
        nombreJugador = textFieldNombre.getText();
        try {
            validarConfiguracionJuego();

            JuegoView vistaJuego = new JuegoView(nombreJugador, cantidadMaquinas);
            vistaJuego.show();

            Stage ventanaInicio = (Stage) btnJugar.getScene().getWindow();
            ventanaInicio.close();

        } catch (MovimientoException e) {
            alertBox.mostrarAdvertencia("ESPERA!!", e.getMessage());
        } catch (IOException e) {
            alertBox.mostrarAdvertencia("ERROR", "ocurrio un problema al iniciar el juego");
        }
    }


    private void validarConfiguracionJuego() throws MovimientoException {
        if (cantidadMaquinas == 0 && nombreJugador.isEmpty()) {
            throw new MovimientoException("No completaste los campos necesarios (Nombre y número de máquinas)" +
                    ", el juego no empieza hasta que lo hagas ");
        }

        if (nombreJugador.isEmpty()) {
            throw new MovimientoException("Debes rellenar el campo de nombre!!");
        }

        if (cantidadMaquinas == 0) {
            throw new MovimientoException("Debes seleccionar la cantidad de jugadores maquina");
        }
    }

    @FXML
    void onActionMostrarReglas(ActionEvent event) {
        alertBox.mostrarReglas();
    }

    @FXML
    void onActionSeleccionarDosJugadores(ActionEvent event) {
        cantidadMaquinas = 2;
        actualizarBotonesSeleccion(btnDosJugadores);
    }

    @FXML
    void onActionSeleccionarTresJugadores(ActionEvent event) {
        cantidadMaquinas = 3;
        actualizarBotonesSeleccion(btnTresJugadores);
    }

    @FXML
    void onActionSeleccionarUnJugador(ActionEvent event) {
        cantidadMaquinas = 1;
        actualizarBotonesSeleccion(btnUnJugador);    }

    private void actualizarBotonesSeleccion(Button botonSeleccionado) {
        // normal
        String estiloNormal = "-fx-background-color: #000993; -fx-text-fill: white;"
                + "-fx-background-radius: 5;"
                + "-fx-font-family: 'Comic Sans MS'; -fx-font-size: 17;"
                + "-fx-border-color: transparent;";

        // seleccionado
        String estiloSeleccionado = "-fx-background-color: #787fe7; -fx-text-fill: white;"
                + "-fx-background-radius: 5;"
                + "-fx-font-family: 'Comic Sans MS'; -fx-font-size: 17;"
                + "-fx-border-color: transparent;";

        // restablecer estilos de los botones
        btnUnJugador.setStyle(estiloNormal);
        btnDosJugadores.setStyle(estiloNormal);
        btnTresJugadores.setStyle(estiloNormal);

        //Aplicar solo al botón seleccionado
        botonSeleccionado.setStyle(estiloSeleccionado);
    }
}

