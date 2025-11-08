package cincuentazo.models;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Carta {
    private String valor;  // "A", "2", "3", ..., "K"
    private String palo;   // "pica", "trebol", "diamante", "corazón"
    private ImageView imagen; //imagen asociada a cada carta

    public Carta(String valor, String palo) {
        this.valor = valor;
        this.palo = palo;
    }

    /*private ImageView crearImagenCarta() {
        String ruta = "/cincuentazo/images/cartas/" + valor + "-" + palo + ".png";
        ImageView imagen = new ImageView(new Image(getClass().getResourceAsStream(ruta)));
        imagen.setFitWidth(90);
        imagen.setFitHeight(120);
        return imagen;
    }
    */
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

    /*public int getValorNumerico() {
        if (valor.equals("A"))
            return 1;

        if (valor.equals("J") || valor.equals("Q") || valor.equals("K"))
            return 10;

        return Integer.parseInt(valor);
    }


    @Override
    public String toString() {
        return valor + " de " + palo;
    }

     */

    public int getValorSegunReglas(int sumaMesa) {
        if (valor.equals("A")) {
            // Si el As como 10 no pasa de 50, cuenta como 10; si no, vale 1
            if (sumaMesa + 10 <= 50)
                return 10;
            else
                return 1;
        }

        if (valor.equals("J") || valor.equals("Q") || valor.equals("K"))
            return -10;

        if (valor.equals("9")){
            return 0;
        }
        // Valor numérico normal
        return Integer.parseInt(valor);
    }

}
