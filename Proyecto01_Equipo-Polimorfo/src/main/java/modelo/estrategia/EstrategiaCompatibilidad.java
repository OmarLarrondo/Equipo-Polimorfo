package modelo.estrategia;

import java.util.List;
import modelo.componente.ComponentePC;

/**
 * Interfaz que define el contrato para las estrategias de verificacion de compatibilidad
 * de componentes de PC. Implementa el patron de diseno Strategy permitiendo intercambiar
 * algoritmos de verificacion de compatibilidad en tiempo de ejecucion.
 *
 * <p>Las implementaciones concretas de esta interfaz deben verificar la compatibilidad
 * entre componentes como CPU, MotherBoard, GPU, RAM, Discos y FuenteAlimentacion,
 * considerando las especificaciones tecnicas de cada fabricante (Intel, AMD).
 *
 * <p>Existen tres estrategias principales:
 * <ul>
 *   <li>CompatibilidadIntel - Para sistemas basados en procesadores Intel</li>
 *   <li>CompatibilidadAMD - Para sistemas basados en procesadores AMD</li>
 *   <li>CompatibilidadMixta - Para sistemas que combinan componentes Intel y AMD</li>
 * </ul>
 *
 * @author Equipo-Polimorfo
 * @version 1.0
 */
public interface EstrategiaCompatibilidad {

    /**
     * Verifica la compatibilidad de una lista de componentes de PC.
     * Analiza las especificaciones tecnicas de cada componente y determina
     * si son compatibles entre si, generando advertencias cuando sea necesario.
     *
     * @param componentes lista de componentes a verificar, que puede incluir CPU,
     *                   MotherBoard, GPU, RAM, Discos y FuenteAlimentacion
     * @return un objeto ResultadoCompatibilidad que indica si los componentes
     *         son compatibles, las advertencias generadas y los componentes adaptados
     */
    public ResultadoCompatibilidad verificarCompatibilidad(List<ComponentePC> componentes);
}
