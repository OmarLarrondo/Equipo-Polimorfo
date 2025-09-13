package main.sistema;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Gestor para manejar la entrada del usuario desde la consola.
 * Proporciona métodos para leer opciones numéricas y texto con validación
 * y manejo robusto de excepciones.
 * 
 * <p>Esta clase encapsula la funcionalidad de entrada del usuario para
 * el sistema de gestión de pedidos de "El Pequeño Cesarín". Utiliza
 * BufferedReader para la lectura eficiente de datos y proporciona
 * validación automática de entrada numérica.</p>
 * 
 * <p>Uso típico:</p>
 * <pre>
 * GestorEntrada gestor = new GestorEntrada();
 * gestor.configurarEntrada();
 * int opcion = gestor.leerOpcion(1, 5);
 * String nombre = gestor.leerTexto("Ingrese su nombre: ");
 * gestor.liberarRecursos();
 * </pre>
 * 
 * @author Equipo-Polimorfo
 * @version 1.0
 * @since 1.0
 */
public class GestorEntrada {
    
    /**
     * BufferedReader para leer la entrada del usuario desde la consola
     */
    private BufferedReader reader;
    
    /**
     * Constructor de GestorEntrada.
     * Inicializa el BufferedReader pero no lo configura.
     */
    public GestorEntrada() {
        this.reader = null;
    }
    
    /**
     * Configura la entrada del sistema.
     * Inicializa el BufferedReader con InputStreamReader de System.in.
     */
    public void configurarEntrada() {
        this.reader = new BufferedReader(new InputStreamReader(System.in));
    }
    
    /**
     * Libera los recursos utilizados por el gestor de entrada.
     * Cierra el BufferedReader de forma segura.
     */
    public void liberarRecursos() {
        if (reader != null) {
            try {
                reader.close();
            } catch (IOException e) {
                System.out.println("Error al cerrar el BufferedReader: " + e.getMessage());
            } finally {
                reader = null;
            }
        }
    }
    
    /**
     * Lee una opción numérica del usuario dentro del rango especificado.
     * 
     * @param min Valor mínimo aceptado
     * @param max Valor máximo aceptado
     * @return La opción seleccionada por el usuario
     */
    public int leerOpcion(int min, int max) {
        int opcion = -1;
        boolean entradaValida = false;
        
        while (!entradaValida) {
            try {
                System.out.print("Seleccione una opción (" + min + "-" + max + "): ");
                String entrada = reader.readLine();
                
                if (entrada != null && !entrada.trim().isEmpty()) {
                    opcion = Integer.parseInt(entrada.trim());
                    
                    if (validarOpcion(opcion, min, max)) {
                        entradaValida = true;
                    } else {
                        System.out.println("Error: La opción debe estar entre " + min + " y " + max + ".");
                    }
                } else {
                    System.out.println("Error: Debe ingresar una opción.");
                }
            } catch (IOException e) {
                manejarExcepcion(e);
                limpiarBuffer();
            } catch (NumberFormatException e) {
                System.out.println("Error: Debe ingresar un número válido.");
                limpiarBuffer();
            }
        }
        
        return opcion;
    }
    
    /**
     * Lee una línea de texto del usuario después de mostrar el prompt.
     * 
     * @param prompt Mensaje a mostrar al usuario
     * @return El texto introducido por el usuario
     */
    public String leerTexto(String prompt) {
        String texto = "";
        boolean entradaValida = false;
        
        while (!entradaValida) {
            try {
                System.out.print(prompt);
                texto = reader.readLine();
                
                if (texto != null) {
                    texto = texto.trim();
                    entradaValida = true;
                } else {
                    System.out.println("Error: Entrada no válida.");
                }
            } catch (IOException e) {
                manejarExcepcion(e);
                limpiarBuffer();
            }
        }
        
        return texto;
    }
    
    /**
     * Maneja las excepciones de entrada/salida.
     * 
     * @param e La excepción IOException a manejar
     */
    public void manejarExcepcion(IOException e) {
        System.out.println("Error de entrada/salida: " + e.getMessage());
        System.out.println("Por favor, inténtelo de nuevo.");
    }
    
    /**
     * Valida que una opción esté dentro del rango especificado.
     * 
     * @param opcion Opción a validar
     * @param min Valor mínimo del rango
     * @param max Valor máximo del rango
     * @return true si la opción está en el rango, false en caso contrario
     */
    private boolean validarOpcion(int opcion, int min, int max) {
        return opcion >= min && opcion <= max;
    }
    
    /**
     * Limpia el buffer de entrada para evitar problemas con lecturas posteriores.
     */
    private void limpiarBuffer() {
        try {
            while (reader != null && reader.ready()) {
                reader.readLine();
            }
        } catch (IOException e) {
            System.out.println("Error al limpiar el buffer de entrada.");
        }
    }
}