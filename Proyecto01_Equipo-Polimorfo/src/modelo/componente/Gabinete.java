package modelo.componente;

/**
 * Representa un gabinete (chasis) de computadora como un componente hoja.
 * 
 * <p>Extiende {@link ComponenteHoja} e incluye un atributo específico del gabinete:
 * su tamaño. Esta clase permite crear instancias de gabinetes con nombre, marca,
 * precio, tipo y tamaño, y obtener sus detalles.
 * 
 * Ejemplos de tamaño: "ATX".
 * 
 * @author Equipo-polimorfo
 * @version 1.0
 */
public class Gabinete extends ComponenteHoja {
    /**Tamaño del gabinete. Por ejemplo: "ATX" pues es el unico que maneja monoschinoxmx. XD*/
    private String tamanio;
        
    /**
     * Construye un nuevo gabinete con los datos especificados.
     * 
     * @param nombre Nombre del gabinete.
     * @param precio Precio del gabinete.
     * @param marca Marca del gabinete.
     * @param tipo Tipo de componente (por ejemplo, "Gabinete").
     * @param tamanio Tamaño del gabinete, que determina la compatibilidad con
     *                motherboards y otros componentes.
     */
    public Gabinete(String nombre, double precio, String marca, String tipo, String tamanio) {
        super(nombre, precio, marca, tipo);
        this.tamanio = tamanio;
    }

    /**
     * Devuelve el tamaño del gabinete.
     * @return una cadena que indica el tamaño del gabinete.
     */
    public String getTamanio() {
        return tamanio;
    }

    /**
     * Devuelve una representación en cadena del gabinete, incluyendo todos sus atributos.
     * <p>La salida incluye el nombre, precio, marca, tipo y tamaño del gabinete, lo que
     * facilita la impresión de los detalles completos de un objeto {@code Gabinete}.
     * @return Una cadena con la información completa del gabinete.
     */
    @Override
    public String toString() {
        return "Gabinete{" +
                "nombre='" + obtenerNombre() + '\'' +
                ", precio=" + obtenerPrecio() +
                ", marca='" + obtenerMarca() + '\'' +
                ", tipo='" + obtenerTipo() + '\'' +
                ", tamanio='" + tamanio + '\'' +
                '}';
    }

}
