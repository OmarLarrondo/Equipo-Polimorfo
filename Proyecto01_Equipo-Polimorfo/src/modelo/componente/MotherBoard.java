package modelo.componente;

public class MotherBoard extends ComponenteHoja {
    private String chipset;
    private String socket;
    private String arquitecturaSeparada;

    
    public MotherBoard(String nombre, double precio, String marca, String tipo, String chipset, String socket,
            String arquitecturaSeparada) {
        super(nombre, precio, marca, tipo);
        this.chipset = chipset;
        this.socket = socket;
        this.arquitecturaSeparada = arquitecturaSeparada;
    }

    public String getChipset() {
        return chipset;
    }



    public String getSocket() {
        return socket;
    }



    public String getArquitecturaSeparada() {
        return arquitecturaSeparada;
    }
        
    //CHECARRRRR
    @Override
    public boolean esCompatibleConComponentePC() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'esCompatibleConComponentePC'");
    }

}
