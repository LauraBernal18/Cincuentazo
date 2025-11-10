package cincuentazo.controllers;

import cincuentazo.models.AlertBox;
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

