package servicios;

import java.util.List;
import java.util.Map;

import patrones.Observer;
import patrones.Subject;
import patrones.EstrategiaCobro;
import modelo.Usuario;
import modelo.HistorialServicio;
import modelo.ResultadoCobro;



public abstract class Servicio implements Subject {

    //EN EL DIAHGRAMA DE CLASES LOS ATRIBUTOS SON PRIVADOS, NO PROTECT CHECAR

    /**Nombre del servicio */
    protected String nombre;
    /**Lista de observadores */
    protected List<Observer> observers;
    /**Recomendaciones por mes<MES,RECOMENDACIONES> */
    protected Map<Integer, List<String>> recomendacionesPorMes;
    /**Historial de los usuarios del servicio */
    protected Map<Usuario, HistorialServicio> historialesUsuarios;

    /**
     * Constructor para inicializar los atributos de Servicio... <code> \n </code>
     * observers y recomendacionesPorMes se inicializarán en la implementación
     * 
     * @param nombre nombre de servicio a inicializar
     */
    public Servicio(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Agrega un observer al servicio
     * lo debe override un servicio
     * 
     * @param observer observador que se agregara a servicio
     * @param estrategia estrategia de cobro asociada con la suscripción
     */
    public void agregarObserver(Observer observer, EstrategiaCobro estrategia) {
        if (observer != null && !observers.contains(observer)) {
            observers.add(observer);
        } else {
            System.out.println(String.format("No se pudo agregar al observador: %s", observer));
        }
    }

    /**
     * Remueve un observador del servicio
     * lo debe override un servicio
     * @param observer observador que se removera de servicio
     */
    public abstract void removerObserver(Observer observer);

    /**
     * Notifica a un Usuario los meses de Uso usando un servicio
     * 
     */
    public abstract void notificarMesesUso();

    /**
     * Notifica las recomendaciones a un Usuario de un servicio
     * @param mes
     */
    public abstract void notificarRecomendacion(int mes);

    /**
     * Notifica la bienvenida al Usuario de un servicio
     * @param observer
     * @param esRenovacion
     */
    public abstract void notificarBienvenida(Observer observer, boolean esRenovacion);

    /**
     * Notifica la despedida a un Usuario de un Servicio
     * @param observer
     */
    public abstract void notificarDespedida(Observer observer);

    /**
     * Metodo para procesar el cobro mensual de un servicio.   
     *  
     * Este método está implementado en la clase abstracta para proporcionar
     * una funcionalidad común que todas las subclases pueden usar directamente.
     * Las subclases pueden sobrescribir este método si requieren un comportamiento
     * diferente.
     * 
     * @param mesActual mes actual, para procesar el cobro
     * @return
     */
    public List<String> procesarCobroMensual(int mesActual){
        //Aqui va su codigo,
        // No es abstracto porque todos hacen lo mismo
        return null;
    }
    
    /**
     * Metodo para Susccribir un usuario al Servicio.
     * 
     * Este método está implementado en la clase abstracta para proporcionar
     * una funcionalidad común que todas las subclases pueden usar directamente.
     * Las subclases pueden sobrescribir este método si requieren un comportamiento
     * diferente.

     * @param usuario
     * @param estrategia
     * @return
     */
    public boolean suscribirUsuario(Usuario usuario, EstrategiaCobro estrategia){
        return false;
    }

    /**
     *  Metodo para cancelar la Suscripcion de un usuario, ya sea por que asi lo quiso o no tiene dinero.
     *  
     * Este método está implementado en la clase abstracta para proporcionar
     * una funcionalidad común que todas las subclases pueden usar directamente.
     * Las subclases pueden sobrescribir este método si requieren un comportamiento
     * diferente.
     * @param usuario
     * @return
     */
    public boolean cancelarSuscripcionUsuario(Usuario usuario){
        return false;
    }

    /**
     * Metodo para cambiar el plan actual del Usuario
     * 
     * Este método está implementado en la clase abstracta para proporcionar
     * una funcionalidad común que todas las subclases pueden usar directamente.
     * Las subclases pueden sobrescribir este método si requieren un comportamiento
     * diferente.
     * 
     * @param usuario
     * @param estrategia
     * @return
     */
    public boolean cambiarPlanUsuario(Usuario usuario, EstrategiaCobro estrategia){
        return false;
    }

    /**
     * Obtiene el historial del usuario con un plan. 
     * 
     * Este método está implementado en la clase abstracta para proporcionar
     * una funcionalidad común que todas las subclases pueden usar directamente.
     * Las subclases pueden sobrescribir este método si requieren un comportamiento
     * diferente.
     * @param usuario
     * @param estrategia
     * @return
     */
    public HistorialServicio obtenerHistorialUsuario(Usuario usuario, EstrategiaCobro estrategia){
        return null;
    }

    /**
     *  Crea el historial para un Usuario. 
     * 
     * Este método está implementado en la clase abstracta para proporcionar
     * una funcionalidad común que todas las subclases pueden usar directamente.
     * Las subclases pueden sobrescribir este método si requieren un comportamiento
     * diferente.
     * @param usuario
     * @return
     */
    public HistorialServicio crearHistorialUsuarios(Usuario usuario){
        return null;
    }

    /**
     * Actualiza el historial de un Usuario con una estrategia.
     * @param usuario
     * @param estratehia
     */
    public abstract void actualizarHistorialUsuario(Usuario usuario, EstrategiaCobro estratehia);

    /**
     * Verifica si un usuario tiene una suscripcion de un servicio activa
     * 
     * Este método está implementado en la clase abstracta para proporcionar
     * una funcionalidad común que todas las subclases pueden usar directamente.
     * Las subclases pueden sobrescribir este método si requieren un comportamiento
     * diferente.
     * @param usuario
     * @return
     */
    public boolean tieneSuscripcionesActiva(Usuario usuario){
        return false;
    } 

    /*
     * Obtiene las estrategias disponibles
     */
    public abstract List<String> obtenerEstrategiasDisponibles();

    /*
     * Obtiene las recomendaciones de un mes, para un Usuario
     */
    public abstract String obtenerRecomendacion(int mes);
    

    /**Inicializa las recomendaciones */
    public abstract void inicializarRecomendaciones();

    /**
     * Obtiene el nombre
     * @return el nombre del Servicio
     */
    public abstract String obtenerNombreServicio();
}
