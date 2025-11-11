package patrones.strategy.colision;

import mvc.modelo.entidades.Bloque;
import mvc.modelo.entidades.ObjetoJuego;

public class EstrategiaColisionPelotaBloque implements EstrategiaColision{

    @Override
    public void manejarColision(ObjetoJuego obj1, ObjetoJuego obj2) {
        // aqui va su codigo  
    }

    @Override
    public boolean verificarColision(ObjetoJuego obj1, ObjetoJuego obj2) {
        // aqui va su codigo
        return false;
    }
    private void generarItem(Bloque bloque){
        //aqui va su codigo 
    }
}
