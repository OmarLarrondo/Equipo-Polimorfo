package main.estado.pedido;

import main.sistema.Pedido;
import main.sistema.Sucursal;

public class EstadoOrdenado implements EstadoPedido{
    /** */
    private final Pedido pedido; // coincide con el UML


    /**
     * El constructor para iniciaizar el estado ordenado del pedido
     * @param pedido El pedido al que pertenece este estado.
     */
    public EstadoOrdenado(Pedido pedido) {
        this.pedido = pedido;
    }
    

    @Override
    public void procesarAgregadoArticulo() {
        //aqui va codigo 
    }

    @Override
    public void procesarEnvioOrden(Sucursal sucursal) {
        //aqui va codio 
    }

    @Override
    public void procesarPeticionEntrega(Sucursal sucursal) {
        //aqui va su codigo
    }

}