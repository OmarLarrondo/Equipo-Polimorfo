package vista;

import java.util.List;
import java.util.Scanner;

import modelo.componente.ComponentePC;
import modelo.ticket.Ticket;

/**
 * Implementación concreta de la interfaz {@link Vista} que permite la interacción
 * con el usuario a través de la consola.
 * <p>Esta clase se encarga de mostrar mensajes, menús, errores, listas de componentes
 * y tickets, así como de leer la entrada del usuario mediante un objeto {@link Scanner}.
 * 
 * <p>Sirve como la "vista" dentro del patrón de arquitectura MVC del sistema, permitiendo
 * la comunicación entre el usuario y el controlador sin depender de interfaces gráficas por el 
 * moemento.
 * 
 * @author Equipo-polimorfo
 * @version 1.0
 */
public class VistaConsola implements Vista {
    /** Objeto {@link Scanner} utilizado para leer la entrada del usuario desde la consola.*/
    private Scanner scanner;

    /**
     * Construye una nueva vista de consola con el lector de entrada especificado.
     * 
     * @param scanner objeto {@code Scanner} utilizado para leer datos del usuario.
     */
    public VistaConsola(Scanner scanner) {
        this.scanner = scanner;
    }

    /**
     * Muestra un mensaje en la consola
     * 
     * @param mensaje el texto del mensaje a mostrar.
     */
    @Override
    public void mostrarMensaje(String mensaje) {
        try {
            System.out.println(mensaje);
        } catch (Exception e) {
            mostrarError(mensaje);
        }
    }

    /**
     * Muestra un mensaje de error en la consola.
     * 
     * @param error el texto del error a mostrar.
     */
    @Override
    public void mostrarError(String error) {
        System.err.println("❌ Error: " + error);
    }

    /**
     * Muestra un menú con una lista de opciones numeradas.
     * 
     * @param opciones lista de cadenas que representan las opciones del menú.
     */
    @Override
    public void mostrarMenu(List<String> opciones) {
        System.out.println("\n--- MENÚ ---");
        for (int i = 0; i < opciones.size(); i++) {
            System.out.println((i + 1) + ". " + opciones.get(i));
        }
        System.out.println("-------------");
    }

    /**
     * Lee la opción elegida por el usuario desde la consola.
     * 
     * <p>Si el usuario ingresa un valor no numérico, se muestra un mensaje de error
     * y se solicita nuevamente la entrada.
     * 
     * @return el número que seleccionó el usuario.
     */
    @Override
    public int leerOpcion() {
        while (true) {
            try {
                System.out.print("Seleccione una opción: ");
                int entrada = scanner.nextInt();
                scanner.nextLine(); // limpia el buffer
                System.out.println("Opción elegida: " + entrada);
                return entrada;
            } catch (Exception e) {
                mostrarError("❌ Entrada inválida. Por favor, ingrese un número.");
                scanner.nextLine(); // limpia el buffer en caso de error
            }
        }
    }


    /**
     * Muestra un mensaje al usuario y lee una línea de texto ingresada.
     * 
     * @param prompt texto que se mostrará antes de la lectura.
     * @return el texto ingresado por el usuario.
     */
    @Override
    public String leerTexto(String prompt) {
        try {
            mostrarMensaje(prompt);
            String entrada = scanner.nextLine();
            return entrada;
        } catch (Exception e) {
            mostrarError("Error al leer la entrada!");
            return null;
        }
    }

    /**
     * Muestra una lista de componentes en la consola.
     * 
     * @param componentes lista de objetos {@link ComponentePC} a mostrar.
     */
    @Override
    public void mostrarComponentes(List<ComponentePC> componentes) {
        if (componentes == null || componentes.isEmpty()) {
            System.out.println("No hay componentes a mostrar.");
            return;
        }

        int count = 0;
        System.out.println("\n--- Lista de Componentes ---");
        for(ComponentePC com : componentes){
            count ++;
            System.out.println("Componente "+count + ". " +com);
        }
    }

    /**
     * Muestra la información de un ticket en la consola.
     * 
     * @param ticket objeto {@link Ticket} que contiene los datos de la compra.
     */
    @Override
    public void mostrarTicket(Ticket ticket) {
        System.out.println(ticket.generarTicket());
    }

    /**
     * Solicita al usuario una confirmación (por ejemplo, "sí" o "no").
     * 
     * @param mensaje mensaje que se mostrará para solicitar la confirmación.
     * @return {@code true} si el usuario confirma (por ejemplo, escribe "s"),
     *         {@code false} en caso contrario.
     */
    @Override
    public boolean confirmar(String mensaje) {
        mostrarMensaje(mensaje);
        while (true) {
            try {
                String entrada = scanner.nextLine().toLowerCase();
                if (entrada.equalsIgnoreCase("si") || entrada.equalsIgnoreCase("s")){
                    return true;
                } else if (entrada.equalsIgnoreCase("no") || entrada.equalsIgnoreCase("n")){
                    return false;
                }else{
                    mostrarError("Entrada no valida");
                    throw new IllegalArgumentException("Entrada no valida");
                }
            } catch (Exception e) {
                    mostrarError("❌ Entrada inválida. Por favor, responda 's' o 'n'.");
                    scanner.nextLine(); // limpia el buffer en caso de error
                }
            }
    }
        


    /**
     * Limpia la pantalla de la consola, si es posible en el entorno actual.
     * Este método puede simular el efecto imprimiendo varias líneas vacías.
     */
    @Override
    public void limpiarPantalla() {
        try {
        // Obtiene el nombre del sistema operativo
        String os = System.getProperty("os.name");

        // Define los comandos para limpiar
        if (os.contains("Windows")) {
            // Comando para Windows: cmd /C cls
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } else {
            // Comando para otros sistemas: clear
            new ProcessBuilder("clear").inheritIO().start().waitFor();
        }
    } catch (Exception e) {
        System.err.println("Error al intentar limpiar la consola: " + e.getMessage());
    }
    }
}
