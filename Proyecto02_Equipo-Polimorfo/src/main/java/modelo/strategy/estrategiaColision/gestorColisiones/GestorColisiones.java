package modelo.strategy.estrategiaColision.gestorColisiones;

import java.util.Map;
import modelo.strategy.estrategiaColision.estrategias.EstrategiaColision;
import modelo.ModeloJuego;
import modelo.ObjetoJuego;

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
