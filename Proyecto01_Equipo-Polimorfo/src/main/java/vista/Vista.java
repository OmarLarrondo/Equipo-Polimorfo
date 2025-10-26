package vista;

import java.util.List;

import modelo.componente.ComponentePC;
import modelo.ticket.Ticket;

/**
 * Interfaz que define el contrato para las vistas del sistema de ensamblaje de computadoras.
 * Proporciona metodos para la interaccion con el usuario, permitiendo mostrar informacion,
 * capturar entradas y gestionar la presentacion de datos mediante interfaz grafica.
 *
 * <p>Esta interfaz forma parte del patron MVC (Model-View-Controller), definiendo las operaciones
 * que cualquier vista debe soportar para interactuar con el controlador. La implementacion
 * concreta VistaJavaFX proporciona una interfaz grafica moderna usando JavaFX.
 *
 * <p>Operaciones principales:
 * <ul>
 *   <li>Mostrar mensajes y errores al usuario</li>
 *   <li>Presentar menus de opciones</li>
 *   <li>Capturar entrada del usuario (texto y numeros)</li>
 *   <li>Mostrar listas de componentes y tickets</li>
 *   <li>Solicitar confirmaciones</li>
 *   <li>Limpiar la pantalla</li>
 * </ul>
 *
 * @author Equipo-Polimorfo
 * @version 1.0
 */
public interface Vista {

    /**
     * Muestra un mensaje informativo al usuario.
     *
     * @param mensaje el texto del mensaje a mostrar
     */
    void mostrarMensaje(String mensaje);

    /**
     * Muestra un mensaje de error al usuario.
     * Implementaciones pueden usar formato especial (color rojo, alertas, etc.)
     * para distinguir errores de mensajes normales.
     *
     * @param error el texto del error a mostrar
     */
    void mostrarError(String error);

    /**
     * Muestra un menu de opciones al usuario.
     * Las opciones se presentan numeradas secuencialmente comenzando desde 1.
     *
     * @param opciones lista de cadenas con las opciones del menu a mostrar
     */
    void mostrarMenu(List<String> opciones);

    /**
     * Lee una opcion numerica del usuario.
     * Debe manejar entradas invalidas y solicitar reingreso hasta obtener un numero valido.
     *
     * @return el numero de opcion seleccionado por el usuario
     */
    int leerOpcion();

    /**
     * Lee una cadena de texto del usuario con un mensaje prompt.
     *
     * @param prompt el mensaje a mostrar antes de leer la entrada
     * @return la cadena de texto ingresada por el usuario
     */
    String leerTexto(String prompt);

    /**
     * Muestra una lista de componentes de PC con sus detalles y precios.
     * Cada componente se presenta con su informacion completa incluyendo
     * tipo, nombre, marca y precio.
     *
     * @param componentes la lista de componentes a mostrar
     */
    void mostrarComponentes(List<ComponentePC> componentes);

    /**
     * Muestra un ticket de compra completo.
     * Presenta toda la informacion del ticket incluyendo componentes,
     * software, compatibilidad y precio total.
     *
     * @param ticket el ticket a mostrar
     */
    void mostrarTicket(Ticket ticket);

    /**
     * Solicita confirmacion al usuario para una accion.
     * Muestra un mensaje y espera una respuesta afirmativa o negativa.
     *
     * @param mensaje el mensaje de confirmacion a mostrar
     * @return true si el usuario confirma, false en caso contrario
     */
    boolean confirmar(String mensaje);

    /**
     * Limpia la pantalla o area de visualizacion.
     * En consola puede limpiar el terminal, en GUI puede limpiar areas de texto.
     */
    void limpiarPantalla();
}
