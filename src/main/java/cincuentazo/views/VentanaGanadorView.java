package cincuentazo.views;

import cincuentazo.controllers.VentanaGanadorController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;


/**
 * Representa la ventana de anuncio del ganador del juego Cincuentazo.
 * <p>
 * Esta vista se muestra al final de una partida para mostrar el nombre del jugador ganador.
 * Carga el diseño {@code ventana-ganador-view.fxml} e interactúa con su controlador
 * {@link cincuentazo.controllers.VentanaGanadorController} para mostrar el nombre del ganador.
 * </p>
 * <p>
 * La ventana se muestra automáticamente al crearse y garantiza que solo
 * exista una instancia mediante el patrón singleton.
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
     * Controlador responsable de gestionar la vista del ganador y actualizar sus elementos de interfaz de usuario.
     */
    private VentanaGanadorController controller;


    /**
     * Crea e inicializa la ventana del ganador.
     * <p>
     * Carga el diseño FXML, lo vincula a su controlador y establece
     * el nombre del ganador para que se muestre en pantalla. La ventana se muestra automáticamente
     * después de su creación.
     * </p>
     *
     * @param nombreGanador el nombre del jugador que ganó la partida.
     * @throws IOException si no se encuentra el archivo FXML o no se carga correctamente.
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
     * Obtiene el controlador asociado a esta vista ganadora.
     *
     * @return la instancia de {@link VentanaGanadorController} que gestiona esta ventana.
     */
    public VentanaGanadorController getController() {
        return this.controller;
    }

    /**
     * Devuelve la instancia única de {@code VentanaGanadorView}.
     * <p>
     * Garantiza que solo se pueda crear una instancia de esta ventana a la vez.
     * Si la instancia no existe, se crea con el nombre del ganador proporcionado.
     * </p>
     *
     * @param nombreGanador el nombre del ganador que se mostrará.
     * @return la única instancia de {@code VentanaGanadorView}.
     * @throws IOException si no se puede cargar el archivo FXML.
     */
    public static VentanaGanadorView getInstance(String nombreGanador) throws IOException {
        if (VentanaGanadorHolder.INSTANCE == null) {
            VentanaGanadorHolder.INSTANCE = new VentanaGanadorView(nombreGanador);
        }
        return VentanaGanadorHolder.INSTANCE;
    }

    /**
     * Clase interna estática que contiene la instancia singleton.
     * Implementa el patrón de inicialización diferida.
     */
    private static class VentanaGanadorHolder {
        private static VentanaGanadorView INSTANCE = null;
    }
}
