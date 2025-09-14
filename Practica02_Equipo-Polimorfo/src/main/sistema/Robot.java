package main.sistema;

import main.estado.robot.EstadoActualRobot;
import main.estado.robot.EstadoConfirmarOrden;
import main.estado.robot.EstadoDormido;
import main.estado.robot.EstadoEsperandoEntregar;
import main.estado.robot.EstadoTomandoOrden;
import main.estado.robot.EstadoTrabajando;
import main.productos.Producto;

/**
 * La clase {@code Robot} representa el robot de la sucursal, es el encargado de atender y preparar los productos.
 * 
 * <p>Este robot implementa el patrón de diseño <b>State</b>, en donde su comportamiento depende de su estado actual:
 * <ul>
 *   <li>{@link EstadoDormido}: El robot está dormido y espera ser despertado.</li>
 *   <li>{@link EstadoEnEspera}: El robot está listo para recibir órdenes o atender clientes.</li>
 *   <li>{@link EstadoTrabajando}: El robot se encuentra preparando un pedido.</li>
 * </ul>
 * 
 * <p>El robot mantiene una pila de pedidos en proceso y un registro de pedidos listos.
 * Su flujo de trabajo incluye recibir pedidos, agregar productos, confirmar o cancelar órdenes,
 * iniciar la preparación, solicitar entrega y realizar la entrega.
 * 
 * @author Equipo-polimorfo
 * @version 1.0
 */
public class Robot {

    /** Estado dormido del robot. */
    private EstadoDormido estadoDormido;
    /** Estado en el que el robot está tomando una orden. */
    private EstadoTomandoOrden estadoTomandoOrden;
    /** Estado en el que el robot confirma la orden. */
    private EstadoConfirmarOrden estadoConfirmarOrden;
    /** Estado en el que el robot se encuentra preparando un pedido. */
    private EstadoTrabajando estadoTrabajando;
    /** Estado en el que el robot espera a que el cliente solicite la entrega. */
    private EstadoEsperandoEntregar estadoEsperandoEntregar;
    /** Estado en el que el robot entregó la orden. */

    /** Estado actual del robot (interfaz). */
    private EstadoActualRobot estadoActual;
    /** Pedido que está siendo procesado actualmente por el robot. */
    private Pedido pedidoActual;

    /**
     * Constructor de la clase {@code Robot}, inicializa todos los estados y colecciones.
     */
    public Robot() {
        this.estadoDormido = new EstadoDormido(this);
        this.estadoTrabajando = new EstadoTrabajando(this, false);
        this.estadoTomandoOrden = new EstadoTomandoOrden(this);
        this.estadoConfirmarOrden = new EstadoConfirmarOrden(this);
        this.estadoEsperandoEntregar = new EstadoEsperandoEntregar(this);
        this.estadoActual = estadoDormido;
    }

    // ========== Acciones que el robot hace al pedido =============

    /**
     * Obtiene el pedido que actualmente está siendo procesado por el robot. 
     * @return el pedido actual, o {@code null} si no hay ninguno.
     */
    public Pedido getPedidoActual() {
        return pedidoActual;
    }

    /**
     * Asigna un pedido como el pedido actual que el robot está procesando. 
     * @param pedidoActual el pedido a asignar como actual
     */
    public void setPedidoActual(Pedido pedidoActual) {
        this.pedidoActual = pedidoActual;
    }

    // ==================== Acciones delegadas al estado ====================

    /**
     * Atiende un pedido. La acción se le pasa al estado actual del robot.
     * @param pedido el pedido que será atendido
     */
    public void atenderPedido(Pedido pedido) {
        estadoActual.atenderPedido(pedido);
    }

    /**
     * Agrega un producto al pedido actual. Encargado al estado actual.
     * @param producto el producto que se desea agregar
     */
    public void agregarProducto(Producto producto) {
        estadoActual.agregarProducto(producto);
    }

    /**
     * Confirma la orden actual. Encargado al estado actual.
     */
    public void confirmarOrden() {
        estadoActual.confirmarOrden();
    }

    /**
     * Cancela la orden en curso. Encargado al estado actual.
     */
    public void cancelarOrden() {
        estadoActual.cancelarOrden();
    }

    /**
     * Inicia la preparación de la orden actual. Encargado al estado actual.
     */
    public void iniciarPreparacion() {
        estadoActual.iniciarPreparacion();
    }

    /**
     * Solicita la entrega de un pedido terminado. Encargado al estado actual.
     */
    public void solicitarEntrega() {
        estadoActual.solicitarEntrega();
    }

    /**
     * Realiza la entrega del pedido al cliente. Encargado al estado actual.
     */
    public void entregar() {
        estadoActual.entregar();
    }

    // ============ Getters y setters de los estados concretos ===================

    /**
     * Obtiene el estado dormido del robot.
     * @return el estado dormido
     */
    public EstadoDormido getEstadoDormido() {
        return estadoDormido;
    }

    /**
     * Asigna el estado dormido al robot.
     * @param estadoDormido el estado a asignar
     */
    public void setEstadoDormido(EstadoDormido estadoDormido) {
        this.estadoDormido = estadoDormido;
    }

    /**
     * Obtiene el estado trabajando del robot.
     * @return el estado trabajando
     */
    public EstadoTrabajando getEstadoTrabajando() {
        return estadoTrabajando;
    }

    /**
     * Asigna el estado trabajando al robot.
     * @param estadoTrabajando el estado a asignar
     */
    public void setEstadoTrabajando(EstadoTrabajando estadoTrabajando) {
        this.estadoTrabajando = estadoTrabajando;
    }

    /**
     * Obtiene el estado tomando orden del robot.
     * @return el estado tomando orden
     */
    public EstadoTomandoOrden getEstadoTomandoOrden() {
        return estadoTomandoOrden;
    }

    /**
     * Asigna el estado tomando orden al robot.
     * @param estadoTomandoOrden el estado a asignar
     */
    public void setEstadoTomandoOrden(EstadoTomandoOrden estadoTomandoOrden) {
        this.estadoTomandoOrden = estadoTomandoOrden;
    }

    /**
     * Obtiene el estado confirmando orden del robot.
     * @return el estado confirmando orden
     */
    public EstadoConfirmarOrden getEstadoConfirmarOrden() {
        return estadoConfirmarOrden;
    }

    /**
     * Asigna el estado confirmando orden al robot.
     * @param estadoConfirmarOrden el estado a asignar
     */
    public void setEstadoConfirmarOrden(EstadoConfirmarOrden estadoConfirmarOrden) {
        this.estadoConfirmarOrden = estadoConfirmarOrden;
    }

    /**
     * Obtiene el estado esperando entregar del robot.
     * @return el estado esperando entregar
     */
    public EstadoEsperandoEntregar getEstadoEsperandoEntregar() {
        return estadoEsperandoEntregar;
    }

    /**
     * Asigna el estado esperando entregar al robot.
     * @param estadoEsperandoEntregar el estado a asignar
     */
    public void setEstadoEsperandoEntregar(EstadoEsperandoEntregar estadoEsperandoEntregar) {
        this.estadoEsperandoEntregar = estadoEsperandoEntregar;
    }

    // ==================== Estado actual (interfaz) ====================

    /**
     * Obtiene el estado actual del robot.
     * @return el estado actual
     */
    public EstadoActualRobot getEstadoActual() {
        return estadoActual;
    }

    /**
     * Asigna el estado actual del robot .
     * @param estadoActual el estado a asignar
     */
    public void setEstadoActual(EstadoActualRobot estadoActual) {
        this.estadoActual = estadoActual;
    }
}