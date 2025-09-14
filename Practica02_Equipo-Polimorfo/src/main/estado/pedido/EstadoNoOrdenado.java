package main.estado.pedido;

import main.productos.Producto;
import main.sistema.Pedido;
import main.sistema.Robot;
import main.sistema.Sucursal;

public class EstadoNoOrdenado  implements EstadoPedido{
    /** */
    private final Pedido pedido; // coincide con el UML


    /**
     * El constructor para iniciaizar el estado no ordenado del pedido
     * @param pedido El pedido al que pertenece este estado.
     */
    public EstadoNoOrdenado(Pedido pedido) {
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