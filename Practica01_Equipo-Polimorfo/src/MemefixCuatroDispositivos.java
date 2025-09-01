/**
 * Estrategia de cobro para Memeflix con plan de cuatro dispositivos.
 * 
 * Esta implementación maneja el plan premium de Memeflix que permite
 * streaming simultáneo en cuatro dispositivos con una tarifa fija mensual de $200.
 * 
 * @author Equipo Polimórfo
 * @version 1.0
 */
public class MemefixCuatroDispositivos implements EstrategiaCobro {
    
    /**
     * Constante que define el costo mensual del plan de cuatro dispositivos.
     */
    private static final double COSTO_MENSUAL = 200.0;
    
    /**
     * Calcula el costo mensual para el plan de Memeflix de cuatro dispositivos.
     * 
     * @param mesesContratados número total de meses contratados (no afecta el precio)
     * @return el costo fijo mensual de $200.00
     */
    @Override
    public double calcularCosto(int mesesContratados) {
        return COSTO_MENSUAL;
    }
    
    /**
     * Obtiene la descripción del plan de suscripción.
     * 
     * @return descripción del plan "Memeflix para 4 dispositivos"
     */
    @Override
    public String obtenerDescripcionPlan() {
        return "Memeflix para 4 dispositivos";
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