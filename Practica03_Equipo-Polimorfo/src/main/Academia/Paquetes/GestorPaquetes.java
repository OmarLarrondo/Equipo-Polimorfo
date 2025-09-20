package Academia.Paquetes;

import PaquetesHerramientas.PaquetesHerramientas;
import java.util.function.Function;

/**
 * Director del patrón Builder para gestionar la construcción de paquetes de herramientas ninja.
 * Coordina la creación de diferentes tipos de paquetes predefinidos y permite
 * construcción personalizada.
 *
 * @author Equipo-Polimorfo
 * @version 1.0
 */
public class GestorPaquetes {

    /** Builder utilizado para construir los paquetes */
    private final PaqueteBuilder builder;

    /**
     * Constructor que inicializa el gestor con un builder específico.
     *
     * @param builder Builder que se utilizará para construir los paquetes
     */
    public GestorPaquetes(PaqueteBuilder builder) {
        this.builder = builder;
    }

    /**
     * Construye un paquete básico predefinido.
     * Contiene: 1 Kunai, 1 Shuriken, 1 Botiquín.
     *
     * @return Paquete básico de herramientas ninja construido
     */
    public PaquetesHerramientas construirPaqueteBasico() {
        return ejecutarConstruccion(b -> b
            .reset()
            .addKunai(1)
            .addShuriken(1)
            .addBotiquin(1)
        );
    }

    /**
     * Construye un paquete avanzado predefinido.
     * Contiene: 2 Shurikens, 3 Papeles Bomba, 2 Bombas de Humo, 2 Botiquines.
     *
     * @return Paquete avanzado de herramientas ninja construido
     */
    public PaquetesHerramientas construirPaqueteAvanzado() {
        return ejecutarConstruccion(b -> b
            .reset()
            .addShuriken(2)
            .addPapelBomba(3)
            .addBombaHumo(2)
            .addBotiquin(2)
        );
    }

    /**
     * Construye un paquete táctico predefinido.
     * Contiene: 3 Kunais, 2 Shurikens, 4 Papeles Bomba, 2 Bombas de Humo.
     *
     * @return Paquete táctico de herramientas ninja construido
     */
    public PaquetesHerramientas construirPaqueteTactico() {
        return ejecutarConstruccion(b -> b
            .reset()
            .addKunai(3)
            .addShuriken(2)
            .addPapelBomba(4)
            .addBombaHumo(2)
        );
    }

    /**
     * Proporciona el builder configurado para construcción personalizada.
     * Permite al cliente dirigir manualmente la construcción del paquete.
     * El builder se reinicia automáticamente para una construcción limpia.
     *
     * @return Builder listo para configuración personalizada
     */
    public PaqueteBuilder dirigirConstruccionPersonalizada() {
        return builder.reset();
    }

    /**
     * Método auxiliar que ejecuta una configuración de construcción específica.
     * Aplica programación funcional para encapsular la lógica de construcción.
     *
     * @param configuracion Función que configura el builder según las especificaciones
     * @return Paquete construido según la configuración aplicada
     */
    private PaquetesHerramientas ejecutarConstruccion(Function<PaqueteBuilder, PaqueteBuilder> configuracion) {
        return configuracion.apply(builder).build();
    }
}
