package main.java.modelo.strategy.estrategiasMovimientos;

public interface EstrategiaMovimiento {
    public Direccion calcularMovimiento(Paleta paleta, Pelota pelota, double tiempoDelta);
}
