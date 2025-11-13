package cincuentazo;

import cincuentazo.views.InicioView;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;
/**

 * Punto de entrada de la aplicación Cincuentazo.
 * <p>
 * Esta clase inicializa el entorno JavaFX y abre la ventana principal
 * del juego mediante {@link cincuentazo.views.InicioView}.
 * Sirve como punto de partida para la interfaz gráfica de usuario (GUI)
 * y gestiona la configuración del escenario principal.
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
     * Inicia la aplicación JavaFX y muestra el menú principal del juego.
     * <p>
     * Este método se llama automáticamente por el entorno de ejecución de JavaFX cuando
     * se inicia la aplicación. Carga y muestra la {@code InicioView}
     * pantalla como la primera ventana del programa.
     * </p>
     *
     * @param stage la pantalla principal proporcionada por el entorno de ejecución de JavaFX.
     * @throws IOException si no se puede cargar el recurso FXML {@link InicioView}.
     */
    @Override
    public void start(Stage stage) throws IOException {

        InicioView inicioView = InicioView.getInstance();
        inicioView.show();
    }
}
