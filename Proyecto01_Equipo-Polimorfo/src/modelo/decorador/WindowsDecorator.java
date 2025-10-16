package modelo.decorador;

import modelo.computadora.ComputadoraBase;

public class WindowsDecorator extends SoftwareDecorator {

    //CHECAR
    public WindowsDecorator(ComputadoraBase computadora, String nombreSoftware, double precioSoftware) {
        super(computadora, nombreSoftware, precioSoftware);
    }

    public String obtenerDescripcion(){
        //aqui va su codiog 
        return null;
    }
}
