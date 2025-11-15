package cincuentazo.controladores;

import cincuentazo.vistas.InicioVista;
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
 * Clase controladora para la ventana del ganador del juego Cincuentazo.
 * <p>
 * Este controlador se encarga de mostrar el nombre del ganador,
 * y de gestionar las acciones para iniciar una nueva partida o salir de la aplicación.
 * </p>
 *
 * @author Hilary Herrera, Dana Gómez, Laura Bernal
 * @version 1.3
 * @since 2025
 * @see InicioVista
 */
public class VentanaGanadorControlador {

    @FXML
    private Label lblGanador;

    @FXML
    private Button btnNuevoJuego;

    @FXML
    private Button btnSalir;


    /**
     * Muestra el nombre del ganador y un mensaje de felicitación en la etiqueta.
     * @param nombre el nombre del jugador que ganó la partida.

     */
    public void mostrarGanador(String nombre) {
        lblGanador.setText("¡" + nombre + " ha ganado el Cincuentazo!");

    }


    /**
     * Maneja el evento que se activa cuando el usuario hace clic en el botón "Nueva partida".
     * <p>
     * Muestra una alerta de confirmación preguntando si el usuario desea comenzar una nueva partida.
     * Si se confirma, cierra la etapa actual y abre la {@link InicioVista}.
     * </p>
     * @param evento el evento de acción que se activa al hacer clic en el botón "Nueva partida".
     * @throws IOException si hay un problema al cargar la {@link InicioVista}.

     */
    @FXML
    private void botonIniciarNuevoJuego(ActionEvent evento) throws IOException {
        // Crear alerta de confirmación
        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar nuevo juego");
        confirmacion.setHeaderText(null);
        confirmacion.setContentText("¿Deseas comenzar una nueva partida?");

        // Mostrar la alerta y guardar la elección
        ButtonType respuesta = confirmacion.showAndWait().orElse(ButtonType.CANCEL);

        // si el usuario presiona OK se genera un nuevo juego
        if (respuesta == ButtonType.OK) {
            InicioVista inicioVista = InicioVista.getInstance();
            inicioVista.show();

            //Cerrar ventana
            Node fuente =(Node)evento.getSource();
            Stage stage = (Stage)fuente.getScene().getWindow();
            stage.close();

        }
    }

    /**
     * Maneja el evento que se activa cuando el usuario hace clic en el botón "Salir".
     * <p>
     * Finaliza la aplicación completamente usando {@code System.exit(0)}.
     * </p>
     * @param evento el evento de acción que se activa al hacer clic en el botón "Salir".
     */
    @FXML
    private void botonSalir(ActionEvent evento) {
        System.exit(0);
    }
}
