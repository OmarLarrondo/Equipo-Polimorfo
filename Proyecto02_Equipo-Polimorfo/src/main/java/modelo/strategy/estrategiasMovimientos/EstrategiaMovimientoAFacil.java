package modelo.strategy.estrategiasMovimientos;

import modelo.Direccion;
import modelo.Paleta;
import modelo.Pelota;

public class EstrategiaMovimientoAFacil implements EstrategiaMovimiento {

    private double retrasoReaccion;

    
    public EstrategiaMovimientoAFacil(double retrasoReaccion) {
        this.retrasoReaccion = retrasoReaccion;
    }

    @Override
    public Direccion calcularMovimiento(Paleta paleta, Pelota pelota, double tiempoDelta) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'calcularMovimiento'");
    }
    
}
