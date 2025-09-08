package main.estado.robot;

import main.productos.Producto;
import main.sistema.Robot;

/**
 * Interfaz que representa el comportamiento de los distintos estados del Robot.
 * Cada estado concreto implementará esta interfaz y definirá cómo se comporta el robot
 * ante las acciones posibles.
 * 
 */
public interface EstadoActualRobot {


    //en el diagrama de clases, no entendi la parte de +robot: Robot
    //NO SE SI SEA FINAL O COMO XD

    /***
     * Metodo que se ejecuta cuando el Robot debe atender un cliente 
     * Cada estado implementa este omportamiento segun corresponda
     */
    public void atenterCliente();

    /**
     * Método que se ejecuta cuando el robot debe atender un pedido.
     * Cada estado implementará este comportamiento según corresponda.
     */
    public void atenderPedido();

    /**
     * Método para agregar un producto al pedido actual del robot.
     * 
     * @param producto el producto que se desea agregar al pedido
     */
    public void agregarProducto(Producto producto);

    /**
     * Método que confirma la orden actual del cliente.
     * Cada estado decide si esta acción es válida o no.
     */
    public void confirmarOrden();

    /**
     * Método que inicia la preparación de los productos del pedido.
     * Dependerá del estado si esta acción se puede realizar.
     */
    public void iniciarPreparacion();

    /**
     * Método que solicita al robot que prepare la entrega del pedido al cliente.
     */
    public void solicitarEntrega();

    /**
     * Método que realiza la entrega del pedido al cliente.
     * Cada estado decidirá cómo se comporta esta acción.
     */
    public void entregar();
    /**
     * 
     */
    public void cancelarOrden();
}
