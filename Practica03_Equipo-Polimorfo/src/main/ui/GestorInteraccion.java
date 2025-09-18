package ui;

import java.util.Scanner;

/**
 * Clase que gestiona todas las interacciones con el usuario a través de la consola.
 * Proporciona métodos para leer entrada del usuario, mostrar mensajes y manejar
 * la entrada/salida de datos del sistema.
 *
 * @author Equipo-Polimorfo
 * @version 1.0
 */
public class GestorInteraccion {

    /** Scanner para leer entrada del usuario desde la consola */
    private Scanner scanner;

    /**
     * Constructor del gestor de interacción.
     * Inicializa el scanner para leer entrada del usuario.
     */
    public GestorInteraccion() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Lee un número entero del usuario con un mensaje personalizado.
     * Maneja errores de entrada y solicita reingresar el valor si es necesario.
     *
     * @param mensaje Mensaje a mostrar al usuario antes de leer el entero
     * @return Número entero ingresado por el usuario
     */
    public int leerEntero(String mensaje) {
        int numero = 0;
        boolean entradaValida = false;

        while (!entradaValida) {
            try {
                System.out.print(mensaje);
                numero = Integer.parseInt(scanner.nextLine());
                entradaValida = true;
            } catch (NumberFormatException e) {
                mostrarMensaje("Error: Debe ingresar un número válido.");
            }
        }

        return numero;
    }

    /**
     * Lee una cadena de texto del usuario con un mensaje personalizado.
     *
     * @param mensaje Mensaje a mostrar al usuario antes de leer la cadena
     * @return Cadena de texto ingresada por el usuario
     */
    public String leerCadena(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine().trim();
    }

    /**
     * Muestra un mensaje al usuario en la consola.
     *
     * @param mensaje Mensaje a mostrar
     */
    public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }

    /**
     * Muestra una línea de texto al usuario sin salto de línea adicional.
     *
     * @param linea Línea de texto a mostrar
     */
    public void mostrarLinea(String linea) {
        System.out.println(linea);
    }

    /**
     * Solicita confirmación del usuario para una acción específica.
     *
     * @param mensaje Mensaje de confirmación a mostrar
     * @return true si el usuario confirma (s/S), false en caso contrario
     */
    public boolean confirmarAccion(String mensaje) {
        String respuesta = leerCadena(mensaje + " (s/n): ");
        return respuesta.toLowerCase().startsWith("s");
    }

    /**
     * Pausa la ejecución hasta que el usuario presione Enter.
     * Útil para permitir al usuario leer información antes de continuar.
     */
    public void pausar() {
        System.out.print("\nPresione Enter para continuar...");
        scanner.nextLine();
    }

    /**
     * Cierra los recursos utilizados por el gestor de interacción.
     * Debe llamarse al finalizar el uso del gestor para liberar recursos.
     */
    public void cerrarRecursos() {
        if (scanner != null) {
            scanner.close();
        }
    }
}