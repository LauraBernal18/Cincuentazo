package cincuentazo.views;

import cincuentazo.controllers.JuegoController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class JuegoView extends Stage {

    private JuegoController juegoController;

    public JuegoView(String nombreJugador, int cantidadMaquinas) throws IOException{


        FXMLLoader fxmlLoader = new FXMLLoader(
                getClass().getResource("/cincuentazo/fxml/juego-view.fxml")
        );

        Parent root = fxmlLoader.load();
        this.juegoController = fxmlLoader.getController();
        juegoController.initialize(nombreJugador,cantidadMaquinas);

        Scene scene = new Scene(root);
        this.setScene(scene);
        this.setTitle(" >> C I N C U E N T A Z O << ");

        this.setMaximized(true);
    }

}
