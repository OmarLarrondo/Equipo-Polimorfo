package main.java.mx.unam.ciencias.modeladoyprogramacion;

import java.util.List;
import java.util.Map;

/**
 * ESTA CLASE ABSTRACTA LA DEBEN EXTENDER LOS SERVICIOS, MEMEFLIX, SPOOTIFY ETC.
 * 
 * 
 */

public abstract class Servicio {

    /**Nombre del servicio */
    protected String nombre;
    /**Lista de observadores */
    protected List<Observer> observers;
    /**Recomendaciones por mes */
    protected Map<Integer, List<String>> recomendacionesPorMes;

    /**
     * Constructor para inicializar los atributos de Servicio... \n
     * observers y recomendacionesPorMes se inicializarán en la implementación
     * @param nombre
     */
    public Servicio(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Agrega un observer al servicio
     * lo debe override un servicio
     * @param observer
     */
    public abstract void agregarObserver(Observer observer);

    /**
     * Remueve un observador del servicio
     * lo debe override un servicio
     * @param observer
     */
    public abstract void removerObserver(Observer observer);

    /**
     * Notifica a un Usuario los meses de Uso usando un servicio
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
     * Un servicio Procesa el cobro mensual
     * @param mesActual
     * @return
     */
    public abstract List<String> procesarCobroMensual(int mesActual);

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
     * @return
     */
    public abstract String obtenerNombre();
}
