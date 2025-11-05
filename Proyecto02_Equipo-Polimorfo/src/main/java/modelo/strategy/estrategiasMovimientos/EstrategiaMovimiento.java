package modelo.strategy.estrategiasMovimientos;

import modelo.Direccion;
import modelo.Paleta;
import modelo.Pelota;

public interface EstrategiaMovimiento {
    public Direccion calcularMovimiento(Paleta paleta, Pelota pelota, double tiempoDelta);
}
