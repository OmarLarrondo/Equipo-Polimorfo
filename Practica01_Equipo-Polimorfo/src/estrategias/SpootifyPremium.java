package estrategias;

import patrones.EstrategiaCobro;

/**
 * Estrategia de cobro para Spootify en su versión premium.
 * 
 * Esta implementación maneja el plan premium de Spootify que incluye
 * funcionalidades adicionales como música sin anuncios y calidad mejorada
 * con una tarifa fija mensual de $80.
 * 
 * @author Equipo Polimórfo
 * @version 1.0
 */
public class SpootifyPremium implements EstrategiaCobro {
    
    /**
     * Constante que define el costo mensual del plan premium de Spootify.
     */
    private static final double COSTO_MENSUAL = 80.0;
    
    /**
     * Calcula el costo mensual para el plan premium de Spootify.
     * 
     * @param mesesContratados número total de meses contratados (no afecta el precio)
     * @return el costo fijo mensual de $80.00
     */
    @Override
    public double calcularCosto(int mesesContratados) {
        return COSTO_MENSUAL;
    }
    
    /**
     * Obtiene la descripción del plan de suscripción.
     * 
     * @return descripción del plan "premium"
     */
    @Override
    public String obtenerDescripcionPlan() {
        return "premium";
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