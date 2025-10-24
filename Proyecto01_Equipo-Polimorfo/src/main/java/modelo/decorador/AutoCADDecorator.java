package modelo.decorador;

import modelo.computadora.ComputadoraBase;

/**
 * Decorador concreto que agrega AutoCAD a una computadora.
 * Implementa el patron Decorator para agregar funcionalidad de diseno
 * asistido por computadora AutoCAD a una computadora base.
 *
 * @author Equipo-Polimorfo
 * @version 1.0
 */
public class AutoCADDecorator extends SoftwareDecorator {

    /**
     * Constructor que inicializa el decorador de AutoCAD.
     *
     * @param computadora La computadora base a decorar.
     * @param nombreSoftware El nombre del software AutoCAD.
     * @param precioSoftware El precio del software AutoCAD.
     */
    public AutoCADDecorator(ComputadoraBase computadora, String nombreSoftware, double precioSoftware) {
        super(computadora, nombreSoftware, precioSoftware);
    }

    /**
     * Obtiene la descripcion de la computadora incluyendo AutoCAD.
     *
     * @return La descripcion de la computadora base mas el software AutoCAD.
     */
    @Override
    public String obtenerDescripcion(){
        return computadora.obtenerDescripcion() + "\n  + Software: " + nombreSoftware;
    }

    @Override
    public boolean tieneSotfware(String sofwareABuscar){
        if(nombreSoftware.equalsIgnoreCase(sofwareABuscar)){
            return true;
        }
        return computadora.tieneSotfware(sofwareABuscar); //se busca a la computadora decorada
    }
}
