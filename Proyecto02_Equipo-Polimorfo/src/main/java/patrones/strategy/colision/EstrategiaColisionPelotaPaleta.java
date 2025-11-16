package patrones.strategy.colision;

import mvc.modelo.entidades.ObjetoJuego;
import mvc.modelo.entidades.paleta.Paleta;
import mvc.modelo.entidades.pelota.Pelota;

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
