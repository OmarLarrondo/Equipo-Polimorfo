package Filtros;

import java.util.ArrayList;
import java.util.List;

import Composite.ProductoComponente;

/**
 * Implementación concreta del patrón Strategy para filtrar productos por género.
 * <p>
 * Esta clase permite filtrar una colección de {@link ProductoComponente} basándose
 * en el género del producto. El filtrado se realiza utilizando programación funcional
 * y es insensible a mayúsculas/minúsculas para mayor flexibilidad de uso.
 * </p>
 *
 * <h3>Comportamiento del filtro:</h3>
 * <ul>
 *   <li>Realiza comparación <strong>case-insensitive</strong> del género</li>
 *   <li>Elimina espacios en blanco del criterio de búsqueda</li>
 *   <li>Filtra todos los tipos de productos: películas, sagas y discos musicales</li>
 *   <li>Retorna una nueva lista sin modificar la original</li>
 *   <li>Maneja casos nulos de forma segura</li>
 * </ul>
 *
 * <h3>Productos soportados:</h3>
 * <ul>
 *   <li><strong>Películas individuales:</strong> Filtradas por su género cinematográfico</li>
 *   <li><strong>Sagas:</strong> Filtradas por el género predominante de la saga</li>
 *   <li><strong>Discos musicales:</strong> Filtrados por género musical (Rock, Pop, Jazz, etc.)</li>
 * </ul>
 *
 * <h3>Ejemplo de uso:</h3>
 * <pre>{@code
 * // Crear filtro por género
 * FiltroGenero filtroGenero = new FiltroGenero();
 *
 * // Filtrar películas de acción
 * List<ProductoComponente> peliculasAccion =
 *     filtroGenero.filtrar(todosCatalogo, "Acción");
 *
 * // Filtrar discos de rock (case-insensitive)
 * List<ProductoComponente> discosRock =
 *     filtroGenero.filtrar(todosCatalogo, "rock");
 *
 * // El resultado incluye tanto "Rock", "ROCK", "rock", etc.
 * }</pre>
 *
 * <h3>Consideraciones técnicas:</h3>
 * <ul>
 *   <li>Utiliza {@code Stream API} para un filtrado eficiente</li>
 *   <li>Implementa null safety en todos los niveles</li>
 *   <li>Retorna {@code ArrayList} vacía si no hay coincidencias</li>
 *   <li>No arroja excepciones, maneja errores graciosamente</li>
 * </ul>
 *
 * @author Equipo-Polimorfo
 * @version 1.0
 * @since 2025-09-28
 *
 * @see FiltroProducto
 * @see FiltroPrecioMaximo
 * @see ProductoComponente
 */
public class FiltroGenero implements FiltroProducto {

    /**
     * Filtra una lista de productos por género.
     *
     * @param Items la lista de productos a filtrar
     * @param criterio el género por el cual filtrar los productos
     * @return una nueva lista que contiene solo los productos que coinciden con el género especificado,
     *         o una lista vacía si no se encuentran coincidencias o si los parámetros son nulos
     */
    @Override
    public List<ProductoComponente> filtrar(List<ProductoComponente> Items, String criterio) {
        return (Items == null || criterio == null) ?
            new ArrayList<>() :
            Items.stream()
                .filter(producto -> producto != null &&
                       producto.getGenero() != null &&
                       producto.getGenero().equalsIgnoreCase(criterio.trim()))
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }
    
}
