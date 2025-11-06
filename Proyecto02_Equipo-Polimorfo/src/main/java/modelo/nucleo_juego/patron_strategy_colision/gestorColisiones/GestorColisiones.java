package modelo.nucleo_juego.patron_strategy_colision.gestorColisiones;

import java.util.Map;

import modelo.ModeloJuego;
import modelo.nucleo_juego.patron_composite.ObjetoJuego;
import modelo.nucleo_juego.patron_strategy_colision.estrategias.EstrategiaColision;

public class GestorColisiones {
    private Map<String, EstrategiaColision> estrategias;

    public GestorColisiones(Map<String, EstrategiaColision> estrategias) {
        this.estrategias = estrategias;
    }

    public void registrarEstrategia(String clave, EstrategiaColision estrategia){
        //aqui va su codigo 
    }
    public void verificarTodasColisiones(ModeloJuego modeloJuego){
        //aqui va su codigo
    }

    public void manejarColision(ObjetoJuego obj1, ObjetoJuego obj2){
        //aqui va su codigo
    }
    
}
