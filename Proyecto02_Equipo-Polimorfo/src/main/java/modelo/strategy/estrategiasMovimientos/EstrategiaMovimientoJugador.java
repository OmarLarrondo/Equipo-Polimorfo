package modelo.strategy.estrategiasMovimientos;

import modelo.Adapter.AdaptadorEntrada;
import modelo.Direccion;
import modelo.Paleta;
import modelo.Pelota;

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
