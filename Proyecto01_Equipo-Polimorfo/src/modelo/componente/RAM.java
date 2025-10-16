package modelo.componente;

public class RAM extends ComponenteHoja {
    private int capacidadGB;
    private String tipoMemoria;

    public RAM(String nombre, double precio, String marca, String tipo, int capacidadGB, String tipoMemoria) {
        super(nombre, precio, marca, tipo);
        this.capacidadGB = capacidadGB;
        this.tipoMemoria = tipoMemoria;
    }

    public int getCapacidadGB() {
        return capacidadGB;
    }

    public String getTipoMemoria() {
        return tipoMemoria;
    }
    //CHECARRRRR
    @Override
    public boolean esCompatibleConComponentePC() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'esCompatibleConComponentePC'");
    }
    

    
}
