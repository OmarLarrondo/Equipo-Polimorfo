package patrones.factory.ia;

/**
 * Clase que encapsula los datos de configuración de un mapa.
 *
 * @author Equipo-polimorfo
 * @version 1.0
 */
public class DatosMapa {

    private String nombre;
    private int filas;
    private int columnas;

    public DatosMapa(String nombre, int filas, int columnas) {
        this.nombre = nombre;
        this.filas = filas;
        this.columnas = columnas;
    }

    public String obtenerNombre() {
        return nombre;
    }

    public int obtenerFilas() {
        return filas;
    }

    public int obtenerColumnas() {
        return columnas;
    }
}
