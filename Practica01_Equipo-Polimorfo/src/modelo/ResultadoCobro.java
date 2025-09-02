package modelo;


import servicios.Servicio;


public class ResultadoCobro {
    
    /**EL usuario al que se cobrara */
    private Usuario usuario;

    /**EL servicio que realizara el cobro */
    private String servicio;

    /**EL monto que se cobrara al usuario */
    private double monto;

    /**Resultado del cobro*/
    private boolean exitoso;

    /**La descripcion del cobro realizado */
    private String descripcion;

    public ResultadoCobro(Usuario usuario, String servicio
    , double monto, boolean exitoso, String descripcion) {
        this.usuario = usuario;
        this.servicio = servicio;
        this.monto = monto;
        this.exitoso = exitoso;
        this.descripcion = descripcion;
    }


    //metodos getters


    /**
     * Obtiene el usuario al que se le realizó el cobro.
     *
     * @return la instancia de {@link Usuario} correspondiente al cobro
     */ 
    public Usuario getUsuario() {
        return usuario;
    }

    /**
     * Obtiene el servicio que realizo el cobro
     * 
     * @return la instancia de {@link Servicio} correspondiente al cobro
     */
    public String getServicio() {
        return servicio;
    }

    /**
     * Obtiene el monto que se realizo
     * 
     * @return el monto cobrado
     */

    public double getMonto() {
        return monto;
    }

    /**
     * Indica si el cobro se realizo
     * 
     * @return {@code true} si el cobro se realizo correctamente 
     *          {@code false} en caso contrario.
     */
    public boolean getExitoso() {
        return exitoso;
    }

    /**
     * Obtiene la descripcion a detalle dle cobro
     * 
     * @return la descripcion del resultado del cobro
     */

    public String getDescripcion() {
        return descripcion;
    }


    //metodos setters


    /**
     * Establece el usuario al que se le realizó el cobro.
     *
     * @param usuario la instancia de {@link Usuario} asociada al cobro
     */
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    /**
     * Establece el nombre del servicio por el cual se realiza el cobro.
     *
     * @param servicio el nombre del servicio
     */
    public void setServicio(String servicio) {
        this.servicio = servicio;
    }

    /**
     * Establece el monto del cobro.
     *
     * @param monto la cantidad a cobrar
     */
    public void setMonto(double monto) {
        this.monto = monto;
    }

    /**
     * Establece si el cobro fue exitoso o fallido.
     *
     * @param exitoso true si el cobro fue exitoso, false en caso contrario
     */
    public void setExitoso(boolean exitoso) {
        this.exitoso = exitoso;
    }

    /**
     * Establece la descripción o detalle del resultado del cobro.
     *
     * @param descripcion el texto descriptivo del resultado del cobro
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }    
}