package patrones.strategy.movimiento;

public class TemporizadorReaccion {
    /** Cuánto tiempo ha pasado desde la última vez que la IA reaccionó*/
    private double tiempoAcumulado;

    /** Cuánto tiempo debe pasar antes de que la IA pueda reaccionar otra vez.*/
    private double intervaloReaccion;

    public TemporizadorReaccion(double tiempoAcumulado, double intervaloReaccion) {
        this.tiempoAcumulado = tiempoAcumulado;
        this.intervaloReaccion = intervaloReaccion;
    }

    /**
     * ncrementa tiempoAcumulado con el tiempo transcurrido.
     * @param tiempoDelta
     */
    public void actualizar(double tiempoDelta){
        tiempoAcumulado += tiempoDelta; 
    }



    /**
     * Reinicia el contador después de que la IA haya reaccionado.
     * @return {@code true} si {@link tiempoAcumulado} >= intervaloReaccion.
     *      {@code false} en otro caso.
     */
    public boolean puedeReaccionar(){
        if(tiempoAcumulado > intervaloReaccion){
            tiempoAcumulado = 0;
            return true;
        }else {
            return false;
        }
    }
    
}
