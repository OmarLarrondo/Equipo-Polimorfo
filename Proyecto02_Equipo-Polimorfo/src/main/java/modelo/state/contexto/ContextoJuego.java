package modelo.state.contexto;

import java.awt.event.InputEvent;
import modelo.state.estados.EstadoJuego;
import modelo.ModeloJuego;

public class ContextoJuego {
    private EstadoJuego estadoActual;
    private ModeloJuego modeloJuego;

    public ContextoJuego(EstadoJuego estadoActual, ModeloJuego modeloJuego) {
        this.estadoActual = estadoActual;
        this.modeloJuego = modeloJuego;
    }
    public void establecerEstado(EstadoJuego estado){
        //Aqui va su codigo 
    }
    public EstadoJuego obtenerEstado(){
        //aqui va su codigo
        return null;
    } 
    public void actualizar(double tiempoDelta){
        //aqui va su codigo 
    }
    public void  manejarEnrada(InputEvent entrada){
        //aqui va su codigo
    }
    

    
}
