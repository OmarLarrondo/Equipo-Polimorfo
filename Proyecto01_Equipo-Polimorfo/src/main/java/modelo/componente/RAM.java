package modelo.componente;
/**
 * Representa un módulo de memoria RAM como un componente hoja de una computadora.
 * 
 * <p>Extiende {@link ComponenteHoja} e incluye atributos específicos de la RAM,
 * como su capacidad en GB y el tipo de memoria. Permite crear instancias de RAM
 * con nombre, marca, precio, tipo, capacidad y tipo de memoria, y obtener esta información
 * mediante sus métodos.
 * 
 * Ejemplos de tipo de memoria: "DDR4", "DDR5".
 * 
 * @author Equipo-polimorfo
 * @version 1.0
 */
public class RAM extends ComponenteHoja {
    /**amaño de la memoria en GB*/
    private int capacidadGB;

    /**Tipo de memoria RAM (por ejemplo: "DDR4", "DDR5", "DDR3").*/
    private String tipoMemoria;

    /**
     * Construye un nuevo módulo de RAM con los datos especificados.
     *
     * @param nombre Nombre del módulo de RAM.
     * @param precio Precio del módulo de RAM.
     * @param marca Marca del módulo de RAM.
     * @param tipo Tipo de componente (por ejemplo, "RAM").
     * @param capacidadGB Capacidad del módulo en GB.
     * @param tipoMemoria Tipo de memoria
     */
    public RAM(String nombre, double precio, String marca, String tipo, int capacidadGB, String tipoMemoria) {
        super(nombre, precio, marca, tipo);
        this.capacidadGB = capacidadGB;
        this.tipoMemoria = tipoMemoria;
    }

    /**
     * Devuelve la capacidad del módulo de RAM.
     * @return capacidad en gigabytes (GB).
     */
    public int getCapacidadGB() {
        return capacidadGB;
    }

    /**
     * Devuelve el tipo de memoria del módulo de RAM.
     * @return tipo de memoria (por ejemplo: "DDR4", "DDR5", "DDR3")
     */
    public String getTipoMemoria() {
        return tipoMemoria;
    }

    /**
     * Devuelve una representación en texto con los detalles completos del módulo de RAM.
     * @return una cadena con la capacidad, tipo de memoria, nombre, marca, tipo y precio.
     */
    @Override
    public String toString() {
        return String.format(
            "RAM: %s | Marca: %s | Tipo: %s | Capacidad: %d GB | Precio: $%.2f",
            obtenerNombre(), obtenerMarca(), tipoMemoria, capacidadGB, obtenerPrecio()
        );
    }
}
