/**
 * Interfaz que define el patrón Strategy para el cálculo de costos de servicios de streaming.
 * 
 * Esta interfaz encapsula los diferentes algoritmos de cobro que utilizan los servicios
 * de streaming (Memeflix, Momazon Prime Video, Spootify, Thisney+, HVO Max). Cada 
 * implementación concreta maneja la lógica específica de tarifas de su servicio,
 * incluyendo tarifas variables por tiempo, períodos gratuitos, y diferentes planes.
 * 
 * El patrón Strategy permite intercambiar dinámicamente los algoritmos de cobro sin
 * afectar al contexto que los utiliza (clase Servicio), cumpliendo con el principio
 * abierto/cerrado y facilitando la extensión del sistema.
 * 
 * @author Equipo Polimórfo
 * @version 1.0
 */
public interface EstrategiaCobro {
    
    /**
     * Calcula el costo mensual del servicio basado en los meses contratados.
     * 
     * Este método permite manejar tarifas variables según el tiempo de suscripción.
     * Algunos servicios como Thisney+ y HVO Max cambian sus tarifas después de
     * cierto período (ej. gratis los primeros 3 meses, luego tarifa completa).
     * 
     * @param mesesContratados número total de meses que el usuario ha estado
     *                        suscrito al servicio (incluyendo períodos anteriores
     *                        si hubo cancelaciones y renovaciones)
     * @return el costo mensual en pesos mexicanos para el mes actual
     */
    double calcularCosto(int mesesContratados);
    
    /**
     * Obtiene una descripción textual del plan de suscripción.
     * 
     * Esta descripción se utiliza para generar mensajes informativos al usuario
     * sobre el plan que está pagando (ej. "Memeflix para 4 dispositivos",
     * "Momazon versión premium").
     * 
     * @return descripción textual del plan de suscripción
     */
    String obtenerDescripcionPlan();
    
    /**
     * Determina si el servicio es gratuito para el número de meses especificado.
     * 
     * Algunos servicios ofrecen períodos gratuitos iniciales (HVO Max, Thisney+)
     * o versiones completamente gratuitas (Spootify versión normal). Este método
     * permite identificar cuando no se debe realizar un cobro.
     * 
     * @param mesesContratados número total de meses que el usuario ha estado
     *                        suscrito al servicio
     * @return true si el servicio es gratuito para el período especificado,
     *         false si se debe realizar un cobro
     */
    boolean esGratis(int mesesContratados);
}