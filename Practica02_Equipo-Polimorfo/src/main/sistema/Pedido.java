package sistema;

import java.util.ArrayList;
import java.util.List;

import estado.pedido.EstadoPedido;
import productos.Producto;

/**
 * Clase para representar un pedido realizado por un cliente en una sucursal hacia el robot.
 * Un pedido contiene el nombre del cliente, los productos solicitados,
 * la sucursal a la que pertenece y el estado actual del pedido.
 *
 * <p>El pedido se construye inicialmente vacío y se va llenando durante el flujo del programa(por la console)</p>.
 */
public class Pedido {
    /**Nombre del cliente que realiza el pedido*/
    private String nombreCliente;

    /**Lista de productos solicitados por el cliente*/
    private List<Producto> productos; 

    /**Sucursal en la que se registró el pedido*/
    private Sucursal sucursal;

    /**Estado actual del pedido (estadoNoOrdenado, estadoOrdenado, estadoHecho, estadoEntregado)*/
    private EstadoPedido estadoPedido;
    /**Si el pedido esta confirmado o no */
    private boolean confirmado;

    /**
     * Constructor vacío. Crea un pedido inicial sin nombre de cliente ni sucursal,
     * con la lista de productos vacía y sin estado definido.
     * 
     * <p>Se espera que estos datos se vayan completando durante el flujo del programa.</p>
     */
    public Pedido() {
        this.productos = new ArrayList<>();
        this.sucursal = null;
        this.estadoPedido = null;
    }

    /**
     * Asigna el nombre del cliente al pedido.
     * @param nombreCliente el nombre del cliente
     */
    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    /**
     * Agrega un producto al pedido.
     * @param articuloNuevo el producto a agregar
     */
    public void agregarArticulo(Producto articuloNuevo) {
        productos.add(articuloNuevo);
    }

    /**
     * Envía la orden de este pedido a la sucursal correspondiente.
     * @param sucursal la sucursal a la que se enviará la orden
     */
    private void enviarOrden(Sucursal sucursal) {
        
    }

    /**
     * Solicita la entrega del pedido en la sucursal.
     * @param sucursal la sucursal que realizará la entrega
     */
    private void pedirEntrega(Sucursal sucursal) {
        // aquí va código
    }

    /**
     * Procesa la acción de agregar un producto al pedido,
     * se debe realizar las validaciones necesarias y agregar el producto.
     * @param articuloNuevo el producto que se quiere agregar
     */
    public void procesarAgregado(Producto articuloNuevo) {
        // aquí va código 
    }

    /**
     * Procesa el envío de la orden a la sucursal y se actualiza el estado del pedido según corresponda.
     * @param sucursal la sucursal a la que se enviará la orden
     */
    public void procesarEnvioOrden(Sucursal sucursal) {
        // aquí va código 
    }

    /**
     * Procesa la petición de entrega del pedido,
     * solicitando a la sucursal que prepare la entrega al cliente, si ya esta..
     * @param sucursal la sucursal encargada de la entrega
     */
    public void procesarPeticionEntrega(Sucursal sucursal) {
        // aquí va código
    }
    
    /**
     * Metodo para confirmar una orden
     */
    public void confirmar() {
        this.confirmado = true;
    }
    
    /**
     * Metodo para cancelar la orden
     */
    public void cancelar() {
        this.confirmado = false;
    }
    
    /**
     * Indica si la orden ha sido confirmada.
     *
     * @return true si la orden está confirmada, false en caso contrario.
     */
    public boolean estaConfirmado() {
        return confirmado;
    }

    /**
     * Devuelve la lista de productos del pedido.
     * @return lista de productos
     */
    public List<Producto> getProductos() {
        return productos;
    }
}
