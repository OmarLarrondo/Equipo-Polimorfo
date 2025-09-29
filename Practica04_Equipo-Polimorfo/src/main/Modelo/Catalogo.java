package Modelo;

import java.util.ArrayList;
import java.util.List;

import Composite.ProductoComponente;
import Visualizar.VisualizadorProducto;
import Filtros.FiltroProducto;
import Filtros.FiltroGenero;
import Filtros.FiltroPrecioMaximo;

/**
 * Clase que representa el catalogo de productos de RockBuster.
 * <p>
 * Gestiona una coleccion de productos (peliculas, sagas y discos musicales adaptados)
 * y proporciona funcionalidades de filtrado y visualizacion utilizando el patron Strategy
 * para los filtros y el patron de inyeccion de dependencias para el visualizador.
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
     * Constructor para inicializar el catalogo con productos y dependencias.
     *
     * @param productos la lista inicial de productos
     * @param visualizador el visualizador a utilizar para mostrar informacion
     */
    public Catalogo(List<ProductoComponente> productos, VisualizadorProducto visualizador) {
        this.productos = productos != null ? new ArrayList<>(productos) : new ArrayList<>();
        this.visualizador = visualizador;
        this.filtroGenero = new FiltroGenero();
        this.filtroPrecio = new FiltroPrecioMaximo();
    }

    /**
     * Agrega un producto al catalogo.
     *
     * @param producto el producto a agregar
     */
    public void agregarProducto(ProductoComponente producto){
        if (producto != null) {
            productos.add(producto);
        }
    }
    
    /**
     * Remueve un producto del catalogo.
     *
     * @param producto el producto a remover
     */
    public void removerProducto(ProductoComponente producto){
        productos.remove(producto);
    }
    /**
     * Obtiene la lista completa de productos en el catalogo.
     *
     * @return una nueva lista con todos los productos del catalogo
     */
    public List<ProductoComponente> verCatalogoCompleto(){
        return new ArrayList<>(productos);
    }
    /**
     * Filtra los productos del catalogo por genero.
     *
     * @param genero el genero por el cual filtrar
     * @return una lista de productos que coinciden con el genero especificado
     */
    public List<ProductoComponente> filtrarPorGenero(String genero){
        return filtroGenero.filtrar(productos, genero);
    }
    /**
     * Filtra los productos del catalogo por precio maximo.
     *
     * @param precioMaximo el precio maximo permitido
     * @return una lista de productos con precio menor o igual al maximo especificado
     */
    public List<ProductoComponente> filtrarPorPrecioMaximo(double precioMaximo){
        return ((FiltroPrecioMaximo) filtroPrecio).filtrar(productos, precioMaximo);
    }
    /**
     * Muestra la informacion completa de un producto especifico.
     *
     * @param producto el producto del cual mostrar informacion completa
     * @return la representacion completa del producto
     */
    public String mostrarProductoCompleto(ProductoComponente producto){
        return (producto != null && visualizador != null) ?
            visualizador.mostrarCompleto(producto) :
            "";
    } 
    /**
     * Muestra el catalogo de productos (resumen).
     *
     * @param productos la lista de productos a mostrar
     * @return la lista de productos para visualizacion
     */
    public List<ProductoComponente> mostrarCatalogo(List<ProductoComponente> productos){
        return (productos != null) ? new ArrayList<>(productos) : new ArrayList<>();
    }
}