package main.java.mx.unam.ciencias.modeladoyprogramacion;

/**
 * Interfaz Observer para recibir notificaciones relacionadas con
 * los servicios que utiliza un Usuario.
 * 
 * Los métodos incluyen notificaciones de:
 *  - Meses de uso de un servicio
 *  - Recomendaciones
 *  - Bienvenida y despedida
 *  - Cobros exitosos o fallidos
 */
public interface Observer {
    /**
     * Notifica los meses usando un servicio.
     * @param servicio Nombre del servicio.
     * @param meses Número de meses de uso.
     */
    public void notificarMesesUso(String servicio, int meses);

    /**
     * Notifica las recomendaciones de un servicio
     * @param servicio Nombre del servicio
     * @param recomendaciones Recomendaciones del servicio  //creo que deberia ser una lista o no se
     * @param mes Numero de meses se uso
     */
    public void notificarRecomendacion(String servicio, String recomendaciones, int mes); // creo que las recomendaciones deberia ser una lista de Strings, o no se xd
    /**
     * Notifica una bienvenida
     * @param servicio Nmbre dle servicio
     * @param esRenovacion <code>true</code> si es renovacion.
     *         <code>false</code> en otro caso.
     */
    public void notificarBienvenida(String servicio, Boolean esRenovacion);
    /**
     * NOtifica una despedida
     * @param servicio NOmbre del servicio
     */
    public void notificarDespedida(String servicio);
    /**
     * Notifca el cobro exitoso de un servicio
     * @param servicio Nombre del servicio
     * @param monto El monto de cobro del servicio
     * @param plan Plan contratado/escogido
     */
    public void  notificarCobroExitoso(String servicio, Double monto, String plan);
    /**
     * Notifica el cobro fallido de un servicio
     * @param servicio NOmbre dle servicio
     * @param monto El monto de cobro del servicio 
     */
    public void notificarCobroFallido(String servicio, Double monto);
}
