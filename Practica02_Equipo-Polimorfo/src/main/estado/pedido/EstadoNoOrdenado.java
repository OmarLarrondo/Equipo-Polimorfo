package main.estado.pedido;

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
    public void procesarAgregadoArticulo() {
        //aqui va su codigo 
    }

    @Override
    public void procesarEnvioOrden(Sucursal sucursal) {
        //aqui va codigo
    }

    @Override
    public void procesarPeticionEntrega(Sucursal sucursal) {
        //aqui va codigo 
    }


}