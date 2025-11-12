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

/**
 * Controller class for the winner window of the Cincuentazo game.
 * <p>
 * This controller is responsible for displaying the winner’s name,
 * and managing the actions to either start a new game or exit the application.
 * </p>
 *
 * @author Hilary Herrera, Dana Gómez, Laura Bernal
 * @version 1.3
 * @since 2025
 * @see cincuentazo.views.InicioView
 */
public class VentanaGanadorController {

    @FXML
    private Label lblGanador;

    @FXML
    private Button btnNuevoJuego;

    @FXML
    private Button btnSalir;


    /**
     * Displays the winner’s name and a congratulatory message on the label.
     *
     * @param nombre the name of the player who won the game.
     */
    public void mostrarGanador(String nombre) {
        lblGanador.setText("¡" + nombre + " ha ganado el Cincuentazo!");

    }


    /**
     * Handles the event triggered when the user clicks the "New Game" button.
     * <p>
     * Displays a confirmation alert asking whether the user wants to start a new game.
     * If confirmed, it closes the current stage and opens the {@link InicioView}.
     * </p>
     *
     * @param event the action event triggered by clicking the "New Game" button.
     * @throws IOException if there is an issue loading the {@link InicioView}.
     */
    @FXML
    private void botonIniciarNuevoJuego(ActionEvent event) throws IOException {
        // Create the confirmation alert
        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar nuevo juego");
        confirmacion.setHeaderText(null);
        confirmacion.setContentText("¿Deseas comenzar una nueva partida?");

        // Show the alert and save the pressed button
        ButtonType respuesta = confirmacion.showAndWait().orElse(ButtonType.CANCEL);

        // If the user presses ACCEPT, generate a new game
        if (respuesta == ButtonType.OK) {
            InicioView inicioView = InicioView.getInstance();
            inicioView.show();

            //Close stage
            Node fuente =(Node)event.getSource();
            Stage stage = (Stage)fuente.getScene().getWindow();
            stage.close();

        }
    }

    /**
     * Handles the event triggered when the user clicks the "Exit" button.
     * <p>
     * Terminates the application completely using {@code System.exit(0)}.
     * </p>
     *
     * @param evento the action event triggered by clicking the "Exit" button.
     */
    @FXML
    private void botonSalir(ActionEvent evento) {
        System.exit(0);
    }
}
