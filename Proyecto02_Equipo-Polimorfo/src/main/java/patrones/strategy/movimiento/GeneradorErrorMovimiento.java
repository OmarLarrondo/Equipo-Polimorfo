package patrones.strategy.movimiento;

import java.util.Random;

public class GeneradorErrorMovimiento {
    //cuanto puede desviarse la posicion
    private double amplitudError;
    //gen num ale
    


    public GeneradorErrorMovimiento(double amplitudError) {
        if(amplitudError > 40 || amplitudError<0){
            throw new IllegalArgumentException("La amplitud del error debe estar entre (-40.00,+40.00)");
        }
        this.amplitudError = amplitudError;
    }




    /**Devuelve un desplazamiento aleatorio 
     * (positivo o negativo) dentro del rango [-amplitudError, +amplitudError].
     * @return
     */
    public double generarError(){

        double aleatorio = (Math.random()*2 -1) *amplitudError;
        //aleatorio ∈ [-amplitud,+aplitud]
        return aleatorio;

    }
}
