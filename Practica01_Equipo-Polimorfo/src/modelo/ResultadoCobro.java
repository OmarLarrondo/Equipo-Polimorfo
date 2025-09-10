package modelo;

/**
 * Clase que encapsula la información sobre el resultado de un cobro específico.
 * Esta clase es inmutable una vez creada, representando un registro histórico
 * de una transacción financiera en el sistema.
 */
public class ResultadoCobro {
    
    /**El usuario al que se cobró */
    private final Usuario usuario;

    /**El servicio que realizó el cobro */
    private final String servicio;

    /**El monto que se cobró al usuario */
    private final double monto;

    /**Resultado del cobro*/
    private final boolean exitoso;

    /**La descripción del cobro realizado */
    private final String descripcion;

    public ResultadoCobro(Usuario usuario, String servicio
    , double monto, boolean exitoso, String descripcion) {
        this.usuario = usuario;
        this.servicio = servicio;
        this.monto = monto;
        this.exitoso = exitoso;
        this.descripcion = descripcion;
    }

    /**
     * Obtiene el usuario al que se le realizó el cobro.
     *
     * @return la instancia de {@link Usuario} correspondiente al cobro
     */ 
    public Usuario getUsuario() {
        return usuario;
    }

    /**
     * Obtiene el nombre del servicio que realizó el cobro
     * 
     * @return el nombre del servicio correspondiente al cobro
     */
    public String getServicio() {
        return servicio;
    }

    /**
     * Obtiene el monto que se realizó
     * 
     * @return el monto cobrado
     */
    public double getMonto() {
        return monto;
    }

    /**
     * Indica si el cobro se realizó exitosamente
     * 
     * @return {@code true} si el cobro se realizó correctamente, 
     *         {@code false} en caso contrario
     */
    public boolean isExitoso() {
        return exitoso;
    }

    /**
     * Obtiene la descripción a detalle del cobro
     * 
     * @return la descripción del resultado del cobro
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Representación textual del resultado del cobro
     * 
     * @return una cadena con la información del cobro
     */
    @Override
    public String toString() {
        return String.format("ResultadoCobro[usuario=%s, servicio=%s, monto=%.2f, exitoso=%s, descripcion='%s']",
                usuario.obtenerNombre(), servicio, monto, exitoso, descripcion);
    }
}
