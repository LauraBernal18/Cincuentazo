package cincuentazo.vistas;

import cincuentazo.controladores.JuegoControlador;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Representa la ventana principal de juego de la aplicación Cincuentazo.
 * <p>
 * Esta vista carga el diseño {@code juego-vista.fxml} y lo conecta con
 * el {@link JuegoControlador}. Inicializa
 * el controlador con el nombre del jugador y el número de oponentes de IA seleccionados.
 * </p>
 * <p>
 * La ventana se muestra maximizada para ofrecer una vista completa del tablero,
 * las cartas y las acciones de los jugadores durante la partida.
 * </p>
 *
 * @author Hilary Herrera, Dana Gómez, Laura Bernal
 * @version 1.1
 * @since 2025
 * @see JuegoControlador
 * @see javafx.stage.Stage
 * @see javafx.fxml.FXMLLoader
 */
public class JuegoVista extends Stage {
    /**
     * Controlador que gestiona toda la lógica del juego y las interacciones del usuario.
     */
    private JuegoControlador juegoControlador;


    /**
     * Crea e inicializa la vista principal del juego para la partida de Cincuentazo.
     * <p>
     * Este constructor carga el archivo FXML correspondiente, lo conecta con
     * su controlador e inicializa la partida con el nombre del jugador
     * y el número de jugadores IA seleccionados.
     * </p>
     *
     * @param nombreJugador el nombre del jugador humano que inicia la partida.
     * @param cantidadMaquinas el número de oponentes IA en la partida.
     * @throws IOException si no se encuentra o no se puede cargar el archivo FXML.
     */
    public JuegoVista(String nombreJugador, int cantidadMaquinas) throws IOException{


        FXMLLoader fxmlLoader = new FXMLLoader(
                getClass().getResource("/cincuentazo/fxml/juego-vista.fxml")
        );

        Parent root = fxmlLoader.load();
        this.juegoControlador = fxmlLoader.getController();
        juegoControlador.initialize(nombreJugador,cantidadMaquinas);

        Scene escena = new Scene(root);
        this.setScene(escena);
        this.setTitle(" >> C I N C U E N T A Z O << ");

        this.setMaximized(true);
    }

}
