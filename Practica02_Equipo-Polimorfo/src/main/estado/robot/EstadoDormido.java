package main.estado.robot;

import main.productos.Producto;
import main.sistema.Pedido;
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
        System.out.println("Cliente ha llamado. EL robot se desperto y esta tomando la orden:");
        robot.setEstado(new EstadoEnEspera(robot));
        robot.atenderPedido(new Pedido());
    }

    @Override
    public void atenderPedido(Pedido pedido) {
        System.out.println("Robot se despertó y está tomando la orden.");
        robot.setEstado(new EstadoEnEspera(robot));
        robot.atenderPedido(pedido);
    }

    @Override
    public void agregarProducto(Producto producto) {
        throw new IllegalStateException("No se puede agregar el producto, el robot esta durmiendo");
    }

    @Override
    public void confirmarOrden() {
        throw new IllegalStateException("No se puede confirmar la orden, el robot esta durmiendo");
    }

    @Override
    public void iniciarPreparacion() {
        throw new IllegalStateException("No se puede iniciar la preparacion, el robot esta durmiendo");
    }

    @Override
    public void solicitarEntrega() {
        throw new IllegalStateException("No se puede solicitar la entrega, el robot esta durmiendo");
    }

    @Override
    public void entregar() {
                throw new IllegalStateException("No se puede entregar el pedido, el robot esta durmiendo");
    }

    @Override
    public void cancelarOrden() {
                throw new IllegalStateException("No se puede cancelar la orden, el robot esta durmiendo");
    }
}