package patrones.strategy.movimiento;

import mvc.modelo.entidades.Paleta;
import mvc.modelo.entidades.Pelota;
import patrones.adapter.AdaptadorEntrada;
import mvc.modelo.enums.Direccion;

public class EstrategiaMovimientoJugador implements EstrategiaMovimiento {

    private AdaptadorEntrada adaptadorEntrada;


    public EstrategiaMovimientoJugador(AdaptadorEntrada adaptadorEntrada) {
        this.adaptadorEntrada = adaptadorEntrada;
    }

    @Override
    public Direccion calcularMovimiento(Paleta paleta, Pelota pelota, double tiempoDelta) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'calcularMovimiento'");
    }

    
}
