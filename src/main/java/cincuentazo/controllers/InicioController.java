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

/**
 * Controller class for the main menu view of the Cincuentazo game.
 * <p>
 * This class handles all user interactions within the start menu, such as
 * selecting the number of machine players, entering the player's name,
 * starting the game, and displaying the game rules.
 * </p>
 *
 * @author Hilary Herrera, Dana Gómez, Laura Bernal
 * @version 2.3
 * @since 2025
 * @see cincuentazo.views.JuegoView
 * @see cincuentazo.models.AlertBox
 * @see cincuentazo.models.MovimientoException
 */
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
    AlertBox alerta = new AlertBox();


    /**
     * Handles the event triggered when the user clicks the "Play" button.
     * <p>
     * Validates the user’s configuration and starts a new game view.
     * </p>
     *
     * @param event the action event triggered by the user.
     * @throws MovimientoException if required fields (name or machine count) are missing.
     * @throws IOException if there is an issue loading the {@link JuegoView}.
     */
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
            alerta.mostrarAdvertencia("ESPERA!!", e.getMessage());
        } catch (IOException e) {
            alerta.mostrarAdvertencia("ERROR", "ocurrio un problema al iniciar el juego");
        }
    }


    /**
     * Validates that the player’s name and the number of machine players are provided.
     *
     * @throws MovimientoException if any configuration field is missing.
     */
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


    /**
     * Displays the official game rules using an alert box.
     *
     * @param event the event triggered by clicking the “Rules” button.
     */
    @FXML
    void onActionMostrarReglas(ActionEvent event) {
        alerta.mostrarReglas();
    }


    /**
     * Selects the configuration for two machine players.
     *
     * @param event the action event triggered by the user.
     */
    @FXML
    void onActionSeleccionarDosJugadores(ActionEvent event) {
        cantidadMaquinas = 2;
        actualizarBotonesSeleccion(btnDosJugadores);
    }


    /**
     * Selects the configuration for three machine players.
     *
     * @param event the action event triggered by the user.
     */
    @FXML
    void onActionSeleccionarTresJugadores(ActionEvent event) {
        cantidadMaquinas = 3;
        actualizarBotonesSeleccion(btnTresJugadores);
    }


    /**
     * Selects the configuration for one machine player.
     *
     * @param event the action event triggered by the user.
     */
    @FXML
    void onActionSeleccionarUnJugador(ActionEvent event) {
        cantidadMaquinas = 1;
        actualizarBotonesSeleccion(btnUnJugador);    }


    /**
     * Updates the visual style of the selection buttons, highlighting the selected one.
     *
     * @param botonSeleccionado the button that was selected by the player.
     */
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

