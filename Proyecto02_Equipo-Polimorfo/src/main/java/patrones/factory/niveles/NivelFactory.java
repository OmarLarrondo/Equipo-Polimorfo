package patrones.factory.niveles;


/**
 * Interfaz {@code NivelFactory} que define el contrato para las fábricas 
 * encargadas de crear instancias de {@link Nivel} a partir de una configuración dada.
 * 
 * <p>
 * Este patrón de diseño permite desacoplar la creación de los diferentes 
 * tipos de niveles (clásico, inteligente, personalizado, etc.) de su 
 * representación concreta, facilitando la extensión y el mantenimiento del código.
 * </p>
 *
 * <p>
 * Cada implementación concreta de esta interfaz deberá determinar cómo se 
 * construye un nivel en función de los parámetros especificados en 
 * {@link ConfigNivel}.
 * </p>
 * 
 * @author  Equipo-polimorfo
 * @version 1.0
 */
public interface NivelFactory {

    /**
     * Crea un nuevo nivel en base a la configuración especificada.
     * @param conf la configuración del nivel, que contiene los parámetros
     *             necesarios para su creación (por ejemplo, dificultad, patron, datosMapa, etc.).
     * @return una instancia de {@link Nivel} configurada según los valores de {@code conf}.
     */
    Nivel crearNivel(ConfigNivel conf);
}
