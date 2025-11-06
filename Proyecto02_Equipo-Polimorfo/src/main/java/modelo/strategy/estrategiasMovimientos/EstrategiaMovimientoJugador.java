package modelo.strategy.estrategiasMovimientos;

import modelo.Adapter.AdaptadorEntrada;
import modelo.nucleo_juego.patron_composite.Paleta;
import modelo.nucleo_juego.patron_composite.Pelota;
import modelo.Direccion;

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
