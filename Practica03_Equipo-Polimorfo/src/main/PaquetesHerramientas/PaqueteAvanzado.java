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
     * Inicializa las herramientas del paquete avanzado usando un enfoque
     * funcional declarativo con Map y streams para manejar cantidades múltiples.
     */
    public void inicializarHerramientas() {
        Map<Supplier<Herramienta>, Integer> herramientasConfig = Map.of(
            Shuriken::new, 2,
            PapelBomba::new, 3,
            BombaHumo::new, 2,
            Botiquin::new, 2
        );

        herramientasConfig.entrySet().stream()
            .forEach(entry -> agregarHerramientas(entry.getKey(), entry.getValue()));
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

        return String.format("Paquete Avanzado - Contiene: %s (Peso total: %.2f kg)",
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
