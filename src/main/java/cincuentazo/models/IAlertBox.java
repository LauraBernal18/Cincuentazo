package cincuentazo.models;

/**
 * The {@code IAlertBox} interface defines the contract for displaying
 * different types of alert dialogs in the "Cincuentazo" game.
 * <p>
 * Implementations of this interface are responsible for showing
 * informational messages, warnings, and interactive dialogs
 * that allow the player to make in-game choices (such as deciding
 * the value of an Ace card).
 * </p>
 *
 * @author Hilary Herrera, Dana Gómez, Laura Bernal
 * @version 1.1
 * @since 2025
 */
public interface IAlertBox {

    /**
     * Displays the official game rules of "Cincuentazo" in an informational dialog box.
     * <p>This method is typically called from the main menu or during gameplay
     * when the player requests to see the rules.</p>
     */
    void mostrarReglas();

    /**
     * Displays a warning message dialog with a custom title and message.
     * <p>This method can be used to inform the user of invalid actions
     * or important events that require attention.</p>
     *
     * @param titulo  the title of the warning dialog.
     * @param mensaje the detailed warning message to display.
     */
    void mostrarAdvertencia(String titulo, String mensaje);


    /**
     * Displays a confirmation dialog that allows the player to select
     * the desired value for an Ace card.
     * <p>The dialog provides three options:
     * <ul>
     *   <li>Play Ace as 1</li>
     *   <li>Play Ace as 10</li>
     *   <li>Cancel (do not play Ace)</li>
     * </ul>
     * </p>
     *
     * @return {@code 1} if the player chooses to play the Ace as 1,
     *         {@code 10} if the player chooses to play it as 10,
     *         or {@code 0} if the player cancels the selection.
     */
    int mostrarEleccionAS();
}
