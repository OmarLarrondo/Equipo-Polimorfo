package main.estado.pedido;

import main.productos.Producto;
import main.sistema.Pedido;
import main.sistema.Robot;
import main.sistema.Sucursal;

public class EstadoNoOrdenado  implements EstadoPedido{
    /** */
    private final Pedido pedido;


    /**
     * El constructor para iniciaizar el estado no ordenado del pedido
     * @param pedido El pedido al que pertenece este estado.
     */
    public EstadoNoOrdenado(Pedido pedido) {
        this.pedido = pedido;
    }


    /**
     * Agrega un producto al pedido cuando está en estado no ordenado.
     * Esta operación es permitida en este estado.
     *
     * @param articuloNuevo el producto a agregar al pedido
     */
    @Override
    public void agregarArticulo(Producto articuloNuevo) {
        if (articuloNuevo == null) {
            throw new IllegalArgumentException("El artículo no puede ser null");
        }

        pedido.agregarArticulo(articuloNuevo);
        System.out.println("Artículo agregado al pedido: " + articuloNuevo.getNombre());
    }

    /**
     * Envía la orden a la sucursal y cambia el estado del pedido a EstadoOrdenado.
     * Esta operación es permitida en este estado y provoca una transición.
     *
     * @param sucursal la sucursal a la que se envía la orden
     */
    @Override
    public void enviarOrden(Sucursal sucursal) {
        if (sucursal == null) {
            throw new IllegalArgumentException("La sucursal no puede ser null");
        }

        if (pedido.getArticulos().isEmpty()) {
            throw new IllegalStateException("No se puede enviar una orden sin productos");
        }

        System.out.println("Enviando orden a la sucursal: " + sucursal.getNombre());
        System.out.println("Productos en la orden: " + pedido.getArticulos().size());

        pedido.setEstadoPedido(new EstadoOrdenado(pedido));
        System.out.println("Estado del pedido cambiado a: EstadoOrdenado");
    }

    /**
     * Intenta solicitar la entrega del pedido, pero esta operación no es permitida
     * en el estado no ordenado, ya que el pedido aún no ha sido enviado a la sucursal.
     *
     * @param sucursal la sucursal que realizaría la entrega
     * @throws IllegalStateException siempre, ya que no se puede solicitar entrega en este estado
     */
    @Override
    public void solicitarEntrega(Sucursal sucursal) {
        throw new IllegalStateException("No se puede solicitar entrega de un pedido que no ha sido ordenado. Primero debe enviar la orden.");
    }



}
