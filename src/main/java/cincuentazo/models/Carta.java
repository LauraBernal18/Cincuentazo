package cincuentazo.models;

public class Carta {
    private String valor;  // "A", "2", "3", ..., "K"
    private String palo;   // "bombón", "burbuja", "bellota", "falta este"

    public Carta(String valor, String palo) {
        this.valor = valor;
        this.palo = palo;
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
