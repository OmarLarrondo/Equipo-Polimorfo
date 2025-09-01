package estrategias;

import patrones.EstrategiaCobro;

/**
 * Estrategia de cobro para Thisney+ con tarifas escalonadas.
 * 
 * Esta implementación maneja el sistema de precios de Thisney+ que ofrece
 * una tarifa promocional de $130 para los primeros 3 meses, después de los
 * cuales la tarifa aumenta a $160 mensual.
 * 
 * @author Equipo Polimórfo
 * @version 1.0
 */
public class ThisneyPlan implements EstrategiaCobro {
    
    /**
     * Constante que define el costo mensual promocional para los primeros 3 meses.
     */
    private static final double COSTO_PROMOCIONAL = 130.0;
    
    /**
     * Constante que define el costo mensual regular a partir del cuarto mes.
     */
    private static final double COSTO_REGULAR = 160.0;
    
    /**
     * Número de meses que duran la tarifa promocional.
     */
    private static final int MESES_PROMOCIONALES = 3;
    
    /**
     * Calcula el costo mensual basado en el total de meses contratados.
     * 
     * Los primeros 3 meses tienen una tarifa promocional de $130,
     * a partir del cuarto mes la tarifa aumenta a $160.
     * 
     * @param mesesContratados número total de meses que el usuario ha estado
     *                        suscrito al servicio
     * @return $130.00 para los primeros 3 meses, $160.00 a partir del cuarto mes
     */
    @Override
    public double calcularCosto(int mesesContratados) {
        if (mesesContratados <= MESES_PROMOCIONALES) {
            return COSTO_PROMOCIONAL;
        } else {
            return COSTO_REGULAR;
        }
    }
    
    /**
     * Obtiene la descripción del plan de suscripción.
     * 
     * @return descripción del plan "Thisney+"
     */
    @Override
    public String obtenerDescripcionPlan() {
        return "Thisney+";
    }
    
    /**
     * Determina si el plan es gratuito para el período especificado.
     * 
     * @param mesesContratados número de meses contratados
     * @return false ya que este plan siempre tiene costo
     */
    @Override
    public boolean esGratis(int mesesContratados) {
        return false;
    }
}