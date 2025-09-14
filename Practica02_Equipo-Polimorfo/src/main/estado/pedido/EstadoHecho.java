package main.estado.pedido;

import main.productos.Producto;
import main.sistema.Pedido;
import main.sistema.Sucursal;

public class EstadoHecho implements EstadoPedido{
        /** */
    private final Pedido pedido;


    /**
     * El constructor para iniciaizar el estado hecho del pedido
     * @param pedido El pedido al que pertenece este estado.
     */
    public EstadoHecho(Pedido pedido) {
        this.pedido = pedido;
    }


    /**
     * Intenta agregar un producto al pedido, pero esta operación no es permitida
     * en el estado hecho ya que el pedido ya fue completamente preparado.
     *
     * @param articuloNuevo el producto que se intenta agregar
     * @throws IllegalStateException siempre, ya que no se pueden agregar productos a un pedido ya hecho
     */
    @Override
    public void agregarArticulo(Producto articuloNuevo) {
        throw new IllegalStateException("No se pueden agregar productos a un pedido que ya ha sido completamente preparado.");
    }

    /**
     * Intenta enviar la orden, pero esta operación no es permitida
     * en el estado hecho ya que el pedido ya fue procesado completamente.
     *
     * @param sucursal la sucursal a la que se intenta enviar
     * @throws IllegalStateException siempre, ya que el pedido ya está terminado
     */
    @Override
    public void enviarOrden(Sucursal sucursal) {
        throw new IllegalStateException("No se puede enviar una orden que ya ha sido completamente preparada.");
    }

    /**
     * Solicita la entrega del pedido y cambia el estado a EstadoEntregado.
     * Esta operación es permitida en este estado ya que el pedido está listo para entregarse.
     *
     * @param sucursal la sucursal que realizará la entrega
     */
    @Override
    public void solicitarEntrega(Sucursal sucursal) {
        if (sucursal == null) {
            throw new IllegalArgumentException("La sucursal no puede ser null");
        }

        System.out.println("Solicitando entrega del pedido a la sucursal: " + sucursal.getNombre());
        System.out.println("Preparando la entrega del pedido con " + pedido.getArticulos().size() + " producto(s)");

        pedido.setEstadoPedido(new EstadoEntregado(pedido));
        System.out.println("Estado del pedido cambiado a: EstadoEntregado");
        System.out.println("¡Pedido entregado exitosamente!");
    }

}
