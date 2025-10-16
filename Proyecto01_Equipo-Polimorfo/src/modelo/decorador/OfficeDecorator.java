package modelo.decorador;

import modelo.computadora.ComputadoraBase;

public class OfficeDecorator extends SoftwareDecorator {

    public OfficeDecorator(ComputadoraBase computadora, String nombreSoftware, double precioSoftware) {
        super(computadora, nombreSoftware, precioSoftware);
        
    }

    public String obtenerDescripcion(){
        //aqui va su codigo 
        return null;
    }
}
