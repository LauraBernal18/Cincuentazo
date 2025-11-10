package cincuentazo.controllers;

import cincuentazo.views.InicioView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class VentanaGanadorController {

    @FXML
    private Label lblGanador;

    @FXML
    private Button btnNuevoJuego;

    @FXML
    private Button btnSalir;

    public void mostrarGanador(String nombre) {
        lblGanador.setText("¡" + nombre + " ha ganado el Cincuentazo!");

    }

    @FXML
    private void botonIniciarNuevoJuego(ActionEvent event) throws IOException {
        // Create the confirmation alert
        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar nuevo juego");
        confirmacion.setHeaderText(null);
        confirmacion.setContentText("¿Deseas comenzar una nueva partida?");

        // Show the alert and save the pressed button
        ButtonType respuesta = confirmacion.showAndWait().orElse(ButtonType.CANCEL);


    /*"Displays the confirmation window (showAndWait()),
    waits for the user to press a button (such as OK or CANCEL),
    and stores the button that the user chose in the variable response.
    " The method showAndWait() returns a value of type Optional<ButtonType>.
    This means that it may or may not contain a value,
    depending on whether the user pressed something.
    For example:If the player presses OK,
    it returns ButtonType.OK.If they press Cancel,
    it returns ButtonType.CANCEL.If they close the window without clicking,
    the Optional has no value.
    That is why .orElse(ButtonType.CANCEL) is used:"If nothing was pressed,
    assume it was CANCEL.".*/


        // If the user presses ACCEPT, generate a new game
        if (respuesta == ButtonType.OK) {
            InicioView inicioView = InicioView.getInstance();
            inicioView.show();

            //Close stage
            Node source =(Node)event.getSource();
            Stage stage = (Stage)source.getScene().getWindow();
            stage.close();

        }
    }

    @FXML
    private void botonSalir(ActionEvent evento) {
        System.exit(0);
    }
}
