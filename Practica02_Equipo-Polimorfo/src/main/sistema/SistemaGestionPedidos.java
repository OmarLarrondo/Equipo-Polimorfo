package main.sistema;

import main.productos.Pizza;
import main.productos.Helado;
import main.productos.helados.ComponenteHelado;
import main.productos.helados.HeladoSimple;
import main.productos.helados.*;
import main.productos.preparadores.*;
import main.enums.TipoMasa;
import main.enums.SaborHelado;
import main.estado.robot.*;

import java.util.List;
import java.util.ArrayList;

/**
 * Sistema de gestión de pedidos para la pizzería "El Pequeño Cesarín".
 * 
 * Esta clase actúa como el controlador principal del sistema, coordinando
 * la interacción entre el robot, los pedidos, la interfaz de usuario y
 * todos los componentes del sistema. Implementa el flujo completo desde
 * que el robot está dormido hasta la entrega del pedido.
 * 
 * El sistema maneja:
 * - Creación y gestión de pedidos
 * - Coordinación del robot y sus estados (patrón State)
 * - Selección de productos (pizzas con patrón Template, helados con patrón Decorator)
 * - Interfaz de usuario para la entrada de datos
 * - Generación y visualización de tickets
 * 
 * Flujo típico de operación:
 * 1. Robot dormido → Cliente llama robot
 * 2. Robot despierto → Tomar orden (pizza/helado)
 * 3. Confirmar orden → Robot no puede volver a dormir
 * 4. Iniciar preparación → Robot trabaja
 * 5. Solicitar entrega →  ticket y entregar
 * 6. Robot vuelve a dormir
 * 
 * @author Equipo-Polimorfo
 * @version 1.0
 */
public class SistemaGestionPedidos {
    
    /**
     * La sucursal donde opera el sistema
     */
    private Sucursal sucursal;
    
    /**
     * El pedido que se está procesando actualmente
     */
    private Pedido pedidoActual;
    
    /**
     * Nombre del cliente actual
     */
    private String clienteActual;
    
    /**
     * Interfaz de usuario para mostrar menús e interactuar con el cliente
     */
    private InterfazUsuarioCLI interfazUsuario;
    
    /**
     * Gestor de entrada para manejar la lectura de datos del usuario
     */
    private GestorEntrada gestorEntrada;
    
    /**
     * Constructor del sistema de gestión de pedidos.
     * Inicializa todos los componentes necesarios del sistema.
     */
    public SistemaGestionPedidos() {
        this.gestorEntrada = new GestorEntrada();
        this.gestorEntrada.configurarEntrada();
        
        this.interfazUsuario = new InterfazUsuarioCLI(this.gestorEntrada);
        
        this.sucursal = new Sucursal("El Pequeño Cesarín - Sucursal Principal");
        
        this.pedidoActual = null;
        this.clienteActual = null;
    }
    
    /**
     * Método principal de ejecución del sistema.
     * Mantiene el bucle principal del programa mostrando el menú
     * y procesando las opciones del usuario.
     */
    public void ejecutar() {
        interfazUsuario.mostrarMensaje("¡Bienvenido al sistema de El Pequeño Cesarín!");
        interfazUsuario.mostrarMensaje("El robot está inicializado y listo para trabajar.");
        
        boolean continuar = true;
        
        while (continuar) {
            try {
                interfazUsuario.mostrarMenuPrincipal();
                int opcion = gestorEntrada.leerOpcion(1, 3);
                
                switch (opcion) {
                    case 1:
                        procesarPedido();
                        break;
                    case 2:
                        mostrarEstadoRobot();
                        break;
                    case 3:
                        interfazUsuario.mostrarMensaje("Liberando recursos...");
                        gestorEntrada.liberarRecursos();
                        interfazUsuario.mostrarMensaje("Gracias por usar El Pequeño Cesarín. ¡Hasta pronto!");
                        continuar = false;
                        break;
                    default:
                        interfazUsuario.mostrarError("Opción no válida. Por favor, elija una opción del 1 al 3.");
                        break;
                }
            } catch (Exception e) {
                interfazUsuario.mostrarError("Error en el sistema: " + e.getMessage());
                interfazUsuario.mostrarMensaje("Intentando continuar...");
            }
        }
    }
    
    /**
     * Procesa un pedido completo coordinando todos los componentes del sistema.
     */
    public void procesarPedido() {
        try {
            llamarRobot();
            
            iniciarNuevoPedido();
            
            boolean ordenCompleta = false;
            while (!ordenCompleta) {
                interfazUsuario.mostrarMensaje("\n=== TOMAR ORDEN ===");
                interfazUsuario.mostrarMensaje("1. Agregar pizza");
                interfazUsuario.mostrarMensaje("2. Agregar helado");
                interfazUsuario.mostrarMensaje("3. Confirmar orden");
                interfazUsuario.mostrarMensaje("4. Cancelar orden");
                
                int opcion = gestorEntrada.leerOpcion(1, 4);
                
                switch (opcion) {
                    case 1:
                        agregarPizza();
                        break;
                    case 2:
                        agregarHelado();
                        break;
                    case 3:
                        if (pedidoActual != null && !pedidoActual.getProductos().isEmpty()) {
                            ordenCompleta = confirmarOrden();
                        } else {
                            interfazUsuario.mostrarError("No hay productos en el pedido. Agregue al menos un producto antes de confirmar.");
                        }
                        break;
                    case 4:
                        cancelarOrden();
                        return; 
                    default:
                        interfazUsuario.mostrarError("Opción no válida.");
                        break;
                }
            }
            
            coordinarRobot();
            
        } catch (Exception e) {
            interfazUsuario.mostrarError("Error procesando el pedido: " + e.getMessage());
	    
            if (pedidoActual != null) {
                cancelarOrden();
            }
        }
    }
    
    /**
     * Coordina las acciones del robot según su estado actual.
     * Maneja las transiciones de estado y las acciones disponibles.
     */
    public void coordinarRobot() {
        Robot robot = sucursal.getEmpleadoRobot();
        
        interfazUsuario.mostrarMensaje("\n=== COORDINACIÓN DEL ROBOT ===");
        
        interfazUsuario.mostrarMensaje("Orden confirmada. El robot procederá con la preparación.");
        
        interfazUsuario.mostrarMensaje("\n¿Desea que el robot inicie la preparación?");
        interfazUsuario.mostrarMensaje("1. Sí, iniciar preparación");
        interfazUsuario.mostrarMensaje("2. Esperar");
        
        int opcionPreparacion = gestorEntrada.leerOpcion(1, 2);
        
        if (opcionPreparacion == 1) {
            iniciarPreparacion();
        } else {
            interfazUsuario.mostrarMensaje("Esperando instrucciones para iniciar preparación...");
            return;
        }
        
        interfazUsuario.mostrarMensaje("\nLa preparación ha terminado. El robot está esperando la solicitud de entrega.");
        
        interfazUsuario.mostrarMensaje("¿Desea solicitar la entrega del pedido?");
        interfazUsuario.mostrarMensaje("1. Sí, solicitar entrega");
        interfazUsuario.mostrarMensaje("2. Esperar");
        
        int opcionEntrega = gestorEntrada.leerOpcion(1, 2);
        
        if (opcionEntrega == 1) {
            solicitarEntrega();
        } else {
            interfazUsuario.mostrarMensaje("El pedido está listo. Puede solicitar la entrega cuando esté preparado.");
            return;
        }
        
        interfazUsuario.mostrarMensaje("\n¡Pedido entregado exitosamente!");
        interfazUsuario.mostrarMensaje("El robot está regresando al estado dormido...");
        
        pedidoActual = null;
        clienteActual = null;
    }
    
    /**
     * Inicia un nuevo pedido solicitando el nombre del cliente
     * y preparando el sistema para recibir la orden.
     */
    public void iniciarNuevoPedido() {
        interfazUsuario.mostrarMensaje("\n=== NUEVO PEDIDO ===");
        
        clienteActual = gestorEntrada.leerTexto("Ingrese el nombre del cliente: ");
        
        pedidoActual = new Pedido();
        pedidoActual.setNombreCliente(clienteActual);
        
        sucursal.getEmpleadoRobot().setPedidoActual(pedidoActual);
        
        interfazUsuario.mostrarMensaje("Pedido iniciado para el cliente: " + clienteActual);
        interfazUsuario.mostrarMensaje("El robot está listo para tomar la orden.");
    }
    
    /**
     * Muestra el menú de pizzas disponibles y permite al usuario
     * seleccionar una pizza con su tipo de masa.
     */
    public void agregarPizza() {
        interfazUsuario.mostrarMenuTiposPizza();
        
        int tipoPizza = gestorEntrada.leerOpcion(1, 5);
        
        interfazUsuario.mostrarMenuTiposMasa();
	
        int tipoMasaOpcion = gestorEntrada.leerOpcion(1, 3);
        TipoMasa tipoMasa;
        
        switch (tipoMasaOpcion) {
            case 1:
		tipoMasa = TipoMasa.NAPOLITANA;
		break;
            case 2:
		tipoMasa = TipoMasa.ROMANA;
		break;
            case 3:
		tipoMasa = TipoMasa.AMERICANA;
		break;
            default:
		tipoMasa = TipoMasa.NAPOLITANA;
		break;
        }
        
        Pizza pizza = null;
        switch (tipoPizza) {
            case 1:
                pizza = new Pizza("PZ001", "Pizza Margherita", "Deliciosa pizza con tomate, mozzarella y albahaca", 120.0, false, new PreparadorPizzaMargherita());
                break;
            case 2:
                pizza = new Pizza("PZ002", "Pizza Pepperoni", "Pizza clásica con pepperoni y queso", 150.0, false, new PreparadorPizzaPepperoni());
                break;
            case 3:
                pizza = new Pizza("PZ003", "Pizza Hawaiana", "Pizza con jamón, piña y queso", 140.0, false, new PreparadorPizzaHawaiana());
                break;
            case 4:
                pizza = new Pizza("PZ004", "Pizza Vegetariana", "Pizza con verduras frescas", 130.0, true, new PreparadorPizzaVegetariana());
                break;
            case 5:
                pizza = new Pizza("PZ005", "Pizza Carne Lovery", "Pizza con múltiples carnes", 180.0, false, new PreparadorPizzaCarneLovery());
                break;
        }
        
        if (pizza != null) {
            pizza.setTipoMasa(tipoMasa);
        }
        
        if (pizza != null && pedidoActual != null) {
            pedidoActual.agregarArticulo(pizza);
            sucursal.getEmpleadoRobot().agregarProducto(pizza);
            interfazUsuario.mostrarMensaje("Pizza agregada: " + pizza.getNombre() + " con masa " + tipoMasa);
        } else {
            interfazUsuario.mostrarError("Error al agregar la pizza al pedido.");
        }
    }
    
    /**
     * Muestra el menú de helados disponibles y permite al usuario
     * seleccionar un sabor y agregar ingredientes extra usando el patrón Decorator.
     */
    public void agregarHelado() {
        interfazUsuario.mostrarMenuSaboresHelado();        
        int saborOpcion = gestorEntrada.leerOpcion(1, 3);
        SaborHelado sabor;
        
        switch (saborOpcion) {
            case 1: sabor = SaborHelado.FRESA; break;
            case 2: sabor = SaborHelado.VAINILLA; break;
            case 3: sabor = SaborHelado.CHOCOLATE; break;
            default: sabor = SaborHelado.VAINILLA; break;
        }
        
        ComponenteHelado helado = new HeladoSimple(sabor);

	int cantidadIngredientesDisponibles = 3;
        boolean agregarIngredientes = true;
        while (agregarIngredientes) {
            interfazUsuario.mostrarMenuIngredientesHelado();            
            int ingredienteOpcion = gestorEntrada.leerOpcion(1, 9);
            
            switch (ingredienteOpcion) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:

		    int cantidad;
		    if(cantidadIngredientesDisponibles > 1){
			interfazUsuario.mostrarMensaje("¿Cuántas porciones desea agregar? (1-" + cantidadIngredientesDisponibles + ")");
			cantidad = gestorEntrada.leerOpcion(1, cantidadIngredientesDisponibles);
		    }
		    else {
			cantidad = 1;
		    }

		    cantidadIngredientesDisponibles -= cantidad;
                    
                    switch (ingredienteOpcion) {
                        case 1:
                            helado = new GomitasGusano(helado, cantidad);
                            interfazUsuario.mostrarMensaje("Agregadas " + cantidad + " porción(es) de gomitas de gusano.");
                            break;
                        case 2:
                            helado = new GomitasPanda(helado, cantidad);
                            interfazUsuario.mostrarMensaje("Agregadas " + cantidad + " porción(es) de gomitas de panda.");
                            break;
                        case 3:
                            helado = new GomitasAro(helado, cantidad);
                            interfazUsuario.mostrarMensaje("Agregadas " + cantidad + " porción(es) de gomitas de aro.");
                            break;
                        case 4:
                            helado = new ChispasChocolate(helado, cantidad);
                            interfazUsuario.mostrarMensaje("Agregadas " + cantidad + " porción(es) de chispas de chocolate.");
                            break;
                        case 5:
                            helado = new Malvaviscos(helado, cantidad);
                            interfazUsuario.mostrarMensaje("Agregadas " + cantidad + " porción(es) de malvaviscos.");
                            break;
                        case 6:
                            helado = new Fresitas(helado, cantidad);
                            interfazUsuario.mostrarMensaje("Agregadas " + cantidad + " porción(es) de fresitas.");
                            break;
                        case 7:
                            helado = new Manguitos(helado, cantidad);
                            interfazUsuario.mostrarMensaje("Agregadas " + cantidad + " porción(es) de manguitos.");
                            break;
                        case 8:
                            helado = new Kiwis(helado, cantidad);
                            interfazUsuario.mostrarMensaje("Agregadas " + cantidad + " porción(es) de kiwis.");
                            break;
                    }
                    break;
                case 9:
                    agregarIngredientes = false;
                    break;
                default:
                    interfazUsuario.mostrarError("Opción no válida.");
                    break;
            }
	    if (cantidadIngredientesDisponibles < 1){
		agregarIngredientes = false;
	    }
        }
        
        if (helado != null && pedidoActual != null) {
            Helado heladoFinal = new Helado(helado);
            pedidoActual.agregarArticulo(heladoFinal);
            sucursal.getEmpleadoRobot().agregarProducto(heladoFinal);
            interfazUsuario.mostrarMensaje("Helado agregado: " + helado.getDescripcion() + " - $" + helado.getPrecio());
        } else {
            interfazUsuario.mostrarError("Error al agregar el helado al pedido.");
        }
    }
    
    /**
     * Confirma la orden actual con el robot.
     * Una vez confirmada, la orden no puede ser cancelada.
     */
    public boolean confirmarOrden() {
        if (pedidoActual == null) {
            interfazUsuario.mostrarError("No hay pedido para confirmar.");
            return false;
        }
        
        if (pedidoActual.getProductos().isEmpty()) {
            interfazUsuario.mostrarError("No se puede confirmar un pedido vacío.");
            return false;
        }
        
	interfazUsuario.mostrarMenuConfirmacion(clienteActual, pedidoActual.getProductos());
        
        int confirmacion = gestorEntrada.leerOpcion(1, 2);
        
        if (confirmacion == 1) {	    
            pedidoActual.confirmar();
            sucursal.getEmpleadoRobot().confirmarOrden();
            
            interfazUsuario.mostrarMensaje("¡Orden confirmada exitosamente!");
            interfazUsuario.mostrarMensaje("El robot ya no puede dormir hasta completar el pedido.");
	    return true;
	}
	
	interfazUsuario.mostrarMensaje("Confirmación cancelada. Puede seguir modificando el pedido.");
	return false;
    }
    
    /**
     * Cancela la orden actual y regresa el robot al estado dormido.
     * Solo puede cancelarse antes de confirmar la orden.
     */
    public void cancelarOrden() {
        if (pedidoActual == null) {
            interfazUsuario.mostrarError("No hay pedido para cancelar.");
            return;
        }
        
        if (pedidoActual.estaConfirmado()) {
            interfazUsuario.mostrarError("No se puede cancelar una orden ya confirmada.");
            return;
        }
        
        interfazUsuario.mostrarMenuCancelacionOrden(clienteActual);
        
        int confirmacion = gestorEntrada.leerOpcion(1, 2);
        
        if (confirmacion == 1) {
	    
            pedidoActual.cancelar();
            sucursal.getEmpleadoRobot().cancelarOrden();
            
            interfazUsuario.mostrarMensaje("Pedido cancelado exitosamente.");
            interfazUsuario.mostrarMensaje("El robot está regresando al estado dormido...");
            
            pedidoActual = null;
            clienteActual = null;
        } else {
            interfazUsuario.mostrarMensaje("Cancelación abortada. El pedido permanece activo.");
        }
    }
    
    /**
     * Llama al robot y lo despierta si está dormido.
     */
    public void llamarRobot() {
        Robot robot = sucursal.getEmpleadoRobot();
        
        interfazUsuario.mostrarMensaje("\n=== LLAMANDO AL ROBOT ===");
        
        String estadoActual = robot.getEstadoActual().getClass().getSimpleName();
        
        if (estadoActual.equals("EstadoDormido")) {
            interfazUsuario.mostrarMensaje("El robot está dormido. Despertándolo...");
	    robot.setEstadoActual(robot.getEstadoTomandoOrden());
            
            interfazUsuario.mostrarMensaje("¡Robot despierto y listo para tomar órdenes!");
            
        } else {
            interfazUsuario.mostrarMensaje("El robot ya está despierto y en estado: " + estadoActual);
            interfazUsuario.mostrarMensaje("No es necesario llamarlo nuevamente.");
        }
    }
    
    /**
     * Solicita al robot que inicie la preparación del pedido confirmado.
     */
    public void iniciarPreparacion() {
        if (pedidoActual == null || !pedidoActual.estaConfirmado()) {
            interfazUsuario.mostrarError("No hay pedido confirmado para preparar.");
            return;
        }
        
        Robot robot = sucursal.getEmpleadoRobot();
        
        interfazUsuario.mostrarMensaje("\n=== INICIANDO PREPARACIÓN ===");
        interfazUsuario.mostrarMensaje("El robot comenzará a preparar el pedido de " + clienteActual);
        
        robot.iniciarPreparacion();
        
        interfazUsuario.mostrarMensaje("El robot está preparando los siguientes productos:");
        for (var producto : pedidoActual.getProductos()) {
            interfazUsuario.mostrarMensaje("- " + producto.getNombre());
            
            if (producto instanceof Pizza) {
                interfazUsuario.mostrarMensaje("  Preparando pizza según el patrón Template Method...");
                producto.preparar();
            } else if (producto instanceof Helado) {
                interfazUsuario.mostrarMensaje("  Preparando helado con ingredientes decorados...");
                producto.preparar();
            }
        }
        
        interfazUsuario.mostrarMensaje("\n¡Preparación completada!");
        interfazUsuario.mostrarMensaje("El robot está esperando la solicitud de entrega.");
    }
    
    /**
     * Solicita la entrega del pedido terminado y muestra el ticket final.
     */
    public void solicitarEntrega() {
        if (pedidoActual == null) {
            interfazUsuario.mostrarError("No hay pedido para entregar.");
            return;
        }
        
        Robot robot = sucursal.getEmpleadoRobot();
        
        interfazUsuario.mostrarMensaje("\n=== SOLICITUD DE ENTREGA ===");
        interfazUsuario.mostrarMensaje("Solicitando entrega del pedido de " + clienteActual);
        
        robot.solicitarEntrega();
        
        interfazUsuario.mostrarMensaje("\n=== GENERANDO TICKET ===");
        
        List<String> productosTicket = new ArrayList<>();
        double totalTicket = 0.0;
        
        for (var producto : pedidoActual.getProductos()) {
            productosTicket.add(producto.getNombre() + " - $" + producto.getPrecio());
            totalTicket += producto.getPrecio();
        }
        
        Ticket ticket = new Ticket(productosTicket, totalTicket);
        
        interfazUsuario.mostrarTicket(ticket);
        
        robot.entregar();
        
        interfazUsuario.mostrarMensaje("\n¡Pedido entregado exitosamente a " + clienteActual + "!");
        interfazUsuario.mostrarMensaje("Gracias por su compra en El Pequeño Cesarín.");
    }
    
    /**
     * Muestra el estado actual del robot en la interfaz de usuario.
     */
    public void mostrarEstadoRobot() {
        Robot robot = sucursal.getEmpleadoRobot();
        String estadoActual = robot.getEstadoActual().getClass().getSimpleName();
        
        interfazUsuario.mostrarMensaje("\n=== ESTADO DEL ROBOT ===");
        interfazUsuario.mostrarMensaje("Sucursal: " + sucursal.getNombre());
        
        String estadoAmigable;
        switch (estadoActual) {
            case "EstadoDormido":
                estadoAmigable = "Dormido - Esperando que lo llamen";
                break;
            case "EstadoTomandoOrden":
                estadoAmigable = "Tomando orden - Recibiendo pedidos";
                break;
            case "EstadoConfirmarOrden":
                estadoAmigable = "Esperando confirmación - Listo para confirmar orden";
                break;
            case "EstadoTrabajando":
                estadoAmigable = "Trabajando - Preparando pedido";
                break;
            case "EstadoEsperandoEntregar":
                estadoAmigable = "Esperando entrega - Pedido listo para entregar";
                break;
            default:
                estadoAmigable = estadoActual;
                break;
        }
        
        interfazUsuario.mostrarEstadoRobot(estadoAmigable);
        
        if (pedidoActual != null && clienteActual != null) {
            interfazUsuario.mostrarMensaje("\nPedido actual:");
            interfazUsuario.mostrarMensaje("Cliente: " + clienteActual);
            interfazUsuario.mostrarMensaje("Productos: " + pedidoActual.getProductos().size());
            interfazUsuario.mostrarMensaje("Estado del pedido: " + (pedidoActual.estaConfirmado() ? "Confirmado" : "En construcción"));
        } else {
            interfazUsuario.mostrarMensaje("\nNo hay pedido activo.");
        }
    }
    
    /**
     * Obtiene el nombre del cliente actual.
     * 
     * @return el nombre del cliente actual, o null si no hay cliente
     */
    public String getClienteActual() {
        return clienteActual;
    }
    
    /**
     * Obtiene el pedido que se está procesando actualmente.
     * 
     * @return el pedido actual, o null si no hay pedido en proceso
     */
    public Pedido getPedidoActual() {
        return pedidoActual;
    }
}
