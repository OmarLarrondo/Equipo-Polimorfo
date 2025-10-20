package modelo.estrategia;

import java.util.List;

import modelo.componente.ComponentePC;

/**
 * Clase que encapsula el resultado de una verificacion de compatibilidad de componentes.
 * Almacena informacion sobre si los componentes son compatibles, las advertencias generadas
 * durante la verificacion y la lista de componentes que fueron adaptados para lograr compatibilidad.
 *
 * <p>Esta clase es utilizada por las implementaciones de {@link EstrategiaCompatibilidad}
 * para retornar el resultado completo de la verificacion, incluyendo:
 * <ul>
 *   <li>Estado de compatibilidad (compatible/incompatible)</li>
 *   <li>Advertencias sobre problemas potenciales</li>
 *   <li>Componentes que requirieron adaptadores (por ejemplo, CPUs AMD)</li>
 * </ul>
 *
 * @author Equipo-Polimorfo
 * @version 1.0
 */
public class ResultadoCompatibilidad {

    /**Indica si los componentes verificados son compatibles entre si.*/
    private boolean esCompatible;

    /**Lista de advertencias generadas durante la verificacion de compatibilidad.*/
    private List<String> advertencias;

    /**Lista de componentes que fueron adaptados para lograr compatibilidad.*/
    private List<ComponentePC> componentesAdaptados;

    /**
     * Construye un nuevo resultado de compatibilidad con la informacion especificada.
     *
     * @param esCompatible indica si los componentes son compatibles (true) o no (false)
     * @param advertencias lista de advertencias generadas durante la verificacion,
     *                    puede estar vacia si no hay advertencias
     * @param componentesAdaptados lista de componentes que fueron adaptados usando
     *                            el patron Adapter, puede estar vacia si no se requirieron adaptaciones
     */
    public ResultadoCompatibilidad(boolean esCompatible, List<String> advertencias,
            List<ComponentePC> componentesAdaptados) {
        this.esCompatible = esCompatible;
        this.advertencias = advertencias;
        this.componentesAdaptados = componentesAdaptados;
    }

    /**
     * Verifica si los componentes son compatibles.
     *
     * @return true si los componentes son compatibles, false en caso contrario
     */
    public boolean isCompatible(){
        return esCompatible;
    }

    /**
     * Obtiene la lista de advertencias generadas durante la verificacion de compatibilidad.
     *
     * @return lista de advertencias como cadenas de texto
     */
    public List<String> getAdvertencias(){
        return advertencias;
    }

    /**
     * Obtiene la lista de componentes que fueron adaptados para lograr compatibilidad.
     *
     * @return lista de componentes adaptados
     */
    public List<ComponentePC> getComponentesAdaptados(){
        return componentesAdaptados;
    }

}
