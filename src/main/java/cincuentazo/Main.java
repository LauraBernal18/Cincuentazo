package cincuentazo;

import cincuentazo.views.InicioView;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        InicioView inicioView = InicioView.getInstance();
        inicioView.show();
    }
}
