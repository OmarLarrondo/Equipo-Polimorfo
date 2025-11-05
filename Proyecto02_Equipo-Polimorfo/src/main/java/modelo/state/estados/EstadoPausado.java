package modelo.state.estados;

import java.awt.event.InputEvent;
import modelo.state.contexto.ContextoJuego;

public class EstadoPausado implements EstadoJuego{

    private EstadoJuego estadoPrevio;
    
    public EstadoPausado(EstadoJuego estadoPrevio) {
        this.estadoPrevio = estadoPrevio;
    }

    @Override
    public void entrar(ContextoJuego contexto) {
        // aqui va su codigo 

    }

    @Override
    public void actualizar(double tiempoDelta) {
        // aqui va su codigo 

    }

    @Override
    public void salir(ContextoJuego contexto) {
        // aqui va su codigo 

    }

    @Override
    public void manejarEntrada(InputEvent entrada) {
        // aqui va su codigo 

    }
    
}
