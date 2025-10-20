package modelo.componente;

/**
 * Representa un procesador (CPU) como un componente hoja de una computadora.
 * <p>Extiende {@link ComponenteHoja} e incluye atributos específicos de un CPU,
 * como la cantidad de núcleos y la arquitectura del procesador. Permite crear
 * instancias de CPU con nombre, marca, precio, tipo, cantidad de núcleos y arquitectura,
 * y obtener esta información mediante sus métodos.
 * 
 * Ejemplos de arquitectura: "x86-64", "x86-64 (AMD64)"")
 * 
 * Ejemplos de cantidad de núcleos: 4, 6, 8, 12, 16.
 * 
 * @author Equipo-polimorfo
 * @version 1.0
 */
public class CPU extends ComponenteHoja {
    
    /**Cantidad de núcleos del procesador.*/
    private int cantidadNucleos;

    /**Arquitectura del procesador ("x86-64 (AMD64)"", x86-64).*/
    private String arquitectura;

    /**
     * Construye un nuevo procesador con los datos especificados.
     * 
     * @param nombre Nombre del procesador.
     * @param precio Precio del procesador.
     * @param marca Marca del procesador.
     * @param tipo Tipo de componente (por ejemplo, "CPU").
     * @param cantidadNucleos Número de núcleos del procesador.
     * @param arquitectura Arquitectura del procesador.
     */
    public CPU(String nombre, double precio, String marca, String tipo, int cantidadNucleos, String arquitectura) {
        super(nombre, precio, marca, tipo);
        this.cantidadNucleos = cantidadNucleos;
        this.arquitectura = arquitectura;
    }

    /**
     * Devuelve la cantidad de núcleos del procesador.
     * @return número de núcleos.
     */
    public int getCantidadNucleos() {
        return cantidadNucleos;
    }

    /**
     * Devuelve la arquitectura del procesador.
     * @return una cadena que indica la arquitectura del CPU.
     */
    public String getArquitectura() {
        return arquitectura;
    }

    /**
     * Devuelve una representación en texto con los detalles completos del procesador.
     * @return una cadena con cantidad de núcleos, arquitectura, nombre, marca, tipo y precio.
     */
    @Override
    public String toString() {
        return String.format(
            "CPU: %s | Marca: %s | Tipo: %s | Núcleos: %d | Arquitectura: %s | Precio: $%.2f",
            obtenerNombre(), obtenerMarca(), obtenerTipo(), cantidadNucleos, arquitectura, obtenerPrecio()
        );
    }
}
