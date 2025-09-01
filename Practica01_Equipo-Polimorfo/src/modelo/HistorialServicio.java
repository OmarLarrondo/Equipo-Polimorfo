package modelo;

import patrones.EstrategiaCobro;

/**
 * Clase que representa el historial de suscripción de un usuario a un servicio específico.
 * Mantiene el estado completo de la relación usuario-servicio, incluyendo los meses totales
 * contratados, el estado actual de la suscripción y la estrategia de cobro activa.
 * 
 * Esta clase es fundamental para cumplir con el requerimiento de que los meses contratados
 * se mantengan incluso después de cancelaciones, evitando que usuarios puedan aprovecharse
 * de cancelaciones para obtener descuentos repetidos en servicios con períodos gratuitos.
 * 
 * @author Equipo Polimórfo
 * @version 1.0
 */
public class HistorialServicio {
    
    /** Nombre del servicio asociado a este historial */
    private String nombreServicio;
    
    /** 
     * Contador acumulativo de meses contratados con el servicio.
     * Este valor nunca se resetea, incluso si el usuario cancela y renueva su suscripción.
     * Es crucial para servicios con períodos gratuitos limitados como Thisney+ y HVO Max.
     */
    private int totalMesesContratados;
    
    /** 
     * Indica si la suscripción está actualmente activa.
     * false significa que el usuario canceló pero mantiene su historial de meses.
     */
    private boolean suscripcionActiva;
    
    /** 
     * Estrategia de cobro actual asociada con la suscripción.
     * Puede ser null si la suscripción está inactiva.
     */
    private EstrategiaCobro estrategiaActual;
    
    /** 
     * Mes en el que se realizó el último pago exitoso.
     * Utilizado para control de flujo temporal en la simulación.
     */
    private int fechaUltimoPago;
    
    /**
     * Constructor que inicializa un nuevo historial para un servicio específico.
     * El historial se crea en estado inactivo sin estrategia asignada.
     * 
     * @param nombreServicio El nombre del servicio para el cual se crea el historial
     * @throws IllegalArgumentException si el nombre del servicio es null o vacío
     */
    public HistorialServicio(String nombreServicio) {
        if (nombreServicio == null || nombreServicio.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del servicio no puede ser null o vacío");
        }
        
        this.nombreServicio = nombreServicio.trim();
        this.totalMesesContratados = 0;
        this.suscripcionActiva = false;
        this.estrategiaActual = null;
        this.fechaUltimoPago = 0;
    }
    
    /**
     * Activa la suscripción con una estrategia de cobro específica.
     * Si la suscripción ya estaba activa, actualiza la estrategia sin afectar los meses acumulados.
     * Si es una nueva activación después de cancelación, preserva el historial de meses previos.
     * 
     * @param estrategia La estrategia de cobro a utilizar para esta suscripción
     * @throws IllegalArgumentException si la estrategia es null
     */
    public void activar(EstrategiaCobro estrategia) {
        if (estrategia == null) {
            throw new IllegalArgumentException("La estrategia de cobro no puede ser null");
        }
        
        this.estrategiaActual = estrategia;
        this.suscripcionActiva = true;
    }
    
    /**
     * Desactiva la suscripción manteniendo el historial de meses contratados.
     * El usuario deja de recibir servicios pero su contador de meses se preserva
     * para futuras reactivaciones, evitando abuso de períodos gratuitos.
     */
    public void desactivar() {
        this.suscripcionActiva = false;
        this.estrategiaActual = null;
    }
    
    /**
     * Cambia la estrategia de cobro de una suscripción activa sin afectar los meses acumulados.
     * Permite que usuarios cambien entre diferentes planes del mismo servicio
     * (por ejemplo, de Momazon Normal a Momazon Premium).
     * 
     * @param nuevaEstrategia La nueva estrategia de cobro a aplicar
     * @throws IllegalStateException si la suscripción no está activa
     * @throws IllegalArgumentException si la nueva estrategia es null
     */
    public void cambiarEstrategia(EstrategiaCobro nuevaEstrategia) {
        if (!this.suscripcionActiva) {
            throw new IllegalStateException("No se puede cambiar la estrategia de una suscripción inactiva");
        }
        
        if (nuevaEstrategia == null) {
            throw new IllegalArgumentException("La nueva estrategia no puede ser null");
        }
        
        this.estrategiaActual = nuevaEstrategia;
    }
    
    /**
     * Incrementa el contador de meses contratados y actualiza la fecha del último pago.
     * Este método debe llamarse cada vez que se procesa exitosamente un pago mensual.
     * 
     * @param mesActual El mes actual en el que se procesa el pago
     * @throws IllegalStateException si la suscripción no está activa
     * @throws IllegalArgumentException si el mes actual es inválido
     */
    public void incrementarMeses(int mesActual) {
        if (!this.suscripcionActiva) {
            throw new IllegalStateException("No se puede incrementar meses de una suscripción inactiva");
        }
        
        if (mesActual < 1 || mesActual > 12) {
            throw new IllegalArgumentException("El mes actual debe estar entre 1 y 12");
        }
        
        this.totalMesesContratados++;
        this.fechaUltimoPago = mesActual;
    }
    
    /**
     * Sobrecarga del método incrementarMeses() que solo incrementa el contador
     * sin actualizar la fecha del último pago. Útil en contextos donde no se
     * requiere el control temporal específico.
     * 
     * @throws IllegalStateException si la suscripción no está activa
     */
    public void incrementarMeses() {
        if (!this.suscripcionActiva) {
            throw new IllegalStateException("No se puede incrementar meses de una suscripción inactiva");
        }
        
        this.totalMesesContratados++;
    }
    
    /**
     * Obtiene el número total de meses que el usuario ha contratado este servicio.
     * Este valor es acumulativo y nunca se resetea, incluso después de cancelaciones.
     * 
     * @return El número total de meses contratados históricos
     */
    public int obtenerTotalMeses() {
        return this.totalMesesContratados;
    }
    
    /**
     * Verifica si la suscripción está actualmente activa.
     * 
     * @return true si la suscripción está activa y el usuario recibe servicios
     */
    public boolean estaActiva() {
        return this.suscripcionActiva;
    }
    
    /**
     * Obtiene la estrategia de cobro actual de la suscripción.
     * 
     * @return La estrategia de cobro actual, o null si la suscripción está inactiva
     */
    public EstrategiaCobro obtenerEstrategia() {
        return this.estrategiaActual;
    }
    
    /**
     * Obtiene el nombre del servicio asociado a este historial.
     * 
     * @return El nombre del servicio
     */
    public String obtenerNombreServicio() {
        return this.nombreServicio;
    }
    
    /**
     * Obtiene el mes en que se realizó el último pago exitoso.
     * 
     * @return El mes del último pago (1-12), o 0 si nunca se ha procesado un pago
     */
    public int obtenerFechaUltimoPago() {
        return this.fechaUltimoPago;
    }
    
    /**
     * Representación en cadena del historial de servicio para debugging y logging.
     * 
     * @return String con información completa del historial
     */
    @Override
    public String toString() {
        return String.format("HistorialServicio{servicio='%s', meses=%d, activa=%s, estrategia=%s, ultimoPago=%d}", 
                        this.nombreServicio, 
                        this.totalMesesContratados, 
                        this.suscripcionActiva,
                        this.estrategiaActual != null ? this.estrategiaActual.obtenerDescripcionPlan() : "null",
                        this.fechaUltimoPago);
    }
}
