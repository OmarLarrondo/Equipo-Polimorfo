package modelo.componente;

/**
 * Adapter para integrar un CPU AMD como un ComponentePC.
 * 
 * <p>Esta clase implementa la interfaz {@link ComponentePC} y redirige todas las
 * llamadas al objeto {@link CPU} que representa un procesador AMD.
 * Permite usar CPUs AMD en sistemas que trabajan con {@link ComponentePC}.
 * 
 * @author Equipo-Polimorfo
 * @version 1.0
 */
public class CPUAMDAdapter implements ComponentePC {
    /** CPU AMD que se está adaptando */
    private CPU cpuAMD;

    /**
     * Constructor que recibe un CPU AMD a adaptar.
     * 
     * @param cpuAMD el CPU AMD que se va a adaptar; no puede ser null
     */
    public CPUAMDAdapter(CPU cpuAMD) {
        if (cpuAMD == null) {
            throw new IllegalArgumentException("El CPU AMD a adaptar no puede ser nulo.");
        }
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

    /**
     * Método opcional del patrón Adapter que indica que este CPU AMD
     * ha sido adaptado como un ComponentePC.
     * 
     * @return mensaje indicando que el CPU AMD está adaptado
     */
    public String adaptar() {
        return "CPU AMD '" + cpuAMD.obtenerNombre() + "' adaptado como ComponentePC";
    }
}
