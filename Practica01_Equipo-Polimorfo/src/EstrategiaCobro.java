public interface EstrategiaCobro {
    double calcularCosto(int mesesContratados);
    String obtenerDescripcionPlan();
    boolean esGratis(int mesesContratados);
}