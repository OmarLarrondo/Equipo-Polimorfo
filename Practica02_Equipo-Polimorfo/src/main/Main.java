package main;

import main.sistema.SistemaGestionPedidos;

/**
 * Clase principal para el sistema de gestión de pedidos
 * de la pizzería "El Pequeño Cesarín".
 * 
 * Esta clase actúa como punto de entrada del sistema, siguiendo el
 * patrón de separación de responsabilidades donde el main solo se
 * encarga de inicializar y ejecutar el sistema principal.
 * 
 * @author Equipo-Polimorfo
 * @version 1.0
 */
public class Main {
    
    /**
     * Método principal que inicia la aplicación.
     * 
     * @param args argumentos de línea de comandos (no utilizados)
     */
    public static void main(String[] args) {
        SistemaGestionPedidos sistema = inicializar();
        sistema.ejecutar();
    }
    
    /**
     * Inicializa el sistema de gestión de pedidos.
     * 
     * @return una nueva instancia del sistema completamente configurado
     */
    private static SistemaGestionPedidos inicializar() {
        return new SistemaGestionPedidos();
    }
}
