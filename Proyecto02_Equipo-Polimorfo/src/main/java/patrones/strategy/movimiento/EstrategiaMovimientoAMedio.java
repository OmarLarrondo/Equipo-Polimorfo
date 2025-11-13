package patrones.strategy.movimiento;

import mvc.modelo.enums.Direccion;
import mvc.modelo.entidades.Paleta;
import mvc.modelo.entidades.Pelota;

public class EstrategiaMovimientoAMedio implements EstrategiaMovimiento {
    private double retrasoReaccion;

    private TemporizadorReaccion temporizador; 
    private CalcularTrayectoria calculador;
    private GeneradorErrorMovimiento generadorError;

    /**
     * Crea una estrategia de movimiento para una IA "media".
     * 
     * @param retrasoReaccion Tiempo en segundos que tarda la IA en reaccionar.
     * @param amplitudError   Máxima desviación vertical que la IA puede cometer al predecir el impacto.
     */
    public EstrategiaMovimientoAMedio(double retrasoReaccion, double amplitudError) {
        this.temporizador = new TemporizadorReaccion(retrasoReaccion);
        this.calculador = new CalcularTrayectoria();
        this.generadorError = new GeneradorErrorMovimiento(amplitudError);
    }

    @Override
    public Direccion calcularMovimiento(Paleta paleta, Pelota pelota, double tiempoDelta) {
        temporizador.actualizar(tiempoDelta);

        if (temporizador.puedeReaccionar()) {
            double yPredicho = calculador.predecirPosicionImpacto(pelota, paleta.obtenerX());
            double yObjetivo = yPredicho + generadorError.generarError();

            if (paleta.obtenerY() > yObjetivo) {
                return Direccion.ARRIBA;
            } else if (paleta.obtenerY() < yObjetivo) {
                return Direccion.ABAJO;
            } else {
                return Direccion.NINGUNA;
            }
        }

        return Direccion.NINGUNA;
    }      
}
