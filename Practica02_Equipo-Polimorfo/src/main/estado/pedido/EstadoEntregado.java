package estado.pedido;

import productos.Producto;
import sistema.Pedido;
import sistema.Sucursal;

public class EstadoEntregado implements EstadoPedido {
    /** */
    private final Pedido pedido; // coincide con el UML

    /**
     * El constructor para iniciaizar el estado entregado del pedido
     * @param pedido El pedido al que pertenece este estado.
     */
    public EstadoEntregado(Pedido pedido) {
        this.pedido = pedido;
    }


    @Override
    public void agregarArticulo(Producto articuloNuevo) {
        throw new UnsupportedOperationException("Unimplemented method 'agregarArticulo'");
    }


    @Override
    public void enviarOrden(Sucursal sucursal) {
        throw new UnsupportedOperationException("Unimplemented method 'enviarOrden'");
    }


    @Override
    public void solicitarEntrega(Sucursal sucursal) {
        throw new UnsupportedOperationException("Unimplemented method 'solicitarEntrega'");
    }


}