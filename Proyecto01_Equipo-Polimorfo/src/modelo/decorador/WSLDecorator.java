package modelo.decorador;

import modelo.computadora.ComputadoraBase;

/**
 * Decorador concreto que agrega WSL (Windows Subsystem for Linux) a una computadora.
 * Implementa el patron Decorator para agregar funcionalidad de terminal
 * y subsistema Linux WSL a una computadora base.
 *
 * @author Equipo-Polimorfo
 * @version 1.0
 */
public class WSLDecorator extends SoftwareDecorator {

    /**
     * Constructor que inicializa el decorador de WSL.
     *
     * @param computadora La computadora base a decorar.
     * @param nombreSoftware El nombre del software WSL.
     * @param precioSoftware El precio del software WSL.
     */
    public WSLDecorator(ComputadoraBase computadora, String nombreSoftware, double precioSoftware) {
        super(computadora, nombreSoftware, precioSoftware);
    }

    /**
     * Obtiene la descripcion de la computadora incluyendo WSL.
     *
     * @return La descripcion de la computadora base mas el software WSL.
     */
    @Override
    public String obtenerDescripcion(){
        return computadora.obtenerDescripcion() + "\n  + Software: " + nombreSoftware;
    }
}
