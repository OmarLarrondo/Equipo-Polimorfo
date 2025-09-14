package main.sistema;

import java.util.ArrayList;
import java.util.List;

import main.estado.pedido.EstadoPedido;
import main.estado.pedido.EstadoNoOrdenado;
import main.productos.Producto;
import main.productos.Pizza;
import main.productos.Helado;

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

    /**Lista de artículos solicitados por el cliente*/
    private List<Producto> articulos; 

    /**Sucursal en la que se registró el pedido*/
    private Sucursal sucursal;

    /**Estado actual del pedido (estadoNoOrdenado, estadoOrdenado, estadoHecho, estadoEntregado)*/
    private EstadoPedido estado;
    /**Si el pedido esta confirmado o no */
    private boolean confirmado;

    /**Indica si el pedido contiene al menos una pizza */
    private boolean tienePizza;

    /**Indica si el pedido contiene al menos un helado */
    private boolean tieneHelado;

    /**
     * Constructor vacío. Crea un pedido inicial sin nombre de cliente ni sucursal,
     * con la lista de productos vacía e inicializa el estado como EstadoNoOrdenado.
     *
     * <p>Se espera que estos datos se vayan completando durante el flujo del programa.</p>
     */
    public Pedido() {
        this.articulos = new ArrayList<>();
        this.sucursal = null;
        this.estado = new EstadoNoOrdenado(this);
        this.confirmado = false;
        this.tienePizza = false;
        this.tieneHelado = false;
    }

    /**
     * Constructor que crea un pedido con el nombre del cliente especificado.
     * Inicializa el pedido con una lista de artículos vacía y el estado NoOrdenado.
     *
     * @param nombreCliente el nombre del cliente que realiza el pedido
     */
    public Pedido(String nombreCliente) {
        this.nombreCliente = nombreCliente;
        this.articulos = new ArrayList<>();
        this.sucursal = null;
        this.estado = new EstadoNoOrdenado(this);
        this.confirmado = false;
        this.tienePizza = false;
        this.tieneHelado = false;
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
     * También actualiza los indicadores tienePizza y tieneHelado según el tipo de producto.
     * @param articuloNuevo el producto a agregar
     */
    public void agregarArticulo(Producto articuloNuevo) {
        articulos.add(articuloNuevo);

        if (articuloNuevo instanceof Pizza) {
            this.tienePizza = true;
        } else if (articuloNuevo instanceof Helado) {
            this.tieneHelado = true;
        }
    }

    /**
     * Envía la orden de este pedido a la sucursal correspondiente.
     * @param sucursal la sucursal a la que se enviará la orden
     */
    public void enviarOrden(Sucursal sucursal) {
        
    }

    /**
     * Solicita la entrega del pedido en la sucursal.
     * @param sucursal la sucursal que realizará la entrega
     */
    public void pedirEntrega(Sucursal sucursal) {
	
    }

    /**
     * Procesa la acción de agregar un producto al pedido,
     * delegando la operación al estado actual del pedido.
     * El comportamiento dependerá del estado en que se encuentre el pedido.
     *
     * @param articuloNuevo el producto que se quiere agregar
     * @throws IllegalArgumentException si el producto es null
     * @throws IllegalStateException si el estado actual no permite agregar productos
     */
    public void procesarAgregado(Producto articuloNuevo) {
        if (articuloNuevo == null) {
            throw new IllegalArgumentException("No se puede agregar un producto null");
        }

        if (!puedeAgregar(articuloNuevo)) {
            if (articuloNuevo instanceof Pizza && tienePizza) {
                throw new IllegalArgumentException("Ya existe una pizza en el pedido. Solo se permite una pizza por pedido.");
            } else if (articuloNuevo instanceof Helado && tieneHelado) {
                throw new IllegalArgumentException("Ya existe un helado en el pedido. Solo se permite un helado por pedido.");
            } else {
                throw new IllegalArgumentException("No se puede agregar este producto al pedido.");
            }
        }

        estado.agregarArticulo(articuloNuevo);
    }

    /**
     * Procesa el envío de la orden a la sucursal delegando al estado actual.
     * Se actualiza el estado del pedido según corresponda.
     *
     * @param sucursal la sucursal a la que se enviará la orden
     * @throws IllegalArgumentException si la sucursal es null
     * @throws IllegalStateException si el estado actual no permite enviar la orden
     */
    public void procesarEnvioOrden(Sucursal sucursal) {
        if (sucursal == null) {
            throw new IllegalArgumentException("La sucursal no puede ser null");
        }

        this.sucursal = sucursal; 
        estado.enviarOrden(sucursal);
    }

    /**
     * Procesa la petición de entrega del pedido,
     * delegando la operación al estado actual del pedido.
     *
     * @param sucursal la sucursal encargada de la entrega
     * @throws IllegalArgumentException si la sucursal es null
     * @throws IllegalStateException si el estado actual no permite solicitar entrega
     */
    public void procesarPeticionEntrega(Sucursal sucursal) {
        if (sucursal == null) {
            throw new IllegalArgumentException("La sucursal no puede ser null");
        }

        estado.solicitarEntrega(sucursal);
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
     * @return lista de artículos
     */
    public List<Producto> getArticulos() {
        return articulos;
    }

    /**
     * Establece el estado del pedido. Este método se usa para las transiciones entre estados.
     *
     * @param nuevoEstado el nuevo estado del pedido
     */
    public void setEstadoPedido(EstadoPedido nuevoEstado) {
        if (nuevoEstado == null) {
            throw new IllegalArgumentException("El estado del pedido no puede ser null");
        }
        this.estado = nuevoEstado;
    }

    /**
     * Obtiene el estado actual del pedido.
     *
     * @return el estado actual del pedido
     */
    public EstadoPedido getEstadoPedido() {
        return this.estado;
    }

    /**
     * Marca el pedido como terminado de preparar.
     * Permite la transición de EstadoOrdenado a EstadoHecho.
     * Este método debería ser llamado por el Robot cuando termina de preparar todos los productos.
     */
    public void marcarComoHecho() {
        if (estado instanceof main.estado.pedido.EstadoOrdenado) {
            ((main.estado.pedido.EstadoOrdenado) estado).marcarComoHecho();
        } else {
            throw new IllegalStateException("Solo se puede marcar como hecho un pedido en estado Ordenado");
        }
    }

    /**
     * Obtiene el nombre del estado actual del pedido como una cadena legible.
     *
     * @return el nombre del estado actual
     */
    public String getNombreEstadoActual() {
        return estado.getClass().getSimpleName();
    }

    /**
     * Genera un ticket con la información del pedido actual.
     * Incluye todos los artículos del pedido con sus nombres y precios,
     * así como el total de la compra.
     *
     * @return un nuevo ticket con la información del pedido
     */
    public Ticket generarTicket() {
        List<String> productosTicket = new ArrayList<>();
        double totalTicket = 0.0;

        for (Producto articulo : articulos) {
            String lineaTicket = articulo.getNombre() + " - $" + articulo.getPrecio();
            productosTicket.add(lineaTicket);
            totalTicket += articulo.getPrecio();
        }

        return new Ticket(productosTicket, totalTicket);
    }

    /**
     * Verifica si se puede agregar un producto específico al pedido.
     * Según las reglas de negocio, el cliente solo puede ordenar:
     * - Una pizza
     * - Un helado
     * - Uno de cada uno (pizza + helado)
     *
     * @param producto el producto que se desea agregar
     * @return true si el producto puede ser agregado, false en caso contrario
     */
    public boolean puedeAgregar(Producto producto) {
        if (producto == null) {
            return false;
        }

        if (producto instanceof Pizza) {
	    
            return !tienePizza;
	    
        } else if (producto instanceof Helado) {
	    
            return !tieneHelado;
        }

        return true;
    }

    /**
     * Indica si el pedido contiene al menos una pizza.
     *
     * @return true si el pedido contiene una pizza, false en caso contrario
     */
    public boolean getTienePizza() {
        return tienePizza;
    }

    /**
     * Indica si el pedido contiene al menos un helado.
     *
     * @return true si el pedido contiene un helado, false en caso contrario
     */
    public boolean getTieneHelado() {
        return tieneHelado;
    }
}
