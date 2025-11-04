package cincuentazo.models;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Carta {
    private String valor;  // "A", "2", "3", ..., "K"
    private String palo;   // "bombón", "burbuja", "bellota", "falta este"
    private ImageView imagen; //imagen asociada a cada carta

    public Carta(String valor, String palo) {
        this.valor = valor;
        this.palo = palo;
        this.imagen = crearImagenCarta();
    }

    private ImageView crearImagenCarta() {
        String ruta = "/cincuentazo/images/cartas/" + valor + "_de_" + palo + ".png";
        ImageView imagen = new ImageView(new Image(getClass().getResourceAsStream(ruta)));
        imagen.setFitWidth(90);
        imagen.setFitHeight(120);
        return imagen;
    }

    //la usamos luego en el controlador para obtener la imagen de cada carta en el fxml y asociarle el evento de mouseclicked
    public ImageView getImagen() {
        return imagen;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getPalo() {
        return palo;
    }

    public void setPalo(String palo) {
        this.palo = palo;
    }

    public int getValorNumerico() {
        if (valor.equals("A"))
            return 1;

        if (valor.equals("J") || valor.equals("Q") || valor.equals("K"))
            return 10;

        return Integer.parseInt(valor);
    }

    /*
    @Override
    public String toString() {
        return valor + " de " + palo;
    }

     */
}
