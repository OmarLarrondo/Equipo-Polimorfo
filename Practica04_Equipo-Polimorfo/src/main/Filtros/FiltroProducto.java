package Filtros;

import java.util.List;

import Composite.ProductoComponente;

/**
 * Interfaz que define el contrato para filtrar productos en el catálogo de RockBuster.
 * <p>
 * Esta interfaz implementa el patrón de diseño <strong>Strategy</strong>, permitiendo
 * diferentes estrategias de filtrado que pueden ser intercambiadas dinámicamente
 * sin afectar el código cliente. Cada implementación concreta define un criterio
 * específico de filtrado (por género, precio, etc.).
 * </p>
 *
 * <h3>Patrón Strategy</h3>
 * <ul>
 *   <li><strong>Strategy (Estrategia):</strong> Esta interfaz {@code FiltroProducto}</li>
 *   <li><strong>ConcreteStrategy:</strong> {@link FiltroGenero}, {@link FiltroPrecioMaximo}</li>
 *   <li><strong>Context:</strong> Clases que utilicen estos filtros (como {@code Catalogo})</li>
 * </ul>
 *
 * <h3>Uso típico:</h3>
 * <pre>{@code
 * // Crear una lista de productos
 * List<ProductoComponente> productos = catalogo.getProductos();
 *
 * // Aplicar filtro por género
 * FiltroProducto filtroGenero = new FiltroGenero();
 * List<ProductoComponente> peliculasAccion = filtroGenero.filtrar(productos, "Acción");
 *
 * // Aplicar filtro por precio
 * FiltroProducto filtroPrecio = new FiltroPrecioMaximo();
 * List<ProductoComponente> productosBaratos = filtroPrecio.filtrar(productos, "50.0");
 * }</pre>
 *
 * @author Equipo-Polimorfo
 * @version 1.0
 * @since 2025-09-28
 *
 * @see FiltroGenero
 * @see FiltroPrecioMaximo
 * @see ProductoComponente
 */
public interface FiltroProducto {
    public List<ProductoComponente> filtrar(List<ProductoComponente> Items, String criterio);
    
}
