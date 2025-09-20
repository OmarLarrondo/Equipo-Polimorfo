package main.Academia.Paquetes;

import main.PaquetesHerramientas.PaquetesHerramientas;

/**
 * Interfaz que define el contrato para construir paquetes de herramientas ninja.
 * Implementa el patrón Builder permitiendo la construcción fluida y modular de paquetes.
 * Utiliza programación funcional para encadenamiento de métodos.
 *
 * @author Equipo-Polimorfo
 * @version 1.0
 */
public interface PaqueteBuilder {

    /**
     * Reinicia el builder para construir un nuevo paquete limpio.
     * Permite reutilizar la misma instancia del builder para múltiples construcciones.
     *
     * @return Esta instancia del builder para encadenamiento de métodos
     */
    PaqueteBuilder reset();

    /**
     * Agrega kunais al paquete en construcción.
     * Permite encadenamiento funcional para construcción fluida.
     *
     * @param cantidad Número de kunais a agregar al paquete
     * @return Esta instancia del builder para encadenamiento de métodos
     */
    PaqueteBuilder addKunai(int cantidad);

    /**
     * Agrega shurikens al paquete en construcción.
     * Permite encadenamiento funcional para construcción fluida.
     *
     * @param cantidad Número de shurikens a agregar al paquete
     * @return Esta instancia del builder para encadenamiento de métodos
     */
    PaqueteBuilder addShuriken(int cantidad);

    /**
     * Agrega papeles bomba al paquete en construcción.
     * Permite encadenamiento funcional para construcción fluida.
     *
     * @param cantidad Número de papeles bomba a agregar al paquete
     * @return Esta instancia del builder para encadenamiento de métodos
     */
    PaqueteBuilder addPapelBomba(int cantidad);

    /**
     * Agrega bombas de humo al paquete en construcción.
     * Permite encadenamiento funcional para construcción fluida.
     *
     * @param cantidad Número de bombas de humo a agregar al paquete
     * @return Esta instancia del builder para encadenamiento de métodos
     */
    PaqueteBuilder addBombaHumo(int cantidad);

    /**
     * Agrega botiquines al paquete en construcción.
     * Permite encadenamiento funcional para construcción fluida.
     *
     * @param cantidad Número de botiquines a agregar al paquete
     * @return Esta instancia del builder para encadenamiento de métodos
     */
    PaqueteBuilder addBotiquin(int cantidad);

    /**
     * Finaliza la construcción y retorna el paquete de herramientas completo.
     * Este método representa el paso final del patrón Builder.
     *
     * @return Paquete de herramientas construido con las especificaciones agregadas
     */
    PaquetesHerramientas build();
}
