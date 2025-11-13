package patrones.strategy.movimiento;

import mvc.modelo.enums.Direccion;
import mvc.modelo.entidades.Paleta;
import mvc.modelo.entidades.Pelota;

// La IA deberá predecir esto, pero lento y comete errores
public class EstrategiaMovimientoADicifil implements EstrategiaMovimiento {

    private double retrasoReaccion;
    
    private TemporizadorReaccion temporizador;
    private CalcularTrayectoria calculador;
    private GeneradorErrorMovimiento generadorError;

    /**
     * Crea una estrategia de movimiento para una IA "fácil".
     *
     * @param retrasoReaccion Tiempo en segundos que tarda la IA en reaccionar.
     * @param amplitudError    Máxima desviación vertical que la IA puede cometer al predecir el impacto.
     */
    public EstrategiaMovimientoADicifil(double retrasoReaccion, double amplitudError) {
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
        } else {
            return Direccion.NINGUNA;
        }
    }
}
