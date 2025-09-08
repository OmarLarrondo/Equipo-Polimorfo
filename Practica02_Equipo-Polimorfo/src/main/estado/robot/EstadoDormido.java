package main.estado.robot;

import main.productos.Producto;
import main.sistema.Robot;

public class EstadoDormido implements EstadoActualRobot {
    private final Robot robot; // coincide con el UML

    
    /**
     * EL constructor para iniciaizar el EstadoDormido del Robot
     * @param robot  el robot al que pertenece este estado, utilizado para cambiar su estado o acceder a sus pedidos
     */
    public EstadoDormido(Robot robot) {
        this.robot = robot;
    }

    @Override
    public void atenterCliente() {
        
    }

    @Override
    public void atenderPedido() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'atenderPedido'");
    }

    @Override
    public void agregarProducto(Producto producto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'agregarProducto'");
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