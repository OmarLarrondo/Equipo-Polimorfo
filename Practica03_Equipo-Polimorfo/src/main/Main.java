package main;

import controladores.AplicacionControlador;

/**
 * Clase principal de la aplicaci�n de la Academia Ninja.
 * Punto de entrada del sistema que inicia el controlador principal
 * y maneja el flujo general de la aplicación.
 *
 * @author Equipo-Polimorfo
 * @version 1.0
 */
public class Main {

    /**
     * M�todo principal que inicia la aplicaci�n del sistema de gestión
     * de la Academia Ninja de la Aldea de las Ciencias.
     *
     * @param args Argumentos de l�nea de comandos (no utilizados)
     */
    public static void main(String[] args) {
        AplicacionControlador controlador = crearControladorAplicacion();
        controlador.iniciarAplicacion();
    }

    /**
     * Crea y configura el controlador principal de la aplicación.
     *
     * @return Instancia configurada del controlador de aplicación
     */
    private static AplicacionControlador crearControladorAplicacion() {
        return new AplicacionControlador();
    }
}
