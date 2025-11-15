package cincuentazo.modelos;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;

import java.util.Optional;

/**
 * La clase {@code CuadroAlerta} proporciona diferentes tipos de alertas gráficas
 * para mostrar información, advertencias y opciones del usuario dentro del juego Cincuentazo.
 * Implementa la interfaz {@link ICuadroAlerta}.
 *
 * <p>Esta clase utiliza los diálogos {@link Alert} de JavaFX para mostrar mensajes personalizados
 * con estilos visuales específicos para la interacción del usuario.</p>
 *
 * @author Hilary Herrera, Dana Gómez, Laura Bernal.
 * @version 1.3
 * @since 2025
 * @see javafx.scene.control.Alert
 * @see ICuadroAlerta
 */

public class CuadroAlerta implements ICuadroAlerta {

    /**
     * Muestra una alerta de información
     * Usa un string de reglas ya definido para el cuerpo del mensaje.
     */
    @Override
    public void mostrarReglas() {
        String reglas = """
            REGLAS DEL CINCUENTAZO
            
            OBJETIVO:
            Ser el ÚLTIMO JUGADOR en pie
            
            PREPARACIÓN:
            • 4 cartas para cada jugador
            • 1 carta inicial en la mesa
            • Mazo restante boca abajo
            
            VALOR DE LAS CARTAS:
            • 2-8 y 10 → Suman su valor
            • 9 → No suma ni resta (0)
            • J, Q, K → Restan 10
            • A → Suma 1 o 10 (a elección)
            
            TURNO DE JUEGO:
            1. Juega 1 carta que NO haga superar 50
            2. Colócala boca arriba en la mesa
            3. Roba 1 carta del mazo
            4. Si no puedes jugar → ¡ELIMINADO! 
            
            ⚡ REGLAS ESPECIALES:
            • Jugador eliminado: Sus cartas van al final del mazo
            
            FIN DEL JUEGO:
            Cuando solo quede UN JUGADOR → ¡GANADOR! 

            """;

        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Reglas del Cincuentazo");
        alerta.setHeaderText("📖 CÓMO JUGAR AL CINCUENTAZO");
        alerta.setContentText(reglas);

        DialogPane panel = alerta.getDialogPane();
        panel.setStyle(
                "-fx-background-color: #afaded;" +   // fondo rosa
                        "-fx-font-size: 14px;" +
                        "-fx-font-weight: bold;"
        );

        alerta.setWidth(600);
        alerta.setHeight(800);
        alerta.showAndWait();
    }


    /**
     * Muestra una alerta de advertencia con un título y un mensaje personalizados.

     * @param titulo el título de la ventana de alerta.
     * @param mensaje el mensaje de advertencia que se mostrará al usuario.
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
                "-fx-background-color: rgba(178,189,245,0.84);" +
                "-fx-font-size: 15px;" + "-fx-font-family: FreeMono;" +
                "-fx-font-weight: bold;"
        );

        alerta.showAndWait();
    }

    /**
     * Muestra un cuadro de diálogo de confirmación que permite al usuario elegir
     * el valor del As (AS) cuando aparece en su mano.

     * <p>El usuario puede seleccionar entre jugar el As con valor {@code 1} o {@code 10},
     * o cancelar la acción.</p>
     *
     * @return {@code 1} si el jugador elige jugar el As como 1,
     *         {@code 10} si el jugador elige jugarlo como 10,
     *          o {@code 0} si el jugador cancela la acción.

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
                "-fx-background-color:  #fd95bd;" +
                        "-fx-font-size: 14px;" + "-fx-font-family: FreeMono;" + "-fx-font-weight: bold"
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
    /**
     * Muestra una alerta de error con un título, cabecera y un mensaje personalizados.

     * @param titulo el título de la ventana de alerta.
     * @param cabecera el título del error
     * @param mensaje el mensaje de error que se mostrará al usuario.
     */
    public static void mostrarError(String titulo, String cabecera, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle(titulo);
        alerta.setHeaderText(cabecera);
        alerta.setContentText(mensaje);

        DialogPane panel = alerta.getDialogPane();
        panel.setStyle(
                "-fx-background-color: #c6fab9;" +
                        "-fx-font-size: 14px;" + "" +
                        "-fx-font-family: FreeMono;"
        );

        alerta.showAndWait();
    }
}
