package patrones.factory.ia;

import java.util.Map;

import patrones.factory.niveles.TipoNivel;

/**
 * Clase que encapsula la configuración necesaria para crear un {@code Nivel}
 * mediante las diferentes fábricas de niveles.
 *
 * <p>Contiene información como el nombre del nivel, el tipo de nivel,
 * la dificultad, el patrón de bloques, los datos del mapa y la selección
 * de prototipos de paletas para jugadores.</p>
 *
 * <p>Es utilizada por las implementaciones de {@code NivelFactory} para
 * construir niveles con parámetros totalmente configurables.</p>
 *
 * @author 
 * @version 1.0
 */
public class ConfigNivel {
    /** Nombre del nivel. */
    private String nombre;

    /** Tipo de nivel seleccionado (Clásico, IA, Personalizado). */
    private TipoNivel tipoNivel;

    /** Dificultad asociada al nivel.(1,2,...10) */
    private int dificultad;

    //CHECAAAAAAAAAAAAAAAAAAR COMO LO HZIO SAUL
    /** Cadena que representa el patrón del mapa (por ejemplo, una matriz codificada). */
    private String patron;

    /** Datos estructurados del mapa personalizados por el usuario. */
    private DatosMapa datosMapa;

    /** Mapeo de jugador → nombre del prototipo de paleta seleccionado. */
    private Map<String, String> prototiposPaletas;

    // -------------------------------------------------------------------------
    // MÉTODOS obtener
    // -------------------------------------------------------------------------

    /**
     * Obtiene el tipo de nivel configurado.
     *
     * @return el tipo de nivel.
     */
    public TipoNivel obtenerTipoNivel() {
        return tipoNivel;
    }

    /**
     * Obtiene el nivel de dificultad configurado.
     *
     * @return la dificultad establecida.
     */
    public int obtenerDificultad() {
        return dificultad;
    }

    /**
     * Obtiene el patrón del mapa configurado.
     *
     * @return el patrón como cadena.
     */
    public String obtenerPatron() {
        return patron;
    }

    /**
     * Obtiene el objeto {@link DatosMapa} asociado a esta configuración.
     *
     * @return los datos del mapa.
     */
    public DatosMapa obtenerDatosMapa() {
        return datosMapa;
    }

    /**
     * Obtiene el nombre configurado para el nivel.
     *
     * @return el nombre del nivel.
     */
    public String obtenerNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del nivel.
     *
     * @param nombre el nombre deseado.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    // -------------------------------------------------------------------------
    // PROTOTIPOS DE PALETAS
    // -------------------------------------------------------------------------

    /**
     * FALTA IMPLEMNTAR LOGICA
     * Asocia un prototipo de paleta a un jugador.
     *
     * @param jugador identificador del jugador (por ejemplo, "J1", "J2", "IA")
     * @param nombrePrototipo nombre del prototipo a asignar.
     *
     * // FALTA implementar la lógica
     */
    public void establecerPrototipoPaleta(String jugador, String nombrePrototipo) {
        //aqui va su codigo
    }

    /**
     * Obtiene el nombre del prototipo de paleta asignado a un jugador.
     *
     * @param jugador identificador del jugador.
     * @return el nombre del prototipo asociado.
     */
    public String obtenerPrototipoPaleta(String jugador) {
        return prototiposPaletas.get(jugador);
    }

    // -------------------------------------------------------------------------
    // GETTERS y SETTERS estándar
    // -------------------------------------------------------------------------

    public TipoNivel getTipoNivel() {
        return tipoNivel;
    }

    public void setTipoNivel(TipoNivel tipoNivel) {
        this.tipoNivel = tipoNivel;
    }

    public int getDificultad() {
        return dificultad;
    }

    public void setDificultad(int dificultad) {
        this.dificultad = dificultad;
    }

    public String getPatron() {
        return patron;
    }

    public void setPatron(String patron) {
        this.patron = patron;
    }

    public DatosMapa getDatosMapa() {
        return datosMapa;
    }

    public void setDatosMapa(DatosMapa datosMapa) {
        this.datosMapa = datosMapa;
    }

    public Map<String, String> getPrototiposPaletas() {
        return prototiposPaletas;
    }

    public void setPrototiposPaletas(Map<String, String> prototiposPaletas) {
        this.prototiposPaletas = prototiposPaletas;
    }
}
