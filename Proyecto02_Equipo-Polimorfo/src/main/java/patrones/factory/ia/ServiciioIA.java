package patrones.factory.ia;

import mvc.modelo.enums.Direccion;
import patrones.strategy.movimiento.EstrategiaMovimiento;
import mvc.modelo.entidades.Paleta;
import mvc.modelo.entidades.Pelota;

public class ServiciioIA {
    private FabricaIA fabricaIA;
    private DificultadIA dificultadIA;

    public void establecerDificultad(DificultadIA dificultad){
        //aqui va su codigo
    }
    public EstrategiaMovimiento obEstrategiaMovimiento(){
        //aqui va su codigo
        return null;
    }
    public Direccion calcularSiguienteMovimiento(Paleta paleta, Pelota pelota, double tiempoDelta){
        //aqui va su codigo
        return null;
    }
    
}
