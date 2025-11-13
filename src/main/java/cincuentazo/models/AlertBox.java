package cincuentazo.models;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;

import java.util.Optional;

/**
 * The {@code AlertBox} class provides different types of graphical alerts
 * for displaying information, warnings, and user choices within the Cincuentazo game.
 * It implements the {@link IAlertBox} interface.
 *
 * <p>This class uses JavaFX {@link Alert} dialogs to show customized messages
 * with specific visual styles for user interaction.</p>
 *
 * @author Hilary Herrera, Dana Gómez, Laura Bernal.
 * @version 1.3
 * @since 2025
 * @see javafx.scene.control.Alert
 * @see IAlertBox
 */

public class AlertBox implements IAlertBox {

    @Override
    public void mostrarReglas() {
        String reglas = """
            🎯 REGLAS DEL CINCUENTAZO 🎯
            
            🏆 OBJETIVO:
            Ser el ÚLTIMO JUGADOR en pie
            
            🎮 PREPARACIÓN:
            • 4 cartas para cada jugador
            • 1 carta inicial en la mesa
            • Mazo restante boca abajo
            
            🃏 VALOR DE LAS CARTAS:
            • 2-8 y 10 → Suman su valor
            • 9 → No suma ni resta (0)
            • J, Q, K → Restan 10
            • A → Suma 1 o 10 (a elección)
            
            🔄 TURNO DE JUEGO:
            1. Juega 1 carta que NO haga superar 50
            2. Colócala boca arriba en la mesa
            3. Roba 1 carta del mazo
            4. Si no puedes jugar → ¡ELIMINADO! ❌
            
            ⚡ REGLAS ESPECIALES:
            • Jugador eliminado: Sus cartas van al final del mazo
            
            🎊 FIN DEL JUEGO:
            Cuando solo quede UN JUGADOR → ¡GANADOR! 🏅

            """;

        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Reglas del Cincuentazo");
        alerta.setHeaderText("📖 CÓMO JUGAR AL CINCUENTAZO");
        alerta.setContentText(reglas);

        DialogPane panel = alerta.getDialogPane();
        panel.setStyle(
                "-fx-background-color: #edade1;" +   // fondo rosa
                        "-fx-font-size: 14px;" +
                        "-fx-font-weight: bold;"
        );

        alerta.setWidth(600);
        alerta.setHeight(800);
        alerta.showAndWait();
    }


    /**
     * Displays a warning alert with a custom title and message.
     *
     * @param titulo  the title of the alert window.
     * @param mensaje the warning message to be displayed to the user.
     */
    @Override
    public void mostrarAdvertencia(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.WARNING);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.getContentText();
        alerta.setContentText(mensaje);

        DialogPane panel = alerta.getDialogPane();
        panel.setStyle(
                "-fx-background-color: #FFF3CD;" +
                "-fx-font-size: 14px;"
        );

        alerta.showAndWait();
    }

    /**
     * Displays a confirmation dialog allowing the user to choose
     * the value of the Ace card (AS) when it appears in their hand.
     *
     * <p>The user can select between playing the Ace as value {@code 1} or {@code 10},
     * or cancel the action.</p>
     *
     * @return {@code 1} if the player chooses to play the Ace as 1,
     *         {@code 10} if the player chooses to play it as 10,
     *         or {@code 0} if the player cancels the action.
     */
    @Override
    public int mostrarEleccionAS(){
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle("Elegir el valor del AS");
        alerta.setHeaderText("En tu mano hay un AS");
        alerta.setContentText("¿Cómo deseas jugar tu AS?");

        ButtonType botonUno = new ButtonType("Jugar AS como 1");
        ButtonType botonDiez = new ButtonType("Jugar AS como 10");
        ButtonType botonCancelar = new ButtonType("No jugar AS ahora");

        alerta.getButtonTypes().setAll(botonUno,botonDiez,botonCancelar);

        DialogPane panel = alerta.getDialogPane();
        panel.setStyle(
                "-fx-background-color:  #f5e8f5;" +
                        "-fx-font-size: 14px;"
        );

        //mostrar la alerta y esperar respuesta del usuario
        Optional<ButtonType> resultado = alerta.showAndWait();

        //verifica que el usuario presionó el botón y no solo cerro la  pestaña
        if(resultado.isPresent()){
            if(resultado.get() == botonUno){
                return 1;
            } else if (resultado.get() == botonDiez){
                return 10;
            }

        }
        //retorna 0 si el usuario presiona cancelar
        return 0;

    }

    public static void mostrarError(String titulo, String cabecera, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle(titulo);
        alerta.setHeaderText(cabecera);
        alerta.setContentText(mensaje);

        DialogPane panel = alerta.getDialogPane();
        panel.setStyle(
                "-fx-background-color: #FFE6E6;" +
                        "-fx-font-size: 14px;"
        );

        alerta.showAndWait();
    }
}
