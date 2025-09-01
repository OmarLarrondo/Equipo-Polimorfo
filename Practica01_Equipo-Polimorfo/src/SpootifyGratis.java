/**
 * Estrategia de cobro para Spootify en su versión gratuita.
 * 
 * Esta implementación maneja el plan gratuito de Spootify que no tiene
 * ningún costo asociado, permitiendo acceso básico al servicio sin pagos.
 * 
 * @author Equipo Polimórfo
 * @version 1.0
 */
public class SpootifyGratis implements EstrategiaCobro {
    
    /**
     * Calcula el costo mensual para el plan gratuito de Spootify.
     * 
     * @param mesesContratados número total de meses contratados (no aplica)
     * @return siempre retorna 0.0 ya que es un servicio gratuito
     */
    @Override
    public double calcularCosto(int mesesContratados) {
        return 0.0;
    }
    
    /**
     * Obtiene la descripción del plan de suscripción.
     * 
     * @return descripción del plan "Spootify versión gratuita"
     */
    @Override
    public String obtenerDescripcionPlan() {
        return "Spootify versión gratuita";
    }
    
    /**
     * Determina si el plan es gratuito para el período especificado.
     * 
     * @param mesesContratados número de meses contratados (no aplica)
     * @return siempre true ya que este plan es completamente gratuito
     */
    @Override
    public boolean esGratis(int mesesContratados) {
        return true;
    }
}