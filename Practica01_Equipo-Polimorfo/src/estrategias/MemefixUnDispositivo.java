package estrategias;

import patrones.EstrategiaCobro;

/**
 * Estrategia de cobro para Memeflix con plan de un solo dispositivo.
 * 
 * Esta implementación maneja el plan básico de Memeflix que permite
 * streaming en un único dispositivo con una tarifa fija mensual de $120.
 * 
 * @author Equipo Polimórfo
 * @version 1.0
 */
public class MemefixUnDispositivo implements EstrategiaCobro {
    
    /**
     * Constante que define el costo mensual del plan de un dispositivo.
     */
    private static final double COSTO_MENSUAL = 120.0;
    
    /**
     * Calcula el costo mensual para el plan de Memeflix de un dispositivo.
     * 
     * @param mesesContratados número total de meses contratados (no afecta el precio)
     * @return el costo fijo mensual de $120.00
     */
    @Override
    public double calcularCosto(int mesesContratados) {
        return COSTO_MENSUAL;
    }
    
    /**
     * Obtiene la descripción del plan de suscripción.
     * 
     * @return descripción del plan "1 dispositivo"
     */
    @Override
    public String obtenerDescripcionPlan() {
        return "1 dispositivo";
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