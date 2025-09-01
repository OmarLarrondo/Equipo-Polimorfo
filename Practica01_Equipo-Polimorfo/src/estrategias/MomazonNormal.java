package estrategias;

import patrones.EstrategiaCobro;

/**
 * Estrategia de cobro para Momazon Prime Video en su versión normal.
 * 
 * Esta implementación maneja el plan básico de Momazon Prime Video con
 * una tarifa fija mensual de $110.
 * 
 * @author Equipo Polimórfo
 * @version 1.0
 */
public class MomazonNormal implements EstrategiaCobro {
    
    /**
     * Constante que define el costo mensual del plan normal de Momazon.
     */
    private static final double COSTO_MENSUAL = 110.0;
    
    /**
     * Calcula el costo mensual para el plan normal de Momazon Prime Video.
     * 
     * @param mesesContratados número total de meses contratados (no afecta el precio)
     * @return el costo fijo mensual de $110.00
     */
    @Override
    public double calcularCosto(int mesesContratados) {
        return COSTO_MENSUAL;
    }
    
    /**
     * Obtiene la descripción del plan de suscripción.
     * 
     * @return descripción del plan "Momazon versión normal"
     */
    @Override
    public String obtenerDescripcionPlan() {
        return "Momazon versión normal";
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