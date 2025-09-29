package Filtros;

import java.util.ArrayList;
import java.util.List;

import Composite.ProductoComponente;
import Composite.Saga;

/**
 * Implementación concreta del patrón Strategy para filtrar productos por precio máximo.
 * <p>
 * Esta clase permite filtrar una colección de {@link ProductoComponente} basándose
 * en un precio máximo especificado. El filtro maneja inteligentemente los diferentes
 * tipos de productos, aplicando el precio correspondiente según el tipo:
 * precio base para productos individuales y precio con descuento para sagas.
 * </p>
 *
 * <h3>Manejo especializado de precios:</h3>
 * <ul>
 *   <li><strong>Películas individuales:</strong> Utiliza {@code getPrecio()} - precio base de renta</li>
 *   <li><strong>Discos musicales:</strong> Utiliza {@code getPrecio()} - precio adaptado del sistema legacy</li>
 *   <li><strong>Sagas:</strong> Utiliza {@code getPrecioSaga()} - precio total con descuento del 5% aplicado</li>
 * </ul>
 *
 * <h3>Conversión de criterios:</h3>
 * <p>
 * El filtro acepta el criterio como {@code String} y realiza una conversión segura a {@code double}.
 * Si la conversión falla, el filtro retorna una lista vacía sin lanzar excepciones.
 * </p>
 *
 * <h3>Ejemplo de uso:</h3>
 * <pre>{@code
 * // Crear filtro por precio máximo
 * FiltroPrecioMaximo filtroPrecio = new FiltroPrecioMaximo();
 *
 * // Filtrar productos de hasta $50.00
 * List<ProductoComponente> productosBaratos =
 *     filtroPrecio.filtrar(todosCatalogo, "50.0");
 *
 * // Filtrar productos de hasta $100.50
 * List<ProductoComponente> productosMediano =
 *     filtroPrecio.filtrar(todosCatalogo, "100.50");
 *
 * // Las sagas se comparan con su precio con descuento
 * // Las películas individuales con su precio base
 * }</pre>
 *
 * <h3>Casos especiales manejados:</h3>
 * <ul>
 *   <li><strong>Criterio inválido:</strong> Retorna lista vacía si el String no es convertible</li>
 *   <li><strong>Productos nulos:</strong> Los filtra automáticamente del resultado</li>
 *   <li><strong>Lista nula:</strong> Retorna lista vacía sin errores</li>
 *   <li><strong>Precios exactos:</strong> Incluye productos con precio igual al máximo</li>
 * </ul>
 *
 * <h3>Consideraciones de negocio:</h3>
 * <p>
 * Este filtro respeta las reglas de negocio de RockBuster donde las sagas tienen
 * un descuento del 5% sobre la suma de sus componentes. Esto significa que una saga
 * puede aparecer en un filtro de precio máximo aún cuando la suma de sus componentes
 * individuales exceda el límite, siempre que el precio final con descuento esté
 * dentro del rango especificado.
 * </p>
 *
 * <h3>Implementación técnica:</h3>
 * <ul>
 *   <li>Utiliza {@code Optional} para manejo seguro de conversiones</li>
 *   <li>Implementa {@code Stream API} para filtrado funcional</li>
 *   <li>Separación de responsabilidades en métodos auxiliares</li>
 *   <li>Null safety en todos los niveles de operación</li>
 * </ul>
 *
 * @author Equipo-Polimorfo
 * @version 1.0
 * @since 2025-09-28
 *
 * @see FiltroProducto
 * @see FiltroGenero
 * @see ProductoComponente
 * @see Saga#getPrecioSaga()
 */
public class FiltroPrecioMaximo  implements FiltroProducto{

    /**
     * Filtra una lista de productos por precio máximo.
     * <p>
     * Para las sagas, utiliza el precio con descuento aplicado (getPrecioSaga()).
     * Para otros productos, utiliza el precio base (getPrecio()).
     * </p>
     *
     * @param Items la lista de productos a filtrar
     * @param criterio el precio máximo como String (será convertido a double)
     * @return una nueva lista que contiene solo los productos con precio menor o igual al máximo especificado,
     *         o una lista vacía si no se encuentran coincidencias, los parámetros son nulos,
     *         o el criterio no puede convertirse a un número válido
     */
    @Override
    public List<ProductoComponente> filtrar(List<ProductoComponente> Items, String criterio) {
        if (Items == null || criterio == null) {
            return new ArrayList<ProductoComponente>();
        }

        return convertirCriterioADouble(criterio.trim())
            .map(precioMaximo -> Items.stream()
                .filter(producto -> producto != null && obtenerPrecioProducto(producto) <= precioMaximo)
                .collect(ArrayList<ProductoComponente>::new, ArrayList::add, ArrayList::addAll))
            .orElse(new ArrayList<ProductoComponente>());
    }

    /**
     * Filtra una lista de productos por precio máximo.
     * <p>
     * Versión sobrecargada que recibe directamente el precio máximo como double.
     * Para las sagas, utiliza el precio con descuento aplicado (getPrecioSaga()).
     * Para otros productos, utiliza el precio base (getPrecio()).
     * </p>
     *
     * @param Items la lista de productos a filtrar
     * @param precioMaximo el precio máximo como double
     * @return una nueva lista que contiene solo los productos con precio menor o igual al máximo especificado,
     *         o una lista vacía si no se encuentran coincidencias o la lista es nula
     */
    public List<ProductoComponente> filtrar(List<ProductoComponente> Items, double precioMaximo) {
        return (Items == null) ?
            new ArrayList<ProductoComponente>() :
            Items.stream()
                .filter(producto -> producto != null && obtenerPrecioProducto(producto) <= precioMaximo)
                .collect(ArrayList<ProductoComponente>::new, ArrayList::add, ArrayList::addAll);
    }

    /**
     * Convierte el criterio String a Double.
     *
     * @param criterio el criterio a convertir
     * @return Optional.of(Double) si la conversión es exitosa, Optional.empty() si falla
     */
    private java.util.Optional<Double> convertirCriterioADouble(String criterio) {
        return java.util.Optional.ofNullable(criterio)
            .filter(c -> !c.isEmpty())
            .flatMap(c -> {
                try {
                    return java.util.Optional.of(Double.parseDouble(c));
                } catch (NumberFormatException e) {
                    return java.util.Optional.empty();
                }
            });
    }

    /**
     * Obtiene el precio apropiado para el producto.
     * <p>
     * Si es una Saga, usa getPrecioSaga() que incluye el descuento.
     * Para otros productos, usa getPrecio().
     * </p>
     *
     * @param producto el producto del cual obtener el precio
     * @return el precio del producto
     */
    private double obtenerPrecioProducto(ProductoComponente producto) {
        return (producto instanceof Saga) ?
            ((Saga) producto).getPrecioSaga() :
            producto.getPrecio();
    }
    
}
