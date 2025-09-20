package PaquetesHerramientas;

import herramientas.*;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * Implementación concreta de un paquete táctico de herramientas ninja.
 * Contiene: 3 Kunais, 2 Shurikens, 4 Papeles Bomba, 2 Bombas de Humo.
 *
 * @author Equipo-Polimorfo
 * @version 1.0
 */
public class PaqueteTactico extends PaquetesHerramientas {

    /**
     * Constructor que inicializa automáticamente el paquete táctico
     * con las herramientas predefinidas.
     */
    public PaqueteTactico() {
        super();
        inicializarHerramientas();
    }

    /**
     * Inicializa las herramientas del paquete táctico usando un enfoque
     * declarativo optimizado para combate táctico.
     */
    public void inicializarHerramientas() {
        Map<Supplier<Herramienta>, Integer> configTactica = Map.of(
            Kunai::new, 3,          
            Shuriken::new, 2,      
            PapelBomba::new, 4,     
            BombaHumo::new, 2
        );

        configTactica.entrySet().stream()
            .forEach(entry -> agregarHerramientas(entry.getKey(), entry.getValue()));
    }

    /**
     * Proporciona una descripción detallada del paquete táctico
     * para análisis táctico de contenido.
     *
     * @return Descripción completa del paquete táctico
     */
    @Override
    public String getDescripcion() {
        String contenidoTactico = herramientas.stream()
            .collect(java.util.stream.Collectors.groupingBy(
                h -> h.getClass().getSimpleName(),
                java.util.stream.Collectors.counting()
            ))
            .entrySet().stream()
            .sorted((e1, e2) -> {
		    
                Map<String, Integer> prioridadTactica = Map.of(
                    "Kunai", 1, "Shuriken", 2, "PapelBomba", 3, "BombaHumo", 4
                );
                return Integer.compare(
                    prioridadTactica.getOrDefault(e1.getKey(), 5),
                    prioridadTactica.getOrDefault(e2.getKey(), 5)
                );
            })
            .map(entry -> entry.getValue() + " " + entry.getKey() +
                (entry.getValue() > 1 ? "s" : ""))
            .collect(java.util.stream.Collectors.joining(", "));

        return String.format("Paquete Táctico - Arsenal: %s (Peso total: %d kg)",
                           contenidoTactico, getPesoTotal());
    }

    /**
     * Análisis táctico del paquete.
     *
     * @return Evaluación táctica del paquete
     */
    public String getAnalisisTactico() {
        long armasOfensivas = herramientas.stream()
            .filter(h -> h instanceof Kunai || h instanceof Shuriken || h instanceof PapelBomba)
            .count();

        long herramientasUtilidad = herramientas.stream()
            .filter(h -> h instanceof BombaHumo)
            .count();

        return String.format("Análisis Táctico - Ofensivas: %d, Utilidad: %d, Eficiencia: %.1f%%",
                           armasOfensivas, herramientasUtilidad,
                           (armasOfensivas / (double) herramientas.size()) * 100);
    }

    /**
     * Representación en cadena del paquete táctico.
     *
     * @return Cadena descriptiva del paquete
     */
    @Override
    public String toString() {
        return getDescripcion() + " | " + getAnalisisTactico();
    }
}
