package cincuentazo.views;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Representa la ventana inicial (menú principal) del juego Cincuentazo.
 * <p>
 * Esta vista carga el diseño FXML {@code inicio-view.fxml}, configura
 * el escenario con un icono y un título personalizados, e impide el cambio de tamaño de la ventana.
 * Sigue un patrón Singleton para garantizar que solo exista una instancia de
 * {@code InicioView} durante la ejecución.
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
     * Crea e inicializa la ventana principal del juego cargando
     * el archivo FXML y configurando las propiedades de la escena.
     *
     * @throws IOException si el archivo FXML no se puede cargar o no existe.
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
     * Devuelve una instancia única de {@code InicioView}, siguiendo
     * el patrón Singleton.
     * <p>
     * Si no existe ninguna instancia, se creará la primera vez
     * que se llame a este método.
     * </p>
     *
     * @return la única instancia de {@code InicioView}.
     * @throws IOException si no se puede cargar el archivo FXML.
     */
    public static InicioView getInstance() throws IOException {
        if (InicioViewHolder.INSTANCE == null) {
            InicioViewHolder.INSTANCE = new InicioView();
        }
        return InicioViewHolder.INSTANCE;
    }


    /**
     * Clase interna estática que mantiene la instancia única
     * de {@code InicioView}.
     * <p>
     * Este enfoque garantiza la inicialización diferida y segura para subprocesos de la
     * instancia Singleton sin necesidad de sincronización explícita.
     * </p>
     */
    private static class InicioViewHolder {
        private static InicioView INSTANCE = null;
    }
}