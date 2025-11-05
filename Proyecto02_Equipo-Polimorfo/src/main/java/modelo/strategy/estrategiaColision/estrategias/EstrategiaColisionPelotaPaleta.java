package modelo.strategy.estrategiaColision.estrategias;

import modelo.ObjetoJuego;
import modelo.Pelota;
import modelo.Paleta;

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
