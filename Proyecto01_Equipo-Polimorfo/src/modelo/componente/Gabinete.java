package modelo.componente;

public class Gabinete extends ComponenteHoja {
    private String tamanio;
        
    
    public Gabinete(String nombre, double precio, String marca, String tipo, String tamanio) {
        super(nombre, precio, marca, tipo);
        this.tamanio = tamanio;
    }

    public String getTamanio() {
        return tamanio;
    }
    
    //CHECARRRRRRRRRRRR
    @Override
    public boolean esCompatibleConComponentePC() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'esCompatibleConComponentePC'");
    }

}
