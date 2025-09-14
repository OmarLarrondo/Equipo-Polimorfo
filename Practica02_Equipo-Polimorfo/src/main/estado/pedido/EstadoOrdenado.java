package main.estado.pedido;

import main.productos.Producto;
import main.sistema.Pedido;
import main.sistema.Sucursal;

public class EstadoOrdenado implements EstadoPedido{
    /** */
    private final Pedido pedido;


    /**
     * El constructor para iniciaizar el estado ordenado del pedido
     * @param pedido El pedido al que pertenece este estado.
     */
    public EstadoOrdenado(Pedido pedido) {
        this.pedido = pedido;
    }


    /**
     * Intenta agregar un producto al pedido, pero esta operación no es permitida
     * en el estado ordenado ya que el pedido ya fue enviado a la sucursal.
     *
     * @param articuloNuevo el producto que se intenta agregar
     * @throws IllegalStateException siempre, ya que no se pueden agregar productos después de ordenar
     */
    @Override
    public void agregarArticulo(Producto articuloNuevo) {
        throw new IllegalStateException("No se pueden agregar productos a un pedido ya ordenado. El pedido está siendo preparado por la sucursal.");
    }

    /**
     * Intenta enviar la orden, pero esta operación no es permitida
     * en el estado ordenado ya que el pedido ya fue enviado previamente.
     *
     * @param sucursal la sucursal a la que se intenta enviar
     * @throws IllegalStateException siempre, ya que la orden ya fue enviada
     */
    @Override
    public void enviarOrden(Sucursal sucursal) {
        throw new IllegalStateException("La orden ya ha sido enviada a la sucursal y está siendo preparada.");
    }

    /**
     * Intenta solicitar la entrega del pedido, pero esta operación no es permitida
     * en el estado ordenado porque el pedido aún no ha sido completamente preparado.
     * El pedido debe transicionar a EstadoHecho antes de poder solicitar entrega.
     *
     * @param sucursal la sucursal que realizaría la entrega
     * @throws IllegalStateException siempre, ya que el pedido no está listo para entregar
     */
    @Override
    public void solicitarEntrega(Sucursal sucursal) {
        throw new IllegalStateException("No se puede solicitar entrega de un pedido que aún no ha sido preparado. El pedido está siendo procesado por la sucursal.");
    }

    /**
     * Marca el pedido como terminado de preparar y cambia el estado a EstadoHecho.
     * Este método debería ser llamado por la sucursal cuando termine la preparación.
     */
    public void marcarComoHecho() {
        System.out.println("Preparación del pedido completada.");
        pedido.setEstadoPedido(new EstadoHecho(pedido));
        System.out.println("Estado del pedido cambiado a: EstadoHecho");
    }
    

}
