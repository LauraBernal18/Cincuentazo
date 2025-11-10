package cincuentazo.models;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Carta {
    private String valor;  // "A", "2", "3", ..., "K"
    private String palo;   // "pica", "trebol", "diamante", "corazón"
    private ImageView imagen; //imagen asociada a cada carta
    private int valorParaElAS = 0; //guarda la elección del usuario

    public Carta(String valor, String palo) {
        this.valor = valor;
        this.palo = palo;
    }

    //el valor del AS queda a elección del humano
    public void setValorAsElegido(int valorElegido) {
        //establece el valor solo si es un AS y si el valor elegido es valido, en este caso 1 o 10
        if (valor.equals("A") && (valorElegido == 1 || valorElegido == 10)) {
            //guardar la elección del usuario
            this.valorParaElAS = valorElegido;
        }
    }


    public String getValor() {
        return valor;
    }



    public String getPalo() {
        return palo;
    }

    public void setPalo(String palo) {
        this.palo = palo;
    }


    public int getValorSegunReglas(int sumaMesa) {
        //verificar que sea AS
        if (valor.equals("A")) {
            //si el humano ya escogio un valor se usa ese, si no lo hizo se usa la logica normal
            if(valorParaElAS != 0){
                return valorParaElAS;
            }


            //Si el As como 10 no pasa de 50, cuenta como 10; si no, vale 1
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

    //retornar verdadero si el valor de la carta en la mano es "A"
    public boolean identificarAS(){
        return valor.equals("A");
    }

}
