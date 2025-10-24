package modelo.decorador;

import modelo.computadora.ComputadoraBase;

/**
 * Decorador concreto que agrega Adobe Photoshop a una computadora.
 * Implementa el patron Decorator para agregar funcionalidad de edicion
 * de imagenes Photoshop a una computadora base.
 *
 * @author Equipo-Polimorfo
 * @version 1.0
 */
public class PhotoshopDecorator extends SoftwareDecorator {

    /**
     * Constructor que inicializa el decorador de Photoshop.
     *
     * @param computadora La computadora base a decorar.
     * @param nombreSoftware El nombre del software Photoshop.
     * @param precioSoftware El precio del software Photoshop.
     */
    public PhotoshopDecorator(ComputadoraBase computadora, String nombreSoftware, double precioSoftware) {
        super(computadora, nombreSoftware, precioSoftware);
    }

    /**
     * Obtiene la descripcion de la computadora incluyendo Photoshop.
     *
     * @return La descripcion de la computadora base mas el software Photoshop.
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
