package cincuentazo.models;

/**
 * Clase de excepción personalizada utilizada para gestionar acciones de movimiento inválidas o ilegales
 * dentro de la lógica del juego Cincuentazo.
 * <p>
 * Esta excepción se produce cuando un jugador intenta un movimiento inválido,
 * como jugar una carta que supera el límite permitido (más de 50)
 * o realizar una acción que infringe las reglas del juego.
 * </p>
 *
 * @author Hilary Herrera, Dana Gómez, Laura Bernal
 * @version 1.2
 * @since 2025
 * @see Exception
 */
public class MovimientoException extends Exception {


    /**
     * Crea una nueva {@code MovimientoException} con el mensaje de detalle especificado.
     *
     * @param mensaje el mensaje de detalle que explica la causa de la excepción.
     */
    public MovimientoException(String mensaje){
        super(mensaje);
    }
}
