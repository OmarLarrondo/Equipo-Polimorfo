package modelo.gestion_ia.patron_strategy.estrategiasMovimientos;

import modelo.Direccion;
import modelo.nucleo_juego.patron_composite.Paleta;
import modelo.nucleo_juego.patron_composite.Pelota;

public class EstrategiaMovimientoAMedio implements EstrategiaMovimiento {

    private double factorPrediccion;

    
    public EstrategiaMovimientoAMedio(double factorPrediccion) {
        this.factorPrediccion = factorPrediccion;
    }


    @Override
    public Direccion calcularMovimiento(Paleta paleta, Pelota pelota, double tiempoDelta) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'calcularMovimiento'");
    }
    
}
