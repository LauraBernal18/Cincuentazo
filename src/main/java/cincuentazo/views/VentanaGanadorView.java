package cincuentazo.views;

import cincuentazo.controllers.VentanaGanadorController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;


/**
 * Represents the winner announcement window of the Cincuentazo game.
 * <p>
 * This view is displayed at the end of a match to show the name of the player who won.
 * It loads the {@code ventana-ganador-view.fxml} layout and interacts with its controller
 * {@link cincuentazo.controllers.VentanaGanadorController} to display the winner's name.
 * </p>
 * <p>
 * The window is automatically shown upon creation and ensures that only
 * one instance exists using a singleton pattern.
 * </p>
 *
 * @author Hilary Herrera, Dana Gómez, Laura Bernal
 * @version 1.1
 * @since 2025
 * @see cincuentazo.controllers.VentanaGanadorController
 * @see javafx.stage.Stage
 */
public class VentanaGanadorView extends Stage {
    /**
     * Controller responsible for managing the winner view and updating its UI elements.
     */
    private VentanaGanadorController controller;


    /**
     * Constructs and initializes the winner window.
     * <p>
     * Loads the FXML layout, links it to its controller, and sets the
     * winner's name to be displayed on screen. The window is automatically
     * shown after creation.
     * </p>
     *
     * @param nombreGanador the name of the player who won the game.
     * @throws IOException if the FXML file cannot be found or loaded properly.
     */
    public VentanaGanadorView(String nombreGanador) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(
                getClass().getResource("/cincuentazo/fxml/ventana-ganador-view.fxml")
        );
        Parent root = fxmlLoader.load();

        // Obtener el controlador y mostrar el nombre del ganador
        this.controller = fxmlLoader.getController();
        this.controller.mostrarGanador(nombreGanador);

        Scene scene = new Scene(root);
        this.setScene(scene);
        this.setTitle("¡Ganador del Cincuentazo!");
        this.show();
    }

    /**
     * Gets the controller associated with this winner view.
     *
     * @return the {@link VentanaGanadorController} instance managing this window.
     */
    public VentanaGanadorController getController() {
        return this.controller;
    }

    /**
     * Returns the singleton instance of {@code VentanaGanadorView}.
     * <p>
     * Ensures that only one instance of this window can be created at a time.
     * If the instance does not exist, it is created with the provided winner name.
     * </p>
     *
     * @param nombreGanador the name of the winner to display.
     * @return the single instance of {@code VentanaGanadorView}.
     * @throws IOException if the FXML file cannot be loaded.
     */
    public static VentanaGanadorView getInstance(String nombreGanador) throws IOException {
        if (VentanaGanadorHolder.INSTANCE == null) {
            VentanaGanadorHolder.INSTANCE = new VentanaGanadorView(nombreGanador);
        }
        return VentanaGanadorHolder.INSTANCE;
    }

    /**
     * Inner static holder class for the singleton instance.
     * Implements the lazy initialization holder idiom.
     */
    private static class VentanaGanadorHolder {
        private static VentanaGanadorView INSTANCE = null;
    }
}
