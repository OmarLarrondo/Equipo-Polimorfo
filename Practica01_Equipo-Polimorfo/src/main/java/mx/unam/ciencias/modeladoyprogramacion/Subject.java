package main.java.mx.unam.ciencias.modeladoyprogramacion;

/**
 * Interfaz que define un sujeto (Subject) en el patrón Observer.
 * Un sujeto mantiene una lista de observadores y notifica cambios a estos.
 * Los observadores implementan la interfaz {@link Observer}.
 */
public interface Subject {
    /**
     * Agrega un observador a la lista de observadores del sujeto.
     * @param observer El observador que se desea agregar.
     */
    public void agregarObserver(Observer observer);
    
    /**
     * Remueve un observador de la lista de observadores del sujeto.
     * @param observer El observador que se desea eliminar.
     */
    public void removeObserver(Observer observer);

    /**
     * Notifica a todos los observadores los meses que han estado usando el servicio.
     * Este método se llama típicamente cada mes o en algún evento relevante.
    */
    public void notificarMesesUso(); 

    /**
     * Notifica a todos los observadores sobre las recomendaciones del servicio
     * correspondientes a un mes específico.
     * @param mes El número de mes sobre el cual se envían las recomendaciones.
     */
    public void notificarRecomendacion(int mes);

    /**
     * Envía un mensaje de bienvenida a un observador específico.
     * Puede diferenciar si es una renovación o un nuevo registro.
     * @param observer El observador al que se envía la bienvenida.
     * @param esRenovacion <code>true</code> si es renovación del servicio; <code>false</code> si es un nuevo registro.
     */
    public void notificarBienvenida(Observer observer, boolean esRenovacion);

    /**
     * Envía un mensaje de despedida a un observador específico.
     * Se llama cuando el observador deja de usar el servicio.
     * @param observer El observador al que se envía la despedida.
     */
    public void notificarDespedida(Observer observer);
}
