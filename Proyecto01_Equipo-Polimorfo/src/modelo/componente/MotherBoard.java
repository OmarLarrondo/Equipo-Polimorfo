package modelo.componente;

/**
 * Clase que representa una placa base (MotherBoard) como componente hoja de una PC.
 * 
 * <p>Extiende {@link ComponenteHoja} e incluye atributos específicos de una
 * motherboard, como chipset, socket y arquitectura soportada.
 *
 * <p>Las AMD no pueden ser compatibles con ninguna motherboards
 * 
 * @author TuNombre
 * @version 1.0
 */
public class MotherBoard extends ComponenteHoja {

    private String chipset;
    private String socket;
    private String arquitecturaSeparada;

    /**
     * Construye una MotherBoard con sus atributos básicos y específicos.
     * 
     * @param nombre el nombre de la motherboard
     * @param precio el precio de la motherboard
     * @param marca la marca de la motherboard (por ejemplo, AMD, ASUS, MSI)
     * @param tipo el tipo de componente (siempre "MotherBoard")
     * @param chipset el chipset de la motherboard (por ejemplo, "B550")
     * @param socket el socket de CPU compatible (por ejemplo, "AM4")
     * @param arquitecturaSeparada la arquitectura soportada (por ejemplo, "x86_64")
     */
    public MotherBoard(String nombre, double precio, String marca, String tipo, String chipset, String socket,
            String arquitecturaSeparada) {
        super(nombre, precio, marca, tipo);
        this.chipset = chipset;
        this.socket = socket;
        this.arquitecturaSeparada = arquitecturaSeparada;
    }

    /**
     * Obtiene el chipset de la motherboard.
     * 
     * @return el chipset
     */
    public String getChipset() {
        return chipset;
    }

    /**
     * Obtiene el socket de CPU soportado por la motherboard.
     * 
     * @return el socket
     */
    public String getSocket() {
        return socket;
    }

    /**
     * Obtiene la arquitectura soportada por la motherboard.
     * 
     * @return la arquitectura (por ejemplo, "x86_64")
     */
    public String getArquitecturaSeparada() {
        return arquitecturaSeparada;
    }

    /**
     * Determina si esta motherboard es compatible con otro componente de PC.
     * 
     * <p>Actualmente, la validación puede incluir compatibilidad con GPUs AMD
     * y CPUs según el chipset, socket y arquitectura. 
     * 
     * @return {@code true} si es compatible con el componente dado, 
     *         {@code false} en caso contrario
     * @throws UnsupportedOperationException si aún no se implementa la lógica
     */
    @Override
    public boolean esCompatibleConComponentePC() {
        // TODO: Implementar la lógica de compatibilidad con GPUs y CPUs
        throw new UnsupportedOperationException("Método no implementado: esCompatibleConComponentePC");
    }
}
