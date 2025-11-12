package cincuentazo.views;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Represents the initial window (main menu) of the Cincuentazo game.
 * <p>
 * This view loads the FXML layout {@code inicio-view.fxml}, sets up
 * the stage with a custom icon and title, and prevents window resizing.
 * It follows a Singleton pattern to ensure only one instance of
 * {@code InicioView} exists during runtime.
 * </p>
 *
 * @author Hilary Herrera, Dana Gómez, Laura Bernal
 * @version 1.1
 * @since 2025
 * @see javafx.stage.Stage
 * @see javafx.fxml.FXMLLoader
 */
public class InicioView extends Stage {


    /**
     * Creates and initializes the main window of the game by loading
     * the FXML file and setting the scene properties.
     *
     * @throws IOException if the FXML file cannot be loaded or is missing.
     */
    public InicioView() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(
                getClass().getResource("/cincuentazo/fxml/inicio-view.fxml")
        );
        Parent root = fxmlLoader.load();
        Scene escena = new Scene(root);
        this.setScene(escena);
        this.setTitle("Inicio");
        this.getIcons().add(new Image(
                getClass().getResourceAsStream("/cincuentazo/images/inicio/iconoburbuja.png")
        ));
        this.setResizable(false);
    }


    /**
     * Returns a unique instance of {@code InicioView}, following
     * the Singleton pattern.
     * <p>
     * If no instance exists, it will be created the first time
     * this method is called.
     * </p>
     *
     * @return the single instance of {@code InicioView}.
     * @throws IOException if the FXML file cannot be loaded.
     */
    public static InicioView getInstance() throws IOException {
        if (InicioViewHolder.INSTANCE == null) {
            InicioViewHolder.INSTANCE = new InicioView();
        }
        return InicioViewHolder.INSTANCE;
    }


    /**
     * Inner static holder class that maintains the unique instance
     * of {@code InicioView}.
     * <p>
     * This approach ensures thread-safe lazy initialization of the
     * Singleton instance without requiring explicit synchronization.
     * </p>
     */
    private static class InicioViewHolder {
        private static InicioView INSTANCE = null;
    }
}