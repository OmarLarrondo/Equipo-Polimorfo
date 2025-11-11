package patrones.strategy.movimiento;

import mvc.modelo.enums.Direccion;
import mvc.modelo.entidades.Paleta;
import mvc.modelo.entidades.Pelota;

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
