package modelo.componente;

public class CPU extends ComponenteHoja {
    
    private int cantidadNucleos;
    private String arquitectura;

    public CPU(String nombre, double precio, String marca, String tipo, int cantidadNucleos, String arquitectura) {
        super(nombre, precio, marca, tipo);
        this.cantidadNucleos = cantidadNucleos;
        this.arquitectura = arquitectura;
    }

    public int getCantidadNucleos() {
        return cantidadNucleos;
    }

    public String getArquitectura() {
        return arquitectura;
    }

    //FALTA IMPLEMENTAR LA LOGICA
    @Override
    public boolean esCompatibleConComponentePC() {
        //aqui va su codigo.
        return false;
    }
}
