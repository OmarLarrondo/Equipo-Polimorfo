package estrategias;

import patrones.EstrategiaCobro;

/**
 * Estrategia de cobro para Momazon Prime Video en su versión premium.
 * 
 * Esta implementación maneja el plan premium de Momazon Prime Video con
 * una tarifa fija mensual de $150, que incluye beneficios adicionales.
 * 
 * @author Equipo Polimórfo
 * @version 1.0
 */
public class MomazonPremium implements EstrategiaCobro {
    
    /**
     * Constante que define el costo mensual del plan premium de Momazon.
     */
    private static final double COSTO_MENSUAL = 150.0;
    
    /**
     * Calcula el costo mensual para el plan premium de Momazon Prime Video.
     * 
     * @param mesesContratados número total de meses contratados (no afecta el precio)
     * @return el costo fijo mensual de $150.00
     */
    @Override
    public double calcularCosto(int mesesContratados) {
        return COSTO_MENSUAL;
    }
    
    /**
     * Obtiene la descripción del plan de suscripción.
     * 
     * @return descripción del plan "Momazon versión premium"
     */
    @Override
    public String obtenerDescripcionPlan() {
        return "Momazon versión premium";
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