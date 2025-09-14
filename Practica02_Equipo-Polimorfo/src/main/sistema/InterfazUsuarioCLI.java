package main.sistema;

import main.productos.Producto;
import main.productos.helados.ComponenteHelado;
import main.productos.helados.HeladoSimple;
import main.productos.helados.GomitasGusano;
import main.productos.helados.GomitasPanda;
import main.productos.helados.GomitasAro;
import main.productos.helados.ChispasChocolate;
import main.productos.helados.Malvaviscos;
import main.productos.helados.Fresitas;
import main.productos.helados.Manguitos;
import main.productos.helados.Kiwis;
import main.enums.SaborHelado;
import main.enums.TipoMasa;

import java.util.List;
import java.util.ArrayList;

/**
 * Interfaz de usuario basada en línea de comandos (CLI) para el sistema de gestión
 * de pedidos de la pizzería "El Pequeño Cesarín".
 * 
 * Esta clase proporciona la interfaz gráfica de usuario a través de la consola,
 * permitiendo a los usuarios interactuar con el sistema para realizar pedidos,
 * seleccionar productos, configurar ingredientes y visualizar información.
 * 
 * La clase maneja todos los menús de navegación, la entrada del usuario y
 * la presentación de información, actuando como la capa de presentación del sistema
 * 
 * Características principales:
 * - Menú principal con opciones de navegación
 * - Selección de pizzas con tipos disponibles
 * - Selección de helados con sabores disponibles  
 * - Sistema de decoración de ingredientes para helados
 * - Visualización de tickets y mensajes del sistema
 * - Manejo de errores y estados del robot
 * 
 * @author Equipo-Polimorfo
 * @version 1.0
 * @since 1.0
 */
public class InterfazUsuarioCLI {
    
    /**
     * Gestor de entrada del usuario que maneja la lectura de datos desde consola
     */
    private GestorEntrada gestorEntrada;
    
    /**
     * Constructor que inicializa la interfaz CLI con el gestor de entrada especificado.
     * 
     * @param gestorEntrada El gestor de entrada que manejará la lectura de datos del usuario.
     *                     No puede ser null.
     * @throws IllegalArgumentException si gestorEntrada es null
     */
    public InterfazUsuarioCLI(GestorEntrada gestorEntrada) {
        if (gestorEntrada == null) {
            throw new IllegalArgumentException("El gestor de entrada no puede ser null");
        }
        this.gestorEntrada = gestorEntrada;
    }
    
    /**
     * Muestra el menú principal del sistema con todas las opciones disponibles.
     * 
     * Presenta al usuario las siguientes opciones:
     * 1. Realizar nuevo pedido
     * 2. Mostrar estado del robot
     * 3. Salir del sistema
     * 
     * Este método solo muestra el menú, no procesa la selección del usuario.
     */
    public void mostrarMenuPrincipal() {
        System.out.println("\n=== EL PEQUEÑO CESARÍN ===");
        System.out.println("=== SISTEMA DE GESTIÓN DE PEDIDOS ===");
        System.out.println();
        System.out.println("1. Realizar nuevo pedido");
        System.out.println("2. Recoger pedido");
        System.out.println("3. Mostrar estado del robot");
        System.out.println("4. Salir");
        System.out.println();
    }
    
    
    /**
     * Muestra el menú de selección de pizzas disponibles.
     * 
     * Presenta todas las opciones de pizza disponibles en el sistema,
     * incluyendo información sobre si son vegetarianas y sus ingredientes
     * principales. También muestra opciones para regresar al menú anterior.
     * 
     * Este método solo muestra las opciones, no procesa la selección.
     */
    public void menuSeleccionarPizza() {
        System.out.println("\n=== SELECCIÓN DE PIZZAS ===");
        System.out.println();
        System.out.println("Pizzas disponibles:");
        System.out.println("1. Pizza Margherita (Vegetariana) - Mozzarella, albahaca");
        System.out.println("2. Pizza Pepperoni - Pepperoni, mozzarella");
        System.out.println("3. Pizza Hawaiana - Jamón, piña, mozzarella");
        System.out.println("4. Pizza Vegetariana - Vegetales mixtos, mozzarella");
        System.out.println("5. Pizza Carne Lovery - Carnes mixtas, mozzarella");
        System.out.println("6. Regresar al menú anterior");
        System.out.println();
    }
    
    /**
     * Muestra el menú de selección de helados disponibles.
     * 
     * Presenta los tres sabores de helado base disponibles en el sistema.
     * Después de seleccionar el sabor base, se puede proceder a agregar
     * ingredientes adicionales usando el patrón Decorator.
     * 
     * Este método solo muestra las opciones, no procesa la selección.
     */
    public void menuSeleccionarHelado() {
        System.out.println("\n=== SELECCIÓN DE HELADOS ===");
        System.out.println();
        System.out.println("Sabores disponibles:");
        System.out.println("1. Helado de Fresa - $25.00");
        System.out.println("2. Helado de Vainilla - $25.00");
        System.out.println("3. Helado de Chocolate - $25.00");
        System.out.println("4. Regresar al menú anterior");
        System.out.println();
    }


    public void menuSeleccionarIngredientesHelado(String descripcionHeladoActual, double precioHeladoActual){
	System.out.println("\n=== AGREGAR INGREDIENTES ===");
	System.out.println("Helado actual: " + descripcionHeladoActual);
	System.out.println("Precio actual: $" + String.format("%.2f", precioHeladoActual));
	System.out.println();
	System.out.println("Ingredientes disponibles (máximo 3 de cada uno):");
	System.out.println("1. Gomitas de gusano - $8.00");
	System.out.println("2. Gomitas de panda - $8.00");
	System.out.println("3. Gomitas de aro - $8.00");
	System.out.println("4. Chispas de chocolate - $6.00");
	System.out.println("5. Malvaviscos - $7.00");
	System.out.println("6. Fresitas - $9.00");
	System.out.println("7. Manguitos - $9.00");
	System.out.println("8. Kiwis - $10.00");
	System.out.println("9. Finalizar personalización");
	System.out.println();
    }
    
    /**
     * Presenta el menú de ingredientes extras para decorar un helado usando el patrón Decorator.
     * 
     * Permite al usuario agregar hasta 3 unidades de cada ingrediente disponible
     * al helado base. Cada ingrediente se aplica como un decorador que modifica
     * tanto la descripción como el precio del helado.
     * 
     * Ingredientes disponibles:
     * - Gomitas de gusano ($8.00 c/u)
     * - Gomitas de panda ($8.00 c/u) 
     * - Gomitas de aro ($8.00 c/u)
     * - Chispas de chocolate ($6.00 c/u)
     * - Malvaviscos ($7.00 c/u)
     * - Fresitas ($9.00 c/u)
     * - Manguitos ($9.00 c/u)
     * - Kiwis ($10.00 c/u)
     * 
     * @param heladoBase El helado base al que se agregarán los ingredientes decoradores
     * @return El helado decorado con los ingredientes seleccionados por el usuario
     * @throws IllegalArgumentException si heladoBase es null
     */
    public ComponenteHelado menuIngredientesHelado(ComponenteHelado heladoBase) {
        if (heladoBase == null) {
            throw new IllegalArgumentException("El helado base no puede ser null");
        }
        
        ComponenteHelado heladoActual = heladoBase;
        boolean continuar = true;
        
        while (continuar) {
	    this.menuSeleccionarIngredientesHelado(heladoActual.getDescripcion(), heladoActual.getPrecio());
            
            int opcion = gestorEntrada.leerOpcion(1, 9);
            
            if (opcion == 9) {
                continuar = false;
                mostrarMensaje("Personalización de helado completada.");
            } else if (opcion >= 1 && opcion <= 8) {
                System.out.println("¿Cuántas porciones desea agregar? (1-3)");
                int cantidad = gestorEntrada.leerOpcion(1, 3);
                
                try {
                    switch (opcion) {
                        case 1:
                            heladoActual = new GomitasGusano(heladoActual, cantidad);
                            mostrarMensaje("Se agregaron " + cantidad + " porción(es) de gomitas de gusano.");
                            break;
                        case 2:
                            heladoActual = new GomitasPanda(heladoActual, cantidad);
                            mostrarMensaje("Se agregaron " + cantidad + " porción(es) de gomitas de panda.");
                            break;
                        case 3:
                            heladoActual = new GomitasAro(heladoActual, cantidad);
                            mostrarMensaje("Se agregaron " + cantidad + " porción(es) de gomitas de aro.");
                            break;
                        case 4:
                            heladoActual = new ChispasChocolate(heladoActual, cantidad);
                            mostrarMensaje("Se agregaron " + cantidad + " porción(es) de chispas de chocolate.");
                            break;
                        case 5:
                            heladoActual = new Malvaviscos(heladoActual, cantidad);
                            mostrarMensaje("Se agregaron " + cantidad + " porción(es) de malvaviscos.");
                            break;
                        case 6:
                            heladoActual = new Fresitas(heladoActual, cantidad);
                            mostrarMensaje("Se agregaron " + cantidad + " porción(es) de fresitas.");
                            break;
                        case 7:
                            heladoActual = new Manguitos(heladoActual, cantidad);
                            mostrarMensaje("Se agregaron " + cantidad + " porción(es) de manguitos.");
                            break;
                        case 8:
                            heladoActual = new Kiwis(heladoActual, cantidad);
                            mostrarMensaje("Se agregaron " + cantidad + " porción(es) de kiwis.");
                            break;
                    }
                } catch (IllegalArgumentException e) {
                    mostrarError("Error al agregar ingrediente: " + e.getMessage());
                }
            }
        }
        
        return heladoActual;
    }
    
    /**
     * Muestra un ticket de compra al usuario.
     * 
     * Delega la responsabilidad de mostrar el ticket al método imprimir()
     * del propio objeto Ticket, manteniendo la separación de responsabilidades.
     * 
     * @param ticket El ticket que contiene la información de la compra
     * @throws IllegalArgumentException si ticket es null
     */
    public void mostrarTicket(Ticket ticket) {
        if (ticket == null) {
            throw new IllegalArgumentException("El ticket no puede ser null");
        }
        
        System.out.println("\n=== TICKET DE COMPRA ===");
        ticket.imprimir();
    }
    
    /**
     * Muestra un mensaje informativo al usuario.
     * 
     * @param mensaje El mensaje que se mostrará al usuario
     */
    public void mostrarMensaje(String mensaje) {
        if (mensaje != null && !mensaje.trim().isEmpty()) {
            System.out.println("[INFO] " + mensaje);
        }
    }

    public void mostrarMensajeInicioPreparacion(String clienteActual){
	String mensaje = "\n=== INICIANDO PREPARACIÓN ===" +
	    "\nEl robot comenzará a preparar el pedido de " + clienteActual + ".";
	this.mostrarMensaje(mensaje);
    }

    public void mostrarMensajePreparacionCompleta(String clienteActual){
	String mensaje = "\n¡Preparación completada!" +
	    "\nEl robot está esperando la solicitud de entrega de " + clienteActual + ".";
	this.mostrarMensaje(mensaje);	
    }
    
    /**
     * Muestra un mensaje de error al usuario con formato especial.
     * 
     * @param error El mensaje de error que se mostrará al usuario
     */
    public void mostrarError(String error) {
        if (error != null && !error.trim().isEmpty()) {
            System.out.println("[ERROR] " + error);
        }
    }
    
    /**
     * Muestra el estado actual del robot al usuario.
     * 
     * @param estado La descripción del estado actual del robot
     */
    public void mostrarEstadoRobot(String estado) {
        if (estado != null && !estado.trim().isEmpty()) {
            System.out.println("[ROBOT] Estado: " + estado);
        }
    }


    public void mostrarMenuTiposPizza(){
	String mensajeMenu = "\n=== SELECCIONAR HELADO ===" +
	    "\n Seleccione el tipo de pizza:" +
	    "\n 1. Pizza Margherita" +
	    "\n 2. Pizza Pepperoni" +
	    "\n 3. Pizza Hawaiana" +
	    "\n 4. Pizza Vegetariana" +
	    "\n 5. Pizza Carne Lovery";
	this.mostrarMensaje(mensajeMenu);
    }


    public void mostrarMenuTiposMasa(){
	String mensajeMenu = "\n \nSeleccione el tipo de masa:" +
	    "\n 1. Napolitana" +
	    "\n 2. Romana" +
	    "\n 3. Americana";
	this.mostrarMensaje(mensajeMenu);
    }

    
    public void mostrarMenuSaboresHelado(){
	String mensajeMenu = "\n=== SELECCIONAR HELADO ===" +
	    "Seleccione el sabor del helado:" +
	    "\n1. Fresa" +
	    "\n2. Vainilla" +
	    "\n3. Chocolate";
	this.mostrarMensaje(mensajeMenu);
    }
	    
    public void mostrarMenuIngredientesHelado(){
	String mensajeMenu = "\n¿Desea agregar ingredientes extra?" +
	    "\n 1. Gomitas de gusano (+$8.00)" +
	    "\n 2. Gomitas de panda (+$10.00)" +
	    "\n 3. Gomitas de aro (+$7.00)" +
	    "\n 4. Chispas de chocolate (+$5.00)" +
	    "\n 5. Malvaviscos (+$12.00)" +
	    "\n 6. Fresitas (+$15.00)" +
	    "\n 7. Manguitos (+$18.00)" +
	    "\n 8. Kiwis (+$20.00)" +
	    "\n 9. Terminar helado" +
	    "\n";
	this.mostrarMensaje(mensajeMenu);
    }


    public void mostrarMenuCancelacionOrden(String clienteActual){
	String mensajeMenu = "\n=== CANCELACIÓN DE ORDEN ===" +
	    "\n¿Está seguro de que desea cancelar el pedido de " + clienteActual + "?" +
	    "\n 1. Sí, cancelar pedido" +
	    "\n 2. No, regresar al menú";
	this.mostrarMensaje(mensajeMenu);
    }

    public void mostrarMenuConfirmacion(String clienteActual, List<Producto> productosPedidoActual){
	String mensajeMenu = "\n=== CONFIRMACIÓN DE ORDEN ===" +
	    "\nCliente: " + clienteActual +
	    "\nProductos en el pedido:";

	double total = 0.0;
	for (int i = 0; i < productosPedidoActual.size(); i++) {
            var producto = productosPedidoActual.get(i);
            mensajeMenu += "\n " + (i + 1) + ". " + producto.getNombre() + " - $" + producto.getPrecio();
            total += producto.getPrecio();
        }
	mensajeMenu += "\n\n - Total: $" + total +
	    "\n\n¿Confirma la orden? (Una vez confirmada no se puede cancelar)" +
	    "\n1. Sí, confirmar orden" +
	    "\n2. No, regresar al menú";
	this.mostrarMensaje(mensajeMenu);
    }
}
