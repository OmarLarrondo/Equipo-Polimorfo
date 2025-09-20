package main.PaquetesHerramientas;

import main.herramientas.*;
import java.util.stream.Stream;

/**
 * Implementación concreta de un paquete básico de herramientas ninja.
 * Contiene: 1 Kunai, 1 Shuriken, 1 Botiquín.
 *
 * @author Equipo-Polimorfo
 * @version 1.0
 */
public class PaqueteBasico extends PaquetesHerramientas {

    /**
     * Constructor que inicializa automáticamente el paquete básico
     * con las herramientas predefinidas.
     */
    public PaqueteBasico() {
        super();
        inicializarHerramientas();
    }

    /**
     * Inicializa las herramientas del paquete básico.
     */
    public void inicializarHerramientas() {
        Stream.of(
            Kunai::new,
            Shuriken::new,
            Botiquin::new
        ).map(java.util.function.Supplier::get)
         .forEach(this::agregarHerramienta);
    }

    /**
     * Proporciona una descripción detallada del paquete básico
     *
     * @return Descripción completa del paquete básico
     */
    @Override
    public String getDescripcion() {
        String contenido = herramientas.stream()
            .collect(java.util.stream.Collectors.groupingBy(
                h -> h.getClass().getSimpleName(),
                java.util.stream.Collectors.counting()
            ))
            .entrySet().stream()
            .map(entry -> entry.getValue() + " " + entry.getKey())
            .collect(java.util.stream.Collectors.joining(", "));

        return String.format("Paquete Básico - Contiene: %s (Peso total: %d kg)",
                           contenido, getPesoTotal());
    }

    /**
     * Representación en cadena del paquete básico.
     *
     * @return Cadena descriptiva del paquete
     */
    @Override
    public String toString() {
        return getDescripcion();
    }
}
