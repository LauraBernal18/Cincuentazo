package cincuentazo.models;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;

import java.util.Optional;

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

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Reglas del Cincuentazo");
        alert.setHeaderText("📖 CÓMO JUGAR AL CINCUENTAZO");
        alert.setContentText(reglas);

        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.setStyle(
                "-fx-background-color: #D6E8FF;" +   // fondo rosa
                        "-fx-font-size: 14px;" +
                        "-fx-font-weight: bold;"
        );

        alert.setWidth(600);
        alert.setHeight(800);
        alert.showAndWait();
    }

    @Override
    public void mostrarAdvertencia(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.getContentText();
        alert.setContentText(mensaje);

        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.setStyle(
                "-fx-background-color: #FFF3CD;" +
                "-fx-font-size: 14px;"
        );

        alert.showAndWait();
    }

    @Override
    public int mostrarEleccionAS(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Elegir el valor del AS");
        alert.setHeaderText("En tu mano hay un AS");
        alert.setContentText("¿Cómo deseas jugar tu AS?");

        ButtonType botonUno = new ButtonType("Jugar AS como 1");
        ButtonType botonDiez = new ButtonType("Jugar AS como 10");
        ButtonType botonCancelar = new ButtonType("No jugar AS ahora");

        alert.getButtonTypes().setAll(botonUno,botonDiez,botonCancelar);

        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.setStyle(
                "-fx-background-color:  #f5e8f5;" +
                        "-fx-font-size: 14px;"
        );

        //mostrar la alerta y esperar respuesta del usuario
        Optional<ButtonType> resultado = alert.showAndWait();

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
}
