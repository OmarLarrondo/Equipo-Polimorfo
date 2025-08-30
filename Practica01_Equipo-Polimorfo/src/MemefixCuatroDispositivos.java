public class MemefixCuatroDispositivos implements EstrategiaCobro {
    
    @Override
    public double calcularCosto(int mesesContratados) {
        return 0.0;
    }
    
    @Override
    public String obtenerDescripcionPlan() {
        return "";
    }
    
    @Override
    public boolean esGratis(int mesesContratados) {
        return false;
    }
}