package cincuentazo.views;

import cincuentazo.controllers.JuegoController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Represents the main gameplay window of the Cincuentazo application.
 * <p>
 * This view loads the {@code juego-view.fxml} layout and connects it to
 * the {@link cincuentazo.controllers.JuegoController}. It initializes
 * the controller with the player's name and the selected number of AI opponents.
 * </p>
 * <p>
 * The window is displayed maximized to provide a complete view of the board,
 * cards, and players’ actions during the match.
 * </p>
 *
 * @author Hilary Herrera, Dana Gómez, Laura Bernal
 * @version 1.1
 * @since 2025
 * @see cincuentazo.controllers.JuegoController
 * @see javafx.stage.Stage
 * @see javafx.fxml.FXMLLoader
 */
public class JuegoView extends Stage {
    /**
     * Controller that manages all the gameplay logic and user interactions.
     */
    private JuegoController juegoController;


    /**
     * Creates and initializes the main game view for the Cincuentazo match.
     * <p>
     * This constructor loads the corresponding FXML file, connects it with
     * its controller, and initializes the match with the player's name
     * and the number of machine players selected.
     * </p>
     *
     * @param nombreJugador     the name of the human player starting the game.
     * @param cantidadMaquinas  the number of AI opponents in the match.
     * @throws IOException if the FXML file cannot be found or loaded.
     */
    public JuegoView(String nombreJugador, int cantidadMaquinas) throws IOException{


        FXMLLoader fxmlLoader = new FXMLLoader(
                getClass().getResource("/cincuentazo/fxml/juego-view.fxml")
        );

        Parent root = fxmlLoader.load();
        this.juegoController = fxmlLoader.getController();
        juegoController.initialize(nombreJugador,cantidadMaquinas);

        Scene escena = new Scene(root);
        this.setScene(escena);
        this.setTitle(" >> C I N C U E N T A Z O << ");

        this.setMaximized(true);
    }

}
