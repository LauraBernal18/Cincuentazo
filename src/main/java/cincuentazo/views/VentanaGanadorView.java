package cincuentazo.views;

import cincuentazo.controllers.VentanaGanadorController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class VentanaGanadorView extends Stage {

    private VentanaGanadorController controller;

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

    public VentanaGanadorController getController() {
        return this.controller;
    }

    public static VentanaGanadorView getInstance(String nombreGanador) throws IOException {
        if (VentanaGanadorHolder.INSTANCE == null) {
            VentanaGanadorHolder.INSTANCE = new VentanaGanadorView(nombreGanador);
        }
        return VentanaGanadorHolder.INSTANCE;
    }

    private static class VentanaGanadorHolder {
        private static VentanaGanadorView INSTANCE = null;
    }
}
