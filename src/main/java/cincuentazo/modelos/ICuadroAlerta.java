package cincuentazo.modelos;

/**
 * La interfaz {@code ICuadroAlerta} define el contrato para mostrar
 * diferentes tipos de diálogos de alerta en el juego "Cincuentazo".
 * <p>
 * Las implementaciones de esta interfaz se encargan de mostrar
 * mensajes informativos, advertencias y diálogos interactivos
 * que permiten al jugador tomar decisiones dentro del juego (como decidir
 * el valor de un As).
 * </p>
 *
 * @author Hilary Herrera, Dana Gómez, Laura Bernal
 * @version 1.1
 * @since 2025
 */
public interface ICuadroAlerta {

    /**
     * Muestra las reglas oficiales del juego "Cincuentazo" en un cuadro de diálogo informativo.
     * <p>Este método se suele llamar desde el menú principal o durante la partida
     * cuando el jugador solicita ver las reglas.</p>
     */
    void mostrarReglas();

    /**
     * Muestra un cuadro de diálogo de advertencia con un título y mensaje personalizados.
     * <p>Este método puede usarse para informar al usuario sobre acciones no válidas
     * o eventos importantes que requieren atención.</p>
     *
     * @param titulo el título del cuadro de diálogo de advertencia.
     * @param mensaje el mensaje de advertencia detallado que se mostrará.
     */
    void mostrarAdvertencia(String titulo, String mensaje);


    /**
     * Muestra un cuadro de diálogo de confirmación que permite al jugador seleccionar
     * el valor deseado para una carta de As.
     * <p>El cuadro de diálogo ofrece tres opciones:
     * <ul>
     *     <li>Jugar el As como 1</li>
     *     <li>Jugar el As como 10</li>
     *     <li>Cancelar (no jugar el As)</li>
     * </ul>
     * </p>
     *
     * @return {@code 1} si el jugador elige jugar el As como 1,
     *         {@code 10} si el jugador elige jugarlo como 10,
     *         o {@code 0} si el jugador cancela la selección.

     */
    int mostrarEleccionAS();
}
