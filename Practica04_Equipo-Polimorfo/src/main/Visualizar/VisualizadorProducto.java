package Visualizar;

import Composite.ProductoComponente;

/**
 * Interfaz que define los métodos para visualizar información de productos.
 * <p>
 * Permite mostrar resúmenes generales, resúmenes específicos 
 * de un producto y detalles completos de un producto.
 * </p>
 */
public interface VisualizadorProducto {

    /**
     * Muestra un resumen de todos los productos.
     * @return un {@link String} que contiene la información resumida de todos los productos.
     */
    public String mostrarResumenDeTodo();

    /**
     * Muestra un resumen de un producto específico.
     *
     * @param producto el {@link ProductoComponente} del cual se desea obtener el resumen.
     * @return un {@link String} que contiene la información resumida del producto proporcionado.
     */
    public String mostrarResumen(ProductoComponente producto);

    /**
     * Muestra la información completa de un producto específico.
     *
     * @param producto El {@link ProductoComponente} del cual se desea obtener la información completa.
     * @return un {@link String} que contiene toda la información detallada del producto proporcionado.
     */
    public String mostrarCompleto(ProductoComponente producto);
}
