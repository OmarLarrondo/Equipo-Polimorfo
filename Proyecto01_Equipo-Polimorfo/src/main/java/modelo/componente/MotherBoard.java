package modelo.componente;

/**
 * Clase que representa una placa base (MotherBoard) como componente hoja de una PC.
 *
 * <p>Extiende {@link ComponenteHoja} e incluye atributos específicos de una
 * motherboard, como chipset, socket y arquitectura soportada.
 *
 * <p>Segun los requerimientos, solo existen motherboards Intel en el inventario.
 * Las CPUs AMD deben trabajar con estos componentes mediante adaptadores.
 *
 * @author Equipo-polimorfo
 * @version 1.0
 */
public class MotherBoard extends ComponenteHoja {
    /** Chipset de la placa madre*/
    private String chipset;

    /** Tipo de socket compatible */
    private String socket;

    /** Tipo de arquitectura admitida por la placa madre*/
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
     * Devuelve una representación en texto con los detalles completos de la motherboard.
     * @return una cadena formateada con el nombre, marca, chipset, socket,
     *         arquitectura y precio de la motherboard.
     */
    @Override
    public String toString() {
        return String.format(
            "Motherboard: %s | Marca: %s | Chipset: %s | Socket: %s | Arquitectura: %s | Precio: $%.2f",
            obtenerNombre(), obtenerMarca(), chipset, socket, arquitecturaSeparada, obtenerPrecio()
        );
    }
}
