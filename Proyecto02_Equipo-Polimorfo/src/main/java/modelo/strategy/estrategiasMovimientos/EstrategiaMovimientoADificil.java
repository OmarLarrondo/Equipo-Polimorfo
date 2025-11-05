package modelo.strategy.estrategiasMovimientos;

import modelo.Direccion;
import modelo.Paleta;
import modelo.Pelota;

public class EstrategiaMovimientoADificil implements EstrategiaMovimiento {

    private boolean prediccionPerfecta;

    
    public EstrategiaMovimientoADificil(boolean prediccionPerfecta) {
        this.prediccionPerfecta = prediccionPerfecta;
    }


    @Override
    public Direccion calcularMovimiento(Paleta paleta, Pelota pelota, double tiempoDelta) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'calcularMovimiento'");
    }
        
}
