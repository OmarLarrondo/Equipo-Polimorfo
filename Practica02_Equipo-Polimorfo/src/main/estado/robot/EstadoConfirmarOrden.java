package main.estado.robot;

import main.productos.Producto;
import main.sistema.Pedido;
import main.sistema.Robot;

public class EstadoConfirmarOrden implements EstadoActualRobot{
    private final Robot robot; // coincide con el UML
    /**
     * EL constructor para iniciaizar el EstadoDormido del Robot
     * @param robot  el robot al que pertenece este estado, utilizado para cambiar su estado o acceder a sus pedidos
     */
    public EstadoConfirmarOrden(Robot robot) {
        this.robot = robot;
    }

    @Override
    public boolean atenderCliente() {
        System.out.println("No es posible atender un cliente");
	return false;
    }

    @Override
    public void atenderPedido(Pedido pedido) {
        System.out.println("Ya existe un pedido confirmado, no se puede atender otro.");
    }

    @Override
    public void agregarProducto(Producto producto) {
        System.out.println("No es posible agregar productos, ya se confirmo la orden");
    }

    @Override
    public void confirmarOrden() {
        System.out.println("Ya se confirmo la orden.");
    }

    @Override
    public void iniciarPreparacion() {
        Pedido pedido = robot.getPedidoActual();
        if (pedido == null) {
        System.out.println("No hay pedido activo para preparar.");
        return;
        }

        if(pedido.estaConfirmado()){
            System.out.println("El robot inicio la preparacion del pedido.");
            robot.setEstadoActual(robot.getEstadoTrabajando());
        }else{
            System.out.println("El pedido no esta confirmado. ");
        }
    }

    @Override
    public void solicitarEntrega() {
        System.out.println("No puedo entregar el pedido, no se ha preparado");
    }

    @Override
    public void entregar() {
        System.out.println("No es posible entregar la orden, no se ha preparado");
    }

    @Override
    public void cancelarOrden() {
        System.out.println("Ya se confirmo la orden, no es posible cancelar.");
    }
    
}
