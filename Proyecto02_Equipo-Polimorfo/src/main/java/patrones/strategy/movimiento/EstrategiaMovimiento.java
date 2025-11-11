package patrones.strategy.movimiento;

import mvc.modelo.enums.Direccion;
import mvc.modelo.entidades.Paleta;
import mvc.modelo.entidades.Pelota;

public interface EstrategiaMovimiento {
    public Direccion calcularMovimiento(Paleta paleta, Pelota pelota, double tiempoDelta);
}
