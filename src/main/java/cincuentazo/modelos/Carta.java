package cincuentazo.modelos;

/**

 * La clase {@code Carta} representa una carta individual utilizada en el juego "Cincuentazo".
 * Cada carta tiene un valor y un palo específicos, y su efecto en la suma total del juego
 * depende de las reglas del juego.
 *
 * <p>El valor de la carta puede representar números ("2"–"10") o rangos especiales como "A", "J", "Q" o "K".</p>
 * <ul>
 *      <li>Los ases ("A") pueden valer 1 o 10 puntos, según la elección del jugador.</li>
 *      <li>Las figuras ("J", "Q", "K") restan 10 puntos.</li>
 *      <li>La carta "9" es neutral (suma 0).</li>
 * </ul>
 *
 * @author Hilary Herrera, Dana Gómez, Laura Bernal.
 * @version 1.4
 * @since 2025
 */
public class Carta {
    private String valor;  // "A", "2", "3", ..., "K"
    private String palo;   // "pica", "trebol", "diamante", "corazón"
    private int valorParaElAS = 0; //guarda la elección del usuario


    /**
     * Crea una nueva {@code Carta} con el valor y palo especificados.
     *
     * @param valor el valor de la carta (p. ej., "A", "5", "K").
     * @param palo el palo de la carta (p. ej., "P" para Picas, "C" para Corazones, etc.).
     */
    public Carta(String valor, String palo) {
        this.valor = valor;
        this.palo = palo;
    }


    /**
     * Establece el valor elegido para una carta de As.
     * <p>Este metodo permite al jugador decidir si el As cuenta como 1 o 10 puntos.
     * Solo se aplica si la carta es un As ("A").</p>
     *
     * @param valorElegido el valor elegido para el As; debe ser 1 o 10.
     */
    //el valor del AS queda a elección del humano
    public void setValorAsElegido(int valorElegido) {
        //establece el valor solo si es un AS y si el valor elegido es valido, en este caso 1 o 10
        if (valor.equals("A") && (valorElegido == 1 || valorElegido == 10)) {
            //guardar la elección del usuario
            this.valorParaElAS = valorElegido;
        }
    }


    /**
     * Devuelve el valor nominal de la carta.
     * @return el valor nominal de la carta (p. ej., "A", "3", "Q").
     */
    public String getValor() {
        return valor;
    }


    /**
     * Devuelve el palo de la carta.
     *
     * @return el palo de la carta (p. ej., "P", "C", "D", "T").
     */
    public String getPalo() {
        return palo;
    }


    /**
     * Determina el valor numérico de la carta según las reglas del juego
     * y la suma actual en la mesa.
     *
     * <p>Reglas aplicadas:</p>
     * <ul>
     *      <li>As ("A") → cuenta como 1 o 10 (dependiendo de la elección del jugador o la suma total).</li>
     *      <li>Figuras ("J", "Q", "K") → restan 10 puntos.</li>
     *      <li>9" → suman 0 puntos.</li>
     *      <li>Otras cartas → suman su valor numérico.</li>
     * </ul>
     *
     * @param sumaMesa la suma acumulada actual en la mesa antes de jugar esta carta.
     * @return el valor numérico de la carta según las reglas del juego.
     */
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

        /*si por error o bug se genera una carta con algún valor no esperado como "S"
        * o cualquier otro, se ejecuta la excepcion no marcada, si no puedo convertir
        * la carta a valor númerico lo que hago es atrapar el error, imprimo en consola
        * que el programa falló y luego retorno 0 para que ni el juego ni la suma se vean
        * afectados*/
        try {
            return Integer.parseInt(valor);
        } catch (NumberFormatException e) {
            // excepción no marcada
            System.err.println("valor de carta inválido - '" + valor + "'");
            //retornar un que no afecta al juego
            return 0;
        }
    }


    /**
     * Comprueba si esta carta es un As.
     *
     * @return {@code true} si el valor de la carta es "A", {@code false} en caso contrario.
     */
    //retornar verdadero si el valor de la carta en la mano es "A"
    public boolean identificarAS(){
        return valor.equals("A");
    }

}
