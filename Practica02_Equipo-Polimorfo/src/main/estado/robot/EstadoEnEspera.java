package main.estado.robot;

import main.productos.Producto;
import main.sistema.Pedido;
import main.sistema.Robot;

public class EstadoEnEspera implements EstadoActualRobot {
    private final Robot robot; // coincide con el UML

    
    /**
     * El constructor para inicializar el EstadoEnEspera del Robot
     * @param robot  el robot al que pertenece este estado, utilizado para cambiar su estado o acceder a sus pedidos
     */
    public EstadoEnEspera(Robot robot) {
        this.robot = robot;
    }

    @Override
    public void atenterCliente(){
        System.out.println("Espera, estoy atendiendo a un cliente");
    }

    @Override
    public void atenderPedido(Pedido pedido) {
        System.out.println("Espera, estoy atendiendo a un cliente");
    }

@Override
public void agregarProducto(Producto producto) {
    if (producto == null) {
        System.out.println("Ops, el producto no existe.");
        return;
    }
    //aqui va su codiog 
}

    @Override
    public void confirmarOrden() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'confirmarOrden'");
    }

    @Override
    public void iniciarPreparacion() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'iniciarPreparacion'");
    }

    @Override
    public void solicitarEntrega() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'solicitarEntrega'");
    }

    @Override
    public void entregar() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'entregar'");
    }

    @Override
    public void cancelarOrden() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'cancelarOrden'");
    }


}