package main.java.modelo.strategy.estrategiaColision.gestorColisiones;

import java.util.Map;

import main.java.modelo.strategy.estrategiaColision.estrategias.EstrategiaColision;

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
