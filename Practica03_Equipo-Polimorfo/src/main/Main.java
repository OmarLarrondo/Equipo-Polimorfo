package main;

import controladores.AplicacionControlador;

/**
 * Clase principal de la aplicacion de la Academia Ninja.
 * Punto de entrada del sistema que inicia el controlador principal
 * y maneja el flujo general de la aplicacion.
 *
 * @author Equipo-Polimorfo
 * @version 1.0
 */
public class Main {

    /**
     * Metodo principal que inicia la aplicacion del sistema de gestion
     * de la Academia Ninja de la Aldea de las Ciencias.
     *
     * @param args Argumentos de linea de comandos (no utilizados)
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
