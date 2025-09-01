/**
 * Estrategia de cobro para HVO Max con período gratuito inicial.
 * 
 * Esta implementación maneja el sistema de precios de HVO Max que ofrece
 * un período completamente gratuito por los primeros 3 meses, después de
 * los cuales comienza a cobrar una tarifa de $140 mensual.
 * 
 * @author Equipo Polimórfo
 * @version 1.0
 */
public class HVOMaxPlan implements EstrategiaCobro {
    
    /**
     * Constante que define el costo mensual regular a partir del cuarto mes.
     */
    private static final double COSTO_REGULAR = 140.0;
    
    /**
     * Número de meses gratuitos iniciales.
     */
    private static final int MESES_GRATUITOS = 3;
    
    /**
     * Calcula el costo mensual basado en el total de meses contratados.
     * 
     * Los primeros 3 meses son completamente gratuitos,
     * a partir del cuarto mes la tarifa es de $140.
     * 
     * @param mesesContratados número total de meses que el usuario ha estado
     *                        suscrito al servicio
     * @return 0.0 para los primeros 3 meses, $140.00 a partir del cuarto mes
     */
    @Override
    public double calcularCosto(int mesesContratados) {
        if (mesesContratados <= MESES_GRATUITOS) {
            return 0.0;
        } else {
            return COSTO_REGULAR;
        }
    }
    
    /**
     * Obtiene la descripción del plan de suscripción.
     * 
     * @return descripción del plan "HVO Max"
     */
    @Override
    public String obtenerDescripcionPlan() {
        return "HVO Max";
    }
    
    /**
     * Determina si el plan es gratuito para el período especificado.
     * 
     * @param mesesContratados número de meses contratados
     * @return true para los primeros 3 meses, false a partir del cuarto mes
     */
    @Override
    public boolean esGratis(int mesesContratados) {
        return mesesContratados <= MESES_GRATUITOS;
    }
}