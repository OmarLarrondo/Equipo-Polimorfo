package modelo.componente;

public class FuenteAlimentacion extends ComponenteHoja {
    private int potenciaMaxima;
    private String certificacion;

    public FuenteAlimentacion(String nombre, double precio, String marca, String tipo, int potenciaMaxima,
            String certificacion) {
        super(nombre, precio, marca, tipo);
        this.potenciaMaxima = potenciaMaxima;
        this.certificacion = certificacion;
    }

    public int getPotenciaMaxima() {
        return potenciaMaxima;
    }

    public String getCertificacion() {
        return certificacion;
    }
    //checaaaaarrr
    @Override
    public boolean esCompatibleConComponentePC() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'esCompatibleConComponentePC'");
    }
    
    
    
}
