package modelo.componente;


//OJOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO
//NO EN EL CLASSROOM, DICE QUE SON EN GB O TB, PERO NO SE COMO DIFERENCIAMOS LOS TB CON LOS GIGAS
//NO SE xd

/**
 * Representa un disco de almacenamiento como un componente hoja de una computadora.
 * <p>Extiende {@link ComponenteHoja} e incluye atributos específicos de un disco,
 * como su capacidad de almacenamiento y el tipo de alimentación que requiere.
 * Permite crear instancias de discos con nombre, marca, precio, tipo, capacidad
 * y tipo de alimentación, y obtener esta información mediante sus métodos.
 * 
 * Ejemplos de capacidad: 500 (GB), 1000 (GB = 1TB).  
 * Ejemplos de tipo de alimentación: "SATA", "M.2", "NVMe".
 * 
 * @author Equipo-polimorfo
 * @version 1.0
 */
public class Disco extends ComponenteHoja {
    /** Capacidad de almacenamiento del disco en gigabytes (GB o TB).*/
    private int capacidadAlmacenamiento;

    /** Tipo de alimentación o conexión del disco ("HDD", "SSD").*/
    private String tipoAlimentacion;

    /**
     * Construye un nuevo disco con los datos especificados.
     * 
     * @param nombre Nombre del disco.
     * @param precio Precio del disco.
     * @param marca Marca del disco.
     * @param tipo Tipo de componente (por ejemplo, "Disco").
     * @param capacidadAlmacenamiento Capacidad del disco en GB.
     * @param tipoAlimentacion Tipo de alimentación o conexión del disco.
     */
    public Disco(String nombre, double precio, String marca, String tipo, int capacidadAlmacenamiento,
            String tipoAlimentacion) {
        super(nombre, precio, marca, tipo);
        this.capacidadAlmacenamiento = capacidadAlmacenamiento;
        this.tipoAlimentacion = tipoAlimentacion;
    }

    /**
     * Devuelve la capacidad de almacenamiento del disco.
     * @return capacidad del disco (GB o TB)).
     */
    public int getCapacidadAlmacenamiento() {
        return capacidadAlmacenamiento;
    }

    /**
     * Devuelve el tipo de alimentación o conexión del disco.
     * 
     * @return una cadena que indica el tipo de alimentación (por ejemplo, "SATA", "M.2").
     */
    public String getTipoAlimentacion() {
        return tipoAlimentacion;
    }

    /**
     * Devuelve una representación en texto con los detalles completos del disco.
     * @return una cadena formateada con el nombre, marca, tipo, capacidad
     *         y tipo de alimentación del disco.
     */
    @Override
    public String toString() {
        return String.format(
            "Disco: %s | Marca: %s | Tipo: %s | Capacidad: %d GB | Alimentación: %s | Precio: $%.2f",
            obtenerNombre(), obtenerMarca(), obtenerTipo(), capacidadAlmacenamiento, tipoAlimentacion, obtenerPrecio()
        );
    }
}
