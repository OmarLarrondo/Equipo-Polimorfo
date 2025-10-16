package modelo.decorador;

import modelo.computadora.ComputadoraBase;

public class AutoCADDecorator extends SoftwareDecorator {

    //AQUI NO SE SI ES TODO EL SUPER O SOLO COMPUATADORA XD
    public AutoCADDecorator(ComputadoraBase computadora, String nombreSoftware, double precioSoftware) {
        super(computadora, nombreSoftware, precioSoftware);
        
    }

    public String obtenerDescripcion(){
        //aqui va su codigo 
        return null;
    }
}
