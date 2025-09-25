package Visualizar;

import java.lang.module.ModuleDescriptor.Builder;
import java.util.List;
import java.util.stream.Stream;

import Composite.ProductoComponente;


/**
 * Clase que se encarga de Mostrar los resumenes de una lista de productosComponenetes.
 * Esta clase impleneta a {@code VisualizadorProducto}, por ende debe implemntar sus metodos.
 */
public class VisualizadorConsola implements VisualizadorProducto{
    /**La lista de prodcutos componenetes.  */
    private List<ProductoComponente> listaProductos;

    /**
     * Constructor para inicializar la lista de productos.
     * @param productos La lista de productos
     */
    public VisualizadorConsola(List<ProductoComponente> productos) {
        this.listaProductos = productos;
    }

    /**
     * Muestra un resumen de todos los productos de la lista.
     * <p>Este método recorre la lista de productos y genera un resumen 
     * con el nombre y precio de cada producto.</p>
     *
     * @return un {@link String} con el resumen completo de todos los productos,
     *         o {@code null} si la lista de productos es {@code null}.
     */
    @Override
    public String mostrarResumenDeTodo() {
        if (listaProductos == null) return null;
        StringBuilder sb = new StringBuilder("=== Mostrando Catalogo Completo : ===\n");
        for (ProductoComponente proCom : listaProductos) {
            sb.append("Nombre: ").append(proCom.getNombre())
            .append(" Precio: ").append(proCom.getPrecio()).append("\n");
        }
        return sb.toString();
    }

    /**
     * Muestra un resumen de un producto específico.
     * <p>Si el producto existe en la lista, devuelve su nombre y precio.
     * Si no se encuentra, devuelve un mensaje indicando que no se encontró.</p>
     *
     * @param producto el {@link ProductoComponente} del cual se desea obtener el resumen.
     * @return un {@link String} con el resumen del producto, 
     *         o {@code null} si el producto es {@code null}.
     */
    @Override
    public String mostrarResumen(ProductoComponente producto) {
        if (producto == null) return null;
        StringBuilder sb = new StringBuilder("Resumen corto de: ").append(producto.getNombre());
        for (ProductoComponente prodCom : listaProductos) {
            if (prodCom.equals(producto)) {
                sb.append("Nombre: ").append(producto.getNombre())
                .append(" Precio: ").append(producto.getPrecio());
                return sb.toString();
            }
        }
        sb.append("Ops... No se encontro nada");
        return sb.toString();
    }

    /**
     * Muestra la información completa de un producto específico.
     * <p>Si el producto existe en la lista, devuelve la información completa
     * usando el método {@code toString()} del producto. 
     * Si no se encuentra, devuelve un mensaje indicando que no se encontró.</p>
     *
     * @param producto el {@link ProductoComponente} del cual se desea obtener la información completa.
     * @return un {@link String} con la información completa del producto, 
     *         o {@code null} si el producto es {@code null}.
     */
    @Override
    public String mostrarCompleto(ProductoComponente producto) {
        if (producto == null) return null;
        StringBuilder sb = new StringBuilder("Resumen Completo de: ").append(producto.getNombre());
        for (ProductoComponente prodCom : listaProductos) {
            if (prodCom.equals(producto)) {
                sb.append(producto.toString());
                return sb.toString();
            }
        }
        sb.append("Ops... No se encontro nada");
        return sb.toString();
    }
}
