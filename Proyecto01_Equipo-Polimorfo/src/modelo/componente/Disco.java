package modelo.componente;

public class Disco extends ComponenteHoja {
    private int capacidadAlmacenamiento;
    private String tipoAlimentacion;

    public Disco(String nombre, double precio, String marca, String tipo, int capacidadAlmacenamiento,
            String tipoAlimentacion) {
        super(nombre, precio, marca, tipo);
        this.capacidadAlmacenamiento = capacidadAlmacenamiento;
        this.tipoAlimentacion = tipoAlimentacion;
    }
    public int getCapacidadAlmacenamiento() {
        return capacidadAlmacenamiento;
    }
    public String getTipoAlimentacion() {
        return tipoAlimentacion;
    }
    //CHECARRRRA
    @Override
    public boolean esCompatibleConComponentePC() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'esCompatibleConComponentePC'");
    }

    
}
