package estado.pedido;

import productos.Producto;
import sistema.Sucursal;

/**
 * Interfaz que define los comportamientos de un pedido según su estado en que se encuentra.
 * <p>Implementa el patrón <b>State</b> para controlar las acciones que se pueden realizar
 * en un pedido dependiendo de: si esta entregado, heecho, no ordenado o ordenado</p>
 * <p> Los métodos quwe deben implemenar las subclases son: 
 * <ul>
 *   <li>{@link #agregarArticulo(Producto)}: Agrega un producto al pedido.</li>
 *   <li>{@link #enviarOrden(Sucursal)}: Envía el pedido a la sucursal para su preparación.</li>
 *   <li>{@link #solicitarEntrega(Sucursal)}: Solicita la entrega del pedido ya preparado.</li>
 * </ul></p>
 * 
 * @author Equipo-Polimorfo
 * @version 1.0
 */
public interface EstadoPedido {
    /**
     * Agrega un producto al pedido.
     * <p>Solo puede funcionar si el pedido se encuentra en un estado que permita modificaciones
     * (por ejemplo, {@code EstadoNoOrdenado}).</p>
     * 
     * @param articuloNuevo el producto que se desea agregar al pedido
     */
    public void agregarArticulo(Producto articuloNuevo);

    /**
     * Envía el pedido a la sucursal para su procesamiento.
     * <p>Cambia el estado del pedido a {@code EstadoOrdenado} o parecido, indicando que
     * está listo para ser preparado por el robot de la sucursal.</p>
     * 
     * @param sucursal la sucursal a la que se envía el pedido
     */
    public void enviarOrden(Sucursal sucursal);
    
    /**
     * Solicita la entrega del pedido una vez que ha sido preparado.
     * <p>Solo tiene efecto si el pedido ya se encuentra en un estado de preparación completa
     * ({@code EstadoHecho}). Cambia el estado a {@code EstadoEntregado}.</p>
     * 
     * @param sucursal la sucursal que realizará la entrega del pedido
     */
    public void solicitarEntrega(Sucursal sucursal);

}
