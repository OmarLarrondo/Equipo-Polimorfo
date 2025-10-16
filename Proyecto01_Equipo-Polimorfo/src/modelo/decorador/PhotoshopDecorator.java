package modelo.decorador;

import modelo.computadora.ComputadoraBase;

public class PhotoshopDecorator extends SoftwareDecorator {

    public PhotoshopDecorator(ComputadoraBase computadora, String nombreSoftware, double precioSoftware) {
        super(computadora, nombreSoftware, precioSoftware);
    }
    
    public String  obtenerDescripcion(){
        //aqui va su codigo 
        return null;
    }
}
