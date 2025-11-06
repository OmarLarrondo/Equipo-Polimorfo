package modelo.nucleo_juego.patron_strategy_colision.estrategias;

import modelo.nucleo_juego.patron_composite.ObjetoJuego;

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
