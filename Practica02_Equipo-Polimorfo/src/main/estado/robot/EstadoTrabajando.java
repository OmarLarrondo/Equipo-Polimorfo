package main.estado.robot;

import main.productos.Producto;
import main.sistema.Robot;

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
        //aqui va codigo 
    }

    /**
     * No puede atender un nuevo cliente mientras el robot está trabajando.
     * 
     * <p>Si se llama a este método mientras el robot esta trabajando ,
     * se lanza una excepcion o un print</p>
     */
    @Override
    public void atenderPedido() {
        System.out.println("El robot ya está trabajando. No puede atender otro cliente.");
        // o tambien  throw new UnsupportedOperationException("El robot ya está trabajando");
    }

    /**
     * No se puede agregar un producto cuando el robot esta trabajando 
     * 
     * <p>Si se llama a este método mientras el robot esta trabajando ,
     * se lanza una excepcion o un print</p>
     * 
     * @param producto el producto que se desea agregar al pedido
     */
    @Override
    public void agregarProducto(Producto producto) {
        // Aquí va el código de agregación
    }

    /**
     * No se puede confirmar la orden cuando el robot esta trabajando
     * 
     * <p>Si se llama a este método mientras el robot esta trabajando ,
     * se lanza una excepcion o un print</p>
     */
    @Override
    public void confirmarOrden() {
        // Aquí va el código de confirmación
    }

    /**
     * Inicia la preparación de los productos del pedido.
     * 
     * <p>El robot comienza a ejecutar los pasos correspondientes
     * a cada producto (pizza o helado) según la receta o instrucciones.</p>
     */
    @Override
    public void iniciarPreparacion() {
        // Aquí va el código de preparación
    }

    /**
     * No se puede solicitar la entrega si el robot esta trabajando.
     * 
     * <p>Si se llama a este método mientras el robot esta trabajando ,
     * se lanza una excepcion o un print</p>
     */
    @Override
    public void solicitarEntrega() {
        // Aquí va el código de solicitud de entrega
    }

    /**
     * No se puede entregar el pedido si el robot esta trabajando.
     * 
     * <p>Si se llama a este método mientras el robot esta trabajando ,
     * se lanza una excepcion o un print</p>
     */
    @Override
    public void entregar() {
        // Aquí va el código de entrega
    }

    @Override
    public void cancelarOrden() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'cancelarOrden'");
    }

}
