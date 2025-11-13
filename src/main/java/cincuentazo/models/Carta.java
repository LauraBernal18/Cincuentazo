package cincuentazo.models;

/**
 * The {@code Carta} class represents a single playing card used in the game "Cincuentazo".
 * Each card has a specific value and suit (palo), and its effect on the total game sum
 * depends on the game's rules.
 *
 * <p>The card value may represent numbers ("2"–"10") or special ranks such as "A", "J", "Q", or "K".</p>
 * <ul>
 *   <li>Aces ("A") can count as either 1 or 10 points, depending on the player's choice.</li>
 *   <li>Face cards ("J", "Q", "K") subtract 10 points.</li>
 *   <li>The "9" card is neutral (adds 0).</li>
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
     * Constructs a new {@code Carta} with the specified value and suit.
     *
     * @param valor the face value of the card (e.g., "A", "5", "K").
     * @param palo  the suit of the card (e.g., "P" for Spades, "C" for Hearts, etc.).
     */
    public Carta(String valor, String palo) {
        this.valor = valor;
        this.palo = palo;
    }


    /**
     * Sets the chosen value for an Ace card.
     * <p>This method allows the player to decide whether the Ace counts as 1 or 10 points.
     * Only applies if the card is an Ace ("A").</p>
     *
     * @param valorElegido the chosen value for the Ace; must be either 1 or 10.
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
     * Returns the face value of the card.
     *
     * @return the card's face value (e.g., "A", "3", "Q").
     */
    public String getValor() {
        return valor;
    }


    /**
     * Returns the suit of the card.
     *
     * @return the card's suit (e.g., "P", "C", "D", "T").
     */
    public String getPalo() {
        return palo;
    }


    /**
     * Determines the numerical value of the card based on the game's rules
     * and the current sum on the table.
     *
     * <p>Rules applied:</p>
     * <ul>
     *   <li>Ace ("A") → counts as 1 or 10 (depending on player choice or total sum).</li>
     *   <li>Face cards ("J", "Q", "K") → subtract 10 points.</li>
     *   <li>Card "9" → adds 0 points.</li>
     *   <li>Other cards → add their numeric value.</li>
     * </ul>
     *
     * @param sumaMesa the current accumulated sum on the table before this card is played.
     * @return the numeric value of the card according to game rules.
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
     * Checks whether this card is an Ace.
     *
     * @return {@code true} if the card value is "A", otherwise {@code false}.
     */
    //retornar verdadero si el valor de la carta en la mano es "A"
    public boolean identificarAS(){
        return valor.equals("A");
    }

}
