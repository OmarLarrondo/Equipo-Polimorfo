package modelo.componente;

public class GPU extends ComponenteHoja {

    private String tipoMemoriaGPU;
    private int vRam;
      
    public GPU(String nombre, double precio, String marca, String tipo, String tipoMemoriaGPU, int vRam) {
        super(nombre, precio, marca, tipo);
        this.tipoMemoriaGPU = tipoMemoriaGPU;
        this.vRam = vRam;
    }
    public String getTipoMemoriaGPU() {
        return tipoMemoriaGPU;
    }
    public int getVRAM(){
        return vRam;
    }

    //CHECARRRRRRRRRRRRRRRRRR
    @Override
    public boolean esCompatibleConComponentePC() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'esCompatibleConComponentePC'");
    }
}
