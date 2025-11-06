package modelo.nucleo_juego.patron_strategy_colision.estrategias;

import modelo.nucleo_juego.patron_composite.ObjetoJuego;
import modelo.nucleo_juego.patron_composite.Paleta;
import modelo.nucleo_juego.patron_composite.Pelota;

public class EstrategiaColisionPelotaPaleta implements EstrategiaColision{


    @Override
    public void manejarColision(ObjetoJuego obj1, ObjetoJuego obj2) {
        // aqui va su codigo
    }

    @Override
    public boolean verificarColision(ObjetoJuego obj1, ObjetoJuego obj2) {
        // aqui va su codigo
        return false;
    }
    
    private double calcularAnguloRebote(Pelota pelota, Paleta paleta){
        //Aqui va su codigo
        return 0;
    }
}
