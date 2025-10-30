package cincuentazo.views;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Clase que muestra la ventana de inicio del juego.
 * Se encarga de cargar el diseño FXML, crear la escena y ajustar las propiedades de la ventana.
 *  @author Laura Valentina Bernal Lozada, Dana Sofia Gomez Manrique, Hilary Herrera Erazo
 *  @version 1.0
 */
public class InicioView extends Stage {

    /**
     * Crea la ventana de inicio.
     * Carga el archivo FXML, configura la escena y define el título, el ícono y otras propiedades.
     *
     * @throws IOException si ocurre un error al cargar el archivo FXML
     */
    public InicioView() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(
                getClass().getResource("/cincuentazo/fxml/inicio-view.fxml")
        );
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        this.setScene(scene);
        this.setTitle("Inicio");
        this.getIcons().add(new Image(
                getClass().getResourceAsStream("/cincuentazo/images/inicio/iconoburbuja.png")
        ));
        this.setResizable(false);
    }

    /**
     * Devuelve la única instancia de esta clase.
     * Usa el patrón Singleton para asegurar que solo exista una ventana de inicio.
     *
     * @return la instancia única de {@code InicioView}
     * @throws IOException si ocurre un error al crear la instancia
     */
    public static InicioView getInstance() throws IOException {
        if (InicioViewHolder.INSTANCE == null) {
            InicioViewHolder.INSTANCE = new InicioView();
        }
        return InicioViewHolder.INSTANCE;
    }

    /**
     * Clase interna que guarda la instancia única de {@code InicioView}.
     * Asegura que la instancia se cree solo cuando se necesite.
     */
    private static class InicioViewHolder {
        private static InicioView INSTANCE = null;
    }
}