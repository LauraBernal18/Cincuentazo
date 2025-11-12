package cincuentazo;

import cincuentazo.views.InicioView;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;
/**
 * Entry point of the Cincuentazo application.
 * <p>
 * This class initializes the JavaFX environment and launches the main
 * window of the game through {@link cincuentazo.views.InicioView}.
 * It serves as the starting point for the graphical user interface (GUI)
 * and handles the setup of the primary stage.
 * </p>
 *
 * @author Hilary Herrera, Dana Gómez, Laura Bernal
 * @version 1.0
 * @since 2025
 * @see javafx.application.Application
 * @see cincuentazo.views.InicioView
 */
public class Main extends Application {

    /**
     * Starts the JavaFX application and displays the main game menu.
     * <p>
     * This method is automatically called by the JavaFX runtime when
     * the application is launched. It loads and shows the {@code InicioView}
     * stage as the first window of the program.
     * </p>
     *
     * @param stage the primary stage provided by the JavaFX runtime.
     * @throws IOException if the {@link InicioView} FXML resource cannot be loaded.
     */
    @Override
    public void start(Stage stage) throws IOException {

        InicioView inicioView = InicioView.getInstance();
        inicioView.show();
    }
}
