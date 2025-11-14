module cincuentazo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires org.junit.jupiter.api;


    opens cincuentazo to javafx.fxml;
    opens cincuentazo.controladores to javafx.fxml;
    exports cincuentazo.modelos to javafx.fxml;
    exports cincuentazo.controladores to javafx.fxml;

    exports cincuentazo;
}