import java.util.ArrayList;
import java.util.List;

import Composite.ProductoComponente;
import Visualizar.VisualizadorProducto;
import Filtros.FiltroProducto;
import Filtros.FiltroGenero;
import Filtros.FiltroPrecioMaximo;

/**
 * Clase que representa el catálogo de productos de RockBuster.
 * <p>
 * Gestiona una colección de productos (películas, sagas y discos musicales adaptados)
 * y proporciona funcionalidades de filtrado y visualización utilizando el patrón Strategy
 * para los filtros y el patrón de inyección de dependencias para el visualizador.
 * </p>
 *
 * @author Equipo-Polimorfo
 * @version 1.0
 * @since 2025-09-28
 */
public class Catalogo {
    private List<ProductoComponente> productos = new ArrayList<>();
    private VisualizadorProducto visualizador;
    private FiltroProducto filtroGenero;
    private FiltroProducto filtroPrecio;
    
    
    /**
     * Constructor para inicializar el catálogo con productos y dependencias.
     *
     * @param productos la lista inicial de productos
     * @param visualizador el visualizador a utilizar para mostrar información
     */
    public Catalogo(List<ProductoComponente> productos, VisualizadorProducto visualizador) {
        this.productos = productos != null ? new ArrayList<>(productos) : new ArrayList<>();
        this.visualizador = visualizador;
        this.filtroGenero = new FiltroGenero();
        this.filtroPrecio = new FiltroPrecioMaximo();
    }

    /**
     * Agrega un producto al catálogo.
     *
     * @param producto el producto a agregar
     */
    public void agregarProducto(ProductoComponente producto){
        if (producto != null) {
            productos.add(producto);
        }
    }
    
    /**
     * Remueve un producto del catálogo.
     *
     * @param producto el producto a remover
     */
    public void removerProducto(ProductoComponente producto){
        productos.remove(producto);
    }
    /**
     * Obtiene la lista completa de productos en el catálogo.
     *
     * @return una nueva lista con todos los productos del catálogo
     */
    public List<ProductoComponente> verCatalogoCompleto(){
        return new ArrayList<>(productos);
    }
    /**
     * Filtra los productos del catálogo por género.
     *
     * @param genero el género por el cual filtrar
     * @return una lista de productos que coinciden con el género especificado
     */
    public List<ProductoComponente> filtrarPorGenero(String genero){
        return filtroGenero.filtrar(productos, genero);
    }
    /**
     * Filtra los productos del catálogo por precio máximo.
     *
     * @param precioMaximo el precio máximo permitido
     * @return una lista de productos con precio menor o igual al máximo especificado
     */
    public List<ProductoComponente> filtrarPorPrecioMaximo(double precioMaximo){
        return ((FiltroPrecioMaximo) filtroPrecio).filtrar(productos, precioMaximo);
    }
    /**
     * Muestra la información completa de un producto específico.
     *
     * @param producto el producto del cual mostrar información completa
     * @return la representación completa del producto
     */
    public String mostrarProductoCompleto(ProductoComponente producto){
        return (producto != null && visualizador != null) ?
            visualizador.mostrarCompleto(producto) :
            "";
    } 
    /**
     * Muestra el catálogo de productos (resumen).
     *
     * @param productos la lista de productos a mostrar
     * @return la lista de productos para visualización
     */
    public List<ProductoComponente> mostrarCatalogo(List<ProductoComponente> productos){
        return (productos != null) ? new ArrayList<>(productos) : new ArrayList<>();
    }
}