package patrones.factory.ia;

/**
 * Clase que encapsula los datos básicos necesarios para definir un mapa
 * dentro del sistema, incluyendo su nombre, número de filas y número de columnas.
 * 
 * @author Equipo-polimorfo
 * @version 1.0
 */
public class DatosMapa {
    /**El nombre del Mapa */
    private String nombre;

    /**Las filas del mapa. */
    private int filas;

    /**Las columnas del mapa */
    private int columnas;

    /**
     * Constructor que inicializa los valores del mapa
     * @param nombre el nombre a asignar
     * @param filas la cantidad de filas a asignar
     * @param columnas la cantidad de columnas a asignar 
     */
    public DatosMapa(String nombre, int filas, int columnas) {
        this.nombre = nombre;
        this.filas = filas;
        this.columnas = columnas;
    }

    /**
     * Obtiene el nombre del mapa
     * @return el nombre del mapa
     */
    public String obtenerNombre() {
        return nombre;
    }

    /**
     * Obtiene la cantidad de filas del mapa
     * @return la cantidad de filas del mapa.
     */
    public int obtenerFilas() {
        return filas;
    }

    /**
     * Obtiene las columnas del mapa.
     * @return la cantidad de columnas del mapa.
     */
    public int obtenerColumnas() {
        return columnas;
    }
}
