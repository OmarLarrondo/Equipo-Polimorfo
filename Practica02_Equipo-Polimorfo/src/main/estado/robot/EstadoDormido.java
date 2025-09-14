package main.estado.robot;

import main.productos.Producto;
import main.sistema.Pedido;
import main.sistema.Robot;

public class EstadoDormido implements EstadoActualRobot {
    private final Robot robot;

    
    /**
     * EL constructor para iniciaizar el EstadoDormido del Robot
     * @param robot  el robot al que pertenece este estado, utilizado para cambiar su estado o acceder a sus pedidos
     */
    public EstadoDormido(Robot robot) {
        this.robot = robot;
    }

    @Override
    public boolean atenderCliente() {
        System.out.println("Nuevo cliente llegó, el robot se despertó");

        Pedido nuevoPedido = new Pedido();
        robot.setPedidoActual(nuevoPedido);

        robot.setEstadoActual(new EstadoTomandoOrden(robot));
	return true;
    }

    @Override
    public void atenderPedido(Pedido pedido) {
        System.out.println("No se puede atender el pedido , el robot esta durmiendo");
    }

    @Override
    public void agregarProducto(Producto producto) {
        System.out.println("No se puede agregar el producto, el robot esta durmiendo");
    }

    @Override
    public void confirmarOrden() {
        System.out.println("No se puede confirmar la orden, el robot esta durmiendo");
    }

    @Override
    public void iniciarPreparacion() {
        System.out.println("No se puede iniciar la preparacion, el robot esta durmiendo");
    }

    @Override
    public void solicitarEntrega() {
        System.out.println("No se puede solicitar la entrega, el robot esta durmiendo");
    }

    @Override
    public void entregar() {
                System.out.println("No se puede entregar el pedido, el robot esta durmiendo");
    }

    @Override
    public void cancelarOrden() {
                System.out.println("No se puede cancelar la orden, el robot esta durmiendo");
    }
}
