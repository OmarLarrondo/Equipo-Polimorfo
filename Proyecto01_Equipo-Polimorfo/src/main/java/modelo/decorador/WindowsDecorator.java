package modelo.decorador;

import modelo.computadora.ComputadoraBase;

/**
 * Decorador concreto que agrega Windows a una computadora.
 * Implementa el patron Decorator para agregar funcionalidad de sistema
 * operativo Windows a una computadora base.
 *
 * @author Equipo-Polimorfo
 * @version 1.0
 */
public class WindowsDecorator extends SoftwareDecorator {

    /**
     * Constructor que inicializa el decorador de Windows.
     *
     * @param computadora La computadora base a decorar.
     * @param nombreSoftware El nombre del software Windows.
     * @param precioSoftware El precio del software Windows.
     */
    public WindowsDecorator(ComputadoraBase computadora, String nombreSoftware, double precioSoftware) {
        super(computadora, nombreSoftware, precioSoftware);
    }

    /**
     * Obtiene la descripcion de la computadora incluyendo Windows.
     *
     * @return La descripcion de la computadora base mas el software Windows.
     */
    @Override
    public String obtenerDescripcion(){
        return computadora.obtenerDescripcion() + "\n  + Software: " + nombreSoftware;
    }
}
