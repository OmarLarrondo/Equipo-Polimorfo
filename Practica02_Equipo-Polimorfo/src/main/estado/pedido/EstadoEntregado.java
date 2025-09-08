package main.estado.pedido;

import main.sistema.Pedido;
import main.sistema.Sucursal;

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
    public void procesarAgregadoArticulo() {
        //aqui va su codigo 
    }

    @Override
    public void procesarEnvioOrden(Sucursal sucursal) {
        //aqui va codido
    }

    @Override
    public void procesarPeticionEntrega(Sucursal sucursal) {
        //aqui va cosido
    }

}