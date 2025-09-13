package estado.robot;

import productos.Producto;
import sistema.Pedido;
import sistema.Robot;

public class EstadoTomandoOrden implements EstadoActualRobot{

    private final Robot robot; // coincide con el UML

    /**
     * EL constructor para iniciaizar el EstadoDormido del Robot
     * @param robot  el robot al que pertenece este estado, utilizado para cambiar su estado o acceder a sus pedidos
     */
    public EstadoTomandoOrden(Robot robot) {
        this.robot = robot;
    }

    @Override
    public void atenterCliente() {
        System.out.println("Ya estoy atentiendo.");
    }

    @Override
    public void atenderPedido(Pedido pedido) {
        // si no hay pedido actual, ent atiende al nuevo
        if (robot.getPedidoActual() == null) {
            robot.setPedidoActual(pedido);
            System.out.println("Pedido asignado al robot y listo para agregar productos.");
        }
        System.out.println("Espera, ya estoy atendiendo un pedido.");
    }

    @Override
    public void agregarProducto(Producto producto) {
    if (producto == null) {
        System.out.println("Ops, el producto no existe.");
        return;
    }

    Pedido pedidoActual = robot.getPedidoActual();
    if (pedidoActual == null) {
        System.out.println("No hay pedido activo para agregar productos.");
        return;
    }
    try {
    pedidoActual.agregarArticulo(producto);
    System.out.println("Producto agregado: " + producto.getNombre());
    } catch (Exception e) {
        System.out.println("Error no se puede agregar" +producto.getNombre());
        }
    }

    @Override
    public void confirmarOrden() {
        Pedido pedido = robot.getPedidoActual();

        if (pedido == null) {
        System.out.println("No hay pedido activo para confirmar.");
        return;
    }
    if (pedido.estaConfirmado()) {
        System.out.println("El pedido ya fue confirmado, anteriormente");
        return;
    }

    pedido.confirmar();
    System.out.println("Pedido confirmado. Iniciando preparación...");

    robot.setEstadoActual(robot.getEstadoConfirmarOrden());
    }

    @Override
    public void iniciarPreparacion() {
        System.out.println("Estoy tomando la orden, debes confirmar primero");
    }

    @Override
    public void solicitarEntrega() {
        System.out.println("Estoy tomando la orden. Espera");
    }

    @Override
    public void entregar() {
        System.out.println("Estoy tomando la orden. Espera");
    }

    @Override
    public void cancelarOrden() {
    Pedido pedido = robot.getPedidoActual();

    if (pedido == null) {
    System.out.println("No hay pedido activo para cancelar.");
    return;
    }

    if (pedido.estaConfirmado()) {
        System.out.println("El pedido ya fue confirmado y no es posible cancelarlo");
        return;
    }

    pedido.cancelar();;
    System.out.println("Pedido cancelado, el robot vuelve a dormir.");
    robot.setPedidoActual(null); 
    robot.setEstadoActual(robot.getEstadoDormido()); //vueleve a dormir.
    }    
}
