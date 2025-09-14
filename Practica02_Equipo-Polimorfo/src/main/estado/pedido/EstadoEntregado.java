package main.estado.pedido;

import main.productos.Producto;
import main.sistema.Pedido;
import main.sistema.Sucursal;

public class EstadoEntregado implements EstadoPedido {
    /** */
    private final Pedido pedido;

    /**
     * El constructor para iniciaizar el estado entregado del pedido
     * @param pedido El pedido al que pertenece este estado.
     */
    public EstadoEntregado(Pedido pedido) {
        this.pedido = pedido;
    }


    /**
     * Intenta agregar un producto al pedido, pero esta operación no es permitida
     * en el estado entregado ya que el pedido ya fue completamente procesado y entregado.
     *
     * @param articuloNuevo el producto que se intenta agregar
     * @throws IllegalStateException siempre, ya que no se pueden agregar productos a un pedido ya entregado
     */
    @Override
    public void agregarArticulo(Producto articuloNuevo) {
        throw new IllegalStateException("No se pueden agregar productos a un pedido que ya ha sido entregado. El pedido está finalizado.");
    }

    /**
     * Intenta enviar la orden, pero esta operación no es permitida
     * en el estado entregado ya que el pedido ya fue completamente procesado y entregado.
     *
     * @param sucursal la sucursal a la que se intenta enviar
     * @throws IllegalStateException siempre, ya que el pedido ya está finalizado
     */
    @Override
    public void enviarOrden(Sucursal sucursal) {
        throw new IllegalStateException("No se puede enviar una orden que ya ha sido entregada. El pedido está finalizado.");
    }

    /**
     * Intenta solicitar la entrega del pedido, pero esta operación no es permitida
     * en el estado entregado ya que el pedido ya fue entregado previamente.
     *
     * @param sucursal la sucursal que realizaría la entrega
     * @throws IllegalStateException siempre, ya que el pedido ya fue entregado
     */
    @Override
    public void solicitarEntrega(Sucursal sucursal) {
        throw new IllegalStateException("El pedido ya ha sido entregado. No se puede solicitar entrega de un pedido finalizado.");
    }

    /**
     * Obtiene información del estado actual del pedido.
     *
     * @return una representación en cadena del estado del pedido
     */
    public String getEstadoInfo() {
        return "Pedido entregado - Estado final";
    }


}
