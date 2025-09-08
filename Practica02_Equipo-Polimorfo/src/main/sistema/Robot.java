package main.sistema;

import java.util.HashMap;
import java.util.Stack;

import main.estado.robot.EstadoActualRobot;
import main.estado.robot.EstadoDormido;
import main.estado.robot.EstadoEnEspera;
import main.estado.robot.EstadoTrabajando;
import main.productos.Producto;

public class Robot {

    /** */
    private EstadoActualRobot estadoDormido;
    /** */
    private EstadoActualRobot estadoTrabajando;
    /** */
    private EstadoActualRobot estadoEnEspera;
    /** */
    private EstadoActualRobot estadoActual;

    private Stack<Pedido> pedidos;
    private HashMap<Integer,Pedido> pedidosListos;

    /**
     * Contructor de la clase, para inicializar los atributos dle Robot
     * @param estadoDormido
     * @param estadoTrabajando
     * @param estadoEnEspera
     * @param estado
     * @param pedidos
     * @param pedidosListos
     */
    public Robot(EstadoActualRobot estadoDormido, EstadoActualRobot estadoTrabajando, EstadoActualRobot estadoEnEspera,
            EstadoActualRobot estadoActual, Stack<Pedido> pedidos, HashMap<Integer, Pedido> pedidosListos) {
        this.estadoDormido = new EstadoDormido(this);
        this.estadoTrabajando = new EstadoTrabajando(this, false);
        this.estadoEnEspera = new EstadoEnEspera(this);
        this.estadoActual = estadoDormido; // al inicio está mimido
        this.pedidos = pedidos;
        this.pedidosListos = pedidosListos;
    }

    /**
     * @param sucursal
     */
    private void inidcarPedidoListo(Sucursal sucursal){

    }

    /**
     * 
     */
    public void atenderPedido(){
        estadoActual.atenderPedido();

    }
    /**
     * Agrega un producto al pedido actual del robot.
     * 
     * @param producto el producto que se desea agregar al pedido
     */
    public void agregarProducto(Producto producto) {
        estadoActual.agregarProducto(producto);
    }

    /**
     * 
     */
    public void confirmarOrden(){
        estadoActual.confirmarOrden();
    }
    /**
     * 
     */
    public void cancelarOrden(){
        estadoActual.cancelarOrden();
    }
    /**
     * 
     */
    public void iniciarPreparacion(){
        estadoActual.iniciarPreparacion();

    }

    /**
     * 
     */
    public void solicitarEntrega(){
        estadoActual.solicitarEntrega();
    }
    /**
     * 
     */
    public void entregar(){
        estadoActual.entregar();
    }


    /**
     * @return El estado actual del robot 
     */
    public EstadoActualRobot getEstadoDormido() {
        return estadoDormido;
    }

    /**
     * @param estadoDormido
     */
    public void setEstadoDormido(EstadoActualRobot estadoDormido) {
        this.estadoDormido = estadoDormido;
    }

    public EstadoActualRobot getEstadoTrabajando() {
        return estadoTrabajando;
    }

    public void setEstadoTrabajando(EstadoActualRobot estadoTrabajando) {
        this.estadoTrabajando = estadoTrabajando;
    }

    public EstadoActualRobot getEstadoEnEspera() {
        return estadoEnEspera;
    }

    public void setEstadoEnEspera(EstadoActualRobot estadoEnEspera) {
        this.estadoEnEspera = estadoEnEspera;
    }

    public EstadoActualRobot getEstado() {
        return estadoActual;
    }

    public void setEstado(EstadoActualRobot estado) {
        this.estadoActual = estado;
    }

    public Stack<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(Stack<Pedido> pedidos) {
        this.pedidos = pedidos;
    }

    public HashMap<Integer, Pedido> getPedidosListos() {
        return pedidosListos;
    }

    public void setPedidosListos(HashMap<Integer, Pedido> pedidosListos) {
        this.pedidosListos = pedidosListos;
    }

    



    
}