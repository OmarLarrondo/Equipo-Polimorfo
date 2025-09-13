package estado.robot;

import java.util.ArrayList;
import java.util.List;

import productos.Producto;
import sistema.Pedido;
import sistema.Robot;
import sistema.Ticket;

public class EstadoEsperandoEntregar implements EstadoActualRobot{

    private final Robot robot; // coincide con el UML
    /**
     * EL constructor para iniciaizar el EstadoDormido del Robot
     * @param robot  el robot al que pertenece este estado, utilizado para cambiar su estado o acceder a sus pedidos
     */

    public EstadoEsperandoEntregar(Robot robot) {
        this.robot = robot;
    }
    @Override
    public void atenterCliente() {
        System.out.println("No es posible atender un cliente.");
    }

    @Override
    public void atenderPedido(Pedido pedido) {
        System.out.println("Debo entregar mi pedido actual primero.");
    }

    @Override
    public void agregarProducto(Producto producto) {
        System.out.println("Debo entregar mi pedido actual primero.");
    }

    @Override
    public void confirmarOrden() {
        System.out.println("Debo entregar mi pedido actual primero.");
    }

    @Override
    public void iniciarPreparacion() {
        System.out.println("Debo entregar mi pedido actual primero.");
    }

    @Override
    public void solicitarEntrega() {
        System.out.println("Claro, te entregare tu pedido");
        entregar();
    }

    @Override
    public void entregar() {
        Pedido pedido = robot.getPedidoActual();

        if (pedido == null) {
        System.out.println("No hay pedido para entregar.");
        return;
        }

        List<Producto> productos = pedido.getProductos();

        List<String> nombresProductos = new ArrayList<>();
        for (Producto p : productos) {
            nombresProductos.add(p.getNombre());
        }

        double total = 0;
        for(Producto p: productos){
            total +=p.getPrecio();
        }

        Ticket ticket = new Ticket(nombresProductos,total);
        ticket.imprimir();

        System.out.println("Pedido entregado");

        robot.setPedidoActual(null);
        robot.setEstadoActual(robot.getEstadoDormido());
    }

    @Override
    public void cancelarOrden() {
        System.out.println("Debo entregar mi pedido actual primero.");
    }
    
}
