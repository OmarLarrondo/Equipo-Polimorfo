package patrones.strategy.colision;

import mvc.modelo.entidades.ObjetoJuego;

public class EstrategiaColisionPelotaPared implements EstrategiaColision{

    @Override
    public void manejarColision(ObjetoJuego obj1, ObjetoJuego obj2) {
        // aqui va su codigo 
    }

    @Override
    public boolean verificarColision(ObjetoJuego obj1, ObjetoJuego obj2) {
        // aqui va su codigo
        return false;
    }
    
}
