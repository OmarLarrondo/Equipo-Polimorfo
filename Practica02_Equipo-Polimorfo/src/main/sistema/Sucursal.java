package main.sistema;

import main.estado.robot.EstadoActualRobot;
import main.estado.robot.EstadoDormido;
import main.estado.robot.EstadoEsperandoEntregar;

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
     * Constructor de la sucursal.
     * Inicializa la sucursal con un nombre y crea el robot empleado.
     * @param nombre el nombre de la sucursal
     */
    public Sucursal(String nombre) {
        this.nombre = nombre;
        this.empleadoRobot = new Robot();
    }

    /**
     * Obtiene el robot empleado de la sucursal.
     * @return el robot empleado
     */
    public Robot getEmpleadoRobot() {
        return empleadoRobot;
    }

    /**
     * Obtiene el nombre de la sucursal.
     * @return el nombre de la sucursal
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Procesa la recepción de un nuevo pedido en la sucursal.
     * Este método se encarga de registrar el pedido y asignarlo al robot
     *
     * @param nuevoPedido el pedido que ha sido recibido en la sucursal
     */
    public void procesarRecepcionPedido(Pedido nuevoPedido) {

        if (empleadoRobot.getEstadoActual() instanceof EstadoDormido) {
            empleadoRobot.getEstadoActual().atenterCliente();
        }

        empleadoRobot.atenderPedido(nuevoPedido);

        asignarNuevoPedido(nuevoPedido);
    }


    /**
     * Procesa la petición de entrega de un pedido ya registrado en la sucursal.
     * @param pedido el pedido que realizo el cliente.
     */
    public void procesarPeticionEntrega(Pedido pedido) {
        if(empleadoRobot.getEstadoActual() instanceof EstadoEsperandoEntregar){
            if(empleadoRobot.getPedidoActual().equals(pedido)){
            empleadoRobot.solicitarEntrega();
            empleadoRobot.setPedidoActual(null);
            }else{
            System.out.println("El pedido no existe en la sucursal: " + nombre);
            }
        }else{
            System.out.println("El robot no está listo para entregar pedidos");
        }
        
    }

    /**
     * Asigna un nuevo pedido al robot de la sucursal para que lo procese.
     *
     * @param pedido el pedido que será asignado al robot
     */
    private void asignarNuevoPedido(Pedido pedido) {
        empleadoRobot.setPedidoActual(pedido);
    
    }

    /**
     * Entrega un pedido que ya está listo al cliente.
     *
     * @param pedido el pedido que se entregará
     */
    private void entregarPedidoListo(Pedido pedido) {
        // aquí va código
        //Creo que no es necesario, porque en procesoPeticionEntrega, ya se encarga de entregarlo
        //
    }
}
