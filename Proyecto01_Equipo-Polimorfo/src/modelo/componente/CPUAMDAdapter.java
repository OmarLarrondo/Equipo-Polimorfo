package modelo.componente;

public class CPUAMDAdapter implements ComponentePC {
    private CPU cpuAMD;

    
    public CPUAMDAdapter(CPU cpuAMD) {
        this.cpuAMD = cpuAMD;
    }

    @Override
    public String obtenerNombre() {
        return cpuAMD.obtenerNombre();
    }

    @Override
    public double obtenerPrecio() {
        return cpuAMD.obtenerPrecio();
    }

    @Override
    public String obtenerMarca() {
        return cpuAMD.obtenerMarca();
    }

    @Override
    public String obtenerTipo() {
        return cpuAMD.obtenerTipo();
    }

    @Override
    public String mostrarDetalles() {
        return cpuAMD.mostrarDetalles();
        
    }

    @Override
    public boolean esCompatibleConComponentePC() {
        return cpuAMD.esCompatibleConComponentePC();
    }
}
