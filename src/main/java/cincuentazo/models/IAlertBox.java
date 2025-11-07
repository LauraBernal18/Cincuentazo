package cincuentazo.models;

public interface IAlertBox {
    /**
     * Muestra las reglas del juego.
     */
    void mostrarReglas();


    /**
     * Muestra advertencias cuando el jugador no completa los campos necesarios
     * antes de iniciar el juego (nombre y cantidad de maquinas)
     */
    void mostrarAdvertencia(String titulo, String mensaje);
}
