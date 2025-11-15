package patrones.factory.niveles;

/**
 * Configuración para la creación de niveles en el juego.
 *
 * <p>
 * Encapsula los parámetros necesarios para construir diferentes tipos
 * de niveles a través del sistema de fábricas. Permite especificar el tipo
 * de nivel, dificultad, patrón de bloques y nombre personalizado.
 * </p>
 *
 * @author Equipo-polimorfo
 * @version 1.0
 */
public class ConfigNivel {
    /** Nombre descriptivo del nivel. */
    private String nombre;

    /** Tipo de nivel a crear (CLASICO, PERSONALIZADO, INTELIGENTE). */
    private TipoNivel tipoNivel;

    /** Nivel de dificultad del juego. */
    private int dificultad;

    /** Patrón de distribución de bloques. */
    private String patron;

    /**
     * Obtiene el tipo de nivel configurado.
     *
     * @return El tipo de nivel.
     */
    public TipoNivel obtenerTipoNivel(){
        return tipoNivel;
    }

    /**
     * Obtiene el nivel de dificultad.
     *
     * @return El nivel de dificultad.
     */
    public int obtenerDificultad(){
        return dificultad;
    }

    /**
     * Obtiene el patrón de bloques configurado.
     *
     * @return El patrón de distribución de bloques.
     */
    public String obtenerPatron(){
        return patron;
    }

    /**
     * Obtiene el nombre del nivel.
     *
     * @return El nombre descriptivo del nivel.
     */
    public String obtenerNombre(){
        return nombre;
    }

    /**
     * Establece el nombre del nivel.
     *
     * @param nombre El nombre descriptivo del nivel.
     */
    public void setNombre(String nombre){
        this.nombre = nombre;
    }

    /**
     * Obtiene el tipo de nivel configurado.
     *
     * @return El tipo de nivel.
     */
    public TipoNivel getTipoNivel() {
        return tipoNivel;
    }

    /**
     * Establece el tipo de nivel.
     *
     * @param tipoNivel El tipo de nivel a configurar.
     */
    public void setTipoNivel(TipoNivel tipoNivel) {
        this.tipoNivel = tipoNivel;
    }

    /**
     * Obtiene el nivel de dificultad.
     *
     * @return El nivel de dificultad.
     */
    public int getDificultad() {
        return dificultad;
    }

    /**
     * Establece el nivel de dificultad.
     *
     * @param dificultad El nivel de dificultad a configurar.
     */
    public void setDificultad(int dificultad) {
        this.dificultad = dificultad;
    }

    /**
     * Obtiene el patrón de bloques configurado.
     *
     * @return El patrón de distribución de bloques.
     */
    public String getPatron() {
        return patron;
    }

    /**
     * Establece el patrón de distribución de bloques.
     *
     * @param patron El patrón de bloques a configurar.
     */
    public void setPatron(String patron) {
        this.patron = patron;
    }
}
