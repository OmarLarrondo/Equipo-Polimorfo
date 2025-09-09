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
        throw new IllegalStateException("Espera, estoy atendiendo a un cliente");
    }

    @Override
    public void atenderPedido(Pedido pedido) {
        // si no hay pedido actual, ent atiende al nuevo
        if (robot.getPedidoActual() == null) {
            robot.setPedidoActual(pedido);
            System.out.println("Pedido asignado al robot y listo para agregar productos.");
        }
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
        System.out.println("El pedido ya fue confirmado.");
        return;
    }

    pedido.confirmar();
    System.out.println("Pedido confirmado. Iniciando preparación...");

    robot.setEstado(robot.getEstadoTrabajando());
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
            robot.setEstado(robot.getEstadoTrabajando());
        }else{
            System.out.println("EL pedido no esta confirmado. ");
        }
    }

    @Override
    public void solicitarEntrega() {
        throw new IllegalStateException("Espera, el robot debe terminar el pedido primero.");
    }

    @Override
    public void entregar() {
        throw new IllegalStateException("Espera, el robot debe terminar el pedido primero.");
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
    System.out.println("Pedido cancelado, el robot volvio a dormir.");
    robot.setPedidoActual(null); 
    robot.setEstado(robot.getEstadoDormido()); //vueleve a dormir.
    }
}