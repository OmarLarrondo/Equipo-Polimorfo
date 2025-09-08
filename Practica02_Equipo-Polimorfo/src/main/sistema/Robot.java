package main.sistema;

import java.util.HashMap;
import java.util.Stack;

import main.estado.robot.EstadoActualRobot;
import main.estado.robot.EstadoDormido;
import main.estado.robot.EstadoEnEspera;
import main.estado.robot.EstadoTrabajando;
import main.productos.Producto;

/**
 * La clase {@code Robot} representa el robot de la sucursal, es el encargado de atender y preparar los productos.
 * 
 * <p>Este robot implementa el patrón de diseño <b>State</b>, en donde su comportamiento depende de su estado actual:
 * <ul>
 *   <li>{@link EstadoDormido}: El robot está dormido y espera ser despertado.</li>
 *   <li>{@link EstadoEnEspera}: El robot está listo para recibir órdenes o atender clientes.</li>
 *   <li>{@link EstadoTrabajando}: El robot se encuentra preparando un pediido.</li>
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
    /** Estado del robot, se encuentra dormido.*/
    private EstadoActualRobot estadoDormido;
    /** Estado en el que el robot está disponible esperando órdenes. */
    private EstadoActualRobot estadoEnEspera;
    /** Estado en el que el robot se encuentra preparando un pedido.*/
    private EstadoActualRobot estadoTrabajando;
    /** Estado actual del robot (se actualiza dinámicamente según el flujo de pedidos). */
    private EstadoActualRobot estadoActual;

    /** Pila de pedidos pendientes por procesar. */
    private Stack<Pedido> pedidos;
    /** Mapa de pedidos listos para entrega, identificados por un ID del pedido. */
    private HashMap<Integer,Pedido> pedidosListos;

    /**
     * Constructor de la clase {@code Robot}, inicializa todos los estados y colecciones.
     * 
     * @param estadoDormido referencia al estado dormido
     * @param estadoTrabajando referencia al estado trabajando
     * @param estadoEnEspera referencia al estado en espera
     * @param estadoActual estado inicial del robot
     * @param pedidos la pila de pedidos en proceso
     * @param pedidosListos mapa de pedidos listos para entrega.
     */
    public Robot(EstadoActualRobot estadoDormido, EstadoActualRobot estadoTrabajando, EstadoActualRobot estadoEnEspera,
            EstadoActualRobot estadoActual, Stack<Pedido> pedidos, HashMap<Integer, Pedido> pedidosListos) {
        this.estadoDormido = new EstadoDormido(this);
        this.estadoTrabajando = new EstadoTrabajando(this, false);
        this.estadoEnEspera = new EstadoEnEspera(this);
        this.estadoActual = estadoDormido; // el robot inicia dormido
        this.pedidos = pedidos;
        this.pedidosListos = pedidosListos;
    }

    /**
     * Indica que un pedido está listo en la sucursal para ser entregado.
     * @param sucursal la sucursal que recibirá el pedido listo
     */
    private void inidcarPedidoListo(Sucursal sucursal){
        // aqui va codigo
    }

    /**
     * Atiende un nuevo pedido asignado al robot.
     * @param pedido el pedido que será atendido
     */
    public void atenderPedido(Pedido pedido){
        estadoActual.atenderPedido(pedido);
    }

    /**
     * El robot agrega un producto al pedido.
     * @param producto el producto que se desea agregar al pedido
     */
    public void agregarProducto(Producto producto) {
        estadoActual.agregarProducto(producto);
    }

    /**
     * Confirma la orden actual, ya no se puede cancelar
     */
    public void confirmarOrden(){
        estadoActual.confirmarOrden();
    }

    /**
     * Cancela la orden en curso.
     */
    public void cancelarOrden(){
        estadoActual.cancelarOrden();
    }

    /**
     * Inicia la preparación de la orden actual.
     */
    public void iniciarPreparacion(){
        estadoActual.iniciarPreparacion();
    }

    /**
     * Solicita la entrega de un pedido terminado a la sucursal.
     */
    public void solicitarEntrega(){
        estadoActual.solicitarEntrega();
    }

    /**
     * Realiza la entrega de un pedido listo al cliente.
     */
    public void entregar(){
        estadoActual.entregar();
    }

    /**
     * @return el estado ACTUAL dormido del robot
     */
    public EstadoActualRobot getEstadoDormido() {
        return estadoDormido;
    }

    /**
     * @param estadoDormido el estado dormido a asignar
     */
    public void setEstadoDormido(EstadoActualRobot estadoDormido) {
        this.estadoDormido = estadoDormido;
    }

    /**
     * @return el estado trabajando del robot
     */
    public EstadoActualRobot getEstadoTrabajando() {
        return estadoTrabajando;
    }

    /**
     * @param estadoTrabajando el estado trabajando a asignar
     */
    public void setEstadoTrabajando(EstadoActualRobot estadoTrabajando) {
        this.estadoTrabajando = estadoTrabajando;
    }

    /**
     * @return el estado en espera del robot
     */
    public EstadoActualRobot getEstadoEnEspera() {
        return estadoEnEspera;
    }

    /**
     * @param estadoEnEspera el estado en espera a asignar
     */
    public void setEstadoEnEspera(EstadoActualRobot estadoEnEspera) {
        this.estadoEnEspera = estadoEnEspera;
    }

    /**
     * @return el estado actual del robot
     */
    public EstadoActualRobot getEstado() {
        return estadoActual;
    }

    /**
     * @param estado el nuevo estado actual del robot
     */
    public void setEstado(EstadoActualRobot estado) {
        this.estadoActual = estado;
    }

    /**
     * @return la pila de pedidos en proceso
     */
    public Stack<Pedido> getPedidos() {
        return pedidos;
    }

    /**
     * @param pedidos la pila de pedidos a asignar
     */
    public void setPedidos(Stack<Pedido> pedidos) {
        this.pedidos = pedidos;
    }

    /**
     * @return el mapa de pedidos listos
     */
    public HashMap<Integer, Pedido> getPedidosListos() {
        return pedidosListos;
    }

    /**
     * @param pedidosListos el mapa de pedidos listos a asignar
     */
    public void setPedidosListos(HashMap<Integer, Pedido> pedidosListos) {
        this.pedidosListos = pedidosListos;
    }
}
