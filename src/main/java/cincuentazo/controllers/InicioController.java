package cincuentazo.controllers;

import cincuentazo.models.AlertBox;
import cincuentazo.models.MovimientoException;
import cincuentazo.views.JuegoView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Clase controladora para la vista del menú principal del juego Cincuentazo.
 * <p>
 * Esta clase gestiona todas las interacciones del usuario dentro del menú de inicio, como:
 * seleccionar el número de jugadores de la máquina, introducir el nombre del jugador,
 * iniciar el juego y mostrar las reglas del juego.
 * </p>

 *
 * @author Hilary Herrera, Dana Gómez, Laura Bernal
 * @version 2.3
 * @since 2025
 * @see cincuentazo.views.JuegoView
 * @see cincuentazo.models.AlertBox
 * @see cincuentazo.models.MovimientoException
 */
public class InicioController {

    @FXML
    private Button btnDosJugadores;

    @FXML
    private Button btnJugar;

    @FXML
    private Button btnReglas;

    @FXML
    private Button btnTresJugadores;

    @FXML
    private Button btnUnJugador;

    @FXML
    private TextField textFieldNombre;

    private String nombreJugador = "";
    private int cantidadMaquinas = 0;
    AlertBox alerta = new AlertBox();


    /**
     * Gestiona el evento que se activa cuando el usuario hace clic en el botón "Jugar".
     * <p>
     * Valida la configuración del usuario e inicia una nueva vista de juego.
     * </p>
     * @param event el evento de acción que activa el usuario.
     * @throws MovimientoException si faltan campos obligatorios (nombre o número de máquinas).
     * @throws IOException si hay un problema al cargar {@link JuegoView}.
     */
    @FXML
    void onActionJugar(ActionEvent event) {
        nombreJugador = textFieldNombre.getText();
        try {
            validarConfiguracionJuego();

            JuegoView vistaJuego = new JuegoView(nombreJugador, cantidadMaquinas);
            vistaJuego.show();

            Stage ventanaInicio = (Stage) btnJugar.getScene().getWindow();
            ventanaInicio.close();

        } catch (MovimientoException e) {
            alerta.mostrarAdvertencia("ESPERA!!", e.getMessage());
        } catch (IOException e) {
            alerta.mostrarAdvertencia("ERROR", "ocurrio un problema al iniciar el juego");
        }
    }


    /**
     * Verifica que se hayan proporcionado el nombre del jugador y el número de jugadores de la máquina.

     * @throws MovimientoException si falta algún campo de configuración.
     */
    private void validarConfiguracionJuego() throws MovimientoException {
        if (cantidadMaquinas == 0 && nombreJugador.isEmpty()) {
            throw new MovimientoException("No completaste los campos necesarios (Nombre y número de máquinas)" +
                    ", el juego no empieza hasta que lo hagas ");
        }

        if (nombreJugador.isEmpty()) {
            throw new MovimientoException("Debes rellenar el campo de nombre!!");
        }

        if (cantidadMaquinas == 0) {
            throw new MovimientoException("Debes seleccionar la cantidad de jugadores maquina");
        }
    }

    /**
     * Muestra las reglas oficiales del juego mediante un cuadro de alerta.
     *
     * @param event el evento que se activa al hacer clic en el botón «Reglas».
     */
    @FXML
    void onActionMostrarReglas(ActionEvent event) {
        alerta.mostrarReglas();
    }


    /**
     * Selecciona la configuración para dos jugadores de máquina.
     *
     * @param event el evento de acción activado por el usuario.
     */
    @FXML
    void onActionSeleccionarDosJugadores(ActionEvent event) {
        cantidadMaquinas = 2;
        actualizarBotonesSeleccion(btnDosJugadores);
    }


    /**
     * Selecciona la configuración para tres jugadores de máquina.
     * @param event el evento de acción activado por el usuario.
     */
    @FXML
    void onActionSeleccionarTresJugadores(ActionEvent event) {
        cantidadMaquinas = 3;
        actualizarBotonesSeleccion(btnTresJugadores);
    }


    /**
     * Selecciona la configuración para un reproductor de máquina.
     *
     * @param event el evento de acción activado por el usuario.
     */
    @FXML
    void onActionSeleccionarUnJugador(ActionEvent event) {
        cantidadMaquinas = 1;
        actualizarBotonesSeleccion(btnUnJugador);    }


    /**
     * Actualiza el estilo visual de los botones de selección, resaltando el seleccionado.
     *
     * @param botonSeleccionado el botón seleccionado por el jugador.
     */
    private void actualizarBotonesSeleccion(Button botonSeleccionado) {
        // normal
        String estiloNormal = "-fx-background-color: #000993; -fx-text-fill: white;"
                + "-fx-background-radius: 5;"
                + "-fx-font-family: 'Comic Sans MS'; -fx-font-size: 17;"
                + "-fx-border-color: transparent;";

        // seleccionado
        String estiloSeleccionado = "-fx-background-color: #787fe7; -fx-text-fill: white;"
                + "-fx-background-radius: 5;"
                + "-fx-font-family: 'Comic Sans MS'; -fx-font-size: 17;"
                + "-fx-border-color: transparent;";

        // restablecer estilos de los botones
        btnUnJugador.setStyle(estiloNormal);
        btnDosJugadores.setStyle(estiloNormal);
        btnTresJugadores.setStyle(estiloNormal);

        //Aplicar solo al botón seleccionado
        botonSeleccionado.setStyle(estiloSeleccionado);
    }
}

