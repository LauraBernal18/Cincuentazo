module cincuentazo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens cincuentazo to javafx.fxml;
    exports cincuentazo.models to javafx.fxml;
    exports cincuentazo.controllers to javafx.fxml;

    exports cincuentazo;
}