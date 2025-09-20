package Academia.Campos;

public abstract class CampoEntrenamiento {
    /**NOmbre del campo de entraniento (ejemplo: Valle del dragon, ect).*/
    private String nombre;
    /** Descripcion del campo de entranamiento (ejemplo: "Es un valle ubicado a las afueras de la Montaña Paoz que se encuentra lleno de huesos y está habitado por lobos carnívoros").*/
    private String descripción;

    
    /**
     * Constructor para inicializar un objeto de tipo CampoEntranimento.
     * Crae un nuevo campo de entrenamiento con nombre y descripcion.
     * @param nombre EL nombre del campo.
     * @param descripción La descripcion del campo.
     */
    public CampoEntrenamiento(String nombre, String descripción) {
        this.nombre = nombre;
        this.descripción = descripción;
    }
    /**@return El nombre del campo*/
    public String getNombre() {
        return nombre;
    }
    /**@param nombre Nombre del campo */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    /**@return La descripcion del campo */
    public String getDescripción() {
        return descripción;
    }
    /**@param descripcion Descripcion del campo */
    public void setDescripción(String descripción) {
        this.descripción = descripción;
    }

    private int sumaNivelesIntegrantes(){
        //aqui va codigo
        return 0;
    }
    

    
}
