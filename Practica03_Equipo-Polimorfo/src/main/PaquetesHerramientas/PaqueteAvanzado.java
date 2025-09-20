package main.PaquetesHerramientas;

import main.herramientas.*;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * Implementación concreta de un paquete avanzado de herramientas ninja.
 * Contiene: 2 Shurikens, 3 Papeles Bomba, 2 Bombas de Humo, 2 Botiquines.
 *
 * @author Equipo-Polimorfo
 * @version 1.0
 */
public class PaqueteAvanzado extends PaquetesHerramientas {

    /**
     * Constructor que inicializa automáticamente el paquete avanzado
     * con las herramientas predefinidas.
     */
    public PaqueteAvanzado() {
        super();
        inicializarHerramientas();
    }

    /**
     * Inicializa las herramientas del paquete avanzado.
     */
    public void inicializarHerramientas() {
        agregarHerramienta(new Shuriken("Shuriken", 0.3, 2));
        agregarHerramienta(new PapelBomba("Papel Bomba", 0.1, 3));
        agregarHerramienta(new BombaHumo("Bomba de Humo", 0.4, 2));
        agregarHerramienta(new Botiquin("Botiquín", 1.2, 2));
    }

    /**
     * Proporciona una descripción detallada del paquete avanzado para generar el contenido dinámicamente.
     *
     * @return Descripción completa del paquete avanzado
     */
    @Override
    public String getDescripcion() {
        String contenido = herramientas.stream()
            .collect(java.util.stream.Collectors.groupingBy(
                h -> h.getClass().getSimpleName(),
                java.util.stream.Collectors.counting()
            ))
            .entrySet().stream()
            .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
            .map(entry -> entry.getValue() + " " + entry.getKey() +
                (entry.getValue() > 1 ? "s" : ""))
            .collect(java.util.stream.Collectors.joining(", "));

        return String.format("Paquete Avanzado - Contiene: %s (Peso total: %d kg)",
                           contenido, getPesoTotal());
    }

    /**
     * Representación en cadena del paquete avanzado.
     *
     * @return Cadena descriptiva del paquete
     */
    @Override
    public String toString() {
        return getDescripcion();
    }
}
