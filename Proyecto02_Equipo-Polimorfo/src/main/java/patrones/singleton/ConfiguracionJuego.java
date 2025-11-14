package patrones.singleton;

/**
 * Clase que gestiona la configuración global del juego.
 * <p>
 * Implementa el patrón <b>Singleton</b>, lo que garantiza que exista
 * únicamente una instancia de esta clase durante toda la ejecución
 * del programa. La configuración incluye parámetros como tamaño de 
 * pantalla, velocidad de la pelota, velocidad de la paleta y volumen.
 * </p>
 *
 * @author equipo-polimorfo
 * @version 1.0
 */
public class ConfiguracionJuego {
    /** Única instancia disponible de la clase (Singleton). */
    private static ConfiguracionJuego instancia;

    //OJOOOOOOOOOOOOOOOOOOOOO, VI QUE NO DEBE GUARDARSE EL TAMAO DE LA VENTANA,
    //PORQUE VA CAMBIANDO MEDIANTE LA EJECUCION. ento checar


    /** Ancho de la pantalla del juego en píxeles. */
    private int anchoPantalla;

    /** Alto de la pantalla del juego en píxeles. */
    private int altoPantalla;

    /** Rapidez inicial de la pelota. */
    private double rapidezPelota;

    /** Rapidez de movimiento de las paletas. */
    private double rapidezPaleta;

    /** Nivel de volumen del juego (0-100). */
    private double volumen;

    /**
     * Constructor privado para impedir la creación externa de instancias.
     * <p> Inicializa los valores por defecto de la configuración.</p>
     */
    private ConfiguracionJuego() {
        anchoPantalla = 800;
        altoPantalla = 600;
        rapidezPelota = 5.0;
        rapidezPaleta = 4.0;
        volumen = 50;
    }

    /**
     * Devuelve la única instancia disponible de {@code ConfiguracionJuego}.
     * <p>Si la instancia aún no ha sido creada, se inicializa antes de retornarse.</p>
     *
     * @return instancia global de la configuración del juego.
     */
    public static ConfiguracionJuego obtenerInstancia() {
        if (instancia == null) {
            instancia = new ConfiguracionJuego();
        }
        return instancia;
    }

    /**
     * Carga la configuración del juego desde una fuente externa.
     * <p>Esta implementación está pendiente.</p>
     */
    public void cargarConfiguracion() {
        // Leer archivo, BD o JSON.
        // Ejemplo: this.volumen = archivo.leerVolumen();
    }

    /**
     * Guarda la configuración del juego desde una fuente externa.
     * <p>Esta implementación está pendiente.</p>
     */
    public void guardarConfiguracion() {
        // Guardar archivo, BD o JSON.
    }

    /**
     * Establece el volumen global del juego.
     *
     * @param volumen valor entre 0-100 que representa el nivel de volumen.
     */
    public void establecerVolumen(double volumen) {
        this.volumen = volumen;
    }

    
    /**
     * Obtiene el ancho de la pantalla configurado.
     *
     * @return ancho de la pantalla en píxeles.
     */
    public int obtenerAnchoPantalla() {
        return anchoPantalla;
    }

    /**
     * Obtiene el alto de la pantalla configurado.
     *
     * @return alto de la pantalla en píxeles.
     */
    public int obtenerAltoPantalla() {
        return altoPantalla;
    }
}
