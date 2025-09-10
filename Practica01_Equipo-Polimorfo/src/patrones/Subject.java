package patrones;

/**
 * Interfaz que define un sujeto (Subject) en el patrón Observer.
 * Un sujeto mantiene una lista de observadores y notifica cambios a estos
 * a través de un sistema de mensajes genérico.
 * Los observadores implementan la interfaz {@link Observer}.
 * 
 * Esta versión simplificada del patrón Observer utiliza un único método
 * de notificación genérico que permite mayor flexibilidad en los tipos
 * de mensajes que se pueden enviar a los observadores.
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
    public void removerObserver(Observer observer);

    /**
     * Notifica a un observador específico enviándole un mensaje.
     * Este método implementa el patrón Observer de forma genérica,
     * permitiendo enviar cualquier tipo de notificación a través
     * de mensajes de texto estructurados.
     * 
     * @param observer El observador que recibirá la notificación.
     * @param mensaje El mensaje a enviar al observador.
     */
    public void notificarObserver(Observer observer, String mensaje);
}
