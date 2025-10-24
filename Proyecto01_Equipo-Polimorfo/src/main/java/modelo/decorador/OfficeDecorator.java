package modelo.decorador;

import modelo.computadora.ComputadoraBase;

/**
 * Decorador concreto que agrega Microsoft Office 365 a una computadora.
 * Implementa el patron Decorator para agregar funcionalidad de suite
 * ofimática Office a una computadora base.
 *
 * @author Equipo-Polimorfo
 * @version 1.0
 */
public class OfficeDecorator extends SoftwareDecorator {

    /**
     * Constructor que inicializa el decorador de Office.
     *
     * @param computadora La computadora base a decorar.
     * @param nombreSoftware El nombre del software Office.
     * @param precioSoftware El precio del software Office.
     */
    public OfficeDecorator(ComputadoraBase computadora, String nombreSoftware, double precioSoftware) {
        super(computadora, nombreSoftware, precioSoftware);
    }

    /**
     * Obtiene la descripcion de la computadora incluyendo Office.
     *
     * @return La descripcion de la computadora base mas el software Office.
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
