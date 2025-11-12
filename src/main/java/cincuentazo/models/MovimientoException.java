package cincuentazo.models;

/**
 * Custom exception class used to handle invalid or illegal move actions
 * within the Cincuentazo game logic.
 * <p>
 * This exception is thrown when a player attempts an invalid move,
 * such as playing a card that exceeds the allowed limit (over 50)
 * or performing an action that violates the game rules.
 * </p>
 *
 * @author Hilary Herrera, Dana Gómez, Laura Bernal
 * @version 1.2
 * @since 2025
 * @see Exception
 */
public class MovimientoException extends Exception {


    /**
     * Constructs a new {@code MovimientoException} with the specified detail message.
     *
     * @param mensaje the detail message explaining the cause of the exception.
     */
    public MovimientoException(String mensaje){
        super(mensaje);
    }
}
