package modelo.nucleo_juego.patron_strategy_colision.estrategias;

import modelo.nucleo_juego.patron_composite.ObjetoJuego;
import modelo.nucleo_juego.patron_composite.Pelota;
import modelo.nucleo_juego.patron_composite.bloque.TemplateBloque;

public class EstrategiaColisionPelotaBloque implements EstrategiaColision{

    public EstrategiaColisionPelotaBloque(){

    }
    
    public static void manejarColision(ObjetoJuego obj1, Pelota pelota) {
        // colisión con pelota  
    }

    public static boolean verificarColision(ObjetoJuego obj1, ObjetoJuego obj2) {
        // aqui va su codigo
        return false;
    }
    private void generarItem(TemplateBloque bloque){
        //aqui va su codigo 
    }
}
