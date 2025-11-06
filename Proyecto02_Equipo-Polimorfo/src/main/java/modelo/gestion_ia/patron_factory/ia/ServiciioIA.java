package modelo.gestion_ia.patron_factory.ia;

import modelo.Direccion;
import modelo.gestion_ia.patron_strategy.estrategiasMovimientos.EstrategiaMovimiento;
import modelo.nucleo_juego.patron_composite.Paleta;
import modelo.nucleo_juego.patron_composite.Pelota;

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
