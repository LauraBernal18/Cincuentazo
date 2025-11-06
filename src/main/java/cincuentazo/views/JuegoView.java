package cincuentazo.views;

import cincuentazo.controllers.JuegoController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class JuegoView extends Stage {

    private JuegoController juegoController;

    public JuegoView() throws IOException{


        FXMLLoader fxmlLoader = new FXMLLoader(
                getClass().getResource("/cincuentazo/fxml/juego-view.fxml")
        );

        Parent root = fxmlLoader.load();
        this.juegoController = fxmlLoader.getController();
        Scene scene = new Scene(root);
        this.setScene(scene);
        this.setTitle(" >> C I N C U E N T A Z O << ");
    }

    public JuegoController getJuegoController(){
        return this.juegoController;
    }

    public static JuegoView getInstance() throws IOException{
        if(JuegoViewHolder.INSTANCE == null){
            JuegoViewHolder.INSTANCE = new JuegoView();
        }
        return JuegoViewHolder.INSTANCE;
    }

    private static class JuegoViewHolder{
        private static JuegoView INSTANCE = null;
    }
}
