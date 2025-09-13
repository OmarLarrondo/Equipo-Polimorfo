package estado.robot;

import java.util.ArrayList;
import java.util.List;

import productos.Producto;
import sistema.Pedido;
import sistema.Robot;
import sistema.Ticket;

/**
 * Clase que representa el estado "Trabajando" del Robot.
 * 
 * <p>En este estado, el robot está procesando un pedido activo.
 * Puede agregar productos, confirmar órdenes, iniciar la preparación
 * y finalmente entregar los productos al cliente. No puede atender
 * nuevos clientes mientras está trabajando.</p>
 */
public class EstadoTrabajando implements EstadoActualRobot {

    /** El robot al que pertenece este estado. */
    private final Robot robot;

    /** Indica si la orden asociada ya fue preparada. */
    private boolean preparada = false;

    /**
     * Constructor para inicializar un estado de trabajo del Robot.
     * 
     * @param robot el robot al que pertenece este estado, utilizado para cambiar su estado o acceder a los pedidos
     * @param preparada indica si la orden asociada ya está preparada ({@code true}) o aún no ({@code false})
     */
    public EstadoTrabajando(Robot robot, boolean preparada) {
        this.robot = robot;
        this.preparada = preparada;
    }

    /**
     * No se puede atender un cliente mientras esta trabajando el robot
     * 
     * 
     * <p>Si se llama a este método mientras el robot esta trabajando,
     * se lanza una excepcion o un print</p>
     */
    @Override
    public void atenterCliente() {
        System.out.println("Espera, no te puedo atender, estoy trabajando");
    }

    /**
     * No puede atender un nuevo cliente mientras el robot está trabajando.
     * 
     * <p>Si se llama a este método mientras el robot esta trabajando ,
     * se lanza una excepcion o un print</p>
     */
    @Override
    public void atenderPedido(Pedido pedido) {
        System.out.println("Espera, no puedo atender tu pedido, estoy trabajando");
    }

    /**
     * No se puede agregar un producto cuando el robot esta trabajando 

     * <p>Si se llama a este método mientras el robot esta trabajando ,
     * se lanza una excepcion o un print</p>
     * 
     * @param producto el producto que se desea agregar al pedido
     */
    @Override
    public void agregarProducto(Producto producto) {
        System.out.println("Espera, no puedo agregar un producto, estoy trabajando");
    }

    /**
     * No se puede confirmar la orden cuando el robot esta trabajando
     * 
     * <p>Si se llama a este método mientras el robot esta trabajando ,
     * se lanza una excepcion o un print</p>
     */
    @Override
    public void confirmarOrden() {
        System.out.println("Espera, no puedo confirmar la orden,  estoy trabajando");
    }

    /**
     * No se puede iniciar una preparacion, cuando ya se esta trabajando en una.
     * 
     * <p>Si se llama a este método mientras el robot esta trabajando ,
     * se lanza una excepcion o un print</p>
     */
    @Override
    public void iniciarPreparacion() {
        System.out.println("Espera, no puedo iniciar la preparacion porque estoy trabajando en una.");
    }

    /**
     * No se puede solicitar la entrega si el robot esta trabajando.                                                                                                                                                                            1
     * 
     * <p>Si se llama a este método mientras el robot esta trabajando ,
     * se lanza una excepcion o un print</p>
     */
    @Override
    public void solicitarEntrega() {
    Pedido pedido = robot.getPedidoActual();

    if (pedido == null) {
        System.out.println("No hay pedido activo para entregar.");
        return;
    }

    if (preparada == false) {
        System.out.println("El pedido aún no está preparado. No se puede solicitar entrega.");
        return;
    }

    System.out.println("Pedido listo, solicitando entrega...");
    robot.setEstadoActual(robot.getEstadoEsperandoEntregar());
    entregar();
}


    /**
     * No se puede entregar el pedido si el robot esta trabajando.
     * 
     * <p>Si se llama a este método mientras el robot esta trabajando ,
     * se lanza una excepcion o un print</p>
     */
    @Override
    public void entregar() {
        System.out.println("Estoy trabajando, no te puedo entregar.");
    }


    @Override
    public void cancelarOrden() {
        System.out.println("No se puede cancelar la orden estoy trabajando en una.");
    }
}
