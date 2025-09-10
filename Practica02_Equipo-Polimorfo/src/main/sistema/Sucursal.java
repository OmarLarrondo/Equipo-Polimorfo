package main.sistema;

/**
 * Clase para representar una sucursal del sistema.
 * Cada sucursal tiene un nombre y un empleado robot
 */
public class Sucursal {
    /** Nombre identificador de la sucursal. */
    private String nombre;
    /** Robot empleado en la sucursal para atender y procesar pedidos*/
    private Robot empleadoRobot;

    /**
     * Procesa la recepción de un nuevo pedido en la sucursal.
     * Este método se encarga de registrar el pedido y asignarlo al robot
     *
     * @param nuevoPedido el pedido que ha sido recibido en la sucursal
     */
    public void procesarRecepcionPedido(Pedido nuevoPedido) {
        // aquí va código
    }

    /**
     * Procesa la petición de entrega de un pedido ya registrado en la sucursal.

     * @param idPedido id único del pedido que realizo la peticion
     */
    public void procesarPeticionEntrega(int idPedido) {
        // aquí va código
    }

    /**
     * Asigna un nuevo pedido al robot de la sucursal para que lo procese.
     *
     * @param pedido el pedido que será asignado al robot
     */
    private void asignarNuevoPedido(Pedido pedido) {
        // aquí va código
    }

    /**
     * Entrega un pedido que ya está listo al cliente.
     *
     * @param pedido el pedido que se entregará
     */
    private void entregarPedidoListo(Pedido pedido) {
        // aquí va código
    }
}
