package main.PaquetesHerramientas;

import main.herramientas.Herramienta;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Implementación concreta de un paquete personalizado de herramientas ninja.
 * Permite crear paquetes con cualquier combinación de herramientas especificada por el usuario.
 *
 * @author Equipo-Polimorfo
 * @version 1.0
 */
public class PaquetePersonalizado extends PaquetesHerramientas {

    /** Nombre personalizado del paquete */
    private final String nombreCustom;

    /**
     * Constructor que crea un paquete personalizado con las herramientas especificadas.
     *
     * @param herramientas Lista de herramientas personalizadas
     */
    public PaquetePersonalizado(List<Herramienta> herramientas) {
        super(herramientas);
        this.nombreCustom = generarNombrePersonalizado();
    }

    /**
     * Constructor con nombre personalizado y herramientas.
     *
     * @param herramientas Lista de herramientas personalizadas
     * @param nombre Nombre personalizado para el paquete
     */
    public PaquetePersonalizado(List<Herramienta> herramientas, String nombre) {
        super(herramientas);
        this.nombreCustom = Optional.ofNullable(nombre)
            .filter(n -> !n.trim().isEmpty())
            .orElse(generarNombrePersonalizado());
    }

    /**
     * Genera un nombre personalizado basado en el contenido.
     *
     * @return Nombre generado automáticamente
     */
    private String generarNombrePersonalizado() {
        if (herramientas.isEmpty()) {
            return "Paquete Vacío";
        }

        Map<String, Long> distribucion = herramientas.stream()
            .collect(Collectors.groupingBy(
                h -> h.getClass().getSimpleName(),
                Collectors.counting()
            ));

        String tipoMayor = distribucion.entrySet().stream()
            .max(Map.Entry.comparingByValue())
            .map(Map.Entry::getKey)
            .orElse("Mixto");

        long totalItems = herramientas.size();

        return String.format("Custom-%s-x%d", tipoMayor, totalItems);
    }

    /**
     * Proporciona una descripción detallada del paquete personalizado
     *
     * @return Descripción completa del paquete personalizado
     */
    @Override
    public String getDescripcion() {
        if (herramientas.isEmpty()) {
            return String.format("Paquete Personalizado '%s' - Vacío (0 kg)", nombreCustom);
        }

        Map<String, Long> inventario = herramientas.stream()
            .collect(Collectors.groupingBy(
                h -> h.getClass().getSimpleName(),
                Collectors.counting()
            ));

        String contenidoDetallado = inventario.entrySet().stream()
            .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
            .map(entry -> String.format("%d %s%s",
                entry.getValue(),
                entry.getKey(),
                entry.getValue() > 1 ? "s" : ""))
            .collect(Collectors.joining(", "));

        String estadisticas = generarEstadisticas();

        return String.format("Paquete Personalizado '%s' - Contiene: %s (Peso: %d kg) | %s",
                           nombreCustom, contenidoDetallado, getPesoTotal(), estadisticas);
    }

    /**
     * Genera estadísticas avanzadas del paquete.
     *
     * @return Estadísticas del paquete
     */
    private String generarEstadisticas() {
        double pesoPromedio = herramientas.stream()
            .mapToDouble(Herramienta::getPeso)
            .average()
            .orElse(0.0);

        long tiposUnicos = herramientas.stream()
            .map(h -> h.getClass().getSimpleName())
            .distinct()
            .count();

        return String.format("Diversidad: %d tipos, Peso promedio: %.2f kg",
                           tiposUnicos, pesoPromedio);
    }

    /**
     * Analiza la composición del paquete.
     *
     * @param categoria Predicado para filtrar herramientas
     * @param nombreCategoria Nombre de la categoría
     * @return Porcentaje de herramientas en la categoría
     */
    public double analizarComposicion(Predicate<Herramienta> categoria, String nombreCategoria) {
        if (herramientas.isEmpty()) return 0.0;

        long count = herramientas.stream()
            .filter(categoria)
            .count();

        return (count / (double) herramientas.size()) * 100.0;
    }

    /**
     * Obtiene el nombre personalizado del paquete.
     *
     * @return Nombre personalizado
     */
    public String getNombrePersonalizado() {
        return nombreCustom;
    }

    /**
     * Aplica una transformación funcional a todas las herramientas del paquete.
     *
     * @param transformer Función de transformación
     * @return Lista de resultados transformados
     */
    public <R> List<R> transformarHerramientas(Function<Herramienta, R> transformer) {
        return herramientas.stream()
            .map(transformer)
            .collect(Collectors.toList());
    }

    /**
     * Representación en cadena del paquete personalizado.
     *
     * @return Cadena descriptiva del paquete
     */
    @Override
    public String toString() {
        return getDescripcion();
    }
}
