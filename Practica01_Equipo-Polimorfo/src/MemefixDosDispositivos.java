/**
 * Estrategia de cobro para Memeflix con plan de dos dispositivos.
 * 
 * Esta implementación maneja el plan intermedio de Memeflix que permite
 * streaming simultáneo en dos dispositivos con una tarifa fija mensual de $170.
 * 
 * @author Equipo Polimórfo
 * @version 1.0
 */
public class MemefixDosDispositivos implements EstrategiaCobro {
    
    /**
     * Constante que define el costo mensual del plan de dos dispositivos.
     */
    private static final double COSTO_MENSUAL = 170.0;
    
    /**
     * Calcula el costo mensual para el plan de Memeflix de dos dispositivos.
     * 
     * @param mesesContratados número total de meses contratados (no afecta el precio)
     * @return el costo fijo mensual de $170.00
     */
    @Override
    public double calcularCosto(int mesesContratados) {
        return COSTO_MENSUAL;
    }
    
    /**
     * Obtiene la descripción del plan de suscripción.
     * 
     * @return descripción del plan "Memeflix para 2 dispositivos"
     */
    @Override
    public String obtenerDescripcionPlan() {
        return "Memeflix para 2 dispositivos";
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