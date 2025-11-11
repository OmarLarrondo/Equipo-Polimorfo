package patrones.strategy.colision;

import java.util.Map;

import mvc.modelo.ModeloJuego;
import mvc.modelo.entidades.ObjetoJuego;
import patrones.strategy.colision.EstrategiaColision;

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
