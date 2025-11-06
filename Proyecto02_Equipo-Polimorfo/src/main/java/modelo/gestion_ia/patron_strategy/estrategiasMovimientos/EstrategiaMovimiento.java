package modelo.gestion_ia.patron_strategy.estrategiasMovimientos;

import modelo.Direccion;
import modelo.nucleo_juego.patron_composite.Paleta;
import modelo.nucleo_juego.patron_composite.Pelota;

public interface EstrategiaMovimiento {
    public Direccion calcularMovimiento(Paleta paleta, Pelota pelota, double tiempoDelta);
}
